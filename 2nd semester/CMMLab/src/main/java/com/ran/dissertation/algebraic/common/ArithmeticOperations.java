package com.ran.dissertation.algebraic.common;

public class ArithmeticOperations {

    private static double DEFAULT_EPSILON = 1e-10;
    
    private ArithmeticOperations() { }
    
    public static boolean doubleEquals(double first, double second) {
        return doubleEquals(first, second, DEFAULT_EPSILON);
    }
    
    public static boolean doubleNotEquals(double first, double second) {
        return doubleNotEquals(first, second, DEFAULT_EPSILON);
    }
    
    public static boolean doubleLess(double first, double second) {
        return doubleLess(first, second, DEFAULT_EPSILON);
    }
    
    public static boolean doubleGreater(double first, double second) {
        return doubleGreater(first, second, DEFAULT_EPSILON);
    }
    
    public static boolean doubleLessOrEquals(double first, double second) {
        return doubleLessOrEquals(first, second, DEFAULT_EPSILON);
    }
    
    public static boolean doubleGreaterOrEquals(double first, double second) {
        return doubleGreaterOrEquals(first, second, DEFAULT_EPSILON);
    }
    
    public static int doubleCompare(double first, double second) {
        return doubleCompare(first, second, DEFAULT_EPSILON);
    }
    
    public static boolean doubleEquals(double first, double second, double epsilon) {
        return Math.abs(first - second) < epsilon;
    }
    
    public static boolean doubleNotEquals(double first, double second, double epsilon) {
        return !doubleEquals(first, second, epsilon);
    }
    
    public static boolean doubleLess(double first, double second, double epsilon) {
        return second - first > epsilon;
    }
    
    public static boolean doubleGreater(double first, double second, double epsilon) {
        return first - second > epsilon;
    }
    
    public static boolean doubleLessOrEquals(double first, double second, double epsilon) {
        return !doubleGreater(first, second, epsilon);
    }
    
    public static boolean doubleGreaterOrEquals(double first, double second, double epsilon) {
        return !doubleLess(first, second, epsilon);
    }
    
    public static int doubleCompare(double first, double second, double epsilon) {
        if (doubleEquals(first, second, epsilon)) {
            return 0;
        } else if (doubleLess(first, second, epsilon)) {
            return -1;
        } else {
            return 1;
        }
    }
    
}