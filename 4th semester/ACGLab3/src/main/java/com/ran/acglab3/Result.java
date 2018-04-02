package com.ran.acglab3;

import java.awt.image.BufferedImage;

public class Result {

    private BufferedImage image;
    private int bound;

    public Result(BufferedImage image, int bound) {
        this.image = image;
        this.bound = bound;
    }

    public BufferedImage getImage() {
        return image;
    }

    public int getBound() {
        return bound;
    }
}
