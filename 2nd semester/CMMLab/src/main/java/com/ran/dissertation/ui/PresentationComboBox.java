package com.ran.dissertation.ui;

import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JPanel;

public class PresentationComboBox<T> extends JPanel {
    
    public PresentationComboBox() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        comboBox = new javax.swing.JComboBox<>();

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(comboBox, 0, 200, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(comboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> comboBox;
    // End of variables declaration//GEN-END:variables

    private List<SelectItem<T>> selectItems = new ArrayList<>();

    public JComboBox<String> getComboBox() {
        return comboBox;
    }

    public List<SelectItem<T>> getSelectItems() {
        return selectItems;
    }

    public void setSelectItems(List<SelectItem<T>> selectItems) {
        this.selectItems = new ArrayList<>();
        this.selectItems.addAll(selectItems);
        String[] presentationArray = new String[selectItems.size()];
        for (int i = 0; i < selectItems.size(); i++) {
            presentationArray[i] = selectItems.get(i).getPresentation();
        }
        comboBox.setModel(new DefaultComboBoxModel<>(presentationArray));
    }
    
    public int getSelectedIndex() {
        return comboBox.getSelectedIndex();
    }
    
    public void setSelectedIndex(int index) {
        comboBox.setSelectedIndex(index);
    }
    
    public T getSelectedValue() {
        if (comboBox.getSelectedIndex() == -1) {
            return null;
        } else {
            return getSelectedItem().getValue();
        }
    }
    
    public void setSelectedValue(T value) {
        for (int i = 0; i < selectItems.size(); i++) {
            if (selectItems.get(i).getValue().equals(value)) {
                comboBox.setSelectedIndex(i);
                return;
            }
        }
    }
    
    public SelectItem<T> getSelectedItem() {
        if (comboBox.getSelectedIndex() == -1) {
            return null;
        } else {
            return selectItems.get(comboBox.getSelectedIndex());
        }
    }
    
    public void setSelectedItem(SelectItem<T> item) {
        for (int i = 0; i < selectItems.size(); i++) {
            if (selectItems.get(i) == item) {
                comboBox.setSelectedIndex(i);
                return;
            }
        }
    }
    
}
