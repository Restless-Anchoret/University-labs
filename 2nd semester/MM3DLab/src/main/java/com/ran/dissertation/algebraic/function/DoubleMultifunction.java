package com.ran.dissertation.algebraic.function;

import com.ran.dissertation.algebraic.common.AlgebraicObject;
import com.ran.dissertation.algebraic.common.ArithmeticOperations;
import com.ran.dissertation.algebraic.exception.CreationException;
import com.ran.dissertation.algebraic.exception.FunctionParameterOutOfBoundsException;
import java.util.Iterator;
import java.util.List;

public class DoubleMultifunction<T extends AlgebraicObject<T>> extends DoubleFunction<T> {
    
    private final List<DoubleFunction<T>> doubleFunctions;
    
    private DoubleMultifunction(List<DoubleFunction<T>> doubleFunctions,
            double minParameterValue, double maxParameterValue) {
        super(
                point -> {
                    for (DoubleFunction<T> doubleFunction: doubleFunctions) {
                        if (ArithmeticOperations.doubleGreaterOrEquals(point, doubleFunction.getMinParameterValue()) &&
                                ArithmeticOperations.doubleLessOrEquals(point, doubleFunction.getMaxParameterValue())) {
                            return doubleFunction.apply(point);
                        }
                    }
                    throw new FunctionParameterOutOfBoundsException(
                            point, minParameterValue, maxParameterValue);
                },
                minParameterValue,
                maxParameterValue
        );
        this.doubleFunctions = doubleFunctions;
    }

    @Override
    public Iterator<T> iteratorForGrid(double firstPoint, double lastPoint, int segments) {
        return new Iterator<T>() {
            private final Iterator<DoubleFunction<T>> doubleFunctionsIterator = doubleFunctions.iterator();
            private DoubleFunction<T> currentFunction = null;
            private int nextIndex = 0;
            @Override
            public boolean hasNext() {
                return nextIndex <= segments;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    return null;
                }
                double nextPoint = firstPoint + (lastPoint - firstPoint) * nextIndex / segments;
                while (currentFunction == null || ArithmeticOperations.doubleGreater(nextPoint, currentFunction.getMaxParameterValue())) {
                    currentFunction = doubleFunctionsIterator.next();
                    if (currentFunction == null) {
                        throw new FunctionParameterOutOfBoundsException(nextPoint, getMinParameterValue(), getMaxParameterValue());
                    }
                }
                T result = currentFunction.apply(nextPoint);
                nextIndex++;
                return result;
            }
        };
    }
    
    public static <T extends AlgebraicObject<T>> DoubleMultifunction<T> makeMultifunction(List<DoubleFunction<T>> doubleFunctions) {
        if (doubleFunctions.isEmpty()) {
            throw new CreationException("Multifunction should contain at least one simple function");
        }
        DoubleFunction<T> previousFunction = null;
        for (DoubleFunction<T> currentFunction: doubleFunctions) {
            if (previousFunction != null && !ArithmeticOperations.doubleEquals(
                    previousFunction.getMaxParameterValue(), currentFunction.getMinParameterValue())) {
                throw new CreationException("Multifunction parameter bounds are not corrected");
            }
            previousFunction = currentFunction;
        }
        double multiFunctionMinParameterValue = doubleFunctions.iterator().next().getMinParameterValue();
        double multiFunctionMaxParameterValue = previousFunction.getMaxParameterValue();
        return new DoubleMultifunction<>(doubleFunctions, multiFunctionMinParameterValue, multiFunctionMaxParameterValue);
    }

}