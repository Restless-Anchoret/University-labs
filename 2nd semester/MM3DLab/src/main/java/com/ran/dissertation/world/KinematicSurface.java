package com.ran.dissertation.world;

import java.awt.*;

public class KinematicSurface extends DisplayableObject {


    public KinematicSurface(Figure figure) {
        super(figure);
    }

    public KinematicSurface(Figure figure, Orientation orientation) {
        super(figure, orientation);
    }

    public KinematicSurface(Figure figure, Orientation orientation, Color color) {
        super(figure, orientation, color);
    }

    public KinematicSurface(Figure figure, Orientation orientation, Color color, float edgePaintWidth, int verticePaintRadius) {
        super(figure, orientation, color, edgePaintWidth, verticePaintRadius);
    }

    public KinematicSurface(Figure figure, Orientation orientation, Color color, float edgePaintWidth, int verticePaintRadius, boolean visible) {
        super(figure, orientation, color, edgePaintWidth, verticePaintRadius, visible);
    }



}