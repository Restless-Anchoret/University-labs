package com.ran.dissertation.algebraic.vector;

import com.ran.dissertation.algebraic.common.AlgebraicObject;
import com.ran.dissertation.algebraic.common.ArithmeticOperations;

public class SingleDouble implements Comparable<SingleDouble>, AlgebraicObject<SingleDouble> {

    public static final SingleDouble ONE = new SingleDouble(1.0);
    public static final SingleDouble ZERO = new SingleDouble(0.0);
    
    private final double value;
    
    public SingleDouble(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (int) (Double.doubleToLongBits(this.value) ^ (Double.doubleToLongBits(this.value) >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SingleDouble other = (SingleDouble) obj;
        return ArithmeticOperations.doubleEquals(this.value, other.value);
    }
    
    @Override
    public int compareTo(SingleDouble other) {
        return Double.compare(this.value, other.value);
    }

    @Override
    public SingleDouble add(SingleDouble other) {
        return new SingleDouble(this.value + other.getValue());
    }

    @Override
    public SingleDouble substract(SingleDouble other) {
        return new SingleDouble(this.value - other.getValue());
    }

    @Override
    public SingleDouble multiply(int number) {
        return new SingleDouble(this.value * number);
    }

    @Override
    public SingleDouble multiply(double number) {
        return new SingleDouble(this.value * number);
    }

    @Override
    public SingleDouble multiply(SingleDouble other) {
        return new SingleDouble(this.value * other.getValue());
    }

    @Override
    public SingleDouble elementWiseMultiply(SingleDouble other) {
        return multiply(other);
    }

    @Override
    public double scalarMultiply(SingleDouble other) {
        return this.value * other.getValue();
    }

    @Override
    public double getNorm() {
        return Math.abs(value);
    }

    @Override
    public SingleDouble normalized() {
        return ONE;
    }

    @Override
    public String toString() {
        return "SingleDouble{" + "value=" + value + '}';
    }

}