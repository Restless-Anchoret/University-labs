package com.ran.nnlab;

public class HopfieldNetwork {
    
    private final int neuronsQuantity;
    private final double[][] w;
    private double[][] originalImages = null;

    public HopfieldNetwork(int neuronsQuantity) {
        this.neuronsQuantity = neuronsQuantity;
        this.w = new double[neuronsQuantity][neuronsQuantity];
    }
    
    public void init(double[][] images) {
        originalImages = images;
        for (double[] image : images) {
            if (image.length != neuronsQuantity) {
                throw new RuntimeException();
            }
            for (int i = 0; i < neuronsQuantity; i++) {
                for (int j = 0; j < neuronsQuantity; j++) {
                    if (i != j) {
                        w[i][j] = w[i][j] + image[i] * image[j];
                    }
                }
            }
        }
    }
    
    public double[] passImage(double[] image) {
        if (image.length != neuronsQuantity) {
            throw new RuntimeException();
        }
        double[] newImage = new double[neuronsQuantity];
        for (int i = 0; i < neuronsQuantity; i++) {
            double s = 0.0;
            for (int j = 0; j < neuronsQuantity; j++) {
                s += image[j] * w[i][j];
            }
            newImage[i] = activate(s, image[i]);
        }
        return newImage;
    }
    
    public int findOriginalImageIndex(double[] image) {
        for (int k = 0; k < originalImages.length; k++) {
            boolean fit = true;
            for (int i = 0; i < neuronsQuantity; i++) {
                if (Math.abs(originalImages[k][i] - image[i]) > 1e-4) {
                    fit = false;
                }
            }
            if (fit) {
                return k;
            }
        }
        return -1;
    }
    
    public void forget(double[] image) {
        double e = 0.05;
        for (int i = 0; i < neuronsQuantity; i++) {
            for (int j = 0; j < neuronsQuantity; j++) {
                w[i][j] -= e * image[i] * image[j];
            }
        }
    }
    
    private double activate(double s, double initPixel) {
        if (s > 1e-4) {
            return 1.0;
        } else if (s < -1e-4) {
            return -1.0;
        } else {
            return initPixel;
        }
    }
    
}
