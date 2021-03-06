package com.ran.mobilab;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Algorithms {
    
    private static final long A = 3;
    private static final long N = 423656;
    
    public List<Result> algorithmTest(
            Function<BigInteger, Result> algorithm,
            int from, int to, int steps) {
        List<Result> results = new ArrayList<>();
        int step = (to - from) / steps;
        for (int i = 0; i <= steps; i++) {
            int order = from + step * i;
            System.out.println("Step #" + i + ", n = " + order);
            BigInteger degree = BigInteger.valueOf(10).pow(order);
            Result result = algorithm.apply(degree);
            System.out.println("Time for n = " + order + ": " + result.getTime() + "; result: " + result.getValue());
            System.out.println();
            results.add(result);
        }
        return results;
    }
    
    public Result binaryDecompositionDegreeTime(BigInteger degree) {
        long start = System.currentTimeMillis();
        long result = 1;
        if (!degree.equals(BigInteger.ZERO)) {
            String binaryDegree = degree.toString(2);
            int k = binaryDegree.length() - 1;
            long a = A;
            for (int i = 1; i <= k; i++) {
                if (binaryDegree.charAt(i) == '0') {
                    a = (a * a) % N;
                } else {
                    a = (a * a * A) % N;
                }
            }
            result = a;
        }
        long end = System.currentTimeMillis();
        return new Result(degree, end - start, result);
    }
    
    public Result binaryExponentiationTime(BigInteger degree) {
        long start = System.currentTimeMillis();
        long a = A;
        BigInteger x = degree;
        long p = 1;
        do {
            if (x.equals(BigInteger.ZERO)) {
                break;
            } else if (x.mod(BigInteger.valueOf(2)).equals(BigInteger.ONE)) {
                p = (p * a) % N;
                x = x.subtract(BigInteger.ONE).divide(BigInteger.valueOf(2));
                a = (a * a) % N;
            } else {
                x = x.divide(BigInteger.valueOf(2));
                a = (a * a) % N;
            }
        } while (true);
        long end = System.currentTimeMillis();
        return new Result(degree, end - start, p);
    }
}

class Result {
    
    private BigInteger degree;
    private long time;
    private long value;

    public Result(BigInteger degree, long time, long value) {
        this.degree = degree;
        this.time = time;
        this.value = value;
    }

    public BigInteger getDegree() {
        return degree;
    }

    public long getTime() {
        return time;
    }

    public long getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "Result{" + "degree=" + degree + ", time=" + time + ", value=" + value + '}';
    }
}
