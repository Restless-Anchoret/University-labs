package com.ran.acglab2;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class LabFrame extends JFrame {

    private ImagePanel originalImagePanel = new ImagePanel();
    private ImagePanel greyImagePanel = new ImagePanel();
    private ImagePanel watsuImagePanel = new ImagePanel();
    private ImagePanel boxFilterImagePanel = new ImagePanel();
    private ImagePanel gaussFilterImagePanel = new ImagePanel();
    private ImagePanel medianFilterImagePanel = new ImagePanel();
    private ImagePanel dilatationImagePanel = new ImagePanel();
    private ImagePanel erosionImagePanel = new ImagePanel();
    private ImagePanel closureImagePanel = new ImagePanel();
    private ImagePanel disjunctionImagePanel = new ImagePanel();
    
    public LabFrame() {
        initComponents();
        
        tabbedPane.addTab("Оригинал", originalImagePanel);
        tabbedPane.addTab("Градации серого", greyImagePanel);
        tabbedPane.addTab("Бокс-фильтр", boxFilterImagePanel);
        tabbedPane.addTab("Фильтр Гаусса", gaussFilterImagePanel);
        tabbedPane.addTab("Медианный фильтр", medianFilterImagePanel);
        tabbedPane.addTab("Бинарное изображение", watsuImagePanel);
        tabbedPane.addTab("Дилатация", dilatationImagePanel);
        tabbedPane.addTab("Эрозия", erosionImagePanel);
        tabbedPane.addTab("Замыкание", closureImagePanel);
        tabbedPane.addTab("Размыкание", disjunctionImagePanel);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabbedPane = new javax.swing.JTabbedPane();
        loadButton = new javax.swing.JButton();
        textField = new javax.swing.JTextField();
        fileBrowser = new com.ran.acglab2.FileBrowser();

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
                        .addComponent(textField, javax.swing.GroupLayout.DEFAULT_SIZE, 780, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(loadButton)
                        .addComponent(textField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(fileBrowser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, 604, Short.MAX_VALUE)
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
            Result watsuImageResult = Algorithms.getBinaryImageByWatsuMethod(greyImage);
            BufferedImage boxFilterImage = Algorithms.getImageWithAppliedBoxFilter(greyImage, 6);
            BufferedImage gaussFilterImage = Algorithms.getImageWithAppliedGaussFilter(greyImage, 1);
            BufferedImage medianFilterImage = Algorithms.getImageWithAppliedMedianFilter(greyImage, 6);
            BufferedImage dilatationImage = Algorithms.getDilatation(watsuImageResult.getImage(), 1);
            BufferedImage erosionImage = Algorithms.getErosion(watsuImageResult.getImage(), 1);
            BufferedImage closureImage = Algorithms.getClosure(watsuImageResult.getImage(), 1);
            BufferedImage disjunctionImage = Algorithms.getDisjunction(watsuImageResult.getImage(), 1);
            originalImagePanel.setImage(originalImage);
            greyImagePanel.setImage(greyImage);
            watsuImagePanel.setImage(watsuImageResult.getImage());
            boxFilterImagePanel.setImage(boxFilterImage);
            gaussFilterImagePanel.setImage(gaussFilterImage);
            medianFilterImagePanel.setImage(medianFilterImage);
            dilatationImagePanel.setImage(dilatationImage);
            erosionImagePanel.setImage(erosionImage);
            closureImagePanel.setImage(closureImage);
            disjunctionImagePanel.setImage(disjunctionImage);
            String message = "Порог метода Оцу: " + watsuImageResult.getBound() + ".";
            textField.setText(message);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_loadButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.ran.acglab2.FileBrowser fileBrowser;
    private javax.swing.JButton loadButton;
    private javax.swing.JTabbedPane tabbedPane;
    private javax.swing.JTextField textField;
    // End of variables declaration//GEN-END:variables

}
