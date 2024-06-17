package jmw.rdtv;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import jmw.rdtv.Model.ImageBin;
import jmw.rdtv.Model.Submission;

/**
 *
 * @author Jonathan
 */
@SpringBootApplication
@EnableWebMvc
@RestController
public class RdtvApplication {

    private static final String DELIMITER = "/";
    private static final String LOGGING_LOCATION = "src/main/resources";
    private static final Set<String> ALLOWED_TYPES = Set.of("jpg", "png", "mp4", "gif", "webm", "mkv", "mov", "m4v", "avi");
    private static ArrayList<Submission> allSubmissions = null;
//    private static final String DATA_JSON = "./data.json";

    /**
     * @author William
     * @param args
     */
    public static void main(String[] args) {
        allSubmissions = Submission.readSubmissionsFile();

        SpringApplication.run(RdtvApplication.class, args);
        System.setProperty("java.awt.headless", "false");
        try {
            UIManager.setLookAndFeel("com.formdev.flatlaf.FlatLightLaf");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(RdtvApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(() -> {
            new jmw.rdtv.tvapp.GUI().setVisible(true);
        });
    }
    //
    //api
    //

    /**
     * Method for supporting "post" method of /upload creates new file with the
     * name of the current number of lines in "Logs.bf".
     *
     * @Author Jonathan
     *
     * @param submission contains information on the media. Passed by the
     * website (upload.html)
     * @param file multipartfile containing image/video/exe etc.
     * @param model idk what this does
     * @return currently retursn to the upload webpage, perhaps in the future an
     * indicator for success will be indicated
     *
     */
    public String uploadSubmission(@ModelAttribute Submission submission, @RequestParam("file") MultipartFile file, Model model) {
        // get file from model
        model.addAttribute("file", file);
        // get file type and extension
        String contentType = file.getContentType();
        if (contentType == null) {
            System.out.println("Content type null!!!");
            return "empty Submission <div><a href='./upload'>Submit another</a></div>";
        }
        String fileExt = contentType.substring(contentType.lastIndexOf("/") + 1);
        //mkv is converted into x - matroska
        //may be some other unsupported formats
        if (fileExt.equals("x-matroska")) {
            fileExt = "mkv";
        }
        System.out.println(fileExt);
        //check if file type allowed
        if (!ALLOWED_TYPES.contains(fileExt)) {
            return "Content type not supported <div><a href='./upload'>Submit another</a></div>";
        }
        // add logging
        File mediaLog = new File(LOGGING_LOCATION + "/storage/media.bf");
        long lines = 0;
        try {
            // get unique file name by counting log lines
            lines = Files.lines(Paths.get(mediaLog.getAbsolutePath())).count();
            try (FileWriter logger = new FileWriter(mediaLog, true)) { // write to log file
                logger.append(submission + DELIMITER + " " + lines + "." + fileExt + "\n");
            }
            //check if file is allowed type
            File img = new File("./media/" + lines + "." + fileExt);
            //transforms from date and time to unix time
            //current method of getting time and date do not have seconds timestamps so they need to be added
            //Z is needed at the end
            final String tweak = ":00Z";
            submission.setStart(Instant.parse(submission.getStart() + tweak).getEpochSecond() + "");
            submission.setEnd(Instant.parse(submission.getEnd() + tweak).getEpochSecond() + "");
            submission.setApproved(false);
            submission.setId(lines);
            ImageBin temp = new ImageBin();
            temp.setType(contentType.substring(0, contentType.lastIndexOf("/") + 0));
            temp.addImage(img.getPath());
            submission.setMedia(temp);
            //append to the arraylist (all)
            //rewrite to arraylist
            //yes this is very inefficient but it's better than writing code that doesn't work
            allSubmissions.add(submission);
            Submission.writeSubmissionsFile(allSubmissions);
            // can only handle single images for now
            // File img = new File("test." + fileType);
            //create file with contents of "file" byte at "img"
            img.createNewFile();
            try (OutputStream os = new FileOutputStream(img)) {
                os.write(file.getBytes());
            }
        } catch (IOException e) {
            System.out.println(e);
        }
        // System.out.println(fileType);
        // System.out.println(image);
        // return uploadPage(model);
        return "Success! <div><a href='./upload'>Submit another</a></div>";
    }

    /**
     * Method allowing acceptance of posts.
     *
     * @author Jonathan
     * @param id
     * @return
     */
    @RequestMapping(path = "/adminPanel/accept/{id}", method = RequestMethod.POST)
    public String acceptSubmission(@PathVariable Long id) {
        allSubmissions.get(Math.toIntExact(id)).setApproved(true);
        Submission.writeSubmissionsFile(allSubmissions);
        return "redirect:/adminPanel";
    }

    /**
     * Method allowing rejection of post
     *
     * @author Jonathan
     * @param id
     * @return
     */
    @RequestMapping(path = "/adminPanel/deny/{id}", method = RequestMethod.POST)
    public String rejectSubmission(@PathVariable Long id) {
        allSubmissions.get(Math.toIntExact(id)).setHidden(true);
        Submission.writeSubmissionsFile(allSubmissions);
        return "redirect:/adminPanel";
    }

    //
    //end of api
    //
    //get function
    /**
     * main page
     *
     * @author jonathan
     * @return
     */
    @RequestMapping(path = "/", method = RequestMethod.GET)
    public ModelAndView homePage() {
        ModelAndView ret = new ModelAndView();
        ret.setViewName("mainPage.html");
        return ret;
    }

    /**
     * unsupported method
     *
     * @return
     */
    @RequestMapping(path = "/adminLogin", method = RequestMethod.GET)
    public ModelAndView adminPage() {
        ModelAndView ret = new ModelAndView();
        ret.setViewName("adminLogin.html");
        return ret;
    }

    /**
     * place to review and accept/reject submissions. Logging in is not
     * implemented so everyone can submit
     *
     * @author Jonathan
     * @param submission
     * @param model
     * @return
     */
    @RequestMapping(path = "/adminPanel", method = RequestMethod.GET)
    public ModelAndView adminPanel(Model model) {
        //make a new resource "submission" in the server that can the website can pass information into
        ArrayList<Submission> unapproved = new ArrayList<>();
        for (Submission x : Submission.readSubmissionsFile()) {
            if (!x.isApproved() && !x.isHidden()) {
                unapproved.add(x);
            }
        }
        model.addAttribute("submissions", unapproved);
        ModelAndView card = new ModelAndView("mediaCard.html");
        return card;
    }

    /**
     * @author jonathan
     * @param model
     * @return
     */
    @RequestMapping(path = "/upload", method = RequestMethod.GET)
    public ModelAndView uploadPage(Model model) {
        model.addAttribute("submission", new Submission());
        ModelAndView ret = new ModelAndView();
        ret.setViewName("upload.html");
        return ret;
    }
}
