package com.ran.mtop;

import com.ran.engine.algebra.vector.TwoDoubleVector;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class GradientDescent2 {

    private static final double DER_EPS_1 = 0.01;
    private static final double DER_EPS_2 = 1e-4;

    public Result findMaximum(Function<TwoDoubleVector, Double> function,
                              double a10, double a20,
                              double tStart, double e, int maxIterations) {
        double a1 = a10;
        double a2 = a20;
        int iteration = 0;
        double nextFunctionValue = function.apply(new TwoDoubleVector(a1, a2));
        double t = tStart;
        List<Double> values = new ArrayList<>();

        outer: while (true) {
            iteration++;
            if (iteration > maxIterations) {
                break;
            }
            System.out.println("Iteration #" + iteration + ": a1 = " + a1 + "; a2 = " + a2);

            if (iteration % 4 == 0) {
                t *= 4.0;
                t = Math.min(t, 5.0);
            }

            double functionValue = nextFunctionValue;
            System.out.println("Function value: " + functionValue);
            values.add(functionValue);

            double functionValueA1Shift = function.apply(new TwoDoubleVector(a1 + DER_EPS_1, a2));
            double functionValueA2Shift = function.apply(new TwoDoubleVector(a1, a2 + DER_EPS_2));
//            System.out.println("Function value a1 shift: " + functionValueA1Shift);
//            System.out.println("Function value a2 shift: " + functionValueA2Shift);

            double derivativeA1 = (functionValueA1Shift - functionValue) / DER_EPS_1;
            double derivativeA2 = (functionValueA2Shift - functionValue) / DER_EPS_2;
            System.out.println("Gradient: (" + derivativeA1 + ", " + derivativeA2 + ")");

            double gradientNorm = new TwoDoubleVector(derivativeA1, derivativeA2).getNorm();
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
                System.out.println("a1next = " + a1next + "; a2next = " + a2next);
                if (!JFunctional2.isA1Correct(a1next) || !JFunctional2.isA2Correct(a2next)) {
                    t /= 2.0;
                    System.out.println("Incorrect values, decreasing t: t = " + t);

                    if (t < 1e-20) {
                        System.out.println("t became zero");
                        break outer;
                    }
                    continue;
                }

                nextFunctionValue = function.apply(new TwoDoubleVector(a1next, a2next));
                System.out.println("nextFunctionValue = " + nextFunctionValue);

                if (nextFunctionValue > functionValue) {
                    a1 = a1next;
                    a2 = a2next;
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
