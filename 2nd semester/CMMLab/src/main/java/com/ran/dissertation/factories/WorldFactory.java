package com.ran.dissertation.factories;

import com.ran.dissertation.world.World;

@FunctionalInterface
public interface WorldFactory {

    World createWorld();
    
}