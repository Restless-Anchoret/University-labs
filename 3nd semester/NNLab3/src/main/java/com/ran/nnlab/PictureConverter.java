package com.ran.nnlab;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;

public class PictureConverter {

    private static final int PICTURE_SIDE = 300;
    private static final int PIXEL_BLOCK_SIDE = 10;
    private static final int PIXEL_PICTURE_SIDE = PICTURE_SIDE / PIXEL_BLOCK_SIDE;
    public static final int ARRAY_SIZE = PIXEL_PICTURE_SIDE * PIXEL_PICTURE_SIDE;
    
    public double[] convertPictureToArray(BufferedImage image) {
        if (image.getWidth() != PICTURE_SIDE ||
                image.getHeight() != PICTURE_SIDE) {
            throw new RuntimeException();
        }
        
        double[][] bigMatrix = convertPictureToMatrix(image);
        double[][] compressedMatrix = compressMatrix(bigMatrix);
        return convertMatrixToArray(compressedMatrix);
    }
    
    public BufferedImage convertArrayToPicture(double[] array) {
        if (array.length != ARRAY_SIZE) {
            throw new RuntimeException();
        }
        
        double[][] matrix = convertArrayToMatrix(array);
        return createImageFromMatrix(matrix);
    }
    
    private double[][] convertPictureToMatrix(BufferedImage image) {
        Raster raster = image.getData();
        double[][] matrix = new double[PICTURE_SIDE][PICTURE_SIDE];
        double[] array = new double[3];
        for (int i = 0; i < PICTURE_SIDE; i++) {
            for (int j = 0; j < PICTURE_SIDE; j++) {
                array = raster.getPixel(i, j, array);
                matrix[i][j] = array[0];
            }
        }
        return matrix;
    }
    
    private double[][] compressMatrix(double[][] matrix) {
        double[][] compressedMatrix = new double[PIXEL_PICTURE_SIDE][PIXEL_PICTURE_SIDE];
        for (int i = 0; i < PIXEL_PICTURE_SIDE; i++) {
            for (int j = 0; j < PIXEL_PICTURE_SIDE; j++) {
                double sum = 0.0;
                int iMin = PIXEL_BLOCK_SIDE * i;
                int iMax = PIXEL_BLOCK_SIDE * (i + 1);
                int jMin = PIXEL_BLOCK_SIDE * j;
                int jMax = PIXEL_BLOCK_SIDE * (j + 1);
                for (int iIndex = iMin; iIndex < iMax; iIndex++) {
                    for (int jIndex = jMin; jIndex < jMax; jIndex++) {
                        sum += matrix[iIndex][jIndex];
                    }
                }
                double average = sum / (PIXEL_BLOCK_SIDE * PIXEL_BLOCK_SIDE);
                compressedMatrix[i][j] = (average < 128 ? -1.0 : 1.0);
            }
        }
        return compressedMatrix;
    }
    
    private BufferedImage createImageFromMatrix(double[][] matrix) {
        BufferedImage image = new BufferedImage(PICTURE_SIDE, PICTURE_SIDE, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();
        for (int i = 0; i < PIXEL_PICTURE_SIDE; i++) {
            for (int j = 0; j < PIXEL_PICTURE_SIDE; j++) {
                int iMin = PIXEL_BLOCK_SIDE * i;
                int jMin = PIXEL_BLOCK_SIDE * j;
                Color color;
                if (matrix[i][j] < 0.0) {
                    color = Color.BLACK;
                } else {
                    color = Color.WHITE;
                }
                graphics.setColor(color);
                graphics.fillRect(jMin, iMin, PIXEL_BLOCK_SIDE, PIXEL_BLOCK_SIDE);
            }
        }
        return image;
    }
    
    private double[] convertMatrixToArray(double[][] matrix) {
        int n = matrix.length;
        int m = matrix[0].length;
        double[] array = new double[n * m];
        for (int i = 0; i < n; i++) {
            System.arraycopy(matrix[i], 0, array, i * m, m);
        }
        return array;
    }
    
    private double[][] convertArrayToMatrix(double[] array) {
        int n = (int)Math.sqrt(array.length);
        double[][] matrix = new double[n][n];
        for (int i = 0; i < n; i++) {
            System.arraycopy(array, i * n, matrix[i], 0, n);
        }
        return matrix;
    }
    
}
