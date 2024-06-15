/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package jmw.rdtv.tvapp;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author hhwl
 */
public class TextsPanel extends javax.swing.JPanel {

    BufferedImage rdtvLogo;
    BufferedImage csdLogo;

    /**
     * Creates new form TextsPanel
     */
    public TextsPanel() {
        initComponents();
        // read logo and load font
        try {
            rdtvLogo = ImageIO.read(new File("./logos/rdtvLogo.png"));
            csdLogo = ImageIO.read(new File("./logos/csdLogo.png"));
            InputStream is = new BufferedInputStream(Files.newInputStream(Paths.get("./VCR OSD Mono.ttf")));
            subs.setFont(Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(Font.PLAIN, 46));
        } catch (IOException | FontFormatException ex) {
            Logger.getLogger(TextsPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @param g
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // draw logos
        g.drawImage(rdtvLogo, 1478, 140, 200, 120, this);
        g.drawImage(csdLogo, 0, 100, 200, 200, this);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        headline = new javax.swing.JLabel();
        scroller = new javax.swing.JScrollPane();
        subs = new javax.swing.JTextArea();

        setBackground(new java.awt.Color(255, 255, 255));
        setForeground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(1678, 300));

        headline.setBackground(new java.awt.Color(255, 255, 255));
        headline.setFont(new java.awt.Font("Cantarell", 0, 72)); // NOI18N
        headline.setForeground(new java.awt.Color(0, 0, 0));
        headline.setText("Breaking: Spy Cows Explode In D.C.");
        headline.setBorder(javax.swing.BorderFactory.createEmptyBorder());

        scroller.setBackground(new java.awt.Color(0, 0, 0));
        scroller.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        scroller.setForeground(new java.awt.Color(0, 0, 0));
        scroller.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroller.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        subs.setEditable(false);
        subs.setBackground(new java.awt.Color(0, 0, 0));
        subs.setColumns(20);
        subs.setFont(new java.awt.Font("VCR OSD Mono", 0, 46)); // NOI18N
        subs.setForeground(new java.awt.Color(255, 255, 255));
        subs.setRows(5);
        subs.setText("This line can hold 64 characters!!!\nThis line can hold 64 characters!!!");
        subs.setWrapStyleWord(true);
        subs.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        subs.setFocusable(false);
        subs.setName(""); // NOI18N
        subs.setVerifyInputWhenFocusTarget(false);
        scroller.setViewportView(subs);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(236, 236, 236)
                .addComponent(headline)
                .addGap(0, 319, Short.MAX_VALUE))
            .addComponent(scroller, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(scroller, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53)
                .addComponent(headline)
                .addContainerGap(60, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel headline;
    private javax.swing.JScrollPane scroller;
    private javax.swing.JTextArea subs;
    // End of variables declaration//GEN-END:variables
}
