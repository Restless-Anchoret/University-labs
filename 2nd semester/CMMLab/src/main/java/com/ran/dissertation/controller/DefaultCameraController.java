package com.ran.dissertation.controller;

import com.ran.dissertation.ui.ImagePanel;
import com.ran.dissertation.ui.ImagePanelListener;
import com.ran.dissertation.world.Camera;

public class DefaultCameraController implements ImagePanelListener {

    private static final double DEFAULT_MOVE_STEP = 0.05;
    private static final double DEFAULT_ANGLE_STEP = Math.PI / 288.0;
    private static final double DEFAULT_ZOOM_STEP = 1.0;
    
    private final Camera camera;
    private final double moveStep;
    private final double angleStep;
    private final double zoomStep;

    public DefaultCameraController(Camera camera, double moveStep, double angleStep, double zoomStep) {
        this.camera = camera;
        this.moveStep = moveStep;
        this.angleStep = angleStep;
        this.zoomStep = zoomStep;
    }
    
    public DefaultCameraController(Camera camera) {
        this(camera, DEFAULT_MOVE_STEP, DEFAULT_ANGLE_STEP, DEFAULT_ZOOM_STEP);
    }
    
    @Override
    public void mouseDraggedLeftMouseButton(ImagePanel imagePanel, int previousX, int previousY, int nextX, int nextY, int width, int height) {
        camera.moveRight((nextX - previousX) * moveStep);
        camera.moveForward((nextY - previousY) * moveStep);
        imagePanel.repaint();
    }

    @Override
    public void mouseDraggedMiddleMouseButton(ImagePanel imagePanel, int previousX, int previousY, int nextX, int nextY, int width, int height) {
        camera.changeAngleXOY((previousX - nextX) * angleStep);
        camera.changeAngleZ((nextY - previousY) * angleStep);
        imagePanel.repaint();
    }

    @Override
    public void mouseDraggedRightMouseButton(ImagePanel imagePanel, int previousX, int previousY, int nextX, int nextY, int width, int height) {
        camera.moveZ((nextY - previousY) * moveStep);
        imagePanel.repaint();
    }

    @Override
    public void mouseWheelMoved(ImagePanel imagePanel, int x, int y, int width, int height, int notches) {
        camera.zoom(-notches * zoomStep);
        imagePanel.repaint();
    }

}