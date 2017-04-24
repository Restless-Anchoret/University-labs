package com.ran.dissertation.world;

import com.ran.dissertation.algebraic.common.Pair;
import com.ran.dissertation.algebraic.vector.ThreeDoubleVector;

import java.util.ArrayList;
import java.util.List;

public class TriangularGridFigure extends Figure {

    public static TriangularGridFigure create(List<List<ThreeDoubleVector>> verticesGrid) {
        int gridRows = verticesGrid.size();
        int gridColumns = verticesGrid.get(0).size();
        List<ThreeDoubleVector> vertices = new ArrayList<>(gridRows * gridColumns);
        List<Pair<Integer, Integer>> figureEdges = new ArrayList<>();
        for (int i = 0; i < gridRows; i++) {
            for (int j = 0; j < gridColumns; j++) {
                vertices.add(verticesGrid.get(i).get(j));
                int currentIndex = i * gridColumns + j;
                if (i != gridRows - 1) {
                    int lowIndex = (i + 1) * gridColumns + j;
                    figureEdges.add(new Pair<>(currentIndex, lowIndex));
                }
                if (j != gridColumns - 1) {
                    int rightIndex = i * gridColumns + j + 1;
                    figureEdges.add(new Pair<>(currentIndex, rightIndex));
                }
                if (i != gridRows - 1 && j != gridColumns - 1) {
                    int diagIndex = (i + 1) * gridColumns + j + 1;
                    figureEdges.add(new Pair<>(currentIndex, diagIndex));
                }
            }
        }
        return new TriangularGridFigure(vertices, figureEdges, verticesGrid, gridRows, gridColumns);
    }

    private List<List<ThreeDoubleVector>> verticesGrid;
    private int gridRows, gridColumns;

    private TriangularGridFigure(List<ThreeDoubleVector> vertices, List<Pair<Integer, Integer>> figureEdges,
                                List<List<ThreeDoubleVector>> verticesGrid, int gridRows, int gridColumns) {
        super(vertices, figureEdges);
        this.verticesGrid = verticesGrid;
        this.gridRows = gridRows;
        this.gridColumns = gridColumns;
    }

    public List<List<ThreeDoubleVector>> getVerticesGrid() {
        return verticesGrid;
    }

    public int getGridRows() {
        return gridRows;
    }

    public int getGridColumns() {
        return gridColumns;
    }

}