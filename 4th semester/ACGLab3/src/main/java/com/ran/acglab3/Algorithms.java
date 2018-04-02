package com.ran.acglab3;

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
    
    public static BufferedImage getImageWithAppliedGaussFilter(BufferedImage image, int delta) {
        return getImageWithAppliedFilter(image, Filters.gaussFilter(delta));
    }
    
    private static BufferedImage getImageWithAppliedFilter(BufferedImage image, double[][] filter) {
        int r = filter.length / 2;
        int[][] greyMatrix = getImageAsMatrixOfGrey(image);
        int[][] expandedMatrix = getExpandedImageMatrix(greyMatrix, r);
        int[][] finalMatrix = getImageMatrixWithAppliedFilter(expandedMatrix, filter);
        return createGreyImageForMatrix(finalMatrix);
    }
    
    private static int[][] getImageAsMatrixOfGrey(BufferedImage image) {
        int w = image.getWidth(null);
        int h = image.getHeight(null);
        int[][] matrix = new int[h][w];
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                int rgb = image.getRGB(j, i);
                Color color = getColor(rgb);
                matrix[i][j] = color.getRed();
            }
        }
        return matrix;
    }
    
    private static int[][] getExpandedImageMatrix(int[][] matrix, int expandPixels) {
        int w = matrix[0].length;
        int h = matrix.length;
        int[][] newMatrix = new int[h + expandPixels * 2][w + expandPixels * 2];
        for (int i = 0; i < h + expandPixels * 2; i++) {
            for (int j = 0; j < w + expandPixels * 2; j++) {
                int iIndex = i - expandPixels;
                if (iIndex < 0) {
                    iIndex = -1 - iIndex;
                } else if (iIndex >= h) {
                    iIndex = h - (iIndex - h + 1);
                }
                int jIndex = j - expandPixels;
                if (jIndex < 0) {
                    jIndex = -1 - jIndex;
                } else if (jIndex >= w) {
                    jIndex = w - (jIndex - w + 1);
                }
                newMatrix[i][j] = matrix[iIndex][jIndex];
            }
        }
        return newMatrix;
    }
    
    private static int[][] getImageMatrixWithAppliedFilter(int[][] matrix, double[][] filter) {
        int w = matrix[0].length;
        int h = matrix.length;
        int r = filter.length / 2;
        int[][] newMatrix = new int[h - r * 2][w - r * 2];
        for (int i = 0; i < h - r * 2; i++) {
            for (int j = 0; j < w - r * 2; j++) {
                double sum = 0.0;
                for (int k = 0; k <= r * 2; k++) {
                    for (int p = 0; p <= r * 2; p++) {
                        sum += matrix[i + k][j + p] * filter[k][p];
                    }
                }
                newMatrix[i][j] = (int)sum;
            }
        }
        return newMatrix;
    }
    
    private static BufferedImage createGreyImageForMatrix(int[][] matrix) {
        int w = matrix[0].length;
        int h = matrix.length;
        BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D gr = image.createGraphics();
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                int grey = matrix[i][j];
                Color binaryColor = new Color(grey, grey, grey);
                gr.setColor(binaryColor);
                gr.drawRect(j, i, 1, 1);
            }
        }
        return image;
    }
    
    public static BufferedImage getImageByKenniyMethod(BufferedImage image, int tMin, int tMax) {
        int[][] imageMatrix = getImageAsMatrixOfGrey(image);
        int[][] expandedMatrix = getExpandedImageMatrix(imageMatrix, 1);
        int[][] gx = getImageMatrixWithAppliedFilter(expandedMatrix, Filters.gx());
        int[][] gy = getImageMatrixWithAppliedFilter(expandedMatrix, Filters.gy());
        int[][] g = calculateG(gx, gy);
        int[][] theta = calculateTheta(gx, gy);
        int[][] expandedG = getExpandedImageMatrix(g, 1);
        int[][] updatedG = removeNotMaximums(expandedG, theta);
        int[][] zeroOneMatrix = calculateZeroOneMatrix(updatedG, tMin, tMax);
        int[][] finalMatrix = removeUnknown(zeroOneMatrix);
        convertZeroOneToWhiteBlack(finalMatrix);
        return createGreyImageForMatrix(finalMatrix);
    }
    
    private static int[][] calculateG(int[][] gx, int[][] gy) {
        int w = gx[0].length;
        int h = gx.length;
        int[][] g = new int[h][w];
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                g[i][j] = (int)Math.round(Math.sqrt(gx[i][j] * gx[i][j] + gy[i][j] * gy[i][j]));
            }
        }
        return g;
    }
    
    private static int[][] calculateTheta(int[][] gx, int[][] gy) {
        int w = gx[0].length;
        int h = gx.length;
        int[][] theta = new int[h][w];
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                double angle = Math.atan2(gy[i][j], gx[i][j]);
                if (angle < -7.0 * Math.PI / 8.0) {
                    theta[i][j] = 0;
                } else if (angle < -5.0 * Math.PI / 8.0) {
                    theta[i][j] = 45;
                } else if (angle < -3.0 * Math.PI / 8.0) {
                    theta[i][j] = 90;
                } else if (angle < -Math.PI / 8.0) {
                    theta[i][j] = 135;
                } else if (angle < Math.PI / 8.0) {
                    theta[i][j] = 0;
                } else if (angle < 3.0 * Math.PI / 8.0) {
                    theta[i][j] = 45;
                } else if (angle < 5.0 * Math.PI / 8.0) {
                    theta[i][j] = 90;
                } else if (angle < 7.0 * Math.PI / 8.0) {
                    theta[i][j] = 135;
                } else {
                    theta[i][j] = 0;
                }
            }
        }
        return theta;
    }
    
    private static int[][] removeNotMaximums(int[][] g, int[][] theta) {
        int w = theta[0].length;
        int h = theta.length;
        int[][] newMatrix = new int[h][w];
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                newMatrix[i][j] = g[i + 1][j + 1];
                if (theta[i][j] == 0 && (newMatrix[i][j] < g[i + 1][j] || newMatrix[i][j] < g[i + 1][j + 2])) {
                    newMatrix[i][j] = 0;
                }
                if (theta[i][j] == 45 && (newMatrix[i][j] < g[i][j] || newMatrix[i][j] < g[i + 2][j + 2])) {
                    newMatrix[i][j] = 0;
                }
                if (theta[i][j] == 90 && (newMatrix[i][j] < g[i][j + 1] || newMatrix[i][j] < g[i + 2][j + 1])) {
                    newMatrix[i][j] = 0;
                }
                if (theta[i][j] == 135 && (newMatrix[i][j] < g[i][j + 2] || newMatrix[i][j] < g[i + 2][j])) {
                    newMatrix[i][j] = 0;
                }
            }
        }
        return newMatrix;
    }
    
    private static int[][] calculateZeroOneMatrix(int[][] g, int tMin, int tMax) {
        int w = g[0].length;
        int h = g.length;
        int[][] newMatrix = new int[h][w];
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                if (g[i][j] < tMin) {
                    newMatrix[i][j] = 0;
                } else if (g[i][j] > tMax) {
                    newMatrix[i][j] = 1;
                } else {
                    newMatrix[i][j] = -1;
                }
            }
        }
        return newMatrix;
    }
    
    private static int[][] removeUnknown(int[][] matrix) {
        int w = matrix[0].length;
        int h = matrix.length;
        int[][] newMatrix = new int[h][w];
        boolean[][] visited = new boolean[h][w];
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                if (!visited[i][j] && matrix[i][j] == 1) {
                    dfs(matrix, newMatrix, visited, i, j);
                }
            }
        }
        return newMatrix;
    }
    
    private static void dfs(int[][] matrix, int[][] newMatrix, boolean[][] visited, int i, int j) {
        visited[i][j] = true;
        newMatrix[i][j] = 1;
        if (i > 0 && !visited[i - 1][j] && Math.abs(matrix[i - 1][j]) == 1) {
            dfs(matrix, newMatrix, visited, i - 1, j);
        }
        if (i < matrix.length - 1 && !visited[i + 1][j] && Math.abs(matrix[i + 1][j]) == 1) {
            dfs(matrix, newMatrix, visited, i + 1, j);
        }
        if (j > 0 && !visited[i][j - 1] && Math.abs(matrix[i][j - 1]) == 1) {
            dfs(matrix, newMatrix, visited, i, j - 1);
        }
        if (j < matrix[0].length - 1 && !visited[i][j + 1] && Math.abs(matrix[i][j + 1]) == 1) {
            dfs(matrix, newMatrix, visited, i, j + 1);
        }
    }
    
    private static void convertZeroOneToWhiteBlack(int[][] matrix) {
        int w = matrix[0].length;
        int h = matrix.length;
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                matrix[i][j] = (matrix[i][j] == 0 ? 255 : 0);
            }
        }
    }
}
