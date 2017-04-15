package com.ran.dissertation.labs.cmm;

import java.util.HashMap;
import java.util.Map;

public class LegendrePolynomsFactory {

//    private static List<DoubleFunction<Double>> polynoms = new ArrayList<>(6);
//
//    static {
//        polynoms.add(t -> 1.0);
//        polynoms.add(t -> t);
//        polynoms.add(t -> t * t * 1.5 - 0.5);
//        polynoms.add(t -> t * t * t * 2.5 - t * 1.5);
//        polynoms.add(t -> t * t * t * t * 35.0 / 8.0 - t * t * 15.0 / 4.0 + 3.0 / 8.0);
//        polynoms.add(t -> t * t * t * t * t * 63.0 / 8.0 - t * t * t * 35.0 / 4.0 + t * 15.0 / 8.0);
//    }

    private Map<Integer, Polynom> polynomMap = new HashMap<>();

    public LegendrePolynomsFactory() {
        polynomMap.put(0, new Polynom(new double[]{1.0}));
        polynomMap.put(1, new Polynom(new double[]{0.0, 1.0}));
    }

    public Polynom getLegendrePolynomOfDegree(int n) {
        if (polynomMap.containsKey(n)) {
            return polynomMap.get(n);
        }
        Polynom previousPolynom = getLegendrePolynomOfDegree(n - 1);
        Polynom beforePreviousPolynom = getLegendrePolynomOfDegree(n - 2);
        Polynom resultPolynom = previousPolynom.multiplyByX().multiplyByNumber((double)(2 * n + 1) / (double)(n + 1))
                .add(beforePreviousPolynom.multiplyByNumber((double)(-n) / (double)(n + 1)));
        polynomMap.put(n, resultPolynom);
//        System.out.println("Polynom of degree " + n + ": " + resultPolynom);
        return resultPolynom;
    }

}