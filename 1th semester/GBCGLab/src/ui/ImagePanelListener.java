package ui;

public interface ImagePanelListener {
    
    void mouseLeftClicked(ImagePanel imagePanel, int x, int y, int width, int height);
    void mouseMiddleClicked(ImagePanel imagePanel, int x, int y, int width, int height);
    void mouseRightClicked(ImagePanel imagePanel, int x, int y, int width, int height);
    void mouseLeftDragged(ImagePanel imagePanel, int previousX, int previousY, int x, int y, int width, int height);
    void mouseMiddleDragged(ImagePanel imagePanel, int previousX, int previousY, int x, int y, int width, int height);
    void mouseRightDragged(ImagePanel imagePanel, int previousX, int previousY, int x, int y, int width, int height);
    void mouseMovedWithoutPressedButtons(ImagePanel imagePanel, int previousX, int previousY, int x, int y, int width, int height);

}