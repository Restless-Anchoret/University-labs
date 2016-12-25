package ui;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PolygonSettingDialog extends JPanel {

    public PolygonSettingDialog() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        headerTitle = new javax.swing.JLabel();
        generateAnyPolygonButton = new javax.swing.JButton();
        generateConvexPolygonButton = new javax.swing.JButton();
        txLabel = new javax.swing.JLabel();
        txTextField = new javax.swing.JTextField();
        tyLabel = new javax.swing.JLabel();
        tyTextField = new javax.swing.JTextField();
        aLabel = new javax.swing.JLabel();
        aTextField = new javax.swing.JTextField();
        bLabel = new javax.swing.JLabel();
        bTextField = new javax.swing.JTextField();
        thetaLabel = new javax.swing.JLabel();
        thetaTextField = new javax.swing.JTextField();
        cleanButton = new javax.swing.JButton();

        headerTitle.setText("Заголовок");

        generateAnyPolygonButton.setText("Сгенерировать произвольный полигон");

        generateConvexPolygonButton.setText("Сгенерировать выпуклый полигон");

        txLabel.setText("Tx:");

        txTextField.setText("400");

        tyLabel.setText("Ty:");

        tyTextField.setText("300");

        aLabel.setText("a:");

        aTextField.setText("50");

        bLabel.setText("b:");

        bTextField.setText("150");

        thetaLabel.setText("Theta:");

        thetaTextField.setText("90");

        cleanButton.setText("Очистить");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cleanButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(generateAnyPolygonButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(generateConvexPolygonButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(headerTitle)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(thetaLabel)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(thetaTextField))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(txLabel)
                                    .addGap(30, 30, 30)
                                    .addComponent(txTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(tyLabel)
                                    .addGap(31, 31, 31)
                                    .addComponent(tyTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(aLabel)
                                    .addGap(37, 37, 37)
                                    .addComponent(aTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(bLabel)
                                    .addGap(37, 37, 37)
                                    .addComponent(bTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(headerTitle)
                .addGap(18, 18, 18)
                .addComponent(cleanButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(generateAnyPolygonButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(generateConvexPolygonButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txLabel)
                    .addComponent(txTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tyLabel)
                    .addComponent(tyTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(aLabel)
                    .addComponent(aTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bLabel)
                    .addComponent(bTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(thetaLabel)
                    .addComponent(thetaTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(205, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel aLabel;
    private javax.swing.JTextField aTextField;
    private javax.swing.JLabel bLabel;
    private javax.swing.JTextField bTextField;
    private javax.swing.JButton cleanButton;
    private javax.swing.JButton generateAnyPolygonButton;
    private javax.swing.JButton generateConvexPolygonButton;
    private javax.swing.JLabel headerTitle;
    private javax.swing.JLabel thetaLabel;
    private javax.swing.JTextField thetaTextField;
    private javax.swing.JLabel txLabel;
    private javax.swing.JTextField txTextField;
    private javax.swing.JLabel tyLabel;
    private javax.swing.JTextField tyTextField;
    // End of variables declaration//GEN-END:variables

    public JButton getCleanButton() {
        return cleanButton;
    }
    
    public JButton getGenerateAnyPolygonButton() {
        return generateAnyPolygonButton;
    }

    public JButton getGenerateConvexPolygonButton() {
        return generateConvexPolygonButton;
    }

    public JLabel getHeaderTitle() {
        return headerTitle;
    }
    
    public double getTx() {
        return Double.parseDouble(txTextField.getText());
    }
    
    public double getTy() {
        return Double.parseDouble(tyTextField.getText());
    }
    
    public double getA() {
        return Double.parseDouble(aTextField.getText());
    }
    
    public double getB() {
        return Double.parseDouble(bTextField.getText());
    }
    
    public double getTheta() {
        return Double.parseDouble(thetaTextField.getText());
    }

}
