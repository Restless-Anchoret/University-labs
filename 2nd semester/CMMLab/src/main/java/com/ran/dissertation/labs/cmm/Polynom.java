package com.ran.dissertation.labs.cmm;

import java.util.Arrays;
import java.util.function.DoubleFunction;

public class Polynom implements DoubleFunction<Double> {

    private double[] factors;

    public Polynom(double[] factors) {
        this.factors = factors;
    }

    public double[] getFactors() {
        return factors;
    }

    public double getFactor(int i) {
        if (i < factors.length) {
            return factors[i];
        } else {
            return 0.0;
        }
    }

    public int getFactorsQuantity() {
        return factors.length;
    }

    public int getDegree() {
        return factors.length - 1;
    }

    @Override
    public Double apply(double value) {
        double result = 0.0;
        double valueInDegree = 1.0;
        for (int i = 0; i <= getDegree(); i++) {
            result += valueInDegree * factors[i];
            if (i < getDegree()) {
                valueInDegree *= value;
            }
        }
        return result;
    }

    public Polynom multiplyByNumber(double number) {
        double[] newFactors = new double[getFactorsQuantity()];
        for (int i = 0; i < newFactors.length; i++) {
            newFactors[i] = this.factors[i] * number;
        }
        return new Polynom(newFactors);
    }

    public Polynom multiplyByX() {
        double[] newFactors = new double[getFactorsQuantity() + 1];
        for (int i = 1; i < newFactors.length; i++) {
            newFactors[i] = this.factors[i - 1];
        }
        newFactors[0] = 0.0;
        return new Polynom(newFactors);
    }

    public Polynom add(Polynom other) {
        double[] newFactors = new double[Math.max(this.getFactorsQuantity(), other.getFactorsQuantity())];
        for (int i = 0; i < newFactors.length; i++) {
            newFactors[i] = this.getFactor(i) + other.getFactor(i);
        }
        return new Polynom(newFactors);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Polynom{");
        sb.append("factors=").append(Arrays.toString(factors));
        sb.append('}');
        return sb.toString();
    }

}