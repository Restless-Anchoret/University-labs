package com.ran.dissertation.labs.cmm;

import com.ran.dissertation.algebraic.common.ArithmeticOperations;
import com.ran.dissertation.algebraic.exception.AlgebraicException;

public class GaussMethod {

    public double[] solve(double[][] a, double[] f, int n) {
        for (int k = 1; k < n; k++) {
            if (ArithmeticOperations.doubleEquals(a[k - 1][k - 1], 0.0)) {
                throw new AlgebraicException("Zero on the main diagonal in Gauss method");
            }
            for (int j = k; j < n; j++) {
                double m = a[j][k - 1] / a[k - 1][k - 1];
                for (int i = 0; i < n; i++) {
                    a[j][i] -= m * a[k - 1][i];
                }
                f[j] -= m * f[k - 1];
            }
        }
        double[] x = new double[n];
        for (int i = n - 1; i >= 0; i--) {
            if (ArithmeticOperations.doubleEquals(a[i][i], 0.0)) {
                throw new AlgebraicException("Zero on the main diagonal in Gauss method");
            }
            x[i] = f[i];
            for (int j = n - 1; j > i; j--) {
                x[i] -= x[j] * a[i][j];
            }
            x[i] /= a[i][i];
        }
        return x;
    }

}