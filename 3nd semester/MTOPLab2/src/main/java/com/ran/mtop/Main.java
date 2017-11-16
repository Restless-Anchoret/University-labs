package com.ran.mtop;

import com.ran.engine.algebra.vector.TwoDoubleVector;
import com.ran.engine.opengl.handlers.mouse.CameraControlMode;
import com.ran.engine.opengl.runner.OpenGLRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        RungeKuttaSolver rungeKuttaSolver = new RungeKuttaSolver(
                Arrays.asList(
                        (l, x) -> x.get(1),
                        (l, x) -> (2 * l * x.get(1)) / (1 + l * l)
                ), 0, Arrays.asList(0.0, 3.0), 0.1, 40, 2);
        List<List<TwoDoubleVector>> resultList = rungeKuttaSolver.solve();
        OpenGLRunner runner = new OpenGLRunner(
                Collections.singletonList(new MtopWorldFactory(resultList)),
                CameraControlMode.TWO_DIMENSION);
        runner.init();
        runner.run();
    }

}
