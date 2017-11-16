package com.ran.mtop;

import com.ran.engine.algebra.vector.TwoDoubleVector;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

public class RungeKuttaSolver {

    private List<BiFunction<Double, List<Double>, Double>> f;
    private double l0, h;
    private List<Double> x0;
    private int steps, n;

    public RungeKuttaSolver(List<BiFunction<Double, List<Double>, Double>> f,
                            double l0, List<Double> x0, double h, int steps, int n) {
        this.f = f;
        this.l0 = l0;
        this.x0 = x0;
        this.h = h;
        this.steps = steps;
        this.n = n;
    }

    public List<List<TwoDoubleVector>> solve() {
        double[] l = new double[steps + 1];
        double[][] x = new double[n][steps + 1];
        for (int j = 0; j <= steps; j++) {
            l[j] = l0 + h * j;
        }

        for (int i = 0; i < n; i++) {
            x[i][0] = x0.get(i);
        }

        for (int j = 0; j < steps; j++) {
            double[] k1 = new double[n];
            List<Double> k1Params = new ArrayList<>(n);
            for (int i = 0; i < n; i++) {
                k1Params.add(x[i][j]);
            }
            for (int i = 0; i < n; i++) {
                k1[i] = h * f.get(i).apply(l[j], k1Params);
            }

            double[] k2 = new double[n];
            List<Double> k2Params = new ArrayList<>(n);
            for (int i = 0; i < n; i++) {
                k2Params.add(x[i][j] + k1[i] / 2.0);
            }
            for (int i = 0; i < n; i++) {
                k2[i] = h * f.get(i).apply(l[j] + h / 2.0, k2Params);
            }

            double[] k3 = new double[n];
            List<Double> k3Params = new ArrayList<>(n);
            for (int i = 0; i < n; i++) {
                k3Params.add(x[i][j] + k2[i] / 2.0);
            }
            for (int i = 0; i < n; i++) {
                k3[i] = h * f.get(i).apply(l[j] + h / 2.0, k3Params);
            }

            double[] k4 = new double[n];
            List<Double> k4Params = new ArrayList<>(n);
            for (int i = 0; i < n; i++) {
                k4Params.add(x[i][j] + k3[i]);
            }
            for (int i = 0; i < n; i++) {
                k4[i] = h * f.get(i).apply(l[j] + h, k4Params);
            }

            for (int i = 0; i < n; i++) {
                x[i][j + 1] = x[i][j] + (k1[i] + 2 * k2[i] + 2 * k3[i] + k4[i]) / 6.0;
            }
        }

        List<List<TwoDoubleVector>> result = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            List<TwoDoubleVector> function = new ArrayList<>(steps + 1);
            for (int j = 0; j <= steps; j++) {
                function.add(new TwoDoubleVector(l[j], x[i][j]));
            }
            result.add(function);
        }
        return result;
    }

}
