package com.ran.dissertation.controller;

import com.ran.dissertation.ui.AnimationControlPanelListener;
import com.ran.dissertation.ui.AnimationStrategy;

public class DefaultAnimationController implements AnimationControlPanelListener {
    
    @Override
    public void startAnimationButtonClicked(AnimationStrategy currentAnimation) {
        if (currentAnimation == null) {
            return;
        }
        currentAnimation.startAnimation();
    }

    @Override
    public void stopAnimationButtonClicked(AnimationStrategy currentAnimation) {
        if (currentAnimation == null) {
            return;
        }
        currentAnimation.stopAnimation();
    }

    @Override
    public void chosenAnimationChanged(AnimationStrategy chosenAnimation, AnimationStrategy previousAnimation) {
//        if (previousAnimation != null) {
//            previousAnimation.stopAnimation();
//        }
    }

}