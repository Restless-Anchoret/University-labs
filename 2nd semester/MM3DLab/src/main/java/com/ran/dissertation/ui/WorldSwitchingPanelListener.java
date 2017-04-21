package com.ran.dissertation.ui;

import com.ran.dissertation.world.World;

public interface WorldSwitchingPanelListener {

    void chosenWorldChanged(World chosenWorld, World previousWorld);
    
}
