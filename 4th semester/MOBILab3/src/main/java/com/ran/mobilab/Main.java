package com.ran.mobilab;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        Algorithms algorithms = new Algorithms();
        List<Result> exponentiationTimeResults = algorithms.algorithmTest(
                algorithms::binaryExponentiationTime, 0, 40_000, 20);
        List<Result> decompositionTimeResults = algorithms.algorithmTest(
                algorithms::binaryDecompositionDegreeTime, 0, 40_000, 20);
        exponentiationTimeResults.forEach(result -> System.out.println(result.getTime()));
        decompositionTimeResults.forEach(result -> System.out.println(result.getTime()));
    }
}
