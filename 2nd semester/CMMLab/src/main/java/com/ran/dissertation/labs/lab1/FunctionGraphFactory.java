package com.ran.dissertation.labs.lab1;

import com.ran.dissertation.algebraic.common.Pair;
import com.ran.dissertation.algebraic.function.DoubleFunction;
import com.ran.dissertation.algebraic.vector.SingleDouble;
import com.ran.dissertation.algebraic.vector.ThreeDoubleVector;
import com.ran.dissertation.world.Figure;
import java.util.ArrayList;
import java.util.List;

public class FunctionGraphFactory {

    private static final FunctionGraphFactory INSTANCE = new FunctionGraphFactory();

    public static FunctionGraphFactory getInstance() {
        return INSTANCE;
    }
    
    private FunctionGraphFactory() { }

    public Figure makeFunctionGraph(DoubleFunction<SingleDouble> function,
            double left, double right, int segments) {
        List<ThreeDoubleVector> vertices = new ArrayList<>(segments + 1);
        for (int i = 0; i <= segments; i++) {
            double xPoint = left + (right - left) * i / segments;
            double yPoint = function.apply(xPoint).getValue();
            ThreeDoubleVector vertice = new ThreeDoubleVector(xPoint, 0.0, yPoint);
            vertices.add(vertice);
        }
        List<Pair<Integer, Integer>> figureEdges = new ArrayList<>(segments);
        for (int i = 0; i < segments; i++) {
            figureEdges.add(new Pair(i, i + 1));
        }
        return new Figure(vertices, figureEdges);
    }
    
}