package com.ran.dissertation.factories;

import com.ran.dissertation.algebraic.common.Pair;
import com.ran.dissertation.algebraic.vector.ThreeDoubleVector;
import com.ran.dissertation.world.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SinCosWorldFactory implements WorldFactory {

    private static final SinCosWorldFactory INSTANCE = new SinCosWorldFactory();

    public static SinCosWorldFactory getInstance() {
        return INSTANCE;
    }

    private SinCosWorldFactory() { }

    @Override
    public World createWorld() {FigureFactory figureFactory = FigureFactory.getInstance();
        LabFigureFactory labFigureFactory = LabFigureFactory.getInstance();
        List<DisplayableObject> displayableObjects = new ArrayList<>();
        addTriangularGridFigureWithLevelLines(displayableObjects,
                labFigureFactory.makeTriangularGridFigureWithLevelLines(
                        (x, y) -> Math.sin(x) + Math.sin(y), -7.0, 7.0, 28, -7.0, 7.0, 28, 7),
                Orientation.INITIAL_ORIENTATION
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