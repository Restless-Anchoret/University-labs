package com.ran.acglab3;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class LabFrame extends JFrame {

    private ImagePanel originalImagePanel = new ImagePanel();
    private ImagePanel greyImagePanel = new ImagePanel();
    private ImagePanel watsuImagePanel = new ImagePanel();
    private ImagePanel gaussFilterImagePanel = new ImagePanel();
    private ImagePanel kenniyImagePanel = new ImagePanel();
    
    public LabFrame() {
        initComponents();
        
        tabbedPane.addTab("Оригинал", originalImagePanel);
        tabbedPane.addTab("Градации серого", greyImagePanel);
        tabbedPane.addTab("Бинарное изображение", watsuImagePanel);
        tabbedPane.addTab("Фильтр Гаусса", gaussFilterImagePanel);
        tabbedPane.addTab("Детектор границ Кенния", kenniyImagePanel);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabbedPane = new javax.swing.JTabbedPane();
        calculateButton = new javax.swing.JButton();
        watsuTextField = new javax.swing.JTextField();
        fileBrowser = new com.ran.acglab3.FileBrowser();
        jLabel1 = new javax.swing.JLabel();
        sigmaTextField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        tMinTextField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        tMaxTextField = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        calculateButton.setText("Calculate");
        calculateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                calculateButtonActionPerformed(evt);
            }
        });

        watsuTextField.setEditable(false);

        jLabel1.setText("Sigma:");

        sigmaTextField.setText("2");

        jLabel2.setText("Tmin:");

        tMinTextField.setText("40");

        jLabel3.setText("Tmax:");

        tMaxTextField.setText("200");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel1)
                                .addComponent(jLabel2)
                                .addComponent(jLabel3))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(sigmaTextField)
                                .addComponent(tMinTextField)
                                .addComponent(tMaxTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(watsuTextField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)
                            .addComponent(fileBrowser, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                    .addComponent(calculateButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tabbedPane, javax.swing.GroupLayout.DEFAULT_SIZE, 962, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tabbedPane)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(fileBrowser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(watsuTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(sigmaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(tMinTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(tMaxTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(calculateButton)
                        .addGap(0, 454, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void calculateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_calculateButtonActionPerformed
        Path filePath = fileBrowser.getFilePath();
        try {
            int gaussSigma = Integer.parseInt(sigmaTextField.getText());
            int tMin = Integer.parseInt(tMinTextField.getText());
            int tMax = Integer.parseInt(tMaxTextField.getText());
            BufferedImage originalImage = ImageIO.read(filePath.toFile());
            BufferedImage greyImage = Algorithms.getGreyImage(originalImage);
            Result watsuImageResult = Algorithms.getBinaryImageByWatsuMethod(greyImage);
            BufferedImage gaussFilterImage = Algorithms.getImageWithAppliedGaussFilter(watsuImageResult.getImage(), gaussSigma);
            BufferedImage kenniyImage = Algorithms.getImageByKenniyMethod(gaussFilterImage, tMin, tMax);
            originalImagePanel.setImage(originalImage);
            greyImagePanel.setImage(greyImage);
            watsuImagePanel.setImage(watsuImageResult.getImage());
            gaussFilterImagePanel.setImage(gaussFilterImage);
            kenniyImagePanel.setImage(kenniyImage);
            String message = "Порог метода Оцу: " + watsuImageResult.getBound() + ".";
            watsuTextField.setText(message);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_calculateButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton calculateButton;
    private com.ran.acglab3.FileBrowser fileBrowser;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JTextField sigmaTextField;
    private javax.swing.JTextField tMaxTextField;
    private javax.swing.JTextField tMinTextField;
    private javax.swing.JTabbedPane tabbedPane;
    private javax.swing.JTextField watsuTextField;
    // End of variables declaration//GEN-END:variables

}
