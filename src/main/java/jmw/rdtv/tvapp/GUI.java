package jmw.rdtv.tvapp;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Timer;
import jmw.rdtv.Model.ImageBin;
import jmw.rdtv.Model.Submission;
import jmw.rdtv.Model.Video;
import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent;

/**
 * Main windows class
 *
 * @author hhwl
 */
public final class GUI extends javax.swing.JFrame {

    // constants
    public static final int LINE_DELAY = 2000;
    public static final int LINE_LENGTH = 64;
    public static final int SCREENSAVER_DELAY = 2 * 60 * 1000;

    // objects
    private final ArrayList<Submission> data = Submission.readSubmissionsFile(); // list of submissions
    private final Texts texts = new Texts();
    private final MediaPopup popup = new MediaPopup();
    private final EmbeddedMediaPlayerComponent vlcj = new EmbeddedMediaPlayerComponent();
    private final Timer lineTimer = new Timer(LINE_DELAY, (ActionEvent e) -> {
        lineHandle();
    });
    private final Timer videoTimer = new Timer(10000, (ActionEvent e) -> { // dummy delay
        videoEndHandle();
    });
    private final Timer imageTimer = new Timer(10000, (ActionEvent e) -> { // dummy delay
        imageHandle();
    });
    private final Timer screensaverTimer = new Timer(SCREENSAVER_DELAY, (ActionEvent e) -> {
        videoEndHandle();
    });

    // mutables
    int submissionIndex = -1;
    int imageIndex = -1;
    int descBlock = -1;
    int numDescBlocks = 0;

    public GUI() {
        // netbeans setup components
        initComponents();
        // set up vlcj
        add(vlcj);
        setVisible(true);
        vlcj.mediaPlayer().media().play("./b-roll.mp4");
        vlcj.mediaPlayer().controls().setRepeat(true);
        // set up overlays
        texts.setVisible(true);
        popup.setVisible(true);
        // set up timers
        lineTimer.setRepeats(true);
        videoTimer.setRepeats(true);
        imageTimer.setRepeats(true);

        presentSubmission();
    }

    public void presentSubmission() {
        submissionIndex++;
        if (submissionIndex >= data.size()) {
            startScreensaver();
            return;
        }
        Submission sm = data.get(submissionIndex);
        texts.getPanel().getHeadline().setText(sm.getHeadline());
        numDescBlocks = (int) Math.ceil(sm.getDescription().length() / (LINE_LENGTH + .0));
        lineHandle();
        lineTimer.start();
        if (sm.getMedia() instanceof Video) {
            Video video = (Video) (sm.getMedia());
            popup.setUpVlcj(video.getLocation());
            videoTimer.setDelay((int) popup.getVlcj().mediaPlayer().media().info().duration());
            videoTimer.start();
        } else if (sm.getMedia() instanceof ImageBin) {
            ImageBin images = (ImageBin) (sm.getMedia());
            popup.setUpImages();
            imageHandle();
            imageTimer.setDelay(numDescBlocks * LINE_DELAY / images.getImages().size());
            imageTimer.start();
        }
    }

    public void lineHandle() {
        descBlock++;
        String desc = data.get(submissionIndex).getDescription();
        if (descBlock >= numDescBlocks) {
            lineTimer.stop();
            imageTimer.stop();
            videoTimer.stop();
            descBlock = -1;
            presentSubmission();
            return;
        }
        String subDesc = desc.substring(descBlock * LINE_LENGTH, descBlock * (LINE_LENGTH + 1)) + "\n";
        if (descBlock + 1 < numDescBlocks) {
            subDesc += desc.substring(descBlock * (LINE_LENGTH + 1), descBlock * (LINE_LENGTH + 2));
        }
        texts.getPanel().getSubs().setText(subDesc);
    }

    public void videoEndHandle() {
        videoTimer.stop();
    }

    public void imageHandle() {
        imageIndex++;
        ImageBin images = (ImageBin) (data.get(submissionIndex).getMedia());
        if (imageIndex >= images.getImages().size()) {
            imageTimer.stop();
            imageIndex = -1;
        } else {
            popup.displayImage(images.getImages().get(imageIndex));
        }
    }

    public void startScreensaver() {
        try {
            Runtime.getRuntime().exec("xscreensaver-command -activate");
            screensaverTimer.start();
        } catch (IOException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void endScreensaver() {
        try {
            Runtime.getRuntime().exec("xscreensaver-command -deactivate");
            screensaverTimer.stop();
            submissionIndex = -1;
            presentSubmission();
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
        vlcj.release();
        texts.dispose();
        popup.dispose();
        popup.getVlcj().release();
    }//GEN-LAST:event_formWindowClosing
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
