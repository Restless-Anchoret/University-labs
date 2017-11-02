package com.ran.mtop;

import com.ran.engine.algebra.vector.TwoDoubleVector;
import com.ran.engine.opengl.handlers.mouse.CameraControlMode;
import com.ran.engine.opengl.runner.OpenGLRunner;

import java.util.Collections;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        RungeKuttaSolver rungeKuttaSolver = new RungeKuttaSolver(
                (t, x) -> t * Math.exp(-t * t) - 2 * x * t, 0, 0, 0.01, 400);
        List<TwoDoubleVector> resultList = rungeKuttaSolver.solve();
        OpenGLRunner runner = new OpenGLRunner(
                Collections.singletonList(new MtopWorldFactory(resultList)),
                CameraControlMode.TWO_DIMENSION);
        runner.init();
        runner.run();
    }

}
