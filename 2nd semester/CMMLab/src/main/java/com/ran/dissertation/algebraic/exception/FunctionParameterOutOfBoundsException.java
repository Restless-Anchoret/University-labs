package com.ran.dissertation.algebraic.exception;

public class FunctionParameterOutOfBoundsException extends AlgebraicException {

    public FunctionParameterOutOfBoundsException(double point, double minBound, double maxBound) {
        super("Point " + point + " is out of bounds " + minBound + " and " + maxBound);
    }

}