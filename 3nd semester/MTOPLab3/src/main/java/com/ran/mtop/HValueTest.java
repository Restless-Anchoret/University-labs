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
        RungeKuttaSolver rungeKuttaSolver = SolverCreator.getSolver(h, SolverCreator.createX7(800, 5));
        List<List<TwoDoubleVector>> resultList = rungeKuttaSolver.solve();
        System.out.println("h = " + h);
        for (int i = 0; i < 6; i++) {
            System.out.println("x" + i + "(L) = " + resultList.get(i).get(resultList.get(i).size() - 1));
        }
        System.out.println();
    }

}
