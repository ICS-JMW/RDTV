//GEN-LINE:variables
package jmw.rdtv.tvapp;

import jmw.rdtv.Model.Submission;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;
import java.awt.image.*;
import javax.imageio.*;

/**
 *
 * @author hhwl
 */
public final class GUI extends javax.swing.JFrame {

    private static int current = 1;
    private static int submissions = 0;
    public static ArrayList<Submission> media = new ArrayList();
    private ArrayList<BufferedImage> images = new ArrayList();
    private int msPassed = 0;
//    private Timer timer;
    private ActionListener display;
    private int runtime = 300;

    private Screen vlcj = new Screen();
    int realWidth = 1920;
    int realHeight = 1080;

    /**
     * Creates new form GUI
     */
    public GUI() {
        initComponents(); // auto create components
        // maximize window
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        // set up vlcj
        add(vlcj);
        setVisible(true);
        vlcj.mediaPlayer().media().play("./b-roll.mp4");
        vlcj.mediaPlayer().controls().setRepeat(true);

        realWidth = getWidth();
        realHeight = getHeight();
        Texts texts = new Texts();
        texts.setAlwaysOnTop(true);
        texts.setLocation(realWidth - 1000, realHeight-200);

        MediaPopup popup = new MediaPopup();
        popup.setAlwaysOnTop(true);

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
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
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
    }// </editor-fold>                        

    private void formWindowClosing(java.awt.event.WindowEvent evt) {                                   
        vlcj.release();
        System.exit(0);
    }                                  
    // Variables declaration - do not modify                     
    // End of variables declaration                   
    /**
     * @return the submissions
     */
    public static int getSubmissions() {
        return submissions;
    }

//    public ArrayList<Submission> readMedia() {
//        Scanner sc;
//        ArrayList<Submission> mList = new ArrayList();
//
//        try {
//            sc = new Scanner(new File("items.json"));
//            while (sc.hasNextLine() && !sc.nextLine().equals("}]")) {
//                Submission m = new Submission();
//                m.setName(sc.nextLine());
//                m.setName(m.getName().substring(13, m.getName().length() - 2));
//                m.setDescription(sc.nextLine());
//                m.setDescription(m.getDescription().substring(20, m.getDescription().length() - 2));
//                m.setBegin(sc.nextLine());
//                m.setEnd(sc.nextLine());
//                m.setFileName(sc.nextLine());
//                m.setFileName(m.getFileName().substring(17, m.getFileName().length() - 1));
//                images.add(ImageIO.read(new File("media/" + m.getFileName())));
//                m.setPriority(runtime);
//                runtime += 3000;
//                mList.add(m);
//
//                submissions++;
//            }
//        } catch (IOException e) {
//            System.out.println("broken");
//        }
//
//        return mList;
//    }
}
