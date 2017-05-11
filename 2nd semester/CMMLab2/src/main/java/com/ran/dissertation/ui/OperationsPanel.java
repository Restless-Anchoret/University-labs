package com.ran.dissertation.ui;

import javax.swing.JButton;
import javax.swing.JPanel;

public class OperationsPanel extends JPanel {

    public OperationsPanel() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        saveScreenshotButton = new javax.swing.JButton();

        setBorder(javax.swing.BorderFactory.createTitledBorder("Operations"));

        saveScreenshotButton.setText("Save screenshot");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(saveScreenshotButton)
                .addContainerGap(71, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(saveScreenshotButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton saveScreenshotButton;
    // End of variables declaration//GEN-END:variables

    public JButton getSaveScreenshotButton() {
        return saveScreenshotButton;
    }

}
