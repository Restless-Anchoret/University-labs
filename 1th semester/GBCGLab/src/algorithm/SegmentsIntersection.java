package algorithm;

import utils.Point;

public class SegmentsIntersection {
    
    public static SegmentsIntersection createEmptyIntersection() {
        return new SegmentsIntersection(null, null, null, Result.DO_NOT_INTERSECT);
    }
    
    public static SegmentsIntersection createIntersectionAtPointTurningLeft(Point point) {
        return new SegmentsIntersection(point, null, null, Result.INTERSECT_AT_POINT_TURNING_LEFT);
    }
    
    public static SegmentsIntersection createIntersectionAtPointTurningRight(Point point) {
        return new SegmentsIntersection(point, null, null, Result.INTERSECT_AT_POINT_TURNING_RIGHT);
    }
    
    public static SegmentsIntersection createIntersectionAtSegment(Point firstPoint, Point secondPoint) {
        return new SegmentsIntersection(null, firstPoint, secondPoint, Result.INTERSECT_AT_SEGMENT);
    }
    
    private final Point intersection;
    private final Point firstIntersectionEndPoint;
    private final Point secondIntersectionEndPoint;
    private final Result intersectionResult;

    private SegmentsIntersection(Point intersection,
            Point firstIntersectionEndPoint, Point secondIntersectionEndPoint,
            Result intersectionResult) {
        this.intersection = intersection;
        this.firstIntersectionEndPoint = firstIntersectionEndPoint;
        this.secondIntersectionEndPoint = secondIntersectionEndPoint;
        this.intersectionResult = intersectionResult;
    }

    public Point getIntersection() {
        return intersection;
    }

    public Point getFirstIntersectionEndPoint() {
        return firstIntersectionEndPoint;
    }

    public Point getSecondIntersectionEndPoint() {
        return secondIntersectionEndPoint;
    }

    public Result getIntersectionResult() {
        return intersectionResult;
    }
    
    public enum Result {
        DO_NOT_INTERSECT,
        INTERSECT_AT_POINT_TURNING_LEFT,
        INTERSECT_AT_POINT_TURNING_RIGHT,
        INTERSECT_AT_SEGMENT
    }

}