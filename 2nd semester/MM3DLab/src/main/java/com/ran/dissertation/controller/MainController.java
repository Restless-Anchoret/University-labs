package com.ran.dissertation.controller;

import com.ran.dissertation.factories.DefaultWorld;
import com.ran.dissertation.factories.WorldFactory;
import com.ran.dissertation.ui.DialogPanelContent;
import com.ran.dissertation.ui.MainFrame;
import com.ran.dissertation.ui.SelectItem;
import com.ran.dissertation.ui.WorldSwitchingPanelListener;
import com.ran.dissertation.world.MobiusStrip;
import com.ran.dissertation.world.World;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class MainController {

    private final List<WorldFactory> worldFactories;
    private List<World> worlds;
    private MainFrame mainFrame;
    private DialogPanelContent dialogPanelContent;

    public MainController(List<WorldFactory> worldFactories) {
        this.worldFactories = worldFactories;
    }

    public void startApplication() {
        worlds = worldFactories.stream()
                .map(worldFactory -> worldFactory.createWorld())
                .collect(Collectors.toList());
        tryToSetBetterLaf();
        mainFrame = new MainFrame();
        setWindowClosingListener(mainFrame);
        dialogPanelContent = new DialogPanelContent();
        WorldSwitchingPanelListener worldSwitchingPanelListener =
                new DefaultWorldSwitchingListener(mainFrame, dialogPanelContent);
        dialogPanelContent.getWorldSwitchingPanel().addWorldSwitchingListener(worldSwitchingPanelListener);
        dialogPanelContent.getWorldSwitchingPanel().setWorlds(prepareWorldSelectItems(worlds));
        worldSwitchingPanelListener.chosenWorldChanged(worlds.get(0), null);
        dialogPanelContent.getOperationsPanel().getSaveScreenshotButton()
                .addActionListener(new ScreenshotSaverListener(mainFrame));
        MobiusStrip mobiusStrip = ((DefaultWorld)worlds.iterator().next()).getMobiusStrip();
        dialogPanelContent.getMobiusStripPanel().addMobiusStripPanelListener(
                new DefaultMobiusStripPanelListener(mainFrame.getImagePanel(), mobiusStrip));
        mainFrame.getDialogPanel().setComponent(dialogPanelContent);
        mainFrame.setVisible(true);
    }
    
    private void setWindowClosingListener(MainFrame mainFrame) {
        mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                dialogPanelContent.getAnimationControlPanel().stopAllAnimations();
            }
        });
    }

    private void tryToSetBetterLaf() {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException |
                IllegalAccessException | UnsupportedLookAndFeelException exception) {
            exception.printStackTrace();
        }
    }

    private List<SelectItem<World>> prepareWorldSelectItems(List<World> worlds) {
        List<SelectItem<World>> selectItems = new ArrayList<>(worlds.size());
        for (int i = 0; i < worlds.size(); i++) {
            String worldName = "World #" + (i + 1);
            selectItems.add(new SelectItem<>(worlds.get(i), worldName));
        }
        return selectItems;
    }

}
