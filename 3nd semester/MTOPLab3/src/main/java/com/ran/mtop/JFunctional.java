package com.ran.mtop;

import com.codepoetics.protonpack.StreamUtils;
import com.ran.engine.algebra.vector.TwoDoubleVector;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JFunctional {

    public static double evaluate(List<List<TwoDoubleVector>> resultList) {
        List<Double> xL = resultList.stream()
                .map(list -> list.get(list.size() - 1).getY())
                .collect(Collectors.toList());
        System.out.println("xL = " + xL);
        List<Double> q = Arrays.asList(0.78, 1.4, 1.4);
        List<Double> m = Arrays.asList(84d, 56d, 42d, 28d, 92d, 16d);
        List<Double> mSlice = m.subList(1, 4);
        List<List<TwoDoubleVector>> xSlice = resultList.subList(1, 4);
        Stream<Double> mx = StreamUtils.zip(mSlice.stream(), xSlice.stream(), (mi, xi) -> mi * xi.get(xi.size() - 1).getY());
        double numerator = StreamUtils.zip(q.stream(), mx, (qi, mxi) -> qi * mxi).reduce((a, b) -> a + b).get();
        double divisor = StreamUtils.zip(m.stream(), resultList.stream(), (mi, xi) -> mi * xi.get(xi.size() - 1).getY())
                .reduce((a, b) -> a + b).get();
        return numerator / divisor;
    }

}
