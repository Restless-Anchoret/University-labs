package com.ran.dissertation.algebraic.vector;

import com.ran.dissertation.algebraic.common.AlgebraicObject;
import com.ran.dissertation.algebraic.exception.CreationException;
import java.util.Objects;

public class TwoDoubleVector implements Comparable<TwoDoubleVector>, AlgebraicObject<TwoDoubleVector> {

    public static final TwoDoubleVector ZERO_TWO_DOUBLE_VECTOR = new TwoDoubleVector(0.0, 0.0);
    
    private final DoubleVector doubleVector;
    
    public TwoDoubleVector(double x, double y) {
        this.doubleVector = new DoubleVector(x, y);
    }
    
    public TwoDoubleVector(DoubleVector doubleVector) {
        if (doubleVector.getDimension() != 2) {
            throw new CreationException("DoubleVector must be two-dimensional for creating TwoDoubleVector");
        }
        this.doubleVector = doubleVector;
    }
    
    public double getX() {
        return doubleVector.getCoordinate(0);
    }
    
    public double getY() {
        return doubleVector.getCoordinate(1);
    }
    
    public DoubleVector getDoubleVector() {
        return doubleVector;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.doubleVector);
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
        final TwoDoubleVector other = (TwoDoubleVector) obj;
        return Objects.equals(this.doubleVector, other.getDoubleVector());
    }

    @Override
    public int compareTo(TwoDoubleVector other) {
        return this.doubleVector.compareTo(other.getDoubleVector());
    }

    @Override
    public TwoDoubleVector add(TwoDoubleVector other) {
        return new TwoDoubleVector(this.doubleVector.add(other.getDoubleVector()));
    }

    @Override
    public TwoDoubleVector substract(TwoDoubleVector other) {
        return new TwoDoubleVector(this.doubleVector.substract(other.getDoubleVector()));
    }
    
    @Override
    public TwoDoubleVector multiply(int number) {
        return new TwoDoubleVector(this.doubleVector.multiply(number));
    }

    @Override
    public TwoDoubleVector multiply(double number) {
        return new TwoDoubleVector(this.doubleVector.multiply(number));
    }
    
    @Override
    public TwoDoubleVector multiply(TwoDoubleVector other) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TwoDoubleVector elementWiseMultiply(TwoDoubleVector other) {
        return new TwoDoubleVector(this.doubleVector.elementWiseMultiply(other.getDoubleVector()));
    }

    @Override
    public double scalarMultiply(TwoDoubleVector other) {
        return this.doubleVector.scalarMultiply(other.getDoubleVector());
    }

    @Override
    public String toString() {
        return "TwoDoubleVector{" + "x=" + getX() + "; y=" + getY() + '}';
    }
    
}