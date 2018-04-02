package com.ran.acglab3;

import javax.swing.JFileChooser;

public class SwingUtil {

    private static JFileChooser fileChooser = null;
    
    public static JFileChooser getFileChooser() {
        if (fileChooser == null) {
            fileChooser = new JFileChooser("D:\\Repositories\\University-labs\\4th semester\\ACGLab3\\pictures");
        }
        return fileChooser;
    }
    
    private SwingUtil() { }
}
