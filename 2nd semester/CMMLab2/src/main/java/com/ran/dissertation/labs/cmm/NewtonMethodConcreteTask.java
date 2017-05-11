package com.ran.dissertation.labs.cmm;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleFunction;

public class NewtonMethodConcreteTask {

    private CollocationsMethod collocationsMethod = new CollocationsMethod();

    public ConcreteTaskDecision solve(double newtonEpsilon, double collocationsEpsilon,
                                      int newtonMaxIterations) {
        DoubleFunction<Double> exactSolution = t -> Math.sqrt(4.0 - t * t);
        List<DoubleFunction<Double>> approximations = new ArrayList<>();

        DoubleFunction<Double> y0 = t -> 0.0;
        DoubleFunction<Double> currentY = y0;
        approximations.add(fixApproximation(currentY));

        //DoubleFunction<Double> y0I = FunctionUtils.getDerivative(y0);
        DoubleFunction<Double> z = t -> 12.0 * Math.pow(2.0 + 0.5 * (t + 1.0) * (Math.sqrt(3.0) - 2.0) + y0.apply(t), -4.0);

        DoubleFunction<Double> p = t -> 4.0;
        DoubleFunction<Double> q = t -> 0.0;
        DoubleFunction<Double> r = t -> -z.apply(t);

        double deviation = Double.MAX_VALUE;
        int iterations = 0;
        while (deviation > newtonEpsilon && iterations < newtonMaxIterations) {
            iterations++;
            System.out.println("Newton iteration #" + iterations + " started.");
            DoubleFunction<Double> currentYForG = currentY;
            DoubleFunction<Double> g = t -> -z.apply(t) * currentYForG.apply(t) -
                    4.0 * Math.pow(2.0 + 0.5 * (t + 1.0) * (Math.sqrt(3.0) - 2.0) + currentYForG.apply(t), -3.0);
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
        return t -> approximation.apply(2.0 * t - 1.0) + 2.0 + t * (Math.sqrt(3.0) - 2.0);
    }

}