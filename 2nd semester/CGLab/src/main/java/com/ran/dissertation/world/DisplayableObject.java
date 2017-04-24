package com.ran.dissertation.world;

import com.ran.dissertation.algebraic.vector.ThreeDoubleVector;
import java.awt.Color;
import java.util.List;

public class DisplayableObject {

    protected static final Color DEFAULT_FIGURE_COLOR = Color.BLACK;
    protected static final float DEFAULT_EDGE_PAINT_WIDTH = 1.0f;
    protected static final int DEFAULT_VERTICE_PAINT_RADIUS = 2;
    
    private final Figure figure;
    private Orientation orientation;
    private List<ThreeDoubleVector> currentFigureVertices = null;
    private final Color color;
    private final float edgePaintWidth;
    private int verticePaintRadius;
    private boolean visible;

    public DisplayableObject(Figure figure) {
        this(figure, Orientation.INITIAL_ORIENTATION, DEFAULT_FIGURE_COLOR,
                DEFAULT_EDGE_PAINT_WIDTH, DEFAULT_VERTICE_PAINT_RADIUS);
    }
    
    public DisplayableObject(Figure figure, Orientation orientation) {
        this(figure, orientation, DEFAULT_FIGURE_COLOR, DEFAULT_EDGE_PAINT_WIDTH, DEFAULT_VERTICE_PAINT_RADIUS);
    }
    
    public DisplayableObject(Figure figure, Orientation orientation, Color color) {
        this(figure, orientation, color, DEFAULT_EDGE_PAINT_WIDTH, DEFAULT_VERTICE_PAINT_RADIUS);
    }
    
    public DisplayableObject(Figure figure, Orientation orientation, Color color,
            float edgePaintWidth, int verticePaintRadius) {
        this(figure, orientation, color, edgePaintWidth, verticePaintRadius, true);
    }

    public DisplayableObject(Figure figure, Orientation orientation, Color color,
            float edgePaintWidth, int verticePaintRadius, boolean visible) {
        this.figure = figure;
        this.orientation = orientation;
        this.color = color;
        this.edgePaintWidth = edgePaintWidth;
        this.verticePaintRadius = verticePaintRadius;
        this.visible = visible;
    }

    public Color getColor() {
        return color;
    }

    public float getEdgePaintWidth() {
        return edgePaintWidth;
    }

    public int getVerticePaintRadius() {
        return verticePaintRadius;
    }

    public Figure getFigure() {
        return figure;
    }

    public Orientation getOrientation() {
        return orientation;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
        this.currentFigureVertices = null;
    }

    public List<ThreeDoubleVector> getCurrentFigureVertices() {
        updateCurrentFigureVertices();
        return currentFigureVertices;
    }
    
    public void updateCurrentFigureVertices() {
        if (currentFigureVertices != null) {
            return;
        }
        currentFigureVertices = OrientationMapper.getInstance().orientVertices(figure.getVertices(), orientation);
    }
    
}