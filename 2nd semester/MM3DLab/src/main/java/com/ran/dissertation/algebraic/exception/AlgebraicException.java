package com.ran.dissertation.algebraic.exception;

public class AlgebraicException extends RuntimeException {

    public AlgebraicException(String message) {
        super(message);
    }

    public AlgebraicException(String message, Throwable cause) {
        super(message, cause);
    }
    
}