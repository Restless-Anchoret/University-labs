package com.ran.acglab2;

import java.util.Arrays;

public class Filters {

    private Filters() { }
    
    public static double[][] boxFilter(int r) {
        int n = 2 * r + 1;
        double[][] filter = new double[n][n];
        double value = 1.0 / ((double)n * (double)n);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                filter[i][j] = value;
            }
        }
        return filter;
    }
    
    public static double[][] gaussFilter(int delta) {
        int n = 6 * delta + 1;
        int c = 3 * delta;
        double[][] filter = new double[n][n];
        double sum = 0.0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                double x = j - c;
                double y = i - c;
                filter[i][j] = Math.exp((-x * x - y * y) / 2 * delta * delta) / (2.0 * Math.PI * delta * delta);
                sum += filter[i][j];
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                filter[i][j] /= sum;
            }
        }
        return filter;
    }
    
    public static int[][] roundStructuralElement(int r) {
        int n = r * 2 + 1;
        int[][] element = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int deltaI = Math.abs(r - i);
                int deltaJ = Math.abs(r - j);
                double dist = Math.sqrt(deltaI * deltaI + deltaJ * deltaJ);
                element[i][j] = (dist <= (double)r ? 0 : 255);
            }
        }
        return element;
    }
}
