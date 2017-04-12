package com.ran.dissertation.labs.cmm;

import java.util.List;
import java.util.function.DoubleFunction;

public class ConcreteTaskDecision {

    private DoubleFunction<Double> exactDecision;
    private List<DoubleFunction<Double>> approximations;

    public ConcreteTaskDecision(DoubleFunction<Double> exactDecision, List<DoubleFunction<Double>> approximations) {
        this.exactDecision = exactDecision;
        this.approximations = approximations;
    }

    public DoubleFunction<Double> getExactDecision() {
        return exactDecision;
    }

    public List<DoubleFunction<Double>> getApproximations() {
        return approximations;
    }

}