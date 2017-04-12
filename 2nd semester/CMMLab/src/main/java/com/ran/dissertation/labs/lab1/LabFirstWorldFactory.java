package com.ran.dissertation.labs.lab1;

import com.ran.dissertation.algebraic.function.DoubleFunction;
import com.ran.dissertation.algebraic.vector.SingleDouble;
import com.ran.dissertation.factories.*;
import com.ran.dissertation.world.Camera;
import com.ran.dissertation.world.DisplayableObject;
import com.ran.dissertation.world.Orientation;
import com.ran.dissertation.world.World;
import java.awt.Color;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class LabFirstWorldFactory {

    private static final LabFirstWorldFactory INSTANCE = new LabFirstWorldFactory();

    public static LabFirstWorldFactory getInstance() {
        return INSTANCE;
    }
    
    private LabFirstWorldFactory() { }

    public World createWorldForCamera(Camera camera, int segments) {
        FigureFactory figureFactory = FigureFactory.getInstance();
        FunctionGraphFactory graphFactory = FunctionGraphFactory.getInstance();
        double halfWidth = camera.getLensWidth() / 2.0;
        double left = camera.getPosition().getX() - halfWidth;
        double right = camera.getPosition().getX() + halfWidth;
        DoubleFunction<SingleDouble> function = new DoubleFunction<>(getFunction());
        List<DisplayableObject> displayableObjects = Arrays.asList(
                new DisplayableObject(figureFactory.makeGrid(40, 0, 40, 1.0, 0.0, 1.0),
                        Orientation.INITIAL_ORIENTATION,
                        Color.LIGHT_GRAY),
                new DisplayableObject(figureFactory.makeGrid(1, 0, 0, 1000.0, 0.0, 0.0)),
                new DisplayableObject(figureFactory.makeGrid(0, 0, 1, 0.0, 0.0, 1000.0)),
                new DisplayableObject(graphFactory.makeFunctionGraph(function, left, right, segments),
                        Orientation.INITIAL_ORIENTATION,
                        Color.BLACK, 1, 0)
        );
        return new World(displayableObjects, Collections.EMPTY_LIST, camera);
    }
    
    private Function<Double, SingleDouble> getFunction() {
        return x -> new SingleDouble(Math.cos(Math.cos(Math.cos(Math.cos(
                x * x * x * x)))) * 3.0 / (x * x * x));
    }
    
}