package com.ran.dissertation.labs.lab1;

import com.ran.dissertation.controller.DefaultPaintStrategy;
import com.ran.dissertation.labs.common.PlainCameraController;
import com.ran.dissertation.ui.ImagePanel;
import com.ran.dissertation.world.Camera;
import com.ran.dissertation.world.World;

public class LabFirstCameraController extends PlainCameraController {

    public LabFirstCameraController(Camera camera, double zoomStep) {
        super(camera, zoomStep);
    }

    public LabFirstCameraController(Camera camera) {
        super(camera);
    }

    @Override
    protected void zoomCamera(Camera camera, ImagePanel imagePanel, int x, int y, int width, int height, int notches) {
        super.zoomCamera(camera, imagePanel, x, y, width, height, notches);
        updateWorld(camera, width, imagePanel);
    }

    @Override
    protected void moveCamera(Camera camera, ImagePanel imagePanel, int previousX, int previousY, int nextX, int nextY, int width, int height) {
        super.moveCamera(camera, imagePanel, previousX, previousY, nextX, nextY, width, height);
        updateWorld(camera, width, imagePanel);
    }
    
    private void updateWorld(Camera camera, int segments, ImagePanel imagePanel) {
        World world = LabFirstWorldFactory.getInstance().createWorldForCamera(camera, segments);
        imagePanel.setImagePanelPaintStrategy(new DefaultPaintStrategy(world));
    }

}