package com.ran.dissertation.labs.cmm;

import com.ran.dissertation.algebraic.matrix.DoubleMatrix;
import com.ran.dissertation.algebraic.vector.DoubleVector;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;
import java.util.Random;

public class GaussMethodTest extends Assert {

    @Test
    public void testLittle() {
        testWithParameters(6, 5, 1e-8);
    }

    @Test
    public void testMiddle() {
        testWithParameters(10, 50, 1e-8);
    }

    @Test
    public void testBig() {
        testWithParameters(10, 1000, 1e-8);
    }

    private void testWithParameters(int numbersBound, int dimension, double epsilon) {
        System.out.println("numbersBound = " + numbersBound);
        System.out.println("dimension = " + dimension);
        Random random = new Random(0);
        double[][] a = new double[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                a[i][j] = random.nextDouble() * numbersBound;
            }
        }
        double[] x = new double[dimension];
        for (int i = 0; i < dimension; i++) {
            x[i] = random.nextDouble() * numbersBound;
        }
        DoubleVector vector = new DoubleMatrix(a).multiply(new DoubleVector(x));
        double[] rightSide = new double[dimension];
        for (int i = 0; i < dimension; i++) {
            rightSide[i] = vector.getCoordinate(i);
        }
        GaussMethod gaussMethod = new GaussMethod();
        Date start = new Date();
        double[] solution = gaussMethod.solve(a, rightSide, dimension);
        Date finish = new Date();
        double maxError = 0.0;
        for (int i = 0; i < dimension; i++) {
            maxError = Math.max(maxError, Math.abs(solution[i] - x[i]));
        }
        System.out.println("Time: " + (finish.getTime() - start.getTime()) + " millis");
        System.out.println("Max error: " + maxError);
        System.out.println();
        assertTrue(Double.isFinite(maxError) && maxError < epsilon);
    }

}