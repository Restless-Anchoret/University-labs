package com.ran.dissertation.algebraic.vector;

import com.ran.dissertation.algebraic.common.AlgebraicObject;
import com.ran.dissertation.algebraic.exception.CreationException;
import java.util.Objects;

public class TwoIntVector implements Comparable<TwoIntVector>, AlgebraicObject<TwoIntVector> {

    public static final TwoIntVector ZERO_TWO_INT_VECTOR = new TwoIntVector(0, 0);
    
    private final IntVector intVector;
    
    public TwoIntVector(int x, int y) {
        this.intVector = new IntVector(x, y);
    }
    
    public TwoIntVector(IntVector intVector) {
        if (intVector.getDimension() != 2) {
            throw new CreationException("IntVector must be two-dimensional for creating TwoIntVector");
        }
        this.intVector = intVector;
    }
    
    public int getX() {
        return intVector.getCoordinate(0);
    }
    
    public int getY() {
        return intVector.getCoordinate(1);
    }
    
    public IntVector getIntVector() {
        return intVector;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.intVector);
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
        final TwoIntVector other = (TwoIntVector) obj;
        return Objects.equals(this.intVector, other.intVector);
    }

    @Override
    public int compareTo(TwoIntVector other) {
        return this.intVector.compareTo(other.getIntVector());
    }

    @Override
    public TwoIntVector add(TwoIntVector other) {
        return new TwoIntVector(this.intVector.add(other.getIntVector()));
    }

    @Override
    public TwoIntVector substract(TwoIntVector other) {
        return new TwoIntVector(this.intVector.substract(other.getIntVector()));
    }
    
    @Override
    public TwoIntVector multiply(int number) {
        return new TwoIntVector(this.intVector.multiply(number));
    }

    @Override
    public TwoIntVector multiply(double number) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public TwoIntVector multiply(TwoIntVector other) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TwoIntVector elementWiseMultiply(TwoIntVector other) {
        return new TwoIntVector(this.intVector.elementWiseMultiply(other.getIntVector()));
    }

    @Override
    public double scalarMultiply(TwoIntVector other) {
        return this.intVector.scalarMultiply(other.getIntVector());
    }

    @Override
    public String toString() {
        return "TwoIntVector{" + "x=" + getX() + "; y=" + getY() + '}';
    }
    
}