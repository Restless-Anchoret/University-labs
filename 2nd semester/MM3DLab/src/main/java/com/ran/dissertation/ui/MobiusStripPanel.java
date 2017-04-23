package com.ran.dissertation.ui;

import javax.swing.JPanel;

public class MobiusStripPanel extends JPanel {

    public MobiusStripPanel() {
        initComponents();
        initListeners();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        labelT0 = new javax.swing.JLabel();
        textFieldT0 = new javax.swing.JTextField();
        labelT1 = new javax.swing.JLabel();
        textFieldT1 = new javax.swing.JTextField();
        labelTau0 = new javax.swing.JLabel();
        textFieldTau0 = new javax.swing.JTextField();
        labelTau1 = new javax.swing.JLabel();
        textFieldTau1 = new javax.swing.JTextField();
        labelTSteps = new javax.swing.JLabel();
        textFieldTSteps = new javax.swing.JTextField();
        labelTauSteps = new javax.swing.JLabel();
        textFieldTauSteps = new javax.swing.JTextField();
        labelR = new javax.swing.JLabel();
        textFieldR = new javax.swing.JTextField();
        labelH = new javax.swing.JLabel();
        textFieldH = new javax.swing.JTextField();
        labelN = new javax.swing.JLabel();
        textFieldN = new javax.swing.JTextField();
        buttonApply = new javax.swing.JButton();

        setBorder(javax.swing.BorderFactory.createTitledBorder("Mobius Strip"));

        labelT0.setText("t0:");

        textFieldT0.setText("0.0");

        labelT1.setText("t1:");

        textFieldT1.setText("6.28");

        labelTau0.setText("tau0:");

        textFieldTau0.setText("-1.0");

        labelTau1.setText("tau1:");

        textFieldTau1.setText("1.0");

        labelTSteps.setText("tSteps:");

        textFieldTSteps.setText("60");

        labelTauSteps.setText("tauSteps:");

        textFieldTauSteps.setText("8");

        labelR.setText("r:");

        textFieldR.setText("6.0");

        labelH.setText("h:");

        textFieldH.setText("2.0");

        labelN.setText("n:");

        textFieldN.setText("1");

        buttonApply.setText("Apply");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(labelTau0)
                                    .addComponent(labelT0))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(textFieldT0)
                                    .addComponent(textFieldTau0, javax.swing.GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(labelTSteps)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(textFieldTSteps, javax.swing.GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(labelH)
                                    .addComponent(labelR)
                                    .addComponent(labelN))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(textFieldR)
                                    .addComponent(textFieldH)
                                    .addComponent(textFieldN))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(labelTau1)
                                    .addComponent(labelT1))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(textFieldT1, javax.swing.GroupLayout.DEFAULT_SIZE, 69, Short.MAX_VALUE)
                                    .addComponent(textFieldTau1)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(labelTauSteps)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(textFieldTauSteps, javax.swing.GroupLayout.DEFAULT_SIZE, 43, Short.MAX_VALUE))))
                    .addComponent(buttonApply))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelT0)
                    .addComponent(textFieldT0, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelT1)
                    .addComponent(textFieldT1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelTau0)
                    .addComponent(textFieldTau0, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelTau1)
                    .addComponent(textFieldTau1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelTSteps)
                    .addComponent(textFieldTSteps, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelTauSteps)
                    .addComponent(textFieldTauSteps, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelR)
                    .addComponent(textFieldR, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelH)
                    .addComponent(textFieldH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelN)
                    .addComponent(textFieldN, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(buttonApply)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void initListeners() {
        buttonApply.addActionListener(action -> {
            mobiusStripPanelListenerSupport.fireApplyParameters(
                    textFieldT0.getText(), textFieldT1.getText(), textFieldTau0.getText(), textFieldTau1.getText(),
                    textFieldTSteps.getText(), textFieldTauSteps.getText(),
                    textFieldR.getText(), textFieldH.getText(), textFieldN.getText());
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonApply;
    private javax.swing.JLabel labelH;
    private javax.swing.JLabel labelN;
    private javax.swing.JLabel labelR;
    private javax.swing.JLabel labelT0;
    private javax.swing.JLabel labelT1;
    private javax.swing.JLabel labelTSteps;
    private javax.swing.JLabel labelTau0;
    private javax.swing.JLabel labelTau1;
    private javax.swing.JLabel labelTauSteps;
    private javax.swing.JTextField textFieldH;
    private javax.swing.JTextField textFieldN;
    private javax.swing.JTextField textFieldR;
    private javax.swing.JTextField textFieldT0;
    private javax.swing.JTextField textFieldT1;
    private javax.swing.JTextField textFieldTSteps;
    private javax.swing.JTextField textFieldTau0;
    private javax.swing.JTextField textFieldTau1;
    private javax.swing.JTextField textFieldTauSteps;
    // End of variables declaration//GEN-END:variables

    private MobiusStripPanelListenerSupport mobiusStripPanelListenerSupport = new MobiusStripPanelListenerSupport();
    
    public void addMobiusStripPanelListener(MobiusStripPanelListener listener) {
        mobiusStripPanelListenerSupport.addMobiusStripPanelListener(listener);
    }
    
    public void removeMobiusStripPanelListener(MobiusStripPanelListener listener) {
        mobiusStripPanelListenerSupport.removeMobiusStripPanelListener(listener);
    }
    
}
