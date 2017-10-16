package com.ran.mtop;

import com.ran.engine.algebra.vector.TwoDoubleVector;
import com.ran.engine.opengl.handlers.mouse.CameraControlMode;
import com.ran.engine.opengl.runner.OpenGLRunner;

import java.util.Collections;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        RungeKuttaSolver rungeKuttaSolver = new RungeKuttaSolver(
                (t, x) -> x + Math.exp(t), 0, 0, 0.1, 40);
        List<TwoDoubleVector> resultList = rungeKuttaSolver.solve();
        OpenGLRunner runner = new OpenGLRunner(
                Collections.singletonList(new MtopWorldFactory(resultList)),
                CameraControlMode.TWO_DIMENSION);
        runner.init();
        runner.run();
    }

}
