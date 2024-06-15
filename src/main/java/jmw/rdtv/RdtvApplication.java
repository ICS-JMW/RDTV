package jmw.rdtv;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
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
 * @author hhwl
 */
@SpringBootApplication
@RestController
public class RdtvApplication {

    private static final String DELIMITER = "/";
    private static final String LOGGING_LOCATION = "src/main/resources";
    private static final String DATA_JSON = "./data.json";
    ObjectMapper mapper = new ObjectMapper();

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
     * @param submission contains information on the media.
     * @param file
     * @param model
     * @return
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

// ??? I thought events and submissions were the same?
//    /**
//     *
//     * @param event
//     * @param model
//     * @return
//     */
//    @RequestMapping(path = "/event", method = RequestMethod.POST)
//    public ModelAndView addEvent(@ModelAttribute EventMedium event, Model model) {
//        File eventLog = new File(LOGGING_LOCATION + "/storage/events.bf");
//        try {
//            long lines = Files.lines(Paths.get(eventLog.getAbsolutePath())).count();
//            try (FileWriter logger = new FileWriter(eventLog, true)) {
//                logger.append(event + "\n");
//            }
//        } catch (IOException e) {
//        }
//        return eventPage(model);
//    }
    /**
     * unfinished admin panel
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
    //
    //api
    //
//	@RequestMapping(path = "/Upload", method=RequestMethod.POST)
//	public ModelAndView UploadImages(@RequestBody ){
//		ModelAndView ret = new ModelAndView();
//		ret.setViewName("mainPage.html");
//		return ret;
//	}
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

//    /**
//     *
//     * @param image
//     * @return
//     */
//    public ImageMedium parseImage(String image) {
//        ImageMedium imageMedium = new ImageMedium();
//        String[] seperated = image.split(Medium.DELIMITER);
//        imageMedium.setApproved(seperated[0].charAt(0));
//        imageMedium.setName(seperated[1].trim());
//        imageMedium.setDescription(seperated[2].trim());
//        imageMedium.setSubmitTime(seperated[3].trim());
//        imageMedium.setEnd(seperated[4].trim());
//        return imageMedium;
//    }
    public ArrayList<Submission> parseSubmissions() {
        try (Scanner s = new Scanner(new File(DATA_JSON))) {
            String json = "";
            while (s.hasNextLine()) {
                json += s.nextLine();
            }
            s.close();
            return mapper.readValue(json, new TypeReference<ArrayList<Submission>>() {
            });
        } catch (FileNotFoundException ex) {
            Logger.getLogger(RdtvApplication.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Data.json not found");
            return null;
        } catch (JsonProcessingException ex) {
            Logger.getLogger(RdtvApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     *
     * @param image
     * @param model
     * @param media
     * @return
     */
    public ModelAndView generateMediaCard(Submission image, Model model, MultipartFile media) {
        model.addAttribute("image", image);
        model.addAttribute("media", media);
        ModelAndView card = new ModelAndView("mediaCard.html");
        return card;
    }

//    @RequestMapping(path = "/upload", method = RequestMethod.GET)
//    public ModelAndView uploadPage(Model model) {
//        model.addAttribute("image", new ImageMedium());
//        ModelAndView ret = new ModelAndView();
//        ret.setViewName("upload.html");
//        return ret;
//    }
}
