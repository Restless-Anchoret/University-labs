package ui;

import java.awt.Graphics2D;

@FunctionalInterface
public interface ImagePanelPaintStrategy {

    void paint(Graphics2D graphics, int width, int height);
    
}