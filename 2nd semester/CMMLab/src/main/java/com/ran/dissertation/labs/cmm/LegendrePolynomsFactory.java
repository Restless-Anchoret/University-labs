package com.ran.dissertation.labs.cmm;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleFunction;

public class LegendrePolynomsFactory {

    private static List<DoubleFunction<Double>> polynoms = new ArrayList<>(6);

    static {
        polynoms.add(t -> 1.0);
        polynoms.add(t -> t);
        polynoms.add(t -> t * t * 1.5 - 0.5);
        polynoms.add(t -> t * t * t * 2.5 - t * 1.5);
        polynoms.add(t -> t * t * t * t * 35.0 / 8.0 - t * t * 15.0 / 4.0 + 3.0 / 8.0);
        polynoms.add(t -> t * t * t * t * t * 63.0 / 8.0 - t * t * t * 35.0 / 4.0 + t * 15.0 / 8.0);
    }

    public DoubleFunction<Double> getLegendrePolynomOfDegree(int degree) {
        if (degree < polynoms.size()) {
            return polynoms.get(degree);
        } else {
            return null;
        }
    }

}