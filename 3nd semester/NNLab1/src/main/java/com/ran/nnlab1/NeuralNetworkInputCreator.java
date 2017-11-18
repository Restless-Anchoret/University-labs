package com.ran.nnlab1;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;

public class NeuralNetworkInputCreator {

    private static final int IMAGE_SIDE_LENGTH = 400;
    private static final int INPUT_SIDE_SIDE_LENGTH = 20;
    
    public int[] createInput(BufferedImage image) {
        int[][] matrix = convertImageToMatrix(image);
        int[][] pictureMatrix = getPictureMatrix(matrix);
        int[][] inputMatrix = getInputMatrix(pictureMatrix);
        printInputMatrix(inputMatrix);
        return convertMatrixToArray(inputMatrix);
    }
    
    private int[][] convertImageToMatrix(BufferedImage image) {
        Raster raster = image.getData();
        int[][] matrix = new int[IMAGE_SIDE_LENGTH][IMAGE_SIDE_LENGTH];
        double[] array = new double[3];
        for (int i = 0; i < IMAGE_SIDE_LENGTH; i++) {
            for (int j = 0; j < IMAGE_SIDE_LENGTH; j++) {
                array = raster.getPixel(i, j, array);
                matrix[i][j] = (array[0] < 1.0 ? 0 : 1);
            }
        }
        return matrix;
    }
    
    private int[][] getPictureMatrix(int[][] matrix) {
        int xMin = IMAGE_SIDE_LENGTH;
        int xMax = -1;
        int yMin = IMAGE_SIDE_LENGTH;
        int yMax = -1;
        
        for (int i = 0; i < IMAGE_SIDE_LENGTH; i++) {
            for (int j = 0; j < IMAGE_SIDE_LENGTH; j++) {
                if (matrix[i][j] == 0) {
                    xMin = Math.min(xMin, i);
                    xMax = Math.max(xMax, i);
                    yMin = Math.min(yMin, j);
                    yMax = Math.max(yMax, j);
                }
            }
        }
        
        if (xMax == -1) {
            return matrix;
        }
        
        System.out.println("x in [" + xMin + "; " + xMax + "], y in [" + yMin + "; " + yMax + "]");
        
        int pictureXSize = xMax - xMin + 1;
        int pictureYSize = yMax - yMin + 1;
        int[][] pictureMatrix = new int[pictureYSize][pictureXSize];
        
        for (int i = 0; i < pictureYSize; i++) {
            for (int j = 0; j < pictureXSize; j++) {
                pictureMatrix[i][j] = matrix[xMin + j][yMin + i];
            }
        }
        return pictureMatrix;
    }
    
    private int[][] getInputMatrix(int[][] pictureMatrix) {
        int n = pictureMatrix.length;
        int m = pictureMatrix[0].length;
        int[][] inputMatrix = new int[INPUT_SIDE_SIDE_LENGTH][INPUT_SIDE_SIDE_LENGTH];
        
        for (int i = 0; i < INPUT_SIDE_SIDE_LENGTH; i++) {
            for (int j = 0; j < INPUT_SIDE_SIDE_LENGTH; j++) {
                inputMatrix[i][j] = 1;
                int iMin = (int)(((double)n / (double)INPUT_SIDE_SIDE_LENGTH) * i);
                int iMax = (int)(((double)n / (double)INPUT_SIDE_SIDE_LENGTH) * (i + 1));
                int jMin = (int)(((double)m / (double)INPUT_SIDE_SIDE_LENGTH) * j);
                int jMax = (int)(((double)m / (double)INPUT_SIDE_SIDE_LENGTH) * (j + 1));
                for (int iIndex = iMin; iIndex < iMax; iIndex++) {
                    for (int jIndex = jMin; jIndex < jMax; jIndex++) {
                        if (pictureMatrix[iIndex][jIndex] == 0) {
                            inputMatrix[i][j] = 0;
                        }
                    }
                }
            }
        }
        return inputMatrix;
    }
    
    private int[] convertMatrixToArray(int[][] matrix) {
        int n = matrix.length;
        int m = matrix[0].length;
        int[] array = new int[n * m];
        for (int i = 0; i < n; i++) {
            System.arraycopy(matrix[i], 0, array, i * m, m);
        }
        return array;
    }
    
    private void printInputMatrix(int[][] matrix) {
        System.out.println("--------------------");
        for (int i = 0; i < INPUT_SIDE_SIDE_LENGTH; i++) {
            for (int j = 0; j < INPUT_SIDE_SIDE_LENGTH; j++) {
                System.out.print(matrix[i][j]);
            }
            System.out.println();
        }
        System.out.println("--------------------");
    }
    
}
