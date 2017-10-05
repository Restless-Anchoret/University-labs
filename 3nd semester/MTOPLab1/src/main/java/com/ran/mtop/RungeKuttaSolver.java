package com.ran.mtop;

import com.ran.engine.algebra.vector.TwoDoubleVector;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

public class RungeKuttaSolver {

    private BiFunction<Double, Double, Double> f;
    private double l0, x0, h;
    private int steps;

    public RungeKuttaSolver(BiFunction<Double, Double, Double> f,
                            double l0, double x0, double h, int steps) {
        this.f = f;
        this.l0 = l0;
        this.x0 = x0;
        this.h = h;
        this.steps = steps;
    }

    public List<TwoDoubleVector> solve() {
        double[] l = new double[steps + 1];
        double[] x = new double[steps + 1];
        for (int i = 0; i <= steps; i++) {
            l[i] = l0 + h * i;
        }
        x[0] = x0;

        for (int i = 0; i < steps; i++) {
            double k1 = h * f.apply(l[i], x[i]);
            double k2 = h * f.apply(l[i] + h / 2.0, x[i] + k1 / 2.0);
            double k3 = h * f.apply(l[i] + h / 2.0, x[i] + k2 / 2.0);
            double k4 = h * f.apply(l[i] + h, x[i] + k3);
            x[i + 1] = x[i] + (k1 + 2 * k2 + 2 * k3 + k4) / 6.0;
        }

        List<TwoDoubleVector> list = new ArrayList<>(steps + 1);
        for (int i = 0; i <= steps; i++) {
            list.add(new TwoDoubleVector(l[i], x[i]));
        }
        return list;
    }

}
