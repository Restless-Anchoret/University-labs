package com.ran.mobilab1;

import java.math.BigInteger;
import java.util.function.Function;
import javax.swing.JFrame;

public class LabFrame extends JFrame {

    private Calculator calculator = new Calculator();
    
    public LabFrame() {
        calculator.evaluatePrimeNumbers();
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        nTextField = new javax.swing.JTextField();
        aTextField = new javax.swing.JTextField();
        bTextField = new javax.swing.JTextField();
        nLabel = new javax.swing.JLabel();
        aLabel = new javax.swing.JLabel();
        bLabel = new javax.swing.JLabel();
        resultTextField = new javax.swing.JTextField();
        modButton = new javax.swing.JButton();
        equalButton = new javax.swing.JButton();
        sumButton = new javax.swing.JButton();
        substractButton = new javax.swing.JButton();
        degreeButton = new javax.swing.JButton();
        sqrtButton = new javax.swing.JButton();
        reverseButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        nTextField.setText("65478");

        aTextField.setText("4582");

        bTextField.setText("45968");

        nLabel.setText("mod(n)");

        aLabel.setText("a");

        bLabel.setText("b");

        resultTextField.setEditable(false);

        modButton.setText("a mod(n)");
        modButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modButtonActionPerformed(evt);
            }
        });

        equalButton.setText("a==b mod(n)");
        equalButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                equalButtonActionPerformed(evt);
            }
        });

        sumButton.setText("(a+b) mod(n)");
        sumButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sumButtonActionPerformed(evt);
            }
        });

        substractButton.setText("(a-b) mod(n)");
        substractButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                substractButtonActionPerformed(evt);
            }
        });

        degreeButton.setText("(a^b) mod(n)");
        degreeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                degreeButtonActionPerformed(evt);
            }
        });

        sqrtButton.setText("sqrt(a) mod(n)");
        sqrtButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sqrtButtonActionPerformed(evt);
            }
        });

        reverseButton.setText("a^(-1) mod(n)");
        reverseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reverseButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(resultTextField)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(modButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(substractButton, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(reverseButton, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
                            .addComponent(equalButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(degreeButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(sumButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(sqrtButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(nLabel)
                                .addGap(145, 145, 145)
                                .addComponent(aLabel))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(nTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(aTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(56, 56, 56)
                                .addComponent(bLabel))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(bTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nLabel)
                    .addComponent(aLabel)
                    .addComponent(bLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(aTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(resultTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(modButton)
                    .addComponent(equalButton)
                    .addComponent(sumButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(substractButton)
                    .addComponent(degreeButton)
                    .addComponent(sqrtButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(reverseButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void modButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modButtonActionPerformed
        operation(calculator::mod);
    }//GEN-LAST:event_modButtonActionPerformed

    private void equalButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_equalButtonActionPerformed
        operation(calculator::equal);
    }//GEN-LAST:event_equalButtonActionPerformed

    private void sumButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sumButtonActionPerformed
        operation(calculator::sum);
    }//GEN-LAST:event_sumButtonActionPerformed

    private void substractButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_substractButtonActionPerformed
        operation(calculator::subtract);
    }//GEN-LAST:event_substractButtonActionPerformed

    private void degreeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_degreeButtonActionPerformed
        operation(calculator::degree);
    }//GEN-LAST:event_degreeButtonActionPerformed

    private void sqrtButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sqrtButtonActionPerformed
        operation(calculator::sqrt);
    }//GEN-LAST:event_sqrtButtonActionPerformed

    private void reverseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reverseButtonActionPerformed
        operation(calculator::reverse);
    }//GEN-LAST:event_reverseButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel aLabel;
    private javax.swing.JTextField aTextField;
    private javax.swing.JLabel bLabel;
    private javax.swing.JTextField bTextField;
    private javax.swing.JButton degreeButton;
    private javax.swing.JButton equalButton;
    private javax.swing.JButton modButton;
    private javax.swing.JLabel nLabel;
    private javax.swing.JTextField nTextField;
    private javax.swing.JTextField resultTextField;
    private javax.swing.JButton reverseButton;
    private javax.swing.JButton sqrtButton;
    private javax.swing.JButton substractButton;
    private javax.swing.JButton sumButton;
    // End of variables declaration//GEN-END:variables

    private void operation(Function<Input, String> function) {
        try {
            BigInteger n = new BigInteger(nTextField.getText());
            BigInteger a = new BigInteger(aTextField.getText());
            BigInteger b = new BigInteger(bTextField.getText());
            Input input = new Input(a, b, n);
            input.validate();
            String result = function.apply(input);
            resultTextField.setText(result);
        } catch (Exception ex) {
            ex.printStackTrace();
            resultTextField.setText("error");
        }
    }
}
