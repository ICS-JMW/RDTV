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

import jmw.rdtv.Medium.EventMedium;
import jmw.rdtv.Medium.ImageMedium;
import jmw.rdtv.Medium.Medium;
import jmw.rdtv.Model.Images;
import jmw.rdtv.Model.Submission;

/**
 *
 * @author hhwl
 */
@SpringBootApplication
@RestController
public class RdtvApplication {

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
//        java.awt.EventQueue.invokeLater(() -> {
//            new jmw.rdtv.tvapp.GUI().setVisible(true);
//        });
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
     * @param media contains information on the media. Passed by the website
     * (upload.html)
     * @param file multipartfile containing image/video/exe etc.
     * @param model idk what this does
     * @return currently retursn to the upload webpage, perhaps in the future an
     * indicator for success will be indicated
     *
     */
    @RequestMapping(path = "/upload", method = RequestMethod.POST)
    public ModelAndView uploadSubmission(@ModelAttribute ImageMedium media, @RequestParam("file") MultipartFile file, Model model) {
        model.addAttribute("file", file);
        //get type of the file
        String fileType = file.getContentType().substring(file.getContentType().lastIndexOf("/") + 1);
        //load media media log
        File mediaLog = new File(LOGGING_LOCATION + "/storage/media.bf");
        try {
            //get number of lines in the log file
            long lines = Files.lines(Paths.get(mediaLog.getAbsolutePath())).count();
            //store the information onto the log. The file name will be line + filetype
            //append media to media log using tostring
            //create image
            File img = new File(LOGGING_LOCATION + "/storage/" + lines + "." + fileType);
            try (FileWriter logger = new FileWriter(mediaLog, true)) {
                logger.append(media + Medium.DELIMITER + " " + lines + "." + fileType + "\n");
                // logger.append(media + "\n");
                // logger.close();
            }
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
        return generateMediaCard(media, model);
    }

    /**
     * @param event
     * @param model
     * @return
     */
    @RequestMapping(path = "/event", method = RequestMethod.POST)
    public ModelAndView addEvent(@ModelAttribute EventMedium event, Model model) {
        File eventLog = new File(LOGGING_LOCATION + "/storage/events.bf");
        try {
            //get number of lines in event file
            long lines = Files.lines(Paths.get(eventLog.getAbsolutePath())).count();
            //make filewriter that appends to the log
            //append the current event to the file
            //write to disk
        } catch (Exception e) {
            try (FileWriter logger = new FileWriter(eventLog, true)) {
                logger.append(event + "\n");
            }
        } catch (IOException e) {
        }
        return eventPage(model);
    }

    // @RequestMapping(path = "/getImages/{id}", method = RequestMethod.GET)
    // public ResponseEntity<InputStreamResource> getImageDynamicType(long id) {
    //     InputStream in = getClass.getResourceAsStream(STORAGE_LOCATION + "/storage/");
    //     return ResponseEntity.ok()
    //             .body(new InputStreamResource(in));
    // }
    /**
     * Not implemented yet, will probably be multiple seperate methods.
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
     * inverse of toString basically
     *
     * @param image stringified version of theimage
     * @return imagemedium based on string provided.
     */
    public ImageMedium parseImage(String image) {
        ImageMedium imageMedium = new ImageMedium();
        String[] seperated = new String[6];
        //split the string based on the delimiter defined in medium.java
        seperated = image.split(Medium.DELIMITER);
        //add to blank image medium
        imageMedium.setApproved(seperated[0].charAt(0));
        imageMedium.setName(seperated[1].trim());
        imageMedium.setDescription(seperated[2].trim());
        imageMedium.setSubmitTime(seperated[3].trim());
        imageMedium.setEnd(seperated[4].trim());
        imageMedium.setFileName(seperated[5].trim());
        return imageMedium;
    }

    public ModelAndView generateMediaCard(ImageMedium image, Model model) {
        //make a new resource "image" in the server that can the website can pass information into
        model.addAttribute("image", image);
        System.out.println(image);
        ModelAndView card = new ModelAndView("mediaCard.html");
        return card;
    }

    @RequestMapping(path = "/event", method = RequestMethod.GET)
    public ModelAndView eventPage(Model model) {
        model.addAttribute("event", new EventMedium());
        ModelAndView ret = new ModelAndView();
        ret.setViewName("event.html");
        return ret;
    }

    @RequestMapping(path = "/upload", method = RequestMethod.GET)
    public ModelAndView uploadPage(Model model) {
        model.addAttribute("image", new ImageMedium());
        ModelAndView ret = new ModelAndView();
        ret.setViewName("upload.html");
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

//    /**
//     *
//     * @param image
//     * @param model
//     * @param media
//     * @return
//     */
//    public ModelAndView generateMediaCard(ImageMedium image, Model model, MultipartFile media) {
//        model.addAttribute("image", image);
//        model.addAttribute("media", media);
//        ModelAndView card = new ModelAndView("mediaCard.html");
//        return card;
//    }
//
//    @RequestMapping(path = "/upload", method = RequestMethod.GET)
//    public ModelAndView uploadPage(Model model) {
//        model.addAttribute("image", new ImageMedium());
//        ModelAndView ret = new ModelAndView();
//        ret.setViewName("upload.html");
//        return ret;
//    }
}
