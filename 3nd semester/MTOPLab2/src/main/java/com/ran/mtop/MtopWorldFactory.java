package com.ran.mtop;

import com.ran.engine.algebra.vector.ThreeDoubleVector;
import com.ran.engine.algebra.vector.TwoDoubleVector;
import com.ran.engine.factories.util.CoordinatesConverter;
import com.ran.engine.factories.world.BaseWorldFactory;
import com.ran.engine.rendering.world.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MtopWorldFactory extends BaseWorldFactory {

    private static final List<Color> colors = Arrays.asList(Color.BLACK, Color.RED);

    private List<List<TwoDoubleVector>> points;

    public MtopWorldFactory(List<List<TwoDoubleVector>> points) {
        this.points = points;
    }

    @Override
    protected List<WorldObjectCreator> getWorldObjectCreators() {
        List<WorldObjectCreator> creators = new ArrayList<>();
        creators.addAll(Arrays.asList(
                plainGridCreator(20, 16, 1.0, 1.0,
                        Orientation.INITIAL_ORIENTATION, true),
                fixedObjectCreator(new WorldObjectPartBuilder().setFigure(getFigureFactory()
                                .makeGrid(20, 0, 0, 1.0, 0.0, 0.0)).build(),
                        Orientation.INITIAL_ORIENTATION),
                fixedObjectCreator(new WorldObjectPartBuilder().setFigure(getFigureFactory()
                                .makeGrid(0, 0, 16, 0.0, 0.0, 1.0)).build(),
                        Orientation.INITIAL_ORIENTATION)
        ));
        for (int i = 0; i < points.size(); i++) {
            List<TwoDoubleVector> pointsGroup = points.get(i);
            creators.add(fixedObjectCreator(new WorldObjectPartBuilder().setFigure(createFigureByPoints(pointsGroup))
                            .setEdgePaintWidth(2f).setVerticePaintRadius(1).setColor(colors.get(i)).build(),
                    Orientation.INITIAL_ORIENTATION));
        }
        return creators;
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
