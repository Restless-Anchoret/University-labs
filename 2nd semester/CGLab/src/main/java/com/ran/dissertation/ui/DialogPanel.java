package com.ran.dissertation.ui;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class DialogPanel extends JPanel {

    public DialogPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }
    
    public void setComponent(JComponent component) {
        this.removeAll();
        this.add(component);
    }
    
}