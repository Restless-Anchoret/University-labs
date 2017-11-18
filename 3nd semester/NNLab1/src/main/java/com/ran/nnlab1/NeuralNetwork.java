package com.ran.nnlab1;

public class NeuralNetwork {

    private static final int INPUT_SIZE = 400;
    
    private double[] w = new double[INPUT_SIZE + 1];
    private int[] lastInput = null;
    private int lastResult = 0;
    
    public void init() {
        for (int i = 0; i <= INPUT_SIZE; i++) {
            w[i] = Math.random() - 0.5;
        }
    }
    
    public int recognize(int[] x) {
        lastInput = x;
        double s = w[0];
        for (int i = 0; i < INPUT_SIZE; i++) {
            s += x[i] * w[i + 1];
        }
        lastResult = activationFunction(s);
        return lastResult;
    }
    
    public void failLastResult() {
        if (lastInput == null) {
            return;
        }
        int delta = (lastResult == 0 ? 1 : -1);
        w[0] = w[0] + delta * 0.5;
        for (int i = 1; i <= INPUT_SIZE; i++) {
            w[i] = w[i] + 0.5 * delta * lastInput[i - 1];
        }
        lastInput = null;
    }
    
    private int activationFunction(double s) {
        return (s >= 0 ? 1 : 0);
    }
    
}
