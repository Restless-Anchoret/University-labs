package com.ran.dissertation.labs.cmm;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleFunction;

public class CollocationsMethod {

    private LegendrePolynomsFactory legendrePolynomsFactory = new LegendrePolynomsFactory();

    public DoubleFunction<Double> solve(
            DoubleFunction<Double> p,
            DoubleFunction<Double> q,
            DoubleFunction<Double> r,
            DoubleFunction<Double> g,
            int n) {
        double[] t = new ChebyshevPolynomZerosProducer().produceZeros(n);
        List<DoubleFunction<Double>> e = new ArrayList<>(n);
        List<DoubleFunction<Double>> eI = new ArrayList<>(n);
        List<DoubleFunction<Double>> eII = new ArrayList<>(n);
        for (int i = 0; i < n; i++) {
            DoubleFunction<Double> legendrePolynom = legendrePolynomsFactory.getLegendrePolynomOfDegree(i);
            DoubleFunction<Double> currentPolynom = u -> (u + 1.0) * (1.0 - u) * legendrePolynom.apply(u);
            e.add(currentPolynom);
            DoubleFunction<Double> firstDerivative = DerivativeGetter.getInstance().getDerivative(currentPolynom);
            eI.add(firstDerivative);
            DoubleFunction<Double> secondDerivative = DerivativeGetter.getInstance().getDerivative(firstDerivative);
            eII.add(secondDerivative);
        }
        double[][] a = new double[n][n];
        double[] f = new double[n];
        for (int j = 0; j < n; j++) {
            for (int k = 0; k < n; k++) {
                a[j][k] = p.apply(t[j]) * eII.get(k).apply(t[j]) +
                        q.apply(t[j]) * eI.get(k).apply(t[j]) +
                        r.apply(t[j]) * e.get(k).apply(t[j]);
            }
            f[j] = g.apply(t[j]);
        }
        double[] solutionCoordinates = new GaussMethod().solve(a, f, n);
        return getLinearCombination(solutionCoordinates, e);
    }

    private DoubleFunction<Double> getLinearCombination(double[] a, List<DoubleFunction<Double>> e) {
        return t -> {
            double result = 0.0;
            for (int i = 0; i < a.length; i++) {
                result += a[i] * e.get(i).apply(t);
            }
            return result;
        };
    }

}