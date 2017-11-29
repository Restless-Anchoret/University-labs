package com.ran.mtop;

import com.codepoetics.protonpack.StreamUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.DoubleFunction;
import java.util.function.Function;

public class SolverCreator {

    private static final double LOW = 373;
    private static final double HIGH = 1500;

    public static RungeKuttaSolver getSolver(double h, DoubleFunction<Double> x7) {
        List<Double> e = Arrays.asList(
                25000.0, 25000.0, 25000.0, 25000.0,
                40000.0, 40000.0, 40000.0,
                20000.0, 20000.0, 20000.0,
                20000.0, 20000.0, 20000.0);
        List<Double> u = Arrays.asList(15.19, 8.18, 13.198, 3.543, 4723.7, 423.7, 204.41, 0.000001466, 0.013, 0.09, 0.000005428, 0.024, 0.00000592);
        List<DoubleFunction<Double>> r = new ArrayList<>();
        for (int index = 0; index < 13; index++) {
            double ui = u.get(index);
            double ei = e.get(index);
            r.add(l -> ui * Math.exp(23.0 - ei / x7.apply(l)));
        }
        double g = 1750.0;
        double gn = 3500.0;
        double m0 = 18.0;
        List<Double> m = Arrays.asList(84.0, 56.0, 42.0, 28.0, 92.0, 16.0);
        Function<Double, Double> p = l -> 5.0 - l / 60.0;
        BiFunction<Double, List<Double>, Double> nu = (l, x) -> {
            double mxSum = StreamUtils.zip(m.stream(), x.stream(), (mi, xi) -> mi * xi)
                    .reduce((a, b) -> a + b).get();
            double sum = mxSum / (x7.apply(l) * (g + gn * mxSum / m0));
            return 509.209 * p.apply(l) * sum;
        };
        List<Double> x0 = Arrays.asList(1.0, 0.0, 0.0, 0.0, 0.0, 0.0);
        List<BiFunction<Double, List<Double>, Double>> f = Arrays.asList(
                (l, x) -> -(r.get(0).apply(l) + r.get(1).apply(l) +
                        r.get(2).apply(l) + r.get(3).apply(l)) * x.get(0) * nu.apply(l, x),
                (l, x) -> (r.get(2).apply(l) * x.get(0) - (r.get(5).apply(l) + r.get(6).apply(l) +
                        r.get(9).apply(l) + r.get(12).apply(l)) * x.get(1)) * nu.apply(l, x),
                (l, x) -> (r.get(1).apply(l) * x.get(0) + r.get(5).apply(l) * x.get(1) -
                        (r.get(4).apply(l) + r.get(8).apply(l) + r.get(11).apply(l)) * x.get(2)) * nu.apply(l, x),
                (l, x) -> (r.get(0).apply(l) * x.get(0) + r.get(6).apply(l) * x.get(1) +
                        r.get(4).apply(l) * x.get(2) - (r.get(7).apply(l) + r.get(10).apply(l)) * x.get(3)) * nu.apply(l, x),
                (l, x) -> (r.get(9).apply(l) * x.get(1) + r.get(8).apply(l) * x.get(2) +
                        r.get(7).apply(l) * x.get(3)) * nu.apply(l, x),
                (l, x) -> (r.get(3).apply(l) * x.get(0) + r.get(12).apply(l) * x.get(1) +
                        r.get(11).apply(l) * x.get(2) + r.get(10).apply(l) * x.get(3)) * nu.apply(l, x)
        );
        int steps = 180 * (int)Math.round(1.0 / h);
        System.out.println("h = " + h + "; steps = " + steps);
        return new RungeKuttaSolver(f, 0.0, x0, h, steps, 6);
    }

    public static DoubleFunction<Double> createX7(double a1, double a2) {
        return l -> {
            if (l <= 90) {
                return LOW + (l / 90.0) * (a1 - LOW);
            } else {
                return a1 + Math.pow((l - 90.0) / 90.0, a2) * (HIGH - a1);
            }
        };
    }

}
