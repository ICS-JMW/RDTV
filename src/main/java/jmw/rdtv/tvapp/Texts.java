/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package jmw.rdtv.tvapp;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import jmw.rdtv.Model.Submission;

/**
 *
 * @author William
 */
public class Texts extends javax.swing.JFrame {

    /**
     * Creates new form NewJFrame
     */
    public Texts() {
        super();
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel = new jmw.rdtv.tvapp.TextsPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);
        setBackground(new java.awt.Color(255, 255, 255));
        setLocation(new java.awt.Point(0, 780));
        setUndecorated(true);
        setType(java.awt.Window.Type.UTILITY);
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.LINE_AXIS));

        panel.setMinimumSize(new java.awt.Dimension(1728, 300));
        getContentPane().add(panel);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     *
     * @return
     */
    public TextsPanel getPanel() {
        return panel;
    }

    /**
     *
     * @param panel
     */
    public void setPanel(TextsPanel panel) {
        this.panel = panel;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private jmw.rdtv.tvapp.TextsPanel panel;
    // End of variables declaration//GEN-END:variables
}
