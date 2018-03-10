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
                Color color = getColor(rgb);
                int grey = (int)((double)color.getRed() * RED_FACTOR +
                        (double)color.getGreen() * GREEN_FACTOR + (double)color.getBlue() * BLUE_FACTOR);
                Color greyColor = new Color(grey, grey, grey);
                gr.setColor(greyColor);
                gr.drawRect(j, i, 1, 1);
            }
        }
        return image;
    }
    
    public static Result getBinaryImageByMediumMethod(BufferedImage sourceImage) {
        int w = sourceImage.getWidth(null);
        int h = sourceImage.getHeight(null);
        int gmin = 255;
        int gmax = 0;
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                int rgb = sourceImage.getRGB(j, i);
                Color color = getColor(rgb);
                int grey = color.getRed();
                gmin = Math.min(gmin, grey);
                gmax = Math.max(gmax, grey);
            }
        }
        int t = (gmin + gmax) / 2;
        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D gr = image.createGraphics();
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                int rgb = sourceImage.getRGB(j, i);
                Color color = getColor(rgb);
                int grey = color.getRed();
                int binary = (grey <= t ? 0 : 255);
                Color binaryColor = new Color(binary, binary, binary);
                gr.setColor(binaryColor);
                gr.drawRect(j, i, 1, 1);
            }
        }
        return new Result(image, t);
    }
    
    public static Result getBinaryImageByYannyMethod(BufferedImage sourceImage) {
        int w = sourceImage.getWidth(null);
        int h = sourceImage.getHeight(null);
        return new Result(sourceImage, 0);
    }
    
    public static Result getBinaryImageByWatsuMethod(BufferedImage sourceImage) {
        int w = sourceImage.getWidth(null);
        int h = sourceImage.getHeight(null);
        return new Result(sourceImage, 0);
    }
    
    private static Color getColor(int rgb) {
        int red   = (rgb & 0x00ff0000) >> 16;
        int green = (rgb & 0x0000ff00) >> 8;
        int blue  = rgb & 0x000000ff;
        return new Color(red, green, blue);
    }
}
