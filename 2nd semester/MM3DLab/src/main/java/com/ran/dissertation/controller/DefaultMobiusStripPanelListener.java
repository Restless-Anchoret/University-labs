package com.ran.dissertation.controller;

import com.ran.dissertation.ui.ImagePanel;
import com.ran.dissertation.ui.MobiusStripPanelListener;
import com.ran.dissertation.world.MobiusStrip;

public class DefaultMobiusStripPanelListener implements MobiusStripPanelListener {

    private ImagePanel imagePanel;
    private MobiusStrip mobiusStrip;

    public DefaultMobiusStripPanelListener(ImagePanel imagePanel, MobiusStrip mobiusStrip) {
        this.imagePanel = imagePanel;
        this.mobiusStrip = mobiusStrip;
    }
    
    @Override
    public void applyParameters(String t0String, String t1String, String tau0String, String tau1String,
            String tStepsString, String tauStepsString, String rString, String hString, String nString) {
        try {
            double t0 = Double.parseDouble(t0String);
            double t1 = Double.parseDouble(t1String);
            double tau0 = Double.parseDouble(tau0String);
            double tau1 = Double.parseDouble(tau1String);
            int tSteps = Integer.parseInt(tStepsString);
            int tauSteps = Integer.parseInt(tauStepsString);
            double r = Double.parseDouble(rString);
            double h = Double.parseDouble(hString);
            int n = Integer.parseInt(nString);
            
            mobiusStrip.setT0(t0);
            mobiusStrip.setT1(t1);
            mobiusStrip.setTau0(tau0);
            mobiusStrip.setTau1(tau1);
            mobiusStrip.settSteps(tSteps);
            mobiusStrip.setTauSteps(tauSteps);
            mobiusStrip.setR(r);
            mobiusStrip.setH(h);
            mobiusStrip.setN(n);
            
            imagePanel.repaint();
        } catch (NumberFormatException exception) {
            exception.printStackTrace();
        }
    }

}