package com.ran.mtop;

import com.ran.engine.algebra.vector.ThreeDoubleVector;
import com.ran.engine.algebra.vector.TwoDoubleVector;
import com.ran.engine.factories.util.CoordinatesConverter;
import com.ran.engine.factories.world.BaseWorldFactory;
import com.ran.engine.rendering.world.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MtopWorldFactory extends BaseWorldFactory {

    private List<TwoDoubleVector> points;

    public MtopWorldFactory(List<TwoDoubleVector> points) {
        this.points = points;
    }

    @Override
    protected List<WorldObjectCreator> getWorldObjectCreators() {
        return Arrays.asList(
                plainGridCreator(20, 16, 1.0, 1.0,
                        Orientation.INITIAL_ORIENTATION, true),
                fixedObjectCreator(new WorldObjectPartBuilder().setFigure(getFigureFactory()
                                .makeGrid(20, 0, 0, 1.0, 0.0, 0.0)).build(),
                        Orientation.INITIAL_ORIENTATION),
                fixedObjectCreator(new WorldObjectPartBuilder().setFigure(getFigureFactory()
                                .makeGrid(0, 0, 16, 0.0, 0.0, 1.0)).build(),
                        Orientation.INITIAL_ORIENTATION),
                fixedObjectCreator(new WorldObjectPartBuilder().setFigure(createFigureByPoints(points))
                                .setEdgePaintWidth(2f).setVerticePaintRadius(1).build(),
                        Orientation.INITIAL_ORIENTATION)
        );
    }

    private Figure createFigureByPoints(List<TwoDoubleVector> points) {
        List<ThreeDoubleVector> vertices = points.stream()
                .map(CoordinatesConverter.CONVERTER_TO_XZ::convert)
                .collect(Collectors.toList());
        return new Figure(vertices, getFigureFactory().makeEdgesSimpleList(points.size() - 1));
    }

    @Override
    protected Camera getCamera() {
        return new Camera();
    }

}
