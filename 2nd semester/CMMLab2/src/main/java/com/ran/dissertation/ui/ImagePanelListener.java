package com.ran.dissertation.ui;

public interface ImagePanelListener {
    
    void mouseDraggedLeftMouseButton(ImagePanel imagePanel, int previousX, int previousY, int nextX, int nextY, int width, int height);
    void mouseDraggedMiddleMouseButton(ImagePanel imagePanel, int previousX, int previousY, int nextX, int nextY, int width, int height);
    void mouseDraggedRightMouseButton(ImagePanel imagePanel, int previousX, int previousY, int nextX, int nextY, int width, int height);
    void mouseWheelMoved(ImagePanel imagePanel, int x, int y, int width, int height, int notches);

}