package com.ran.dissertation.world;

import java.awt.Color;
import java.util.Collections;
import java.util.List;

public class AnimatedObject extends DisplayableObject {
    
    private List<Orientation> orientations;
    private int currentOrientationIndex = 0;
    private boolean cyclic;
    
    public AnimatedObject(Figure figure, List<Orientation> orientations, boolean cyclic,
            Color color, float edgePaintWidth, int verticePaintRadius) {
        super(figure, (orientations.isEmpty() ? Orientation.INITIAL_ORIENTATION : orientations.get(0)),
                color, edgePaintWidth, verticePaintRadius);
        this.orientations = (orientations.isEmpty() ? Collections.singletonList(Orientation.INITIAL_ORIENTATION) : orientations);
        this.cyclic = cyclic;
    }
    
    public AnimatedObject(Figure figure, List<Orientation> orientations, boolean cyclic, Color color) {
        this(figure, orientations, cyclic, color, DEFAULT_EDGE_PAINT_WIDTH, DEFAULT_VERTICE_PAINT_RADIUS);
    }
    
    public AnimatedObject(Figure figure, List<Orientation> orientations, boolean cyclic) {
        this(figure, orientations, cyclic, DEFAULT_FIGURE_COLOR, DEFAULT_EDGE_PAINT_WIDTH, DEFAULT_VERTICE_PAINT_RADIUS);
    }

    public List<Orientation> getOrientations() {
        return Collections.unmodifiableList(orientations);
    }

    public int getCurrentOrientationIndex() {
        return currentOrientationIndex;
    }

    public boolean isCyclic() {
        return cyclic;
    }
    
    public void goToNextOrientation() {
        currentOrientationIndex = (currentOrientationIndex + 1) % orientations.size();
        setOrientation(orientations.get(currentOrientationIndex));
    }
    
}