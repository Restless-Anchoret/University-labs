package com.ran.dissertation.labs.lab1;

import com.ran.dissertation.algebraic.vector.ThreeDoubleVector;
import com.ran.dissertation.controller.*;
import com.ran.dissertation.ui.MainFrame;
import com.ran.dissertation.world.Camera;
import com.ran.dissertation.world.World;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class LabFirstMainController {

    private Camera camera;
    private MainFrame mainFrame;
    
    public void startApplication() {
        tryToSetBetterLaf();
        mainFrame = new MainFrame();
        int imagePanelWidth = mainFrame.getImagePanel().getWidth();
        camera = Camera.createForPositionReversedDistanceAndLensWidth(new ThreeDoubleVector(0.0, 1.0, 1.0), 0.0, 25.0);
        World world = LabFirstWorldFactory.getInstance().createWorldForCamera(camera, imagePanelWidth);
        DefaultPaintStrategy paintStrategy = new DefaultPaintStrategy(world);
        mainFrame.getImagePanel().setImagePanelPaintStrategy(paintStrategy);
        mainFrame.getImagePanel().addImagePanelListener(new LabFirstCameraController(camera));
        mainFrame.getDialogPanel().setVisible(false);
        mainFrame.setVisible(true);
    }

    private void tryToSetBetterLaf() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException |
                IllegalAccessException | UnsupportedLookAndFeelException exception) {
            exception.printStackTrace();
        }
    }
    
}