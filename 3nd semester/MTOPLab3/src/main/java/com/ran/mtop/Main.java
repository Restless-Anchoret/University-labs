package com.ran.mtop;

import com.ran.engine.algebra.vector.TwoDoubleVector;
import com.ran.engine.opengl.handlers.mouse.CameraControlMode;
import com.ran.engine.opengl.runner.OpenGLRunner;

import java.util.Collections;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        RungeKuttaSolver rungeKuttaSolver = SolverCreator.getSolver(0.1, l -> 900.0);
        List<List<TwoDoubleVector>> resultList = rungeKuttaSolver.solve();
        System.out.println("Functional value: " + JFunctional.evaluate(resultList));
        OpenGLRunner runner = new OpenGLRunner(
                Collections.singletonList(new MtopWorldFactory(resultList)),
                CameraControlMode.TWO_DIMENSION);
        runner.init();
        runner.run();
    }

}
