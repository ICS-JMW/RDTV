package jmw.rdtv.tvapp;

import jmw.rdtv.Model.Submission;
import java.util.*;
import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent;

/**
 * Main windows class
 *
 * @author hhwl
 */
public final class GUI extends javax.swing.JFrame {

    /**
     *
     */
    public static ArrayList<Submission> Submissions = new ArrayList(); // list of submissions

    // Max's code
//    private static int current = 1; // current submission index
//    private int msPassed = 0;
//    private Timer timer;
//    private ActionListener display;
//    private int runtime = 300;
    // video player component
    private final EmbeddedMediaPlayerComponent vlcj = new EmbeddedMediaPlayerComponent();

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

        Texts texts = new Texts();
        texts.setVisible(true);

        MediaPopup popup = new MediaPopup();
        popup.setVisible(true);

//        media = readMedia();
//        display = new ActionListener() {
//            public void actionPerformed(ActionEvent evnt) {
//                current++;
//                if (current >= submissions) {
//                    current = 0;
//                }
//                select(current);
//                System.out.println(media.get(current).getRuntime());
//                timer.setDelay(media.get(current).getRuntime());
//            }
//        };
//        timer = new Timer(media.get(current).getRuntime(), display);
//        timer.setRepeats(true);
//        timer.start();
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
        vlcj.release();
    }//GEN-LAST:event_formWindowClosing
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
