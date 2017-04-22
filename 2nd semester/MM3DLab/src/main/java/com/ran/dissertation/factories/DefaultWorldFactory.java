package com.ran.dissertation.factories;

import com.ran.dissertation.algebraic.vector.ThreeDoubleVector;
import com.ran.dissertation.world.Camera;
import com.ran.dissertation.world.DisplayableObject;
import com.ran.dissertation.world.MobiusStrip;
import com.ran.dissertation.world.Orientation;
import com.ran.dissertation.world.World;

import java.awt.*;
import java.util.Arrays;
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
        MobiusStrip mobiusStrip = MobiusStrip.create(0.0, Math.PI * 2.0, -1.0, 1.0, 6.0, 2.0, 1,
                        Orientation.createForOffset(0.0, 0.0, 3.0), Color.BLACK, 1.0f, 2);
        List<DisplayableObject> displayableObjects = Arrays.asList(
                new DisplayableObject(figureFactory.makePlainGrid(16, 16, 1.0, 1.0),
                        Orientation.INITIAL_ORIENTATION,
                        Color.LIGHT_GRAY),
                mobiusStrip
        );
        Camera camera = Camera.createForPositionAndAngles(new ThreeDoubleVector(0.0, 10.0, 4.0), 0.0, 0.0);
        return new DefaultWorld(displayableObjects, camera, mobiusStrip);
    }
    
}