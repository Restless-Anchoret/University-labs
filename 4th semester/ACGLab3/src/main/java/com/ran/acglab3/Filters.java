package com.ran.acglab3;

public class Filters {

    private Filters() { }
    
    public static double[][] gaussFilter(int delta) {
        int n = 6 * delta + 1;
        int c = 3 * delta;
        double[][] filter = new double[n][n];
        double sum = 0.0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                double x = j - c;
                double y = i - c;
                filter[i][j] = Math.exp(-(x * x + y * y) / (2.0 * delta * delta)) / Math.sqrt(2.0 * Math.PI * delta * delta);
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
    
    public static double[][] gx() {
        return new double[][] {
            new double[] { -1, 0, 1 },
            new double[] { -2, 0, 2 },
            new double[] { -1, 0, 1 }
        };
    }
    
    public static double[][] gy() {
        return new double[][] {
            new double[] { -1, -2, -1 },
            new double[] { 0, 0, 0 },
            new double[] { 1, 2, 1 }
        };
    }
}
