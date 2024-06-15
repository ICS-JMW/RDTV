package jmw.rdtv;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import jmw.rdtv.Model.Submission;

/**
 *
 * @author Johnathan, William
 */
@SpringBootApplication
@RestController
public class RdtvApplication {

    private static final String DELIMITER = "/";
    private static final String LOGGING_LOCATION = "src/main/resources";
//    private static final String DATA_JSON = "./data.json";

    /**
     *
     * @param args
     */
    public static void main(String[] args) {

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
     * name of the current number of lines in "Logs.bf". As of now, all formats
     * can be uploaded, ranging from images to weird things things like
     * executables.
     *
     * @param submission contains information on the media. Passed by the
     * website (upload.html)
     * @param file multipartfile containing image/video/exe etc.
     * @param model idk what this does
     * @return currently retursn to the upload webpage, perhaps in the future an
     * indicator for success will be indicated
     *
     */
    @RequestMapping(path = "/upload", method = RequestMethod.POST)
    public ModelAndView uploadSubmission(@ModelAttribute Submission submission, @RequestParam("file") MultipartFile file, Model model) {
        // get file from model
        model.addAttribute("file", file);
        // get file type and extension
        String contentType = file.getContentType();
        if (contentType == null) {
            System.out.println("Content type null!!!");
            return null;
        }
        String fileExt = contentType.substring(contentType.lastIndexOf("/") + 1);
        // add logging
        File mediaLog = new File(LOGGING_LOCATION + "/storage/media.bf");
        try {
            // get unique file name by counting log lines???
            long lines = Files.lines(Paths.get(mediaLog.getAbsolutePath())).count();
            try (FileWriter logger = new FileWriter(mediaLog, true)) { // write to log file
                logger.append(submission + DELIMITER + " " + lines + "." + fileExt + "\n");
            }

            // can only handle single images for now
            File img = new File("./media/" + lines + "." + fileExt);
            // File img = new File("test." + fileType);
            // System.out.println(img.getAbsolutePath());
            img.createNewFile();
            try (OutputStream os = new FileOutputStream(img)) {
                os.write(file.getBytes());
            }
        } catch (IOException e) {
            return new ModelAndView("epic fail");
            // System.out.println(e);
        }
        // System.out.println(fileType);
        // System.out.println(image);
        // return uploadPage(model);
        return generateMediaCard(submission, model, file);
    }

    /**
     * Not implemented yet, will probably be multiple separate methods.
     *
     * ======= /**
     *
     * @return
     */
    @RequestMapping(path = "/admin", method = RequestMethod.POST)
    public boolean modifyThing() {
        return true;
    }

    //
    //end of api
    //
    //get function
    /**
     *
     * @return
     */
    @RequestMapping(path = "/", method = RequestMethod.GET)
    public ModelAndView homePage() {
        ModelAndView ret = new ModelAndView();
        ret.setViewName("mainPage.html");
        return ret;
    }

    //will probably be a login page
    /**
     *
     * @return
     */
    @RequestMapping(path = "/admin", method = RequestMethod.GET)

    public ModelAndView adminPage() {
        ModelAndView ret = new ModelAndView();
        ret.setViewName("adminPanel.html");
        return ret;
    }

    /**
     *
     * @return
     */
    @RequestMapping(path = "/admin/events", method = RequestMethod.GET)
    public ModelAndView adminEventView() {
        ModelAndView ret = new ModelAndView();
        ret.setViewName("adminPanel.html");
        return ret;
    }

    /**
     *
     * @return
     */
    @RequestMapping(path = "/admin/media", method = RequestMethod.GET)
    public ModelAndView adminMediaView() {
        ModelAndView ret = new ModelAndView();
        ret.setViewName("adminPanel.html");
        return ret;
    }

    /**
     *
     * @param submission
     * @param model
     * @return
     */
    public ModelAndView generateMediaCard(Submission submission, Model model) {
        //make a new resource "submission" in the server that can the website can pass information into
        model.addAttribute("submission", submission);
        System.out.println(submission);
        ModelAndView card = new ModelAndView("mediaCard.html");
        return card;
    }

    /**
     *
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

    /**
     *
     * @param submission
     * @param model
     * @param file
     * @return
     */
    public ModelAndView generateMediaCard(Submission submission, Model model, MultipartFile file) {
        model.addAttribute("submission", submission);
        model.addAttribute("file", file);
        ModelAndView card = new ModelAndView("mediaCard.html");
        return card;
    }
}
