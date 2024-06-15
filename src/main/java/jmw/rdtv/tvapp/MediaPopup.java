/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package jmw.rdtv.tvapp;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent;

/**
 * Class for displaying submission media
 *
 * @author hhwl
 */
public class MediaPopup extends javax.swing.JFrame {

    private EmbeddedMediaPlayerComponent vlcj = new EmbeddedMediaPlayerComponent();
    private JLabel imageContainer = new JLabel();
    private static final int WIN_WIDTH = 640;
    private static final int WIN_HEIGHT = 480;

    /**
     * Creates new form NewJFrame
     */
    public MediaPopup() {
        initComponents();
        setVisible(true);
        this.setSize(WIN_WIDTH, WIN_HEIGHT);
    }

    public void setUpVlcj(String fileLocation) {
        removeAll();
        add(vlcj);
        vlcj.mediaPlayer().media().play(fileLocation);
        vlcj.mediaPlayer().controls().setRepeat(true);
        revalidate();
        repaint();
    }

    public void setUpImages() {
        removeAll();
        add(imageContainer);
        revalidate();
        repaint();
    }

    public void displayImage(String fileLocation) {
        try {
            Image image = ImageIO.read(new File(fileLocation));
            int imageWidth = image.getWidth(rootPane);
            int imageHeight = image.getHeight(rootPane);
            double factor;
            if (imageWidth / (imageHeight + .0) > WIN_WIDTH / (WIN_HEIGHT + .0)) {
                factor = imageWidth / WIN_WIDTH;
            } else {
                factor = imageHeight / WIN_HEIGHT;
            }
            imageContainer.setIcon(new ImageIcon(image.getScaledInstance((int) (WIN_WIDTH * factor), (int) (WIN_HEIGHT * factor), Image.SCALE_FAST)));
        } catch (IOException ex) {
            Logger.getLogger(MediaPopup.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public EmbeddedMediaPlayerComponent getVlcj() {
        return vlcj;
    }

    public void setVlcj(EmbeddedMediaPlayerComponent vlcj) {
        this.vlcj = vlcj;
    }

    public JLabel getImageContainer() {
        return imageContainer;
    }

    public void setImageContainer(JLabel imageContainer) {
        this.imageContainer = imageContainer;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setAlwaysOnTop(true);
        setBackground(new java.awt.Color(0, 0, 0));
        setUndecorated(true);
        setType(java.awt.Window.Type.UTILITY);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.LINE_AXIS));

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // release vlcj on window close
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        evt.getID();
        vlcj.release();
    }//GEN-LAST:event_formWindowClosing

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
