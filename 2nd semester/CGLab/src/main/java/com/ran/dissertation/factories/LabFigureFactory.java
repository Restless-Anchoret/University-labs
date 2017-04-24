package com.ran.dissertation.factories;

import com.ran.dissertation.algebraic.common.Pair;
import com.ran.dissertation.algebraic.vector.ThreeDoubleVector;
import com.ran.dissertation.world.Figure;
import com.ran.dissertation.world.TriangularGridFigure;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

public class LabFigureFactory extends FigureFactory {

    private static final LabFigureFactory INSTANCE = new LabFigureFactory();

    public static LabFigureFactory getInstance() {
        return INSTANCE;
    }

    public TriangularGridFigure makeTriangularGridFigureByFunction(
            BiFunction<Double, Double, Double> function,
            double x0, double x1, int xSteps,
            double y0, double y1, int ySteps) {
        List<List<ThreeDoubleVector>> verticesGrid = new ArrayList<>(xSteps + 1);
        for (int i = 0; i <= xSteps; i++) {
            double x = x0 + (x1 - x0) / xSteps * i;
            verticesGrid.add(new ArrayList<>(ySteps + 1));
            for (int j = 0; j <= ySteps; j++) {
                double y = y0 + (y1 - y0) / ySteps * j;
                double z = function.apply(x, y);
                verticesGrid.get(i).add(new ThreeDoubleVector(x, y, z));
            }
        }
        return TriangularGridFigure.create(verticesGrid);
    }

    public Figure makeLevelLineForTriangularGridFigure(
            TriangularGridFigure triangularGridFigure, double z) {
        List<ThreeDoubleVector> vertices = new ArrayList<>();
        List<Pair<Integer, Integer>> edges = new ArrayList<>();
        for (int i = 0; i < triangularGridFigure.getGridRows() - 1; i++) {
            for (int j = 0; j < triangularGridFigure.getGridColumns() - 1; j++) {
                PlaneTriangleIntersector.Result rightResult =
                        PlaneTriangleIntersector.getInstance().findIntersectionOfPlainAndTriangle(
                                triangularGridFigure.getVerticesGrid().get(i).get(j),
                                triangularGridFigure.getVerticesGrid().get(i).get(j + 1),
                                triangularGridFigure.getVerticesGrid().get(i + 1).get(j + 1), z
                        );
                processIntersectorResult(rightResult, vertices, edges);
                PlaneTriangleIntersector.Result leftResult =
                        PlaneTriangleIntersector.getInstance().findIntersectionOfPlainAndTriangle(
                                triangularGridFigure.getVerticesGrid().get(i).get(j),
                                triangularGridFigure.getVerticesGrid().get(i + 1).get(j),
                                triangularGridFigure.getVerticesGrid().get(i + 1).get(j + 1), z
                        );
                processIntersectorResult(leftResult, vertices, edges);
            }
        }
        return new Figure(vertices, edges);
    }

    private void processIntersectorResult(PlaneTriangleIntersector.Result result,
                                          List<ThreeDoubleVector> vertices,
                                          List<Pair<Integer, Integer>> edges) {
        int firstVerticeIndex, secondVerticeIndex, thirdVerticeIndex;
        switch (result.getIntersectionType()) {
            case SEGMENT:
                if (result.getIntersectionPoints().size() != 2) {
                    System.out.println("one");
                    System.out.println(result.getIntersectionPoints());
                }
                firstVerticeIndex = vertices.size();
                secondVerticeIndex = vertices.size() + 1;
                vertices.addAll(result.getIntersectionPoints());
                edges.add(new Pair<>(firstVerticeIndex, secondVerticeIndex));
                break;
            case TRIANGLE:
                if (result.getIntersectionPoints().size() != 3) {
                    System.out.println("two");
                    System.out.println(result.getIntersectionPoints());
                }
                firstVerticeIndex = vertices.size();
                secondVerticeIndex = vertices.size() + 1;
                thirdVerticeIndex = vertices.size() + 2;
                vertices.addAll(result.getIntersectionPoints());
                edges.add(new Pair<>(firstVerticeIndex, secondVerticeIndex));
                edges.add(new Pair<>(firstVerticeIndex, thirdVerticeIndex));
                edges.add(new Pair<>(secondVerticeIndex, thirdVerticeIndex));
                break;
        }
    }

    public Pair<TriangularGridFigure, Figure> makeTriangularGridFigureWithLevelLines(
            BiFunction<Double, Double, Double> function,
            double x0, double x1, int xSteps,
            double y0, double y1, int ySteps,
            int levelsQuantity) {
        TriangularGridFigure triangularGridFigure = makeTriangularGridFigureByFunction(
                function, x0, x1, xSteps, y0, y1, ySteps);
        double minZValue = findExtremumZValueInVertices(
                triangularGridFigure.getVertices(), (t1, t2) -> t1 < t2);
        double maxZValue = findExtremumZValueInVertices(
                triangularGridFigure.getVertices(), (t1, t2) -> t1 > t2);
        List<Figure> levelLines = new ArrayList<>(levelsQuantity);
        for (int i = 1; i <= levelsQuantity; i++) {
            double z = minZValue + (maxZValue - minZValue) / (levelsQuantity + 1) * i;
            levelLines.add(makeLevelLineForTriangularGridFigure(triangularGridFigure, z));
        }
        return new Pair<>(triangularGridFigure, makeMultiFigure(levelLines));
    }

    private double findExtremumZValueInVertices(List<ThreeDoubleVector> vertices,
                                                BiFunction<Double, Double, Boolean> comparingFunction) {
        double extremumZValue = Double.NaN;
        for (ThreeDoubleVector vertice: vertices) {
            if (Double.isNaN(extremumZValue) || comparingFunction.apply(vertice.getZ(), extremumZValue)) {
                extremumZValue = vertice.getZ();
            }
        }
        return extremumZValue;
    }

}