package com.ran.nnlab;

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.Timer;

public class LabFrame extends JFrame {

    private static final int PICTURES_QUANTITY = 4;
    private static final String PICTURE_FILE_NAME = "picture%s.png";
    private static final String PROCESSING = "Processing...";
    private static final int TIMER_PERIOD = 1000;
    
    private List<BufferedImage> images = new ArrayList<>();
    private List<String> imageNames = new ArrayList<>();
    private int loadedImageIndex = 1;
    private PictureConverter pictureConverter = new PictureConverter();
    private HopfieldNetwork hopfieldNetwork = null;
    private Timer timer = null;
    
    public LabFrame() {
        initComponents();
        initPictures();
        initHopfieldNetwork();
    }
    
    private void initPictures() {
        for (int i = 1; i <= PICTURES_QUANTITY; i++) {
            String fileName = String.format(PICTURE_FILE_NAME, Integer.toString(i));
            try {
                BufferedImage image = ImageIO.read(LabFrame.class.getResourceAsStream(fileName));
                double[] imageArray = pictureConverter.convertPictureToArray(image);
                BufferedImage compressedImage = pictureConverter.convertArrayToPicture(imageArray);
                images.add(compressedImage);
                imageNames.add(fileName);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        setImageByIndex(1);
    }
    
    private void initHopfieldNetwork() {
        hopfieldNetwork = new HopfieldNetwork(PictureConverter.ARRAY_SIZE);
        double[][] doubleImages = new double[PICTURES_QUANTITY][PictureConverter.ARRAY_SIZE];
        for (int i = 0; i < PICTURES_QUANTITY; i++) {
            BufferedImage image = images.get(i);
            doubleImages[i] = pictureConverter.convertPictureToArray(image);
        }
        hopfieldNetwork.init(doubleImages);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        paintPanel = new com.ran.nnlab.PaintPanel();
        buttonPrevious = new javax.swing.JButton();
        buttonNext = new javax.swing.JButton();
        textFieldInitPicture = new javax.swing.JTextField();
        buttonRecognize = new javax.swing.JButton();
        labelLoaded = new javax.swing.JLabel();
        labelRecognized = new javax.swing.JLabel();
        textFieldRecognized = new javax.swing.JTextField();
        labelProcessing = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout paintPanelLayout = new javax.swing.GroupLayout(paintPanel);
        paintPanel.setLayout(paintPanelLayout);
        paintPanelLayout.setHorizontalGroup(
            paintPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
        paintPanelLayout.setVerticalGroup(
            paintPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        buttonPrevious.setText("Previous");
        buttonPrevious.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonPreviousActionPerformed(evt);
            }
        });

        buttonNext.setText("Next");
        buttonNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonNextActionPerformed(evt);
            }
        });

        textFieldInitPicture.setEditable(false);

        buttonRecognize.setText("Recognize");
        buttonRecognize.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonRecognizeActionPerformed(evt);
            }
        });

        labelLoaded.setText("Loaded:");

        labelRecognized.setText("Recognized:");

        textFieldRecognized.setEditable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(paintPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(buttonPrevious)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonNext))
                    .addComponent(textFieldInitPicture)
                    .addComponent(buttonRecognize, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(labelLoaded)
                    .addComponent(labelRecognized)
                    .addComponent(textFieldRecognized)
                    .addComponent(labelProcessing, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(paintPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(labelLoaded)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textFieldInitPicture, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(buttonPrevious)
                            .addComponent(buttonNext))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonRecognize)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelRecognized)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textFieldRecognized, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelProcessing, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonPreviousActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonPreviousActionPerformed
        setImageByIndex(loadedImageIndex - 1);
    }//GEN-LAST:event_buttonPreviousActionPerformed

    private void buttonNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonNextActionPerformed
        setImageByIndex(loadedImageIndex + 1);
    }//GEN-LAST:event_buttonNextActionPerformed

    private void buttonRecognizeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonRecognizeActionPerformed
        labelProcessing.setText(PROCESSING);
        textFieldRecognized.setText("");
        
        BufferedImage startImage = paintPanel.getImage();
        double[] imageArray = pictureConverter.convertPictureToArray(startImage);
        BufferedImage compressedStartImage = pictureConverter.convertArrayToPicture(imageArray);
        paintPanel.setImage(compressedStartImage);
        paintPanel.repaint();
       
        if (checkIfImageWasFound(imageArray)) {
            System.out.println("Initial image is original");
            return;
        }
        
        timer = new Timer(TIMER_PERIOD, new TimerAction(imageArray));
        timer.start();
        System.out.println("Recognizing started");
        

////        paintPanel.repaint();
//        repaint();
//        int iterations = 0;
//        
//        while(true) {
//            iterations++;
//            sleep();
//            
//            imageArray = hopfieldNetwork.passImage(imageArray);
//            BufferedImage compressedImage = pictureConverter.convertArrayToPicture(imageArray);
//            paintPanel.setImage(compressedImage);
////            paintPanel.repaint();
//            repaint();
//            int imageIndex = hopfieldNetwork.findOriginalImageIndex(imageArray);
//            
//            if (imageIndex != -1) {
//                System.out.println("Iteration #" + iterations + ": found original image");
//                labelProcessing.setText("");
//                textFieldRecognized.setText(imageNames.get(imageIndex));
//                break;
//            }
//            
//            hopfieldNetwork.forget(imageArray);
//            System.out.println("Iteration #" + iterations + ": forgot false image");
//        }
    }//GEN-LAST:event_buttonRecognizeActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonNext;
    private javax.swing.JButton buttonPrevious;
    private javax.swing.JButton buttonRecognize;
    private javax.swing.JLabel labelLoaded;
    private javax.swing.JLabel labelProcessing;
    private javax.swing.JLabel labelRecognized;
    private com.ran.nnlab.PaintPanel paintPanel;
    private javax.swing.JTextField textFieldInitPicture;
    private javax.swing.JTextField textFieldRecognized;
    // End of variables declaration//GEN-END:variables

    private void setImageByIndex(int index) {
        loadedImageIndex = index;
        int correctedIndex = (PICTURES_QUANTITY * 100000 + index - 1) % PICTURES_QUANTITY;
        BufferedImage originalImage = images.get(correctedIndex);
        BufferedImage image = (BufferedImage)createImage(originalImage.getWidth(), originalImage.getHeight());
        Graphics2D graphics = image.createGraphics();
        graphics.drawImage(originalImage, 0, 0, this);
        paintPanel.setImage(image);
        paintPanel.repaint();
        textFieldInitPicture.setText(imageNames.get(correctedIndex));
    }
    
    private boolean checkIfImageWasFound(double[] currentImage) {
        int imageIndex = hopfieldNetwork.findOriginalImageIndex(currentImage);
        if (imageIndex != -1) {
            labelProcessing.setText("");
            textFieldRecognized.setText(imageNames.get(imageIndex));
            return true;
        }
        return false;
    }
    
    private class TimerAction implements ActionListener {
        
        private int iterations = 0;
        private double[] imageArray;

        public TimerAction(double[] imageArray) {
            this.imageArray = imageArray;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            iterations++;
            imageArray = hopfieldNetwork.passImage(imageArray);
            BufferedImage compressedImage = pictureConverter.convertArrayToPicture(imageArray);
            paintPanel.setImage(compressedImage);
            paintPanel.repaint();
            
            if (checkIfImageWasFound(imageArray)) {
                System.out.println("Iteration #" + iterations + ": found original image");
                timer.stop();
                return;
            }
            
            do {
                hopfieldNetwork.forget(imageArray);
            } while (Arrays.equals(imageArray, hopfieldNetwork.passImage(imageArray)));
            System.out.println("Iteration #" + iterations + ": forgot false image");
        }
        
    }
    
}
