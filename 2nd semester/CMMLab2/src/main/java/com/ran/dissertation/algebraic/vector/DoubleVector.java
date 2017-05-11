package com.ran.dissertation.algebraic.vector;

import com.ran.dissertation.algebraic.common.AlgebraicObject;
import com.ran.dissertation.algebraic.common.ArithmeticOperations;
import com.ran.dissertation.algebraic.exception.AlgebraicException;
import com.ran.dissertation.algebraic.exception.CreationException;
import java.util.Arrays;

public class DoubleVector implements Comparable<DoubleVector>, AlgebraicObject<DoubleVector> {

    private double[] coordinates;
    
    public DoubleVector(double... coordinates) {
        if (coordinates == null) {
            throw new CreationException("Initialization parameters should be not null");
        }
        this.coordinates = coordinates;
    }
    
    public int getDimension() {
        return coordinates.length;
    }
    
    public double getCoordinate(int index) {
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
        DoubleVector other = (DoubleVector) obj;
        if (this.getDimension() != other.getDimension()) {
            return false;
        }
        for (int i = 0; i < this.getDimension(); i++) {
            if (ArithmeticOperations.doubleNotEquals(this.getCoordinate(i), other.getCoordinate(i))) {
                return false;
            }
        }
        return true;
    }
    
    @Override
    public int compareTo(DoubleVector other) {
        checkDimensions(this, other);
        for (int i = 0; i < this.getDimension(); i++) {
            int comparisingResult = ArithmeticOperations.doubleCompare(this.getCoordinate(i), other.getCoordinate(i));
            if (comparisingResult != 0) {
                return comparisingResult;
            }
        }
        return 0;
    }
    
    @Override
    public DoubleVector add(DoubleVector other) {
        checkDimensions(this, other);
        double[] resultCoordinates = new double[this.getDimension()];
        for (int i = 0; i < this.getDimension(); i++) {
            resultCoordinates[i] = this.getCoordinate(i) + other.getCoordinate(i);
        }
        return new DoubleVector(resultCoordinates);
    }
    
    @Override
    public DoubleVector substract(DoubleVector other) {
        checkDimensions(this, other);
        double[] resultCoordinates = new double[this.getDimension()];
        for (int i = 0; i < this.getDimension(); i++) {
            resultCoordinates[i] = this.getCoordinate(i) - other.getCoordinate(i);
        }
        return new DoubleVector(resultCoordinates);
    }
    
    @Override
    public DoubleVector multiply(double number) {
        double[] resultCoordinates = new double[getDimension()];
        for (int i = 0; i < getDimension(); i++) {
            resultCoordinates[i] = getCoordinate(i) * number;
        }
        return new DoubleVector(resultCoordinates);
    }
    
    @Override
    public DoubleVector multiply(int number) {
        return multiply((double)number);
    }

    @Override
    public DoubleVector elementWiseMultiply(DoubleVector other) {
        checkDimensions(this, other);
        double[] resultCoordinates = new double[this.getDimension()];
        for (int i = 0; i < this.getDimension(); i++) {
            resultCoordinates[i] = this.getCoordinate(i) * other.getCoordinate(i);
        }
        return new DoubleVector(resultCoordinates);
    }
    
    @Override
    public double scalarMultiply(DoubleVector other) {
        checkDimensions(this, other);
        double result = 0;
        for (int i = 0; i < getDimension(); i++) {
            result += this.getCoordinate(i) * other.getCoordinate(i);
        }
        return result;
    }
    
    @Override
    public DoubleVector multiply(DoubleVector other) {
        throw new UnsupportedOperationException();
    }
    
    private static void checkDimensions(DoubleVector first, DoubleVector second) {
        if (first.getDimension() != second.getDimension()) {
            throw new AlgebraicException("Comparising of vectors with different dimensions");
        }
    }

    @Override
    public String toString() {
        return "DoubleVector{" + "coordinates=" + Arrays.toString(coordinates) + '}';
    }
    
}