package com.ran.dissertation.factories;

import com.ran.dissertation.algebraic.common.Pair;
import com.ran.dissertation.algebraic.function.DoubleFunction;
import com.ran.dissertation.algebraic.vector.SingleDouble;
import com.ran.dissertation.algebraic.vector.ThreeDoubleVector;
import com.ran.dissertation.world.Figure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FigureFactory {
    
    private static final FigureFactory INSTANCE = new FigureFactory();

    public static FigureFactory getInstance() {
        return INSTANCE;
    }
    
    public Figure makeFigureWithOneVertice(ThreeDoubleVector vertice) {
        return new Figure(Arrays.asList(vertice), Collections.emptyList());
    }
    
    public Figure makeGrid(int xCellsQuantity, int yCellsQuantity, int zCellsQuantity,
            double xCellWidth, double yCellWidth, double zCellWidth) {
        return makeGrid(xCellsQuantity, yCellsQuantity, zCellsQuantity, xCellWidth, yCellWidth, zCellWidth,
                ThreeDoubleVector.ZERO_THREE_DOUBLE_VECTOR);
    }
    
    public Figure makeGrid(int xCellsQuantity, int yCellsQuantity, int zCellsQuantity,
            double xCellWidth, double yCellWidth, double zCellWidth, ThreeDoubleVector center) {
        List<ThreeDoubleVector> vertices = new ArrayList<>();
        List<Pair<Integer, Integer>> figureEdges = new ArrayList<>();
        
        double xHalfSize = xCellWidth * xCellsQuantity / 2.0;
        double yHalfSize = yCellWidth * yCellsQuantity / 2.0;
        double zHalfSize = zCellWidth * zCellsQuantity / 2.0;
        
        int[][][] indexes = new int[xCellsQuantity + 1][yCellsQuantity + 1][zCellsQuantity + 1];
        int currentIndex = 0;
        for (int i = 0; i <= xCellsQuantity; i++) {
            for (int j = 0; j <= yCellsQuantity; j++) {
                for (int k = 0; k <= zCellsQuantity; k++) {
                    double x = i * xCellWidth - xHalfSize + center.getX();
                    double y = j * yCellWidth - yHalfSize + center.getY();
                    double z = k * zCellWidth - zHalfSize + center.getZ();
                    vertices.add(new ThreeDoubleVector(x, y, z));
                    indexes[i][j][k] = currentIndex;
                    if (i > 0) {
                        figureEdges.add(new Pair<>(indexes[i - 1][j][k], currentIndex));
                    }
                    if (j > 0) {
                        figureEdges.add(new Pair<>(indexes[i][j - 1][k], currentIndex));
                    }
                    if (k > 0) {
                        figureEdges.add(new Pair<>(indexes[i][j][k - 1], currentIndex));
                    }
                    currentIndex++;
                }
            }
        }
        return new Figure(vertices, figureEdges);
    }
    
    public Figure makePlainGrid(int xCellsQuantity, int yCellsQuantity,
            double xCellWidth, double yCellWidth) {
        return makeGrid(xCellsQuantity, yCellsQuantity, 0, xCellWidth, yCellWidth, 0.0);
    }
    
    public Figure makeParallelepiped(double xWidth, double yWidth, double zWidth) {
        return makeGrid(1, 1, 1, xWidth, yWidth, zWidth);
    }
    
    public Figure makeCube(double width) {
        return makeParallelepiped(width, width, width);
    }
    
    public Figure makeOval(ThreeDoubleVector center, ThreeDoubleVector firstAxis,
            ThreeDoubleVector secondAxis, int segments) {
        List<ThreeDoubleVector> vertices = new ArrayList<>();
        List<Pair<Integer, Integer>> figureEdges = new ArrayList<>();
        for (int i = 0; i < segments; i++) {
            double angle = 2 * Math.PI * i / segments;
            ThreeDoubleVector vertice = firstAxis.multiply(Math.sin(angle))
                    .add(secondAxis.multiply(Math.cos(angle)))
                    .add(center);
            vertices.add(vertice);
            figureEdges.add(new Pair<>(i, (i + 1) % segments));
        }
        return new Figure(vertices, figureEdges);
    }
    
    public Figure makeMultiFigure(List<Figure> figures) {
        List<ThreeDoubleVector> vertices = new ArrayList<>();
        List<Pair<Integer, Integer>> figureEdges = new ArrayList<>();
        int currentVerticesQuantity = 0;
        for (Figure figure: figures) {
            for (ThreeDoubleVector vertice: figure.getVertices()) {
                vertices.add(vertice);
            }
            for (Pair<Integer, Integer> figureEdge: figure.getFigureEdges()) {
                figureEdges.add(new Pair<>(figureEdge.getLeft() + currentVerticesQuantity,
                        figureEdge.getRight() + currentVerticesQuantity));
            }
            currentVerticesQuantity += figure.getVertices().size();
        }
        return new Figure(vertices, figureEdges);
    }
    
    public Figure makeSphereFrame(ThreeDoubleVector center, double radius, int segments) {
        ThreeDoubleVector xAxis = new ThreeDoubleVector(radius, 0.0, 0.0);
        ThreeDoubleVector yAxis = new ThreeDoubleVector(0.0, radius, 0.0);
        ThreeDoubleVector zAxis = new ThreeDoubleVector(0.0, 0.0, radius);
        
        Figure xyCircle = makeOval(center, xAxis, yAxis, segments);
        Figure xzCircle = makeOval(center, xAxis, zAxis, segments);
        Figure yzCircle = makeOval(center, yAxis, zAxis, segments);
        
        return makeMultiFigure(Arrays.asList(xyCircle, xzCircle, yzCircle));
    }
    
    public Figure makeGlobe(ThreeDoubleVector center, double radius, int meridiansHalfQuantity) {
        ThreeDoubleVector zAxis = new ThreeDoubleVector(0.0, 0.0, radius);
        List<Figure> circles = new ArrayList<>(meridiansHalfQuantity);
        for (int i = 0; i < meridiansHalfQuantity; i++) {
            double angle = Math.PI * i / meridiansHalfQuantity;
            ThreeDoubleVector meridianAxis = new ThreeDoubleVector(
                    radius * Math.sin(angle), radius * Math.cos(angle), 0.0);
            circles.add(makeOval(center, zAxis, meridianAxis, meridiansHalfQuantity * 2));
        }
        return makeMultiFigure(circles);
    }

    public Figure makeFigureByPoints(List<Pair<Double, Double>> pointsWithValues,
            CoordinatesConverter coordinatesConverter) {
        List<ThreeDoubleVector> vertices = new ArrayList<>(pointsWithValues.size());
        for (Pair<Double, Double> pointWithValue: pointsWithValues) {
            vertices.add(coordinatesConverter.convert(pointWithValue.getLeft(), pointWithValue.getRight()));
        }
        List<Pair<Integer, Integer>> figureEdges = Collections.emptyList();
        return new Figure(vertices, figureEdges);
    }
    
    public Figure makeFigureByFunction(DoubleFunction<SingleDouble> function,
            int segments, CoordinatesConverter coordinatesConverter) {
        List<ThreeDoubleVector> vertices = new ArrayList<>(segments + 1);
        double parameterStart = function.getMinParameterValue();
        double parameterEnd = function.getMaxParameterValue();
        for (int i = 0; i <= segments; i++) {
            double point = parameterStart + (parameterEnd - parameterStart) * i / segments;
            double value = function.apply(point).getValue();
            vertices.add(coordinatesConverter.convert(point, value));
        }
        return new Figure(vertices, makeEdgesSimpleList(segments));
    }
    
    protected List<Pair<Integer, Integer>> makeEdgesSimpleList(int segments) {
        List<Pair<Integer, Integer>> figureEdges = new ArrayList<>(segments);
        for (int i = 0; i < segments; i++) {
            figureEdges.add(new Pair<>(i, i + 1));
        }
        return figureEdges;
    }

}