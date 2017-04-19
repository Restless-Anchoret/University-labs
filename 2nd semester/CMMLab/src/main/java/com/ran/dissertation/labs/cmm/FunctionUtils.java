package com.ran.dissertation.labs.cmm;

import java.util.function.DoubleFunction;

public class FunctionUtils {

    private static final double DERIVATIVE_EPSILON = 1e-4;
    private static final int DEVIATION_COUNTING_STEPS = 1000;

    private FunctionUtils() { }

    public static DoubleFunction<Double> getDerivative(DoubleFunction<Double> function) {
        return t -> (function.apply(t + DERIVATIVE_EPSILON) - function.apply(t)) / DERIVATIVE_EPSILON;
    }

    public static DoubleFunction<Double> getSecondDerivative(DoubleFunction<Double> function) {
        return t -> (function.apply(t + DERIVATIVE_EPSILON) - 2.0 * function.apply(t) +
                function.apply(t - DERIVATIVE_EPSILON)) / (DERIVATIVE_EPSILON * DERIVATIVE_EPSILON);
    }

    public static double getDeviation(DoubleFunction<Double> firstFunction,
                                DoubleFunction<Double> secondFunction,
                                double a, double b) {
        int steps = DEVIATION_COUNTING_STEPS;
        double result = 0.0;
        for (int i = 0; i <= steps; i++) {
            double point = a + i * (b - a) / steps;
            result = Math.max(result, Math.abs(firstFunction.apply(point) - secondFunction.apply(point)));
        }
        return result;
    }

}
