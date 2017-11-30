package com.ran.mtop;

import com.codepoetics.protonpack.StreamUtils;
import com.ran.engine.algebra.function.DoubleFunction;
import com.ran.engine.algebra.vector.SingleDouble;
import com.ran.engine.algebra.vector.TwoDoubleVector;
import com.ran.engine.opengl.handlers.mouse.CameraControlMode;
import com.ran.engine.opengl.runner.OpenGLRunner;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

public class Main {

    public static void main(String[] args) {
//        JFunctional2 jFunctional = new JFunctional2();
//        GradientDescent2 gradientDescent = new GradientDescent2();
//        GradientDescent2.Result result = gradientDescent.findMaximum(jFunctional, 900.0, 0.5, 5, 1e-4, 200);
//        System.out.println();
//        System.out.println("Result: a1 = " + result.getA1() + "; a2 = " + result.getA2() +
//                "; functional value: " + result.getFunctionValue());
//        java.util.function.DoubleFunction<Double> sourceX7 = JFunctional2.createX7(result.getA1(), result.getA2());

        JFunctional3 jFunctional = new JFunctional3();
        GradientDescent3 gradientDescent = new GradientDescent3();
        GradientDescent3.Result result = gradientDescent.findMaximum(jFunctional, 500.0, 150.0, 1200.0, 5, 1e-5, 200);
        System.out.println();
        System.out.println("Result: a1 = " + result.getA1() + "; a2 = " + result.getA2() + "; a3 = " + result.getA3() +
                "; functional value: " + result.getFunctionValue());
        java.util.function.DoubleFunction<Double> sourceX7 = JFunctional3.createX7(result.getA1(), result.getA2(), result.getA3());

        Function<Double, SingleDouble> tempX7 = l -> new SingleDouble(sourceX7.apply(l));
        DoubleFunction<SingleDouble> actualX7 = new DoubleFunction<>(tempX7);
        List<Double> grid = DoubleStream.iterate(0.0, x -> x + 0.1).limit(1801).boxed().collect(Collectors.toList());
        List<Double> gridValues = actualX7.applyForList(grid).stream().map(SingleDouble::getValue).collect(Collectors.toList());
        List<TwoDoubleVector> graphGrid = StreamUtils.zip(grid.stream(), gridValues.stream(), TwoDoubleVector::new).collect(Collectors.toList());
        OpenGLRunner runner = new OpenGLRunner(
                Collections.singletonList(new MtopWorldFactory(Collections.singletonList(graphGrid))),
                CameraControlMode.TWO_DIMENSION);
        runner.init();
        runner.run();
    }

}
