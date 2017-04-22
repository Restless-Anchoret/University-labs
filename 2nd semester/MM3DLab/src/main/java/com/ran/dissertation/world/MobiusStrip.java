package com.ran.dissertation.world;

import com.ran.dissertation.algebraic.vector.ThreeDoubleVector;

public class MobiusStrip extends KinematicSurface {

    private double r, h;
    private int n;
    
    public static MobiusStrip create(double t0, double t1, double tau0, double tau1,
            double r, double h, int n) {
        MobiusStrip strip = new MobiusStrip(t0, t1, tau0, tau1, r, h, n);
        strip.setP(
                (t, tau) -> new ThreeDoubleVector(
                        (strip.getR() + strip.getH() * tau * Math.cos(strip.getN() * t / 2.0)) * Math.cos(t),
                        strip.getH() * tau * Math.sin(strip.getN() * t / 2.0),
                        (strip.getR() - strip.getH() * tau * Math.cos(strip.getN() * t / 2.0)) * Math.sin(t)
                )
        );
        return strip;
    }
    
    private MobiusStrip(double t0, double t1, double tau0, double tau1,
            double r, double h, int n) {
        super(
                (t, tau) -> ThreeDoubleVector.ZERO_THREE_DOUBLE_VECTOR,
                t0, t1, tau0, tau1
        );
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