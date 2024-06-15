package jmw.rdtv.tvapp;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.Timer;
import jmw.rdtv.Model.Submission;
import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent;

/**
 * Main windows class
 *
 * @author hhwl
 */
public final class GUI extends javax.swing.JFrame {

    // constants
    public static final int LINE_DELAY = 2000;

    // singletons
    private static final ArrayList<Submission> data = Submission.readSubmissionsFile(); // list of submissions
    private static final Texts texts = new Texts();
    private static final MediaPopup popup = new MediaPopup();
    private static final EmbeddedMediaPlayerComponent vlcj = new EmbeddedMediaPlayerComponent();
    private static final Timer lineTimer = new Timer(LINE_DELAY, new ActionListener() {
        lineHandle();
    });
    private static final Timer videoTimer = new Timer(10000, new ActionListener() { // dummy delay value
        videoEndHandle();
    });
    private static final Timer imageTimer = new Timer(10000, new ActionListener() { // dummy delay value
        imageHandle();
    });
    private static final Timer screensaverTimer = new Timer(2 * 60 * 1000, new ActionListener() {
        endScreensaver();
    });

    // mutables
    int currentSubmission = 0;

    /**
     * Creates new form GUI
     */
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
        mediaTimer.setRepeats(true);
        presentSubmission(currentSubmission);
    }

    public void presentSubmission(int index) {
        Submission sm = data.get(index);
        if (sm.getMedia() instanceof Video) {

            videoTimer.setDelay();
        } else if (sm.getMedia() instanceof Images) {

        }
    }

    public void prepareVideo(String fileLocation) {

    }

    public void lineHandle() {

    }

    public void videoEndHandle() {

    }

    public void imageHandle() {

    }

    public void startScreensaver() {
        Runtime.getRuntime().exec("xscreensaver-command -activate");
        screensaverTimer.start();
    }

    public void endScreensaver() {
        Runtime.getRuntime().exec("xscreensaver-command -deactivate");
        screensaverTimer.stop();
        currentSubmission = 0;
        presentSubmission(currentSubmission);
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
        popup.vlcj.release();
    }//GEN-LAST:event_formWindowClosing
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
