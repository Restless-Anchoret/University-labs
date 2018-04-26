package com.ran.mobilab;

public class Main {

    public static void main(String[] args) {
        Algorithms algorithms = new Algorithms();
        System.out.println("First algorithm (n in: 0 - 2_000_000).\n");
        algorithms.algorithmTest(algorithms::binaryDecompositionDegreeTime, 0, 2_000_000, 50);
        System.out.println("First algorithm (n in: 0 - 40_000).\n");
        algorithms.algorithmTest(algorithms::binaryDecompositionDegreeTime, 0, 40_000, 10);
        System.out.println("Second algorithm (n in: 0 - 40_000).\n");
        algorithms.algorithmTest(algorithms::binaryExponentiationTime, 0, 40_000, 10);
    }
}
