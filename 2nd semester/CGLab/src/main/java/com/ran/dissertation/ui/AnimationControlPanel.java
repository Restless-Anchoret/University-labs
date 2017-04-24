package com.ran.dissertation.ui;

import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JPanel;

public class AnimationControlPanel extends JPanel {

    public AnimationControlPanel() {
        initComponents();
        initListeners();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        startAnimationButton = new javax.swing.JButton();
        stopAnimationButton = new javax.swing.JButton();
        animationsComboBox = new com.ran.dissertation.ui.PresentationComboBox<>();

        setBorder(javax.swing.BorderFactory.createTitledBorder("Animations"));

        startAnimationButton.setText("Start animation");

        stopAnimationButton.setText("Stop animation");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(animationsComboBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(startAnimationButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(stopAnimationButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(animationsComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(startAnimationButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(stopAnimationButton))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void initListeners() {
        startAnimationButton.addActionListener(event -> {
            animationControlPanelListenerSupport.fireStartAnimationButtonClicked(chosenAnimationStrategy);
        });
        stopAnimationButton.addActionListener(event -> {
            animationControlPanelListenerSupport.fireStopAnimationButtonClicked(chosenAnimationStrategy);
        });
        animationsComboBox.getComboBox().addItemListener(event -> {
            AnimationStrategy previousAnimationStrategy = chosenAnimationStrategy;
            chosenAnimationStrategy = animationsComboBox.getSelectedValue();
            animationControlPanelListenerSupport.fireChosenAnimationChanged(chosenAnimationStrategy, previousAnimationStrategy);
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.ran.dissertation.ui.PresentationComboBox<AnimationStrategy> animationsComboBox;
    private javax.swing.JButton startAnimationButton;
    private javax.swing.JButton stopAnimationButton;
    // End of variables declaration//GEN-END:variables

    private AnimationControlPanelListenerSupport animationControlPanelListenerSupport = new AnimationControlPanelListenerSupport();
    private AnimationStrategy chosenAnimationStrategy = null;
    
    public void addAnimationControlPanelListener(AnimationControlPanelListener listener) {
        animationControlPanelListenerSupport.addAnimationControlPanelListener(listener);
    }
    
    public void removeAnimationControlPanelListener(AnimationControlPanelListener listener) {
        animationControlPanelListenerSupport.removeAnimationControlPanelListener(listener);
    }
    
    public void setAnimations(List<SelectItem<AnimationStrategy>> animationStrategiesSelectItems) {
        animationsComboBox.setSelectItems(animationStrategiesSelectItems);
        chosenAnimationStrategy = animationsComboBox.getSelectedValue();
    }
    
    public void stopAllAnimations() {
        animationsComboBox.getSelectItems().forEach(selectItem -> selectItem.getValue().stopAnimation());
    }
    
}
