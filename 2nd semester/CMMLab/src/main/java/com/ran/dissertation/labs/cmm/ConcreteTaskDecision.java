package com.ran.dissertation.labs.cmm;

import java.util.List;
import java.util.function.DoubleFunction;

public class ConcreteTaskDecision {

    private DoubleFunction<Double> exactSolution;
    private List<DoubleFunction<Double>> approximations;

    public ConcreteTaskDecision(DoubleFunction<Double> exactSolution, List<DoubleFunction<Double>> approximations) {
        this.exactSolution = exactSolution;
        this.approximations = approximations;
    }

    public DoubleFunction<Double> getExactSolution() {
        return exactSolution;
    }

    public List<DoubleFunction<Double>> getApproximations() {
        return approximations;
    }

}