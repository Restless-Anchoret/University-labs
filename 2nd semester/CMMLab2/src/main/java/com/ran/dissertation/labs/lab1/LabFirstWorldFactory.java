package com.ran.dissertation.labs.lab1;

import com.ran.dissertation.algebraic.function.DoubleFunction;
import com.ran.dissertation.algebraic.vector.SingleDouble;
import com.ran.dissertation.factories.*;
import com.ran.dissertation.labs.cmm.ConcreteTaskDecision;
import com.ran.dissertation.labs.cmm.NewtonMethodConcreteTask;
import com.ran.dissertation.world.Camera;
import com.ran.dissertation.world.DisplayableObject;
import com.ran.dissertation.world.Orientation;
import com.ran.dissertation.world.World;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class LabFirstWorldFactory {

    private static final LabFirstWorldFactory INSTANCE = new LabFirstWorldFactory();

    public static LabFirstWorldFactory getInstance() {
        return INSTANCE;
    }

    private ConcreteTaskDecision concreteTaskDecision = null;

    private ConcreteTaskDecision getConcreteTaskDecision() {
        if (concreteTaskDecision == null) {
            concreteTaskDecision = new NewtonMethodConcreteTask().solve(0.05, 0.001, 4);
        }
        return concreteTaskDecision;
    }

    private LabFirstWorldFactory() { }

    public World createWorldForCamera(Camera camera, int segments) {
        FigureFactory figureFactory = FigureFactory.getInstance();
        FunctionGraphFactory graphFactory = FunctionGraphFactory.getInstance();
        double halfWidth = camera.getLensWidth() / 2.0;
        double left = camera.getPosition().getX() - halfWidth;
        double right = camera.getPosition().getX() + halfWidth;
        ConcreteTaskDecision taskDecision = getConcreteTaskDecision();
        DoubleFunction<SingleDouble> exactDecisionFunction = new DoubleFunction<>(
                x -> new SingleDouble(taskDecision.getExactSolution().apply(x)),
                0.0, 1.0);
        List<DisplayableObject> displayableObjects = new ArrayList<>();
        displayableObjects.addAll(Arrays.asList(
                new DisplayableObject(figureFactory.makeGrid(40, 0, 40, 1.0, 0.0, 1.0),
                        Orientation.INITIAL_ORIENTATION,
                        Color.LIGHT_GRAY),
                new DisplayableObject(figureFactory.makeGrid(1, 0, 0, 1000.0, 0.0, 0.0)),
                new DisplayableObject(figureFactory.makeGrid(0, 0, 1, 0.0, 0.0, 1000.0)),
                new DisplayableObject(graphFactory.makeFunctionGraph(exactDecisionFunction, left, right, segments),
                        Orientation.INITIAL_ORIENTATION,
                        Color.RED, 1, 0)
        ));
        for (java.util.function.DoubleFunction<Double> function: taskDecision.getApproximations()) {
            DoubleFunction<SingleDouble> approximationFunction = new DoubleFunction<>(
                    x -> new SingleDouble(function.apply(x)),
                    0.0, 1.0);
            displayableObjects.add(new DisplayableObject(
                    graphFactory.makeFunctionGraph(approximationFunction, left, right, segments),
                    Orientation.INITIAL_ORIENTATION,
                    Color.BLACK, 1, 0));
        }
        return new World(displayableObjects, Collections.emptyList(), camera);
    }
    
}