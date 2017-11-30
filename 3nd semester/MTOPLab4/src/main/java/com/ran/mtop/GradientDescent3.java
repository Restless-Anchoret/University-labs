package com.ran.mtop;

import com.ran.engine.algebra.vector.ThreeDoubleVector;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class GradientDescent3 {

    private static final double DER_EPS_1 = 0.01;
//    private static final double DER_EPS_1 = 1e-4;
    private static final double DER_EPS_2 = 0.01;
//    private static final double DER_EPS_2 = 1e-4;
//    private static final double DER_EPS_3 = 1e-4;
    private static final double DER_EPS_3 = 0.01;

    public Result findMaximum(Function<ThreeDoubleVector, Double> function,
                              double a10, double a20, double a30,
                              double tStart, double e, int maxIterations) {
        double a1 = a10;
        double a2 = a20;
        double a3 = a30;
        int iteration = 0;
        double nextFunctionValue = function.apply(new ThreeDoubleVector(a1, a2, a3));
        double t = tStart;
        List<Double> values = new ArrayList<>();

        outer: while (true) {
            iteration++;
            if (iteration > maxIterations) {
                break;
            }
            System.out.println("Iteration #" + iteration + ": a1 = " + a1 + "; a2 = " + a2 + "; a3 = " + a3);

            if (iteration % 4 == 0) {
                t *= 4.0;
                t = Math.min(t, 5.0);
            }

            double functionValue = nextFunctionValue;
            System.out.println("Function value: " + functionValue);
            values.add(functionValue);

            double functionValueA1Shift = function.apply(new ThreeDoubleVector(a1 + DER_EPS_1, a2, a3));
            double functionValueA2Shift = function.apply(new ThreeDoubleVector(a1, a2 + DER_EPS_2, a3));
            double functionValueA3Shift = function.apply(new ThreeDoubleVector(a1, a2, a3 + DER_EPS_3));
//            System.out.println("Function value a1 shift: " + functionValueA1Shift);
//            System.out.println("Function value a2 shift: " + functionValueA2Shift);

            double derivativeA1 = (functionValueA1Shift - functionValue) / DER_EPS_1;
            double derivativeA2 = (functionValueA2Shift - functionValue) / DER_EPS_2;
            double derivativeA3 = (functionValueA3Shift - functionValue) / DER_EPS_3;
            System.out.println("Gradient: (" + derivativeA1 + ", " + derivativeA2 + ", " + derivativeA3 + ")");

            double gradientNorm = new ThreeDoubleVector(derivativeA1, derivativeA2, derivativeA3).getNorm();
            System.out.println("Gradient norm: " + gradientNorm);
            if (gradientNorm < e) {
                System.out.println("Gradient is small, the end of cycle");
                break;
            }

            if (values.size() >= 5 &&
                    Math.abs(values.get(values.size() - 1) - values.get(values.size() - 5)) < e) {
                System.out.println("Value changes insignificantly, the end of cycle");
                break;
            }

//            double t = tStart;
            while (true) {
                double a1next = a1 + t * derivativeA1 / gradientNorm;
                double a2next = a2 + t * derivativeA2 / gradientNorm;
                double a3next = a3 + t * derivativeA3 / gradientNorm;
                System.out.println("a1next = " + a1next + "; a2next = " + a2next + "; a3next = " + a3next);
                if (!JFunctional3.isA1Correct(a1next) || !JFunctional3.isA2Correct(a2next) || !JFunctional3.isA3Correct(a3next)) {
                    t /= 2.0;
                    System.out.println("Incorrect values, decreasing t: t = " + t);

                    if (t < 1e-20) {
                        System.out.println("t became zero");
                        break outer;
                    }
                    continue;
                }

                nextFunctionValue = function.apply(new ThreeDoubleVector(a1next, a2next, a3next));
                System.out.println("nextFunctionValue = " + nextFunctionValue);

                if (nextFunctionValue > functionValue) {
                    a1 = a1next;
                    a2 = a2next;
                    a3 = a3next;
                    break;
                } else {
                    t /= 2.0;
                    System.out.println("Too big t, decreasing: t = " + t);
                }

                if (t < 1e-20) {
                    System.out.println("t became zero");
                    break outer;
                }
            }

            System.out.println("tFinal = " + t);
            System.out.println();
        }

        return new Result(a1, a2, a3, nextFunctionValue);
    }

    public static class Result {
        private double a1;
        private double a2;
        private double a3;
        private double functionValue;

        public Result(double a1, double a2, double a3, double functionValue) {
            this.a1 = a1;
            this.a2 = a2;
            this.a3 = a3;
            this.functionValue = functionValue;
        }

        public double getA1() {
            return a1;
        }

        public double getA2() {
            return a2;
        }

        public double getA3() {
            return a3;
        }

        public double getFunctionValue() {
            return functionValue;
        }
    }

}
