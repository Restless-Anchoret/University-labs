package com.ran.mtop;

import java.util.function.BiFunction;

public class GradientDescent {

    public Result findMaximum(BiFunction<Double, Double, Double> function,
                              double a10, double a20, double t, double e) {
        // todo
        return new Result(a10, a20, 0.0);
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
