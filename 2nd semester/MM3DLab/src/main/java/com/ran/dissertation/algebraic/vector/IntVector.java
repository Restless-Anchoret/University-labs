package com.ran.dissertation.algebraic.vector;

import com.ran.dissertation.algebraic.common.AlgebraicObject;
import com.ran.dissertation.algebraic.exception.AlgebraicException;
import com.ran.dissertation.algebraic.exception.CreationException;
import java.util.Arrays;

public class IntVector implements Comparable<IntVector>, AlgebraicObject<IntVector> {

    private int[] coordinates;
    
    public IntVector(int... coordinates) {
        if (coordinates == null) {
            throw new CreationException("Initialization parameters should be not null");
        }
        this.coordinates = coordinates;
    }
    
    public int getDimension() {
        return coordinates.length;
    }
    
    public int getCoordinate(int index) {
        return coordinates[index];
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Arrays.hashCode(this.coordinates);
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
        IntVector other = (IntVector) obj;
        return Arrays.equals(this.coordinates, other.coordinates);
    }
    
    @Override
    public int compareTo(IntVector other) {
        checkDimensions(this, other);
        for (int i = 0; i < this.getDimension(); i++) {
            int comparisingResult = Integer.compare(this.getCoordinate(i), other.getCoordinate(i));
            if (comparisingResult != 0) {
                return comparisingResult;
            }
        }
        return 0;
    }
    
    @Override
    public IntVector add(IntVector other) {
        checkDimensions(this, other);
        int[] resultCoordinates = new int[this.getDimension()];
        for (int i = 0; i < this.getDimension(); i++) {
            resultCoordinates[i] = this.getCoordinate(i) + other.getCoordinate(i);
        }
        return new IntVector(resultCoordinates);
    }
    
    @Override
    public IntVector substract(IntVector other) {
        checkDimensions(this, other);
        int[] resultCoordinates = new int[this.getDimension()];
        for (int i = 0; i < this.getDimension(); i++) {
            resultCoordinates[i] = this.getCoordinate(i) - other.getCoordinate(i);
        }
        return new IntVector(resultCoordinates);
    }
    
    @Override
    public IntVector multiply(int number) {
        int[] resultCoordinates = new int[getDimension()];
        for (int i = 0; i < getDimension(); i++) {
            resultCoordinates[i] = getCoordinate(i) * number;
        }
        return new IntVector(resultCoordinates);
    }

    @Override
    public IntVector multiply(double number) {
        throw new UnsupportedOperationException();
    }

    @Override
    public IntVector elementWiseMultiply(IntVector other) {
        checkDimensions(this, other);
        int[] resultCoordinates = new int[this.getDimension()];
        for (int i = 0; i < this.getDimension(); i++) {
            resultCoordinates[i] = this.getCoordinate(i) * other.getCoordinate(i);
        }
        return new IntVector(resultCoordinates);
    }
    
    @Override
    public double scalarMultiply(IntVector other) {
        checkDimensions(this, other);
        int result = 0;
        for (int i = 0; i < getDimension(); i++) {
            result += this.getCoordinate(i) * other.getCoordinate(i);
        }
        return result;
    }
    
    @Override
    public IntVector multiply(IntVector other) {
        throw new UnsupportedOperationException();
    }
    
    private static void checkDimensions(IntVector first, IntVector second) {
        if (first.getDimension() != second.getDimension()) {
            throw new AlgebraicException("Comparising of vectors with different dimensions");
        }
    }

    @Override
    public String toString() {
        return "IntVector{" + "coordinates=" + Arrays.toString(coordinates) + '}';
    }
    
}