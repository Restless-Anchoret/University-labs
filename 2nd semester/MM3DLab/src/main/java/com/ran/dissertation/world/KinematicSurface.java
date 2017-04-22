package com.ran.dissertation.world;

import com.ran.dissertation.algebraic.function.DoubleFunction;
import com.ran.dissertation.algebraic.matrix.DoubleMatrix;
import com.ran.dissertation.algebraic.vector.ThreeDoubleVector;
import java.awt.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.function.BiFunction;

public class KinematicSurface extends DisplayableObject {

    private final static int DEFAULT_T_STEPS = 100;
    private final static int DEFAULT_TAU_STEPS = 10;
    
    private BiFunction<Double, Double, ThreeDoubleVector> p;
    private double t0, t1, tau0, tau1;
    private int tSteps, tauSteps;

    public KinematicSurface(BiFunction<Double, Double, ThreeDoubleVector> p,
            double t0, double t1, double tau0, double tau1, int tSteps, int tauSteps,
            Orientation orientation, Color color, float edgePaintWidth, int verticePaintRadius) {
        super(new Figure(Arrays.asList(ThreeDoubleVector.ZERO_THREE_DOUBLE_VECTOR), Collections.emptyList()),
                orientation, color, edgePaintWidth, verticePaintRadius);
        this.p = p;
        this.t0 = t0;
        this.t1 = t1;
        this.tau0 = tau0;
        this.tau1 = tau1;
        this.tSteps = tSteps;
        this.tauSteps = tauSteps;
    }
    
    public KinematicSurface(BiFunction<Double, Double, ThreeDoubleVector> p,
            double t0, double t1, double tau0, double tau1,
            Orientation orientation, Color color, float edgePaintWidth, int verticePaintRadius) {
        this(p, t0, t1, tau0, tau1, DEFAULT_T_STEPS, DEFAULT_TAU_STEPS,
                orientation, color, edgePaintWidth, verticePaintRadius);
    }
    
    public KinematicSurface(BiFunction<Double, Double, DoubleMatrix> a,
            DoubleFunction<ThreeDoubleVector> p0,
            DoubleFunction<ThreeDoubleVector> pn,
            double t0, double t1, double tau0, double tau1, int tSteps, int tauSteps,
            Orientation orientation, Color color, float edgePaintWidth, int verticePaintRadius) {
        this(
                (t, tau) -> new ThreeDoubleVector(a.apply(t, tau).multiply(p0.apply(tau).getDoubleVector())).add(pn.apply(t)),
                t0, t1, tau0, tau1, tSteps, tauSteps,
                orientation, color, edgePaintWidth, verticePaintRadius
        );
    }
    
    public KinematicSurface(BiFunction<Double, Double, DoubleMatrix> a,
            DoubleFunction<ThreeDoubleVector> p0,
            DoubleFunction<ThreeDoubleVector> pn,
            double t0, double t1, double tau0, double tau1,
            Orientation orientation, Color color, float edgePaintWidth, int verticePaintRadius) {
        this(a, p0, pn, t0, t1, tau0, tau1, DEFAULT_T_STEPS, DEFAULT_TAU_STEPS,
                orientation, color, edgePaintWidth, verticePaintRadius);
    }

    public BiFunction<Double, Double, ThreeDoubleVector> getP() {
        return p;
    }

    public void setP(BiFunction<Double, Double, ThreeDoubleVector> p) {
        this.p = p;
    }

    public double getT0() {
        return t0;
    }

    public void setT0(double t0) {
        this.t0 = t0;
    }

    public double getT1() {
        return t1;
    }

    public void setT1(double t1) {
        this.t1 = t1;
    }

    public double getTau0() {
        return tau0;
    }

    public void setTau0(double tau0) {
        this.tau0 = tau0;
    }

    public double getTau1() {
        return tau1;
    }

    public void setTau1(double tau1) {
        this.tau1 = tau1;
    }

    public int gettSteps() {
        return tSteps;
    }

    public void settSteps(int tSteps) {
        this.tSteps = tSteps;
    }

    public int getTauSteps() {
        return tauSteps;
    }

    public void setTauSteps(int tauSteps) {
        this.tauSteps = tauSteps;
    }

}