package com.ran.dissertation.labs.cmm;

import java.util.function.DoubleFunction;

public class DerivativeGetter {

    private static final double DERIVATIVE_EPSILON = 1e-8;

    private static final DerivativeGetter INSTANCE = new DerivativeGetter();

    public static DerivativeGetter getInstance() {
        return INSTANCE;
    }

    private DerivativeGetter() { }

    public DoubleFunction<Double> getDerivative(DoubleFunction<Double> function) {
        return t -> (function.apply(t + DERIVATIVE_EPSILON) - function.apply(t)) / DERIVATIVE_EPSILON;
    }

}
