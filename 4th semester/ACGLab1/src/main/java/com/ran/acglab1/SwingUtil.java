package com.ran.acglab1;

import javax.swing.JFileChooser;

public class SwingUtil {

    private static JFileChooser fileChooser = null;
    
    public static JFileChooser getFileChooser() {
        if (fileChooser == null) {
            fileChooser = new JFileChooser(System.getProperty("user.home"));
        }
        return fileChooser;
    }
    
    private SwingUtil() { }
}
