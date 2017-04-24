package com.ran.dissertation.algebraic.function;

import com.ran.dissertation.algebraic.common.AlgebraicObject;
import com.ran.dissertation.algebraic.common.ArithmeticOperations;
import com.ran.dissertation.algebraic.exception.FunctionParameterOutOfBoundsException;
import com.ran.dissertation.algebraic.vector.SingleDouble;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class DoubleFunction<T extends AlgebraicObject<T>> implements AlgebraicObject<DoubleFunction<T>>, Function<Double, T> {

    private final Function<Double, T> function;
    private final double minParameterValue;
    private final double maxParameterValue;
    
    public static <T extends AlgebraicObject<T>> DoubleFunction<T> createConstantFunction(T value) {
        return new DoubleFunction<>(point -> value);
    }
    
    public DoubleFunction(Function<Double, T> function, double minParameterValue, double maxParameterValue) {
        this.function = function;
        this.minParameterValue = minParameterValue;
        this.maxParameterValue = maxParameterValue;
    }

    public DoubleFunction(Function<Double, T> function) {
        this(function, -Double.MAX_VALUE, Double.MAX_VALUE);
    }

    public double getMinParameterValue() {
        return minParameterValue;
    }

    public double getMaxParameterValue() {
        return maxParameterValue;
    }

    private Function<Double, T> getFunction() {
        return function;
    }
    
    @Override
    public T apply(Double point) {
        if (ArithmeticOperations.doubleLess(point, minParameterValue) ||
                ArithmeticOperations.doubleGreater(point, maxParameterValue)) {
            throw new FunctionParameterOutOfBoundsException(point, minParameterValue, maxParameterValue);
        }
        return function.apply(point);
    }
    
    public List<T> applyForList(List<Double> points) {
        List<T> values = new ArrayList<>(points.size());
        Iterator<T> iterator = iteratorForList(points);
        while (iterator.hasNext()) {
            values.add(iterator.next());
        }
        return values;
    }
    
    public List<T> applyForGrid(double firstPoint, double lastPoint, int segments) {
        List<T> values = new ArrayList<>(segments + 1);
        Iterator<T> iterator = iteratorForGrid(firstPoint, lastPoint, segments);
        while (iterator.hasNext()) {
            values.add(iterator.next());
        }
        return values;
    }
    
    public Map<Double, T> mapForList(List<Double> points) {
        Map<Double, T> pointsToValues = new HashMap<>(points.size());
        for (double point: points) {
            pointsToValues.put(point, apply(point));
        }
        return pointsToValues;
    }
    
    public Iterator<T> iteratorForList(List<Double> points) {
        return new Iterator<T>() {
            private final Iterator<Double> doubleIterator = points.iterator();
            @Override
            public boolean hasNext() {
                return doubleIterator.hasNext();
            }

            @Override
            public T next() {
                Double nextDouble = doubleIterator.next();
                if (nextDouble == null) {
                    return null;
                } else {
                    return apply(nextDouble);
                }
            }
        };
    }
    
    public Iterator<T> iteratorForGrid(double firstPoint, double lastPoint, int segments) {
        List<Double> points = new ArrayList<>(segments + 1);
        for (int i = 0; i <= segments; i++) {
            points.add(firstPoint + (lastPoint - firstPoint) * i / segments);
        }
        return iteratorForList(points);
    }
    
    @Override
    public DoubleFunction<T> add(DoubleFunction<T> other) {
        return joinFunctions(this, other,
                point -> this.getFunction().apply(point).add(other.getFunction().apply(point))
        );
    }

    @Override
    public DoubleFunction<T> substract(DoubleFunction<T> other) {
        return joinFunctions(this, other,
                point -> this.getFunction().apply(point).substract(other.getFunction().apply(point))
        );
    }

    @Override
    public DoubleFunction<T> multiply(int number) {
        return new DoubleFunction<>(
                point -> function.apply(point).multiply(number),
                minParameterValue, maxParameterValue
        );
    }

    @Override
    public DoubleFunction<T> multiply(double number) {
        return new DoubleFunction<>(
                point -> function.apply(point).multiply(number),
                minParameterValue, maxParameterValue
        );
    }

    @Override
    public DoubleFunction<T> multiply(DoubleFunction<T> other) {
        return joinFunctions(this, other,
                point -> this.getFunction().apply(point).multiply(other.apply(point))
        );
    }

    @Override
    public DoubleFunction<T> elementWiseMultiply(DoubleFunction<T> other) {
        return joinFunctions(this, other,
                point -> this.getFunction().apply(point).elementWiseMultiply(other.getFunction().apply(point))
        );
    }

    @Override
    public double scalarMultiply(DoubleFunction<T> other) {
        throw new UnsupportedOperationException();
    }
    
    private DoubleFunction<T> joinFunctions(DoubleFunction<T> first, DoubleFunction<T> second, Function<Double, T> function) {
        return new DoubleFunction<>(
                function,
                Math.max(first.getMinParameterValue(), second.getMinParameterValue()),
                Math.min(first.getMaxParameterValue(), second.getMaxParameterValue())
        );
    }
    
    public DoubleFunction<T> superposition(DoubleFunction<SingleDouble> singleDoubleFunction) {
        return new DoubleFunction<>(
                point -> this.apply(singleDoubleFunction.apply(point).getValue()),
                singleDoubleFunction.getMinParameterValue(),
                singleDoubleFunction.getMaxParameterValue()
        );
    }
    
}