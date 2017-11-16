package com.ran.mtop;

import com.codepoetics.protonpack.StreamUtils;
import com.ran.engine.algebra.vector.TwoDoubleVector;
import com.ran.engine.opengl.handlers.mouse.CameraControlMode;
import com.ran.engine.opengl.runner.OpenGLRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        RungeKuttaSolver rungeKuttaSolver = getSolver();
        List<List<TwoDoubleVector>> resultList = rungeKuttaSolver.solve();
        OpenGLRunner runner = new OpenGLRunner(
                Collections.singletonList(new MtopWorldFactory(resultList)),
                CameraControlMode.TWO_DIMENSION);
        runner.init();
        runner.run();
    }

    private static RungeKuttaSolver getSolver() {
        List<Double> e = Arrays.asList(25000.0, 25000.0, 25000.0, 25000.0,
                40000.0, 40000.0, 40000.0,
                20000.0, 20000.0, 20000.0,
                20000.0, 20000.0, 20000.0);
        List<Double> u = Arrays.asList(15.19, 8.18, 13.198, 3.543, 4723.7, 423.7, 204.41, 1.466e-6, 0.013, 0.09, 5.428e-6, 0.024, 5.92e-6);
        double x7 = 900.0;
        List<Double> r = new ArrayList<>();
        for (int i = 0; i < 13; i++) {
            r.add(u.get(i) * Math.exp(23.0 - e.get(i) / x7));
        }
        double g = 1750.0;
        double gn = 3500.0;
        double m0 = 18d;
        List<Double> m = Arrays.asList(84d, 56d, 42d, 28d, 92d, 16d);
        Function<Double, Double> p = l -> 5.0 - l / 60.0;
        BiFunction<Double, List<Double>, Double> nu = (l, x) -> {
            List<Double> mx = StreamUtils.zip(m.stream(), x.stream(), (mi, xi) -> mi * xi).collect(Collectors.toList());
            double mxSum = mx.stream().reduce((a, b) -> a + b).get();
            double sum = mx.stream()
                    .map(mxi -> mxi / (x7 * (g + gn * mxSum / m0)))
                    .reduce((a, b) -> a + b).get();
            return 509.209 * p.apply(l) * sum;
        };
        List<Double> x0 = Arrays.asList(1.0, 0.0, 0.0, 0.0, 0.0, 0.0);
        List<BiFunction<Double, List<Double>, Double>> f = Arrays.asList(
                (l, x) -> -(r.get(0) + r.get(1) + r.get(2) + r.get(3)) * x.get(0) * nu.apply(l, x),
                (l, x) -> (r.get(2) * x.get(0) - (r.get(5) + r.get(6) + r.get(9) + r.get(12)) * x.get(0)) * nu.apply(l, x),
                (l, x) -> (r.get(1) * x.get(0) + r.get(5) * x.get(1) - (r.get(4) + r.get(8) + r.get(11)) * x.get(2)) * nu.apply(l, x),
                (l, x) -> (r.get(0) * x.get(0) + r.get(6) * x.get(1) + r.get(4) * x.get(2) - (r.get(7) + r.get(10)) * x.get(3)) * nu.apply(l, x),
                (l, x) -> (r.get(9) * x.get(1) + r.get(8) * x.get(2) + r.get(7) * x.get(3)) * nu.apply(l, x),
                (l, x) -> (r.get(3) * x.get(0) + r.get(12) * x.get(1) + r.get(11) * x.get(2) + r.get(10) * x.get(3)) * nu.apply(l, x)
        );
        return new RungeKuttaSolver(f, 0.0, x0, 0.1, 1800, 6);
    }

}
