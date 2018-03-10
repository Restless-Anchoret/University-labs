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
        BufferedImage image = buildImageForBound(sourceImage, t);
        return new Result(image, t);
    }
    
    public static Result getBinaryImageByYannyMethod(BufferedImage sourceImage) {
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
        int gmid = (gmin + gmax) / 2;
        long allPixels = (long)w * (long)h;
        long darkPixels = 0;
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                int rgb = sourceImage.getRGB(j, i);
                Color color = getColor(rgb);
                int grey = color.getRed();
                if (grey <= gmid) {
                    darkPixels++;
                }
            }
        }
        int t = gmin + (int)((gmax - gmin) * ((double)darkPixels / (double)allPixels));
        BufferedImage image = buildImageForBound(sourceImage, t);
        return new Result(image, t);
    }
    
    public static Result getBinaryImageByWatsuMethod(BufferedImage sourceImage) {
        int w = sourceImage.getWidth(null);
        int h = sourceImage.getHeight(null);
        int gmin = 255;
        int gmax = 0;
        int[] n = new int[256];
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                int rgb = sourceImage.getRGB(j, i);
                Color color = getColor(rgb);
                int grey = color.getRed();
                gmin = Math.min(gmin, grey);
                gmax = Math.max(gmax, grey);
                n[grey]++;
            }
        }
        double p[] = new double[256];
        for (int i = 0; i < 256; i++) {
            p[i] = (double)n[i] / (double)(w * h);
        }
        double maxSigma = -Double.MAX_VALUE;
        int foundT = -1;
        for (int t = 1; t <= gmax; t++) {
            double w0 = countW0(t, p);
            double w1 = countW1(t, gmax, p);
            double m0 = countM0(t, w0, p);
            double m1 = countM1(t, gmax, w1, p);
            double sigma = countSigma(w0, w1, m0, m1);
            if (sigma > maxSigma) {
                maxSigma = sigma;
                foundT = t;
            }
        }
        BufferedImage image = buildImageForBound(sourceImage, foundT);
        return new Result(image, foundT);
    }
    
    private static double countW0(int t, double[] p) {
        double result = 0.0;
        for (int i = 1; i <= t; i++) {
            result += p[i];
        }
        return result;
    }
    
    private static double countW1(int t, int gmax, double[] p) {
        double result = 0.0;
        for (int i = t + 1; i <= gmax; i++) {
            result += p[i];
        }
        return result;
    }
    
    private static double countM0(int t, double w0, double[] p) {
        double result = 0.0;
        for (int i = 1; i <= t; i++) {
            result += i * p[i];
        }
        return result / w0;
    }
    
    private static double countM1(int t, int gmax, double w1, double[] p) {
        double result = 0.0;
        for (int i = t + 1; i <= gmax; i++) {
            result += i * p[i];
        }
        return result / w1;
    }
    
    private static double countSigma(double w0, double w1, double m0, double m1) {
        return w0 * w1 * (m1 - m0) * (m1 - m0);
    }
    
    private static Color getColor(int rgb) {
        int red   = (rgb & 0x00ff0000) >> 16;
        int green = (rgb & 0x0000ff00) >> 8;
        int blue  = rgb & 0x000000ff;
        return new Color(red, green, blue);
    }
    
    private static BufferedImage buildImageForBound(BufferedImage sourceImage, int t) {
        int w = sourceImage.getWidth(null);
        int h = sourceImage.getHeight(null);
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
        return image;
    }
}
