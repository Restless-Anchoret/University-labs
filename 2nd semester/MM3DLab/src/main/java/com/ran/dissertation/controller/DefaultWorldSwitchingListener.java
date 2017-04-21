package com.ran.dissertation.controller;

import com.ran.dissertation.ui.AnimationStrategy;
import com.ran.dissertation.ui.DialogPanelContent;
import com.ran.dissertation.ui.ImagePanel;
import com.ran.dissertation.ui.MainFrame;
import com.ran.dissertation.ui.SelectItem;
import com.ran.dissertation.ui.WorldSwitchingPanelListener;
import com.ran.dissertation.world.AnimatedObject;
import com.ran.dissertation.world.World;
import java.util.ArrayList;
import java.util.List;

public class DefaultWorldSwitchingListener implements WorldSwitchingPanelListener {

    private MainFrame mainFrame;
    private DialogPanelContent dialogPanelContent;

    public DefaultWorldSwitchingListener(MainFrame mainFrame, DialogPanelContent dialogPanelContent) {
        this.mainFrame = mainFrame;
        this.dialogPanelContent = dialogPanelContent;
    }
    
    @Override
    public void chosenWorldChanged(World chosenWorld, World previousWorld) {
        if (previousWorld != null) {
            dialogPanelContent.getAnimationControlPanel().stopAllAnimations();
        }
        mainFrame.getImagePanel().setImagePanelPaintStrategy(new DefaultPaintStrategy(chosenWorld));
        mainFrame.getImagePanel().addImagePanelListener(new DefaultCameraController(chosenWorld.getCamera()));
        dialogPanelContent.getAnimationControlPanel()
                .setAnimations(prepareAnimationSelectItemsForWorld(chosenWorld, mainFrame.getImagePanel()));
        dialogPanelContent.getAnimationControlPanel()
                .addAnimationControlPanelListener(new DefaultAnimationController());
        mainFrame.getImagePanel().repaint();
    }
    
    private List<SelectItem<AnimationStrategy>> prepareAnimationSelectItemsForWorld(World world, ImagePanel imagePanel) {
        List<AnimatedObject> animatedObjects = world.getAnimatedObjects();
        List<SelectItem<AnimationStrategy>> selectItems = new ArrayList<>(animatedObjects.size());
        for (int i = 0; i < animatedObjects.size(); i++) {
            String animationName = "Animation #" + (i + 1);
            AnimationStrategy animationStrategy = new DefaultAnimationStrategy(animatedObjects.get(i), imagePanel);
            selectItems.add(new SelectItem<>(animationStrategy, animationName));
        }
        return selectItems;
    }

}