package jmw.rdtv;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
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

import jmw.rdtv.Medium.EventMedium;
import jmw.rdtv.Medium.ImageMedium;
import jmw.rdtv.Medium.Medium;

@SpringBootApplication
@RestController
public class RdtvApplication {

    private static final String STORAGE_LOCATION = "src/main/resources";

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
     * @param media contains information on the media. Passed by the website
     * (upload.html)
     * @param file multipartfile containing image/video/exe etc.
     * @param model idk what this does
     * @return currently retursn to the upload webpage, perhaps in the future an
     * indicator for success will be indicated
     *
     */
    @RequestMapping(path = "/upload", method = RequestMethod.POST)
    public ModelAndView uploadImages(@ModelAttribute ImageMedium media, @RequestParam("file") MultipartFile file, Model model) {
        model.addAttribute("file", file);
        //get type of the file
        String fileType = file.getContentType().substring(file.getContentType().lastIndexOf("/") + 1);
        //load media media log
        File mediaLog = new File(STORAGE_LOCATION + "/storage/media.bf");
        try {
            //get number of lines in the log file
            long lines = Files.lines(Paths.get(mediaLog.getAbsolutePath())).count();
            FileWriter logger = new FileWriter(mediaLog, true);
            //store the information onto the log. The file name will be line + filetype
            media.setFileName(lines + "." + fileType);
            //append media to media log using tostring
            logger.append(media + "\n");
            logger.close();
            //create image
            File img = new File(STORAGE_LOCATION + "/storage/" + lines + "." + fileType);
            img.createNewFile();
            OutputStream os = new FileOutputStream(img);
            os.write(file.getBytes());
            os.close();
        } catch (Exception e) {
            return new ModelAndView("epic fail");
            // System.out.println(e);
        }
        return generateMediaCard(media, model);
    }

    /**
     * allows uploading events
     */
    @RequestMapping(path = "/event", method = RequestMethod.POST)
    public ModelAndView addEvent(@ModelAttribute EventMedium event, Model model) {
        File eventLog = new File(STORAGE_LOCATION + "/storage/events.bf");
        try {
            //get number of lines in event file
            long lines = Files.lines(Paths.get(eventLog.getAbsolutePath())).count();
            //make filewriter that appends to the log
            FileWriter logger = new FileWriter(eventLog, true);
            //append the current event to the file
            logger.append(event + "\n");
            //write to disk
            logger.close();
        } catch (Exception e) {
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

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public ModelAndView homePage() {
        ModelAndView ret = new ModelAndView();
        ret.setViewName("mainPage.html");
        return ret;
    }

    //will probably be a login page
    @RequestMapping(path = "/admin", method = RequestMethod.GET)
    public ModelAndView adminPage() {
        ModelAndView ret = new ModelAndView();
        ret.setViewName("adminPanel.html");
        return ret;
    }

    @RequestMapping(path = "/admin/events", method = RequestMethod.GET)
    public ModelAndView adminEventView() {
        ModelAndView ret = new ModelAndView();
        ret.setViewName("adminPanel.html");
        return ret;
    }

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
}
