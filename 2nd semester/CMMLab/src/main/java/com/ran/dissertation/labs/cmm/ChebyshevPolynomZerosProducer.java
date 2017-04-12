package com.ran.dissertation.labs.cmm;

public class ChebyshevPolynomZerosProducer {

    public double[] produceZeros(int n) {
        double[] zeros = new double[n];
        for (int k = 0; k < n; k++) {
            zeros[k] = Math.cos((2.0 * k + 1.0) / (2.0 * n) * Math.PI);
        }
        return zeros;
    }

}