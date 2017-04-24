package com.ran.dissertation.ui;

@FunctionalInterface
public interface ImagePanelPaintStrategy {

    void paint(PaintDelegate paintDelegate, int width, int height);
    
}