package algorithm;

import java.util.List;
import utils.LabConstants;
import utils.Point;
import utils.Segment;

public class PointToPolygonBelongingAlgorithm {

    public static PointLocation findPointLocationInRelationToPolygon(Point point, List<Point> polygonPoints) {
        int n = polygonPoints.size();
        double theRightestXCoordinate = -Double.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            Point firstPoint = polygonPoints.get(i);
            Point secondPoint = polygonPoints.get((i + 1) % n);
            SegmentsIntersection segmentsIntersection = SegmentsIntersector
                    .findSegmentsIntersection(new Segment(firstPoint, secondPoint), new Segment(point, point));
            if (segmentsIntersection.getIntersectionResult() == SegmentsIntersection.Result.INTERSECT_AT_SEGMENT) {
                return PointLocation.ON_THE_EDGE_OF_POLYGON;
            }
            theRightestXCoordinate = Math.max(theRightestXCoordinate, firstPoint.getX());
        }
        Point rayEndPoint = new Point(Math.max(theRightestXCoordinate, point.getX() + 10.0), point.getY());
        Segment raySegment = new Segment(point, rayEndPoint);
        int intersectionsCounter = 0;
        for (int i = 0; i < n; i++) {
            Point firstPoint = movePointUpIfNecessary(polygonPoints.get(i), point);
            Point secondPoint = movePointUpIfNecessary(polygonPoints.get((i + 1) % n), point);
            Segment segment = new Segment(firstPoint, secondPoint);
            SegmentsIntersection segmentsIntersection = SegmentsIntersector
                    .findSegmentsIntersection(raySegment, segment);
            if (segmentsIntersection.getIntersectionResult() == 
                    SegmentsIntersection.Result.INTERSECT_AT_POINT_TURNING_LEFT ||
                    segmentsIntersection.getIntersectionResult() ==
                    SegmentsIntersection.Result.INTERSECT_AT_POINT_TURNING_RIGHT) {
                intersectionsCounter++;
            }
        }
        if (intersectionsCounter % 2 == 0) {
            return PointLocation.OUTSIDE_OF_POLYGON;
        } else {
            return PointLocation.INSIDE_POLYGON;
        }
    }
    
    private static Point movePointUpIfNecessary(Point pointToMoveUp, Point rayStartPoint) {
        if (Math.abs(pointToMoveUp.getY() - rayStartPoint.getY()) < LabConstants.EPSILON) {
            return new Point(pointToMoveUp.getX(), pointToMoveUp.getY() + LabConstants.EPSILON * 2.0);
        } else {
            return pointToMoveUp;
        }
    }
    
    public static enum PointLocation {
        INSIDE_POLYGON,
        ON_THE_EDGE_OF_POLYGON,
        OUTSIDE_OF_POLYGON
    }
    
}