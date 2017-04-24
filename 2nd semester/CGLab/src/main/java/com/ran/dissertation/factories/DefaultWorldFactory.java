package com.ran.dissertation.factories;

import com.ran.dissertation.algebraic.common.Pair;
import com.ran.dissertation.algebraic.vector.ThreeDoubleVector;
import com.ran.dissertation.world.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DefaultWorldFactory implements WorldFactory {

    private static final DefaultWorldFactory INSTANCE = new DefaultWorldFactory();

    public static DefaultWorldFactory getInstance() {
        return INSTANCE;
    }
    
    private DefaultWorldFactory() { }

    @Override
    public World createWorld() {
        FigureFactory figureFactory = FigureFactory.getInstance();
        LabFigureFactory labFigureFactory = LabFigureFactory.getInstance();
        List<DisplayableObject> displayableObjects = new ArrayList<>();
        displayableObjects.add(
                new DisplayableObject(figureFactory.makePlainGrid(74, 14, 1.0, 1.0),
                        Orientation.createForOffset(30.0, 0.0, 0.0),
                        Color.LIGHT_GRAY)
        );
        addTriangularGridFigureWithLevelLines(displayableObjects,
                labFigureFactory.makeTriangularGridFigureWithLevelLines(
                        (x, y) -> (x * x + y * y) / 5.0, -5.0, 5.0, 10, -5.0, 5.0, 10, 9),
                Orientation.INITIAL_ORIENTATION
        );
        addTriangularGridFigureWithLevelLines(displayableObjects,
                labFigureFactory.makeTriangularGridFigureWithLevelLines(
                        (x, y) -> (x * x - y * y) / 5.0 + 5.0, -5.0, 5.0, 10, -5.0, 5.0, 10, 9),
                Orientation.createForOffset(20.0, 0.0, 0.0)
        );
        addTriangularGridFigureWithLevelLines(displayableObjects,
                labFigureFactory.makeTriangularGridFigureWithLevelLines(
                        (x, y) -> x * x / 5.0 - y / 2.0 + 3.0, -5.0, 5.0, 10, -5.0, 5.0, 10, 9),
                Orientation.createForOffset(40.0, 0.0, 0.0)
        );
        addTriangularGridFigureWithLevelLines(displayableObjects,
                labFigureFactory.makeTriangularGridFigureWithLevelLines(
                        (x, y) -> Math.sqrt(25.0 - x * x), -5.0, 5.0, 20, -5.0, 5.0, 10, 9),
                Orientation.createForOffset(60.0, 0.0, 0.0)
        );
        Camera camera = Camera.createForPositionAndAngles(new ThreeDoubleVector(0.0, 10.0, 4.0), 0.0, 0.0);
        return new World(displayableObjects, Collections.emptyList(), camera);
    }

    private void addTriangularGridFigureWithLevelLines(
            List<DisplayableObject> displayableObjects,
            Pair<TriangularGridFigure, Figure> triangularGridFigureFigurePair,
            Orientation orientation) {
        displayableObjects.add(new DisplayableObject(triangularGridFigureFigurePair.getLeft(),
                orientation, Color.DARK_GRAY));
        displayableObjects.add(new DisplayableObject(triangularGridFigureFigurePair.getRight(),
                orientation, Color.RED));
    }
    
}