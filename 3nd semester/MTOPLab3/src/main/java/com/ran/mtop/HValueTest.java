package com.ran.mtop;

import com.ran.engine.algebra.vector.TwoDoubleVector;

import java.util.List;

public class HValueTest {

    public static void main(String[] args) {
        double[] h = new double[] { 0.5, 0.2, 0.1, 0.05, 0.02, 0.01, 0.005, 0.002 };
        for (double hVal : h) {
            test(hVal);
        }
    }

    private static void test(double h) {
//        RungeKuttaSolver rungeKuttaSolver = SolverCreator.getSolver(h, SolverCreator.createX7(800, 5));
        RungeKuttaSolver rungeKuttaSolver = SolverCreator.getSolver(h, l -> 900.0);
        List<List<TwoDoubleVector>> resultList = rungeKuttaSolver.solve();
        System.out.println("h = " + h);
        for (int i = 0; i < 6; i++) {
            System.out.println("x" + i + "(L) = " + resultList.get(i).get(resultList.get(i).size() - 1));
        }
        double maxError = 0.0;
        for (int i = 0; i < resultList.get(0).size(); i++) {
            double sum = 0.0;
            for (int j = 0; j < 6; j++) {
                sum += resultList.get(j).get(i).getY();
            }
            maxError = Math.max(maxError, Math.abs(1.0 - sum));
        }
        System.out.println("Max error = " + maxError);
        System.out.println();
    }

}
