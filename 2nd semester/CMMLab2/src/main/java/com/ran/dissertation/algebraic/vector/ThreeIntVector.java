package com.ran.dissertation.algebraic.vector;

import com.ran.dissertation.algebraic.common.AlgebraicObject;
import com.ran.dissertation.algebraic.exception.CreationException;
import java.util.Objects;

public class ThreeIntVector implements Comparable<ThreeIntVector>, AlgebraicObject<ThreeIntVector> {

    public static final ThreeIntVector ZERO_THREE_INT_VECTOR = new ThreeIntVector(0, 0, 0);
    
    private final IntVector intVector;
    
    public ThreeIntVector(int x, int y, int z) {
        this.intVector = new IntVector(x, y, z);
    }
    
    public ThreeIntVector(IntVector intVector) {
        if (intVector.getDimension() != 3) {
            throw new CreationException("IntVector must be three-dimensional for creating ThreeIntVector");
        }
        this.intVector = intVector;
    }
    
    public int getX() {
        return intVector.getCoordinate(0);
    }
    
    public int getY() {
        return intVector.getCoordinate(1);
    }
    
    public int getZ() {
        return intVector.getCoordinate(2);
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
        final ThreeIntVector other = (ThreeIntVector) obj;
        return Objects.equals(this.intVector, other.intVector);
    }

    @Override
    public int compareTo(ThreeIntVector other) {
        return this.intVector.compareTo(other.getIntVector());
    }

    @Override
    public ThreeIntVector add(ThreeIntVector other) {
        return new ThreeIntVector(this.intVector.add(other.getIntVector()));
    }

    @Override
    public ThreeIntVector substract(ThreeIntVector other) {
        return new ThreeIntVector(this.intVector.substract(other.getIntVector()));
    }
    
    @Override
    public ThreeIntVector multiply(int number) {
        return new ThreeIntVector(this.intVector.multiply(number));
    }

    @Override
    public ThreeIntVector multiply(double number) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public ThreeIntVector multiply(ThreeIntVector other) {
        int x = this.getZ() * other.getY() - this.getY() * other.getZ();
        int y = this.getX() * other.getZ() - this.getZ() * other.getX();
        int z = this.getY() * other.getX() - this.getX() * other.getY();
        return new ThreeIntVector(x, y, z);
    }

    @Override
    public ThreeIntVector elementWiseMultiply(ThreeIntVector other) {
        return new ThreeIntVector(this.intVector.elementWiseMultiply(other.getIntVector()));
    }

    @Override
    public double scalarMultiply(ThreeIntVector other) {
        return this.intVector.scalarMultiply(other.getIntVector());
    }

    @Override
    public String toString() {
        return "ThreeIntVector{" + "x=" + getX() + "; y=" + getY() + "; z=" + getZ() + '}';
    }
    
}