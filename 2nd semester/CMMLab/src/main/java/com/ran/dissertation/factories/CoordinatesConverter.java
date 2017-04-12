package com.ran.dissertation.factories;

import com.ran.dissertation.algebraic.vector.ThreeDoubleVector;
import com.ran.dissertation.algebraic.vector.TwoDoubleVector;
import java.util.function.Function;

public enum CoordinatesConverter {
    
    CONVERTER_TO_XY(vector -> new ThreeDoubleVector(vector.getX(), vector.getY(), 0.0)),
    CONVERTER_TO_YX(vector -> new ThreeDoubleVector(vector.getY(), vector.getX(), 0.0)),
    CONVERTER_TO_YZ(vector -> new ThreeDoubleVector(0.0, vector.getX(), vector.getY())),
    CONVERTER_TO_ZY(vector -> new ThreeDoubleVector(0.0, vector.getY(), vector.getX())),
    CONVERTER_TO_XZ(vector -> new ThreeDoubleVector(vector.getX(), 0.0, vector.getY())),
    CONVERTER_TO_ZX(vector -> new ThreeDoubleVector(vector.getY(), 0.0, vector.getX()));

    private Function<TwoDoubleVector, ThreeDoubleVector> converter;
    
    private CoordinatesConverter(Function<TwoDoubleVector, ThreeDoubleVector> converter) {
        this.converter = converter;
    }
    
    public ThreeDoubleVector convert(TwoDoubleVector twoDoubleVector) {
        return converter.apply(twoDoubleVector);
    }
    
    public ThreeDoubleVector convert(double x, double y) {
        return converter.apply(new TwoDoubleVector(x, y));
    }
    
}