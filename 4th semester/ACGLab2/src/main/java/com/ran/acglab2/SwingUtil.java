package com.ran.acglab2;

import javax.swing.JFileChooser;

public class SwingUtil {

    private static JFileChooser fileChooser = null;
    
    public static JFileChooser getFileChooser() {
        if (fileChooser == null) {
            fileChooser = new JFileChooser("D:\\Dropbox\\Repositories\\University-labs\\4th semester\\ACGLab2\\pictures");
        }
        return fileChooser;
    }
    
    private SwingUtil() { }
}
