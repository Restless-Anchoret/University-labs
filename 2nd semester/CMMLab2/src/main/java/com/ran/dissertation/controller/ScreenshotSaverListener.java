package com.ran.dissertation.controller;

import com.ran.dissertation.ui.MainFrame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ScreenshotSaverListener implements ActionListener {

    private final static String FILE_EXTENSTION_DESCRIPTION = "PNG files";
    private final static String FILE_EXTENSION = "png";
    private final static String ERROR_MESSAGE = "An I/O error occured while saving screenshot";
    private final static String ERROR_DIALOG_TITLE = "Error";
    
    private MainFrame mainFrame;

    public ScreenshotSaverListener(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }
    
    @Override
    public void actionPerformed(ActionEvent event) {
        BufferedImage image = mainFrame.getImagePanel().getLastFrameImage();
        Path filePath = getFilePath();
        if (filePath != null) {
            saveImageToPath(image, filePath);
        }
    }
    
    private Path getFilePath() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.resetChoosableFileFilters();
        fileChooser.setFileFilter(new FileNameExtensionFilter(FILE_EXTENSTION_DESCRIPTION, FILE_EXTENSION));
        int fileChoosingResult = fileChooser.showSaveDialog(mainFrame);
        if (fileChoosingResult == JFileChooser.APPROVE_OPTION) {
            Path chosenPath = fileChooser.getSelectedFile().toPath();
            if (!chosenPath.toString().endsWith("." + FILE_EXTENSION)) {
                chosenPath = Paths.get(chosenPath.toString() + "." + FILE_EXTENSION);
            }
            return chosenPath;
        } else {
            return null;
        }
    }
    
    private void saveImageToPath(BufferedImage image, Path path) {
        try (OutputStream outputStream = Files.newOutputStream(path)) {
            ImageIO.write(image, FILE_EXTENSION, outputStream);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(mainFrame, 
                    ERROR_MESSAGE, ERROR_DIALOG_TITLE, JOptionPane.ERROR_MESSAGE);
        }
    }

}