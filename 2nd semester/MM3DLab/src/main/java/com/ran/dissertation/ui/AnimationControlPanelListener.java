package com.ran.dissertation.ui;

public interface AnimationControlPanelListener {

    void startAnimationButtonClicked(AnimationStrategy currentAnimation);
    void stopAnimationButtonClicked(AnimationStrategy currentAnimation);
    void chosenAnimationChanged(AnimationStrategy chosenAnimation, AnimationStrategy previousAnimation);
    
}
