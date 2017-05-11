package com.ran.dissertation.controller;

import com.ran.dissertation.ui.AnimationStrategy;
import com.ran.dissertation.ui.ImagePanel;
import com.ran.dissertation.world.AnimatedObject;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class DefaultAnimationStrategy implements AnimationStrategy {

    private static final int DEFAULT_INTERVAL_BETWEEN_FRAMES = 30;
    
    private final AnimatedObject animatedObject;
    private final ImagePanel imagePanel;
    private final Timer timer;

    public DefaultAnimationStrategy(AnimatedObject animatedObject, ImagePanel imagePanel, int intervalBetweenFrames) {
        this.animatedObject = animatedObject;
        this.timer = new Timer(intervalBetweenFrames, new AnimationWorker());
        this.imagePanel = imagePanel;
    }
    
    public DefaultAnimationStrategy(AnimatedObject animatedObject, ImagePanel imagePanel) {
        this(animatedObject, imagePanel, DEFAULT_INTERVAL_BETWEEN_FRAMES);
    }
    
    @Override
    public void startAnimation() {
        timer.start();
    }

    @Override
    public void stopAnimation() {
        timer.stop();
    }
    
    private class AnimationWorker implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            animatedObject.goToNextOrientation();
            imagePanel.repaint();
            if (!animatedObject.isCyclic() && animatedObject.getCurrentOrientationIndex() ==
                    animatedObject.getOrientations().size() - 1) {
                timer.stop();
            }
        }
    }

}