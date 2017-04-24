package com.ran.dissertation.ui;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class AnimationControlPanelListenerSupport {

    private Set<AnimationControlPanelListener> animationControlPanelListeners = new HashSet<>();
    
    public void addAnimationControlPanelListener(AnimationControlPanelListener listener) {
        animationControlPanelListeners.add(listener);
    }
    
    public Set<AnimationControlPanelListener> getAnimationControlPanelListeners() {
        return Collections.unmodifiableSet(animationControlPanelListeners);
    }
    
    public void removeAnimationControlPanelListener(AnimationControlPanelListener listener) {
        animationControlPanelListeners.remove(listener);
    }
    
    public void fireStartAnimationButtonClicked(AnimationStrategy currentAnimation) {
        for (AnimationControlPanelListener listener: animationControlPanelListeners) {
            listener.startAnimationButtonClicked(currentAnimation);
        }
    }
    
    public void fireStopAnimationButtonClicked(AnimationStrategy currentAnimation) {
        for (AnimationControlPanelListener listener: animationControlPanelListeners) {
            listener.stopAnimationButtonClicked(currentAnimation);
        }
    }
    
    public void fireChosenAnimationChanged(AnimationStrategy chosenAnimation, AnimationStrategy previousAnimation) {
        for (AnimationControlPanelListener listener: animationControlPanelListeners) {
            listener.chosenAnimationChanged(chosenAnimation, previousAnimation);
        }
    }
    
}