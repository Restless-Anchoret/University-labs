package com.ran.dissertation.factories;

import com.ran.dissertation.algebraic.common.ArithmeticOperations;
import com.ran.dissertation.algebraic.exception.AlgebraicException;
import com.ran.dissertation.algebraic.vector.ThreeDoubleVector;

import java.util.*;

public class PlaneTriangleIntersector {

    private static final PlaneTriangleIntersector INSTANCE = new PlaneTriangleIntersector();

    public static PlaneTriangleIntersector getInstance() {
        return INSTANCE;
    }

    public Result findIntersectionOfPlainAndTriangle(ThreeDoubleVector firstPoint,
                                                     ThreeDoubleVector secondPoint,
                                                     ThreeDoubleVector thirdPoint,
                                                     double z) {
        if (ArithmeticOperations.doubleEquals(firstPoint.getZ(), z) &&
                ArithmeticOperations.doubleEquals(secondPoint.getZ(), z) &&
                ArithmeticOperations.doubleEquals(thirdPoint.getZ(), z)) {
            return new Result(IntersectionType.TRIANGLE, Arrays.asList(firstPoint, secondPoint, thirdPoint));
        }

        if (ArithmeticOperations.doubleEquals(firstPoint.getZ(), z) &&
                ArithmeticOperations.doubleEquals(secondPoint.getZ(), z)) {
            return new Result(IntersectionType.SEGMENT, Arrays.asList(firstPoint, secondPoint));
        }
        if (ArithmeticOperations.doubleEquals(firstPoint.getZ(), z) &&
                ArithmeticOperations.doubleEquals(thirdPoint.getZ(), z)) {
            return new Result(IntersectionType.SEGMENT, Arrays.asList(firstPoint, thirdPoint));
        }
        if (ArithmeticOperations.doubleEquals(secondPoint.getZ(), z) &&
                ArithmeticOperations.doubleEquals(thirdPoint.getZ(), z)) {
            return new Result(IntersectionType.SEGMENT, Arrays.asList(secondPoint, thirdPoint));
        }

        Set<ThreeDoubleVector> intersections = new TreeSet<>();
        ThreeDoubleVector firstIntersection = findIntersectionOfPlainAndSegment(
                firstPoint, secondPoint, z);
        if (firstIntersection != null) {
            intersections.add(firstIntersection);
        }
        ThreeDoubleVector secondIntersection = findIntersectionOfPlainAndSegment(
                secondPoint, thirdPoint, z);
        if (secondIntersection != null) {
            intersections.add(secondIntersection);
        }
        ThreeDoubleVector thirdIntersection = findIntersectionOfPlainAndSegment(
                firstPoint, thirdPoint, z);
        if (thirdIntersection != null) {
            intersections.add(thirdIntersection);
        }

        if (intersections.isEmpty()) {
            return new Result(IntersectionType.NONE, Collections.emptyList());
        } else if (intersections.size() == 1) {
            return new Result(IntersectionType.SINGLE_POINT,
                    Collections.singletonList(intersections.iterator().next()));
        } else if (intersections.size() == 2) {
            Iterator<ThreeDoubleVector> iterator = intersections.iterator();
            ThreeDoubleVector firstFoundIntersection = iterator.next();
            ThreeDoubleVector secondFoundIntersection = iterator.next();
            return new Result(IntersectionType.SEGMENT, Arrays.asList(firstFoundIntersection, secondFoundIntersection));
        } else {
            throw new AlgebraicException("More than two vertices in intersection");
        }
    }

    private ThreeDoubleVector findIntersectionOfPlainAndSegment(ThreeDoubleVector firstPoint,
                                                                ThreeDoubleVector secondPoint,
                                                                double z) {
        if (firstPoint.getZ() < z && secondPoint.getZ() < z ||
                firstPoint.getZ() > z && secondPoint.getZ() > z ||
                ArithmeticOperations.doubleEquals(firstPoint.getZ(), secondPoint.getZ())) {
            return null;
        }
        double zProportion = (z - firstPoint.getZ()) / (secondPoint.getZ() - firstPoint.getZ());
        return new ThreeDoubleVector(
                zProportion * (secondPoint.getX() - firstPoint.getX()) + firstPoint.getX(),
                zProportion * (secondPoint.getY() - firstPoint.getY()) + firstPoint.getY(),
                z
        );
    }

    public enum IntersectionType {
        NONE,
        SINGLE_POINT,
        SEGMENT,
        TRIANGLE
    }

    public static class Result {
        private IntersectionType intersectionType;
        private List<ThreeDoubleVector> intersectionPoints;

        public Result(IntersectionType intersectionType, List<ThreeDoubleVector> intersectionPoints) {
            this.intersectionType = intersectionType;
            this.intersectionPoints = intersectionPoints;
        }

        public IntersectionType getIntersectionType() {
            return intersectionType;
        }

        public List<ThreeDoubleVector> getIntersectionPoints() {
            return intersectionPoints;
        }
    }

}