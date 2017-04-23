package com.ran.dissertation.world;

import com.ran.dissertation.algebraic.vector.ThreeDoubleVector;
import java.awt.Color;

public class MobiusStrip extends KinematicSurface {

    private double r, h;
    private int n;
    
    public static MobiusStrip create(double t0, double t1, double tau0, double tau1,
            double r, double h, int n,
            Orientation orientation, Color color, float edgePaintWidth, int verticePaintRadius) {
        MobiusStrip strip = new MobiusStrip(t0, t1, tau0, tau1, r, h, n,
                orientation, color, edgePaintWidth, verticePaintRadius);
        strip.setP(
                (t, tau) -> new ThreeDoubleVector(
                        (strip.getR() + strip.getH() * tau * Math.cos(strip.getN() * t / 2.0)) * Math.cos(t),
                        (strip.getR() - strip.getH() * tau * Math.cos(strip.getN() * t / 2.0)) * Math.sin(t),
                        strip.getH() * tau * Math.sin(strip.getN() * t / 2.0)
                )
        );
        return strip;
    }
    
    private MobiusStrip(double t0, double t1, double tau0, double tau1,
            double r, double h, int n,
            Orientation orientation, Color color, float edgePaintWidth, int verticePaintRadius) {
        super(
                (t, tau) -> ThreeDoubleVector.ZERO_THREE_DOUBLE_VECTOR,
                t0, t1, tau0, tau1,
                orientation, color, edgePaintWidth, verticePaintRadius
        );
        this.r = r;
        this.h = h;
        this.n = n;
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }

    public double getH() {
        return h;
    }

    public void setH(double h) {
        this.h = h;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }
    
}