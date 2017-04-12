package com.ran.dissertation.ui;

import com.ran.dissertation.world.World;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class WorldSwitchingPanelListenerSupport {

    private Set<WorldSwitchingPanelListener> worldSwitchingPanelListeners = new HashSet<>();
    
    public void addWorldSwitchingPanelListener(WorldSwitchingPanelListener listener) {
        worldSwitchingPanelListeners.add(listener);
    }
    
    public Set<WorldSwitchingPanelListener> getWorldSwitchingPanelListeners() {
        return Collections.unmodifiableSet(worldSwitchingPanelListeners);
    }
    
    public void removeWorldSwitchingPanelListener(WorldSwitchingPanelListener listener) {
        worldSwitchingPanelListeners.remove(listener);
    }
    
    public void fireChosenWorldChanged(World chosenWorld, World previousWorld) {
        for (WorldSwitchingPanelListener listener: worldSwitchingPanelListeners) {
            listener.chosenWorldChanged(chosenWorld, previousWorld);
        }
    }
    
}