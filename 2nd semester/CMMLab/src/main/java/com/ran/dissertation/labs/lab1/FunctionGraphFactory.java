package com.ran.dissertation.labs.lab1;

import com.ran.dissertation.algebraic.common.Pair;
import com.ran.dissertation.algebraic.function.DoubleFunction;
import com.ran.dissertation.algebraic.vector.SingleDouble;
import com.ran.dissertation.algebraic.vector.ThreeDoubleVector;
import com.ran.dissertation.world.Figure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FunctionGraphFactory {

    private static final FunctionGraphFactory INSTANCE = new FunctionGraphFactory();

    public static FunctionGraphFactory getInstance() {
        return INSTANCE;
    }
    
    private FunctionGraphFactory() { }

    public Figure makeFunctionGraph(DoubleFunction<SingleDouble> function,
            double left, double right, int segments) {
        double leftBound = Math.max(left, function.getMinParameterValue());
        double rightBound = Math.min(right, function.getMaxParameterValue());
        if (rightBound <= leftBound) {
            return new Figure(
                    Arrays.asList(ThreeDoubleVector.ZERO_THREE_DOUBLE_VECTOR),
                    Collections.emptyList()
            );
        }
        int realSegmentsQuantity = (int)(segments * ((rightBound - leftBound) / (right - left)));
        List<ThreeDoubleVector> vertices = new ArrayList<>(realSegmentsQuantity + 1);
        for (int i = 0; i <= realSegmentsQuantity; i++) {
            double xPoint = leftBound + (rightBound - leftBound) * i / realSegmentsQuantity;
            double yPoint = function.apply(xPoint).getValue();
            ThreeDoubleVector vertice = new ThreeDoubleVector(xPoint, 0.0, yPoint);
            vertices.add(vertice);
        }
        List<Pair<Integer, Integer>> figureEdges = new ArrayList<>(realSegmentsQuantity);
        for (int i = 0; i < realSegmentsQuantity; i++) {
            figureEdges.add(new Pair<>(i, i + 1));
        }
        return new Figure(vertices, figureEdges);
    }
    
}