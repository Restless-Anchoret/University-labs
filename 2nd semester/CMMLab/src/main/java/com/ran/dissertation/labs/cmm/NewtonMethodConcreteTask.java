package com.ran.dissertation.labs.cmm;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleFunction;

public class NewtonMethodConcreteTask {

    private CollocationsMethod collocationsMethod = new CollocationsMethod();

    public ConcreteTaskDecision solve(int iterations, int collocationsMethodDegree) {
        DoubleFunction<Double> exactSolution = t -> -Math.sqrt(1 - t * t);
        List<DoubleFunction<Double>> approximations = new ArrayList<>(iterations + 1);

//        DoubleFunction<Double> y0NotConverted = t -> t * t - t;
//        DoubleFunction<Double> y0 = t -> y0NotConverted.apply((t + 1) / 2.0);
        DoubleFunction<Double> y0 = t -> 0.0;
        DoubleFunction<Double> currentY = y0;
        approximations.add(currentY);

        DoubleFunction<Double> y0I = DerivativeGetter.getInstance().getDerivative(y0);
        DoubleFunction<Double> z = t -> 3.0 * (1.0 + 2.0 * y0I.apply(t)) *
                Math.sqrt(Math.pow(1.0 + 2.0 * y0I.apply(t), 2.0) + 1.0);

        DoubleFunction<Double> p = t -> 4.0;
        DoubleFunction<Double> q = t -> -2.0 * z.apply(t);
        DoubleFunction<Double> r = t -> 0.0;

        for (int i = 0; i < iterations; i++) {
            DoubleFunction<Double> currentYI = DerivativeGetter.getInstance().getDerivative(currentY);
            DoubleFunction<Double> g = t -> -2.0 * z.apply(t) * currentYI.apply(t) +
                    Math.pow(Math.pow(1.0 + 2.0 * currentYI.apply(t), 2.0) + 1.0, 1.5);
            DoubleFunction<Double> nextY = collocationsMethod.solve(p, q, r, g, collocationsMethodDegree);
            approximations.add(nextY);
            currentY = nextY;
        }

        List<DoubleFunction<Double>> fixedApproximations = new ArrayList<>(iterations + 1);
        for (int i = 0; i <= iterations; i++) {
            DoubleFunction<Double> currentApproximation = approximations.get(i);
            DoubleFunction<Double> fixedApproximation =
                    t -> currentApproximation.apply(2.0 * t - 1.0) + (t - 1);
            fixedApproximations.add(fixedApproximation);
        }

        return new ConcreteTaskDecision(exactSolution, fixedApproximations);
    }

}