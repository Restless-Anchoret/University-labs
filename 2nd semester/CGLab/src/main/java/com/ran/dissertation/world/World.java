package com.ran.dissertation.world;

import java.util.ArrayList;
import java.util.List;

public class World {

    private final List<DisplayableObject> displayableObjects;
    private final List<AnimatedObject> animatedObjects;
    private final Camera camera;

    public World(List<DisplayableObject> displayableObjects, List<AnimatedObject> animatedObjects, Camera camera) {
        this.displayableObjects = new ArrayList<>(displayableObjects);
        this.displayableObjects.addAll(animatedObjects);
        this.animatedObjects = animatedObjects;
        this.camera = camera;
    }

    public List<DisplayableObject> getDisplayableObjects() {
        return displayableObjects;
    }

    public List<AnimatedObject> getAnimatedObjects() {
        return animatedObjects;
    }

    public Camera getCamera() {
        return camera;
    }
    
}