package com.ran.mtop;

import com.ran.engine.algebra.vector.TwoDoubleVector;

import java.util.function.BiFunction;

public class GradientDescent {

    private static final double DER_EPS = 1e-4;

    public Result findMaximum(BiFunction<Double, Double, Double> function,
                              double a10, double a20,
                              double t1, double t2,
                              double e, int maxIterations) {
        double a1 = a10;
        double a2 = a20;
        int iteration = 0;
        double nextFunctionValue = function.apply(a1, a2);

        outer: while (true) {
            iteration++;
            if (iteration > maxIterations) {
                break;
            }
            System.out.println("Iteration #" + iteration + ": a1 = " + a1 + "; a2 = " + a2);

            double functionValue = nextFunctionValue;
            System.out.println("Function value: " + functionValue);

            double functionValueA1Shift = function.apply(a1 + DER_EPS, a2);
            double functionValueA2Shift = function.apply(a1, a2 + DER_EPS);
//            System.out.println("Function value a1 shift: " + functionValueA1Shift);
//            System.out.println("Function value a2 shift: " + functionValueA2Shift);

            double derivativeA1 = (functionValueA1Shift - functionValue) / DER_EPS;
            double derivativeA2 = (functionValueA2Shift - functionValue) / DER_EPS;
            System.out.println("Gradient: (" + derivativeA1 + ", " + derivativeA2 + ")");

            double gradientNorm = new TwoDoubleVector(derivativeA1, derivativeA2).getNorm();
            System.out.println("Gradient norm: " + gradientNorm);
            if (gradientNorm < e) {
                break;
            }

            while (true) {
                double a1next = a1 + t1 * derivativeA1;
                double a2next = a2 + t2 * derivativeA2;
                System.out.println("a1next = " + a1next + "; a2next = " + a2next);
                if (!JFunctional.isA1Correct(a1next) || !JFunctional.isA2Correct(a2next)) {
                    t1 /= 2.0;
                    t2 /= 2.0;
                    System.out.println("Incorrect values, decreasing t: t1 = " + t1 + ", t2 = " + t2);

                    if (t1 < 1e-20 || t2 < 1e-20) {
                        System.out.println("t became zero");
                        break outer;
                    }
                    continue;
                }

                nextFunctionValue = function.apply(a1next, a2next);
                System.out.println("nextFunctionValue = " + nextFunctionValue);

                if (nextFunctionValue > functionValue) {
                    a1 = a1next;
                    a2 = a2next;
                    break;
                } else {
                    t1 /= 2.0;
                    t2 /= 2.0;
                    System.out.println("Too big t, decreasing: t1 = " + t1 + ", t2 = " + t2);
                }

                if (t1 < 1e-20 || t2 < 1e-20) {
                    System.out.println("t became zero");
                    break outer;
                }
            }

            System.out.println("t1 = " + t1 + "; t2 = " + t2);
            System.out.println();
        }

        return new Result(a1, a2, nextFunctionValue);
    }

    public static class Result {
        private double a1;
        private double a2;
        private double functionValue;

        public Result(double a1, double a2, double functionValue) {
            this.a1 = a1;
            this.a2 = a2;
            this.functionValue = functionValue;
        }

        public double getA1() {
            return a1;
        }

        public double getA2() {
            return a2;
        }

        public double getFunctionValue() {
            return functionValue;
        }
    }

}
