package com.ran.dissertation.labs.cmm;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleFunction;

public class CollocationsMethod {

    private static final int START_METHOD_DEGREE = 4;
    private static final Polynom POLYNOM_FOR_MULTIPLYING = new Polynom(new double[] {1.0, 0.0, -1.0});

    private LegendrePolynomsFactory legendrePolynomsFactory = new LegendrePolynomsFactory();

    private List<Polynom> e = new ArrayList<>();
    private List<Polynom> eI = new ArrayList<>();
    private List<Polynom> eII = new ArrayList<>();

    public DoubleFunction<Double> solve(
            DoubleFunction<Double> p,
            DoubleFunction<Double> q,
            DoubleFunction<Double> r,
            DoubleFunction<Double> g,
            double epsilon) {
        double deviation = Double.MAX_VALUE;
        int n = START_METHOD_DEGREE;
        DoubleFunction<Double> currentSolution = solve(p, q, r, g, n);
        while (deviation > epsilon) {
            n++;
            DoubleFunction<Double> nextSolution = solve(p, q, r, g, n);
            deviation = FunctionUtils.getDeviation(currentSolution, nextSolution, -1.0, 1.0);
            currentSolution = nextSolution;
        }
        System.out.println("Collocations method max N = " + n);
        return currentSolution;
    }

    public DoubleFunction<Double> solve(
            DoubleFunction<Double> p,
            DoubleFunction<Double> q,
            DoubleFunction<Double> r,
            DoubleFunction<Double> g,
            int n) {
        double[] t = new ChebyshevPolynomZerosProducer().produceZeros(n);

        for (int i = 0; i < n; i++) {
            if (i < e.size()) {
                // Means that polynom of this degree is already counted
                continue;
            }
            Polynom legendrePolynom = legendrePolynomsFactory.getLegendrePolynomOfDegree(i);
//            System.out.println("legendre: " + legendrePolynom);
            Polynom currentPolynom = POLYNOM_FOR_MULTIPLYING.multiply(legendrePolynom);
//            System.out.println("current: " + currentPolynom);
            e.add(currentPolynom);
            Polynom firstDerivative = currentPolynom.getDerivative();
//            System.out.println("first: " + firstDerivative);
            eI.add(firstDerivative);
            Polynom secondDerivative = firstDerivative.getDerivative();
//            System.out.println("second: " + secondDerivative);
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

    private DoubleFunction<Double> getLinearCombination(double[] a, List<Polynom> e) {
        Polynom resultPolynom = Polynom.ZERO_POLYNOM;
        for (int i = 0; i < a.length; i++) {
            resultPolynom = resultPolynom.add(e.get(i).multiplyByNumber(a[i]));
        }
        return resultPolynom;
    }

}