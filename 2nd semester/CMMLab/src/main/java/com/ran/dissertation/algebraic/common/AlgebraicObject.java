package com.ran.dissertation.algebraic.common;

import com.ran.dissertation.algebraic.exception.AlgebraicException;

public interface AlgebraicObject<T> {

    T add(T other);
    T substract(T other);
    T multiply(int number);
    T multiply(double number);
    T multiply(T other);
    T elementWiseMultiply(T other);
    double scalarMultiply(T other);
    
    default double getNorm() {
        return Math.sqrt(this.scalarMultiply((T)this));
    }
    
    default T normalized() {
        double norm = this.getNorm();
        if (ArithmeticOperations.doubleLessOrEquals(norm, 0.0)) {
            throw new AlgebraicException("Cannot normalize object with null norm");
        }
        return this.multiply(1.0 / norm);
    }
    
    default boolean isZero() {
        return (ArithmeticOperations.doubleEquals(this.getNorm(), 0.0));
    }
    
}
