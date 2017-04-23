package com.ran.dissertation.ui;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class MobiusStripPanelListenerSupport {

    private Set<MobiusStripPanelListener> mobiusStripPanelListeners = new HashSet<>();
    
    public void addMobiusStripPanelListener(MobiusStripPanelListener listener) {
        mobiusStripPanelListeners.add(listener);
    }
    
    public Set<MobiusStripPanelListener> getMobiusStripPanelListeners() {
        return Collections.unmodifiableSet(mobiusStripPanelListeners);
    }
    
    public void removeMobiusStripPanelListener(MobiusStripPanelListener listener) {
        mobiusStripPanelListeners.remove(listener);
    }
    
    public void fireApplyParameters(String t0String, String t1String, String tau0String, String tau1String,
            String tStepsString, String tauStepsString, String rString, String hString, String nString) {
        for (MobiusStripPanelListener listener: mobiusStripPanelListeners) {
            listener.applyParameters(t0String, t1String, tau0String, tau1String,
                    tStepsString, tauStepsString, rString, hString, nString);
        }
    }
    
}