package com.ran.acglab1;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class LabFrame extends JFrame {

    private ImagePanel originalImagePanel = new ImagePanel();
    private ImagePanel greyImagePanel = new ImagePanel();
    private ImagePanel mediumImagePanel = new ImagePanel();
    private ImagePanel yannyImagePanel = new ImagePanel();
    private ImagePanel watsuImagePanel = new ImagePanel();
    
    public LabFrame() {
        initComponents();
        
        tabbedPane.addTab("Оригинал", originalImagePanel);
        tabbedPane.addTab("Градации серого", greyImagePanel);
        tabbedPane.addTab("Метод среднего", mediumImagePanel);
        tabbedPane.addTab("Метод Янни", yannyImagePanel);
        tabbedPane.addTab("Метод Оцу", watsuImagePanel);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabbedPane = new javax.swing.JTabbedPane();
        fileBrowser = new com.ran.acglab1.FileBrowser();
        loadButton = new javax.swing.JButton();
        textField = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        loadButton.setText("Load");
        loadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadButtonActionPerformed(evt);
            }
        });

        textField.setEditable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tabbedPane)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(fileBrowser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(loadButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textField, javax.swing.GroupLayout.DEFAULT_SIZE, 525, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(fileBrowser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(loadButton)
                        .addComponent(textField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, 644, Short.MAX_VALUE)
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {fileBrowser, loadButton});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void loadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadButtonActionPerformed
        Path filePath = fileBrowser.getFilePath();
        try {
            BufferedImage originalImage = ImageIO.read(filePath.toFile());
            BufferedImage greyImage = Algorithms.getGreyImage(originalImage);
            Result mediumImageResult = Algorithms.getBinaryImageByMediumMethod(greyImage);
            Result yannyImageResult = Algorithms.getBinaryImageByYannyMethod(greyImage);
            Result watsuImageResult = Algorithms.getBinaryImageByWatsuMethod(greyImage);
            originalImagePanel.setImage(originalImage);
            greyImagePanel.setImage(greyImage);
            mediumImagePanel.setImage(mediumImageResult.getImage());
            yannyImagePanel.setImage(yannyImageResult.getImage());
            watsuImagePanel.setImage(watsuImageResult.getImage());
            String message = "Порог метода среднего: " + mediumImageResult.getBound() +
                    ", порог метода Янни: " + yannyImageResult.getBound() +
                    ", порог метода Оцу: " + watsuImageResult.getBound() + ".";
            textField.setText(message);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_loadButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.ran.acglab1.FileBrowser fileBrowser;
    private javax.swing.JButton loadButton;
    private javax.swing.JTabbedPane tabbedPane;
    private javax.swing.JTextField textField;
    // End of variables declaration//GEN-END:variables

}
