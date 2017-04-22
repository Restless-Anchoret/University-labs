package com.ran.dissertation.factories;

import com.ran.dissertation.world.Camera;
import com.ran.dissertation.world.DisplayableObject;
import com.ran.dissertation.world.MobiusStrip;
import com.ran.dissertation.world.World;
import java.util.Collections;
import java.util.List;

public class DefaultWorld extends World {

    private MobiusStrip mobiusStrip;

    public DefaultWorld(List<DisplayableObject> displayableObjects, Camera camera, MobiusStrip mobiusStrip) {
        super(displayableObjects, Collections.emptyList(), camera);
        this.mobiusStrip = mobiusStrip;
    }

    public MobiusStrip getMobiusStrip() {
        return mobiusStrip;
    }
    
}