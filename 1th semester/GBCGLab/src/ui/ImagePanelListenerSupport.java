package ui;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ImagePanelListenerSupport {

    private Set<ImagePanelListener> imagePanelListeners = new HashSet<>();
    
    public void addImagePanelListener(ImagePanelListener listener) {
        imagePanelListeners.add(listener);
    }
    
    public Set<ImagePanelListener> getImagePanelListeners() {
        return Collections.unmodifiableSet(imagePanelListeners);
    }
    
    public void removeImagePanelListener(ImagePanelListener listener) {
        imagePanelListeners.remove(listener);
    }
    
    public void removeAllImagePanelListeners() {
        imagePanelListeners.clear();
    }
    
    public void fireMouseLeftClicked(ImagePanel imagePanel, int x, int y, int width, int height) {
        for (ImagePanelListener listener: imagePanelListeners) {
            listener.mouseLeftClicked(imagePanel, x, y, width, height);
        }
    }
    
    public void fireMouseMiddleClicked(ImagePanel imagePanel, int x, int y, int width, int height) {
        for (ImagePanelListener listener: imagePanelListeners) {
            listener.mouseMiddleClicked(imagePanel, x, y, width, height);
        }
    }
    
    public void fireMouseRightClicked(ImagePanel imagePanel, int x, int y, int width, int height) {
        for (ImagePanelListener listener: imagePanelListeners) {
            listener.mouseRightClicked(imagePanel, x, y, width, height);
        }
    }
    
    public void fireMouseLeftDragged(ImagePanel imagePanel, int previousX, int previousY, int x, int y, int width, int height) {
        for (ImagePanelListener listener: imagePanelListeners) {
            listener.mouseLeftDragged(imagePanel, previousX, previousY, x, y, width, height);
        }
    }
    
    public void fireMouseMiddleDragged(ImagePanel imagePanel, int previousX, int previousY, int x, int y, int width, int height) {
        for (ImagePanelListener listener: imagePanelListeners) {
            listener.mouseMiddleDragged(imagePanel, previousX, previousY, x, y, width, height);
        }
    }
    
    public void fireMouseRightDragged(ImagePanel imagePanel, int previousX, int previousY, int x, int y, int width, int height) {
        for (ImagePanelListener listener: imagePanelListeners) {
            listener.mouseRightDragged(imagePanel, previousX, previousY, x, y, width, height);
        }
    }
    
    public void fireMouseMovedWithoutPressedButtons(ImagePanel imagePanel, int previousX, int previousY, int x, int y, int width, int height) {
        for (ImagePanelListener listener: imagePanelListeners) {
            listener.mouseMovedWithoutPressedButtons(imagePanel, previousX, previousY, x, y, width, height);
        }
    }
    
}