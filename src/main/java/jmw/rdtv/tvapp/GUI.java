package jmw.rdtv.tvapp;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Timer;
import jmw.rdtv.Model.ImageBin;
import jmw.rdtv.Model.Submission;
import jmw.rdtv.Model.Video;
import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent;

/**
 * Main windows class
 *
 * @author William
 * @author Max
 */
public final class GUI extends javax.swing.JFrame {

    // constants
    /**
     *
     */
    public static final int LINE_DELAY = 300; // how long to wait before displaying next subtitle line

    /**
     *
     */
    public static final int LINE_LENGTH = 64; // each line of subtitles can have 64 characters

    /**
     *
     */
    public static final int SCREENSAVER_CYCLES = 2; // cycle through 4 screensavers

    /**
     *
     */
    public static final int SCREENSAVER_DELAY = 5 * 1000; // 15 second delay

    // objects
    private final ArrayList<Submission> data = Submission.readSubmissionsFile(); // list of submissions
    private final Texts texts = new Texts(); // headline and subtitles
    private final MediaPopup popup = new MediaPopup(); // submission media
    private final EmbeddedMediaPlayerComponent vlcj = new EmbeddedMediaPlayerComponent();
    // timers
    private final Timer lineTimer = new Timer(LINE_DELAY, (ActionEvent e) -> {
        lineHandle();
    });
    private final Timer imageTimer = new Timer(10000, (ActionEvent e) -> { // dummy delay
        imageHandle();
    });
    private final Timer screensaverTimer = new Timer(SCREENSAVER_DELAY, (ActionEvent e) -> {
        screensaverHandle();
    });

    // mutables
    int submissionIndex = -1;
    int imageIndex = -1;
    int descBlock = -1;
    int screensaverIndex = -1;
    int numDescBlocks = 0;

    /**
     * @author William
     * @author Max
     *
     * sets up the gui
     */
    public GUI() {
        // netbeans setup components
        initComponents();
        // remove window decorations and add icon
        dispose();
        setUndecorated(true);
        try {
            setIconImage(ImageIO.read(new File("./logos/rdtvLogo.png")));
        } catch (IOException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        // set up vlcj
        add(vlcj);
        setVisible(true);
        vlcj.mediaPlayer().media().play("./b-roll.mp4"); // play mini-RD video
        vlcj.mediaPlayer().controls().setRepeat(true);
        // set up xscreensaver
        try {
            Runtime.getRuntime().exec("xscreensaver &");
        } catch (IOException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        // set up overlays
        texts.setVisible(true);
        popup.setVisible(true);
        // set up timers
        lineTimer.setRepeats(true);
        imageTimer.setRepeats(true);
        // validate submission inputs
        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        Date now = new Date();
        ArrayList<Submission> toRemove = new ArrayList<>();
        for (Submission e : data) {
            try {
                // make sure the current date is within range
                if (parser.parse(e.getStart()).after(now) || parser.parse(e.getEnd()).before(now) || !e.isApproved()) {
                    toRemove.add(e);
                }
            } catch (ParseException ex) {
                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        data.removeAll(toRemove);
        // start presenting
        presentSubmission();
    }

    /**
     * @author William
     * @author Max
     *
     * presents a submission
     */
    public void presentSubmission() {
        submissionIndex++;
        if (submissionIndex >= data.size()) { // start screensaver if no submissions left
            submissionIndex = -1;
            startScreensaver();
            return;
        }
        Submission sm = data.get(submissionIndex);
        // set text window headline as submission headline
        texts.getPanel().getHeadline().setText(sm.getHeadline());
        // calculate how many lines of subtitles we need
        numDescBlocks = (int) Math.ceil(sm.getDescription().length() / (LINE_LENGTH + .0));
        lineHandle(); // display those lines
        lineTimer.start();
        if (sm.getMedia() instanceof Video) {
            Video video = (Video) (sm.getMedia());
            popup.setUpVlcj(video.getLocation()); // show video in mediaPopup
        } else if (sm.getMedia() instanceof ImageBin) {
            ImageBin images = (ImageBin) (sm.getMedia());
            popup.setUpImages();
            imageHandle(); // update images
            // calculate how long each image will be shown given the amount of subtitles
            imageTimer.setDelay(numDescBlocks * LINE_DELAY / images.getImages().size());
            imageTimer.start();
        }
    }

    /**
     * @author Max
     * @author William
     *
     * display a line
     */
    public void lineHandle() {
        descBlock++;
        if (descBlock >= numDescBlocks) { // go to next submission if no more lines to show
            // subtitles actually control everything
            lineTimer.stop();
            imageTimer.stop();
            descBlock = -1;
            imageIndex = -1;
            presentSubmission();
            return;
        }
        String desc = data.get(submissionIndex).getDescription();
        // display only two lines of the description
        String subDesc = desc.substring(descBlock * LINE_LENGTH, Math.min((descBlock + 1) * LINE_LENGTH, desc.length())) + "\n";
        if (descBlock + 1 < numDescBlocks) {
            subDesc += desc.substring((descBlock + 1) * LINE_LENGTH, Math.min((descBlock + 2) * LINE_LENGTH, desc.length()));
        }
        texts.getPanel().getSubs().setText(subDesc);
    }

    /**
     * @author William
     * @author Max
     *
     * display images
     */
    public void imageHandle() {
        imageIndex++;
        ImageBin images = (ImageBin) (data.get(submissionIndex).getMedia());
        if (imageIndex >= images.getImages().size()) { // exit if no more images to show
            imageTimer.stop();
            imageIndex = -1;
        } else {
            popup.displayImage(images.getImages().get(imageIndex));
        }
    }

    /**
     * @author William
     *
     * start screensaver
     */
    public void startScreensaver() {
        try {
            // activates the screensaver
            Runtime.getRuntime().exec("xscreensaver-command -activate");
            screensaverTimer.start();
            screensaverIndex++;
        } catch (IOException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @author William
     *
     * change and end the screensaver
     */
    public void screensaverHandle() {
        screensaverIndex++;
        try {
            // go back to playing submissions if the screensaver has gone through enough cycles
            if (screensaverIndex >= SCREENSAVER_CYCLES) {
                Runtime.getRuntime().exec("xscreensaver-command -deactivate");
                screensaverTimer.stop();
                screensaverIndex = -1;
                presentSubmission();
            } else { // otherwise, go to the next screensaver
                Runtime.getRuntime().exec("xscreensaver-command -cycle");
            }
        } catch (IOException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("RDTV Official");
        setMinimumSize(new java.awt.Dimension(1920, 1080));
        setPreferredSize(new java.awt.Dimension(1920, 1080));
        setSize(new java.awt.Dimension(1920, 1080));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // close vlcj when window closes
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        evt.getID(); // actually does nothing, I just don't like seeing warnings
        // clean up stuff
        vlcj.release();
        texts.dispose();
        popup.dispose();
        popup.getVlcj().release();
    }//GEN-LAST:event_formWindowClosing
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
