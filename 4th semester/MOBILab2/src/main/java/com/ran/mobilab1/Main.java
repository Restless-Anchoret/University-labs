package com.ran.mobilab1;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Main {

    public static void main(String[] args) {
        try {
            String name = UIManager.getSystemLookAndFeelClassName();
            UIManager.setLookAndFeel(name);
        } catch (ClassNotFoundException | InstantiationException |
                IllegalAccessException | UnsupportedLookAndFeelException exception) {
            exception.printStackTrace();
        }
        LabFrame frame = new LabFrame();
        frame.setVisible(true);
    }
}
