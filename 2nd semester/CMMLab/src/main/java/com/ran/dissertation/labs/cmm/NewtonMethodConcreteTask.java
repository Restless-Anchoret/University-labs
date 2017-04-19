package com.ran.dissertation.labs.cmm;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleFunction;

public class NewtonMethodConcreteTask {

    private CollocationsMethod collocationsMethod = new CollocationsMethod();

    public ConcreteTaskDecision solve(double newtonEpsilon, double collocationsEpsilon,
                                      int newtonMaxIterations) {
        DoubleFunction<Double> exactSolution = t -> -Math.sqrt(1 - t * t);
        List<DoubleFunction<Double>> approximations = new ArrayList<>();

        DoubleFunction<Double> y0 = t -> 0.0;
        DoubleFunction<Double> currentY = y0;
        approximations.add(fixApproximation(currentY));

        DoubleFunction<Double> y0I = FunctionUtils.getDerivative(y0);
        DoubleFunction<Double> z = t -> 3.0 * (1.0 + 2.0 * y0I.apply(t)) *
                Math.sqrt(Math.pow(1.0 + 2.0 * y0I.apply(t), 2.0) + 1.0);

        DoubleFunction<Double> p = t -> 4.0;
        DoubleFunction<Double> q = t -> -2.0 * z.apply(t);
        DoubleFunction<Double> r = t -> 0.0;

        double deviation = Double.MAX_VALUE;
        int iterations = 0;
        while (deviation > newtonEpsilon && iterations < newtonMaxIterations) {
            iterations++;
            System.out.println("Newton iteration #" + iterations + " started.");
            DoubleFunction<Double> currentYI = FunctionUtils.getDerivative(currentY);
            DoubleFunction<Double> g = t -> -2.0 * z.apply(t) * currentYI.apply(t) +
                    Math.pow(Math.pow(1.0 + 2.0 * currentYI.apply(t), 2.0) + 1.0, 1.5);
            DoubleFunction<Double> nextY = collocationsMethod.solve(p, q, r, g, collocationsEpsilon);
            DoubleFunction<Double> fixedApproximation = fixApproximation(nextY);
            approximations.add(fixedApproximation);
            deviation = FunctionUtils.getDeviation(fixedApproximation, exactSolution, 0.0, 1.0);
            System.out.println("Newton iteration #" + iterations + ": deviation = " + deviation);
            currentY = nextY;
        }

        System.out.println("Newton method iterations quantity: " + iterations);
        return new ConcreteTaskDecision(exactSolution, approximations);
    }

    private DoubleFunction<Double> fixApproximation(DoubleFunction<Double> approximation) {
        return t -> approximation.apply(2.0 * t - 1.0) + (t - 1);
    }

}