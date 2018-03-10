package com.ran.acglab1;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Algorithms {
    
    private final static double RED_FACTOR = 0.2126;
    private final static double GREEN_FACTOR = 0.7152;
    private final static double BLUE_FACTOR = 0.0722;
    
    private Algorithms() { }

    public static BufferedImage getGreyImage(BufferedImage sourceImage) {
        int w = sourceImage.getWidth(null);
        int h = sourceImage.getHeight(null);
        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D gr = image.createGraphics();
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                int rgb = sourceImage.getRGB(j, i);
                int red   = (rgb & 0x00ff0000) >> 16;
                int green = (rgb & 0x0000ff00) >> 8;
                int blue  = rgb & 0x000000ff;
                int grey = (int)((double)red * RED_FACTOR +
                        (double)green * GREEN_FACTOR + (double)blue * BLUE_FACTOR);
                Color color = new Color(grey, grey, grey);
                gr.setColor(color);
                gr.drawRect(j, i, 1, 1);
            }
        }
        return image;
    }
    
    public static BufferedImage getBinaryImageByMediumMethod(BufferedImage sourceImage) {
        return sourceImage;
    }
    
    public static BufferedImage getBinaryImageByYannyMethod(BufferedImage sourceImage) {
        return sourceImage;
    }
    
    public static BufferedImage getBinaryImageByWatsuMethod(BufferedImage sourceImage) {
        return sourceImage;
    }
}
