package com.ran.dissertation.ui;

import com.ran.dissertation.world.World;
import java.util.List;
import javax.swing.JPanel;

public class WorldSwitchingPanel extends JPanel {

    public WorldSwitchingPanel() {
        initComponents();
        initListeners();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        worldComboBox = new com.ran.dissertation.ui.PresentationComboBox<>();

        setBorder(javax.swing.BorderFactory.createTitledBorder("World Switching"));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(worldComboBox, javax.swing.GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(worldComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void initListeners() {
        worldComboBox.getComboBox().addItemListener(event -> {
            World previousWorld = chosenWorld;
            chosenWorld = worldComboBox.getSelectedValue();
            worldSwitchingPanelListenerSupport.fireChosenWorldChanged(chosenWorld, previousWorld);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.ran.dissertation.ui.PresentationComboBox<World> worldComboBox;
    // End of variables declaration//GEN-END:variables

    private WorldSwitchingPanelListenerSupport worldSwitchingPanelListenerSupport = new WorldSwitchingPanelListenerSupport();
    private World chosenWorld = null;
    
    public void addWorldSwitchingListener(WorldSwitchingPanelListener listener) {
        worldSwitchingPanelListenerSupport.addWorldSwitchingPanelListener(listener);
    }
    
    public void removeWorldSwitchingListener(WorldSwitchingPanelListener listener) {
        worldSwitchingPanelListenerSupport.removeWorldSwitchingPanelListener(listener);
    }
    
    public void setWorlds(List<SelectItem<World>> worldsSelectItems) {
        worldComboBox.setSelectItems(worldsSelectItems);
        chosenWorld = worldComboBox.getSelectedValue();
    }
    
}