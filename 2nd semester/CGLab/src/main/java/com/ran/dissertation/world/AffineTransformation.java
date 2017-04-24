package com.ran.dissertation.world;

import java.util.function.Function;

public class AffineTransformation {

    private Function<Orientation, Orientation> orientationMaker;

    public AffineTransformation(Function<Orientation, Orientation> orientationMaker) {
        this.orientationMaker = orientationMaker;
    }
    
    public void performTransformation(DisplayableObject displayableObject) {
        displayableObject.setOrientation(
                orientationMaker.apply(displayableObject.getOrientation()));
    }
    
}