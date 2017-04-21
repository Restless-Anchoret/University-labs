package com.ran.dissertation.factories;

import com.ran.dissertation.algebraic.vector.ThreeDoubleVector;
import com.ran.dissertation.world.Camera;
import com.ran.dissertation.world.DisplayableObject;
import com.ran.dissertation.world.Orientation;
import com.ran.dissertation.world.World;

import java.awt.*;
import java.util.Arrays;
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
        List<DisplayableObject> displayableObjects = Arrays.asList(
                new DisplayableObject(figureFactory.makePlainGrid(20, 8, 1.0, 1.0),
                        Orientation.INITIAL_ORIENTATION,
                        Color.LIGHT_GRAY),
//                new DisplayableObject(figureFactory.makeGrid(6, 6, 6, 2.0, 2.0, 2.0),
//                        Orientation.createForOffset(12.0, 0.0, 1.0)),
                new DisplayableObject(figureFactory.makeGlobe(ThreeDoubleVector.ZERO_THREE_DOUBLE_VECTOR, 3.0, 12),
                        Orientation.createForOffset(-6.0, 0.0, 4.0),
                        Color.LIGHT_GRAY, 1, 0),
//                new DisplayableObject(figureFactory.makeInterpolatedCurve(makeVerticesForInterpolationList(), 1, 100),
//                        Orientation.createForOffset(-6.0, 0.0, 4.0)),
                new DisplayableObject(figureFactory.makeGlobe(ThreeDoubleVector.ZERO_THREE_DOUBLE_VECTOR, 3.0, 12),
                        Orientation.createForOffset(6.0, 0.0, 4.0),
                        Color.LIGHT_GRAY, 1, 0)//,
//                new DisplayableObject(figureFactory.makeSpline(makePointsWithValuesForInterpolationList(), 1, 100,
//                        CoordinatesConverter.CONVERTER_TO_XZ))
        );
        Camera camera = Camera.createForPositionAndAngles(new ThreeDoubleVector(0.0, 10.0, 4.0), 0.0, 0.0);
        return new World(displayableObjects, Collections.emptyList(), camera);
    }
    
}