package algorithm;

import utils.LabConstants;
import utils.Line;
import utils.Point;
import utils.Segment;

public class SegmentsIntersector {
    
    public static SegmentsIntersection findSegmentsIntersection(Segment firstSegment, Segment secondSegment) {
        Point a = firstSegment.getFirstPoint();
        Point b = firstSegment.getSecondPoint();
        Point c = secondSegment.getFirstPoint();
        Point d = secondSegment.getSecondPoint();
        
        if (!areIntervalsIntersected(a.getX(), b.getX(), c.getX(), d.getX()) ||
                !areIntervalsIntersected(a.getY(), b.getY(), c.getY(), d.getY())) {
            return SegmentsIntersection.createEmptyIntersection();
        }
        
        Line firstLine = new Line(a, b);
        Line secondLine = new Line(c, d);
        double linesCollinearityDeterminant = Point.countDeterminant(
                firstLine.getB(), firstLine.getA(), secondLine.getB(), secondLine.getA());
        
        if (Math.abs(linesCollinearityDeterminant) < LabConstants.EPSILON) {
            if (Math.abs(firstLine.distanceFromPoint(c)) > LabConstants.EPSILON ||
                    Math.abs(secondLine.distanceFromPoint(a)) > LabConstants.EPSILON) {
                return SegmentsIntersection.createEmptyIntersection();
            }
            if (a.compareTo(b) > 0) {
                Point temp = a;
                a = b;
                b = temp;
            }
            if (c.compareTo(d) > 0) {
                Point temp = c;
                c = d;
                d = temp;
            }
            Point firstIntersectionEndPoint = Point.max(a, c);
            Point secondIntersectionEndPoint = Point.min(b, d);
            return SegmentsIntersection.createIntersectionAtSegment(
                    firstIntersectionEndPoint, secondIntersectionEndPoint);
        } else {
            double intersectionX = Point.countDeterminant(
                    firstLine.getC(), firstLine.getB(), secondLine.getC(), secondLine.getB()) /
                    linesCollinearityDeterminant;
            double intersectionY = Point.countDeterminant(
                    firstLine.getA(), firstLine.getC(), secondLine.getA(), secondLine.getC()) /
                    linesCollinearityDeterminant;
            if (!isNumberInInterval(a.getX(), b.getX(), intersectionX) ||
                    !isNumberInInterval(a.getY(), b.getY(), intersectionY) ||
                    !isNumberInInterval(c.getX(), d.getX(), intersectionX) ||
                    !isNumberInInterval(c.getY(), d.getY(), intersectionY)) {
                return SegmentsIntersection.createEmptyIntersection();
            }
            Point intersection = new Point(intersectionX, intersectionY);
            return (linesCollinearityDeterminant > 0.0 ?
                    SegmentsIntersection.createIntersectionAtPointTurningLeft(intersection) :
                    SegmentsIntersection.createIntersectionAtPointTurningRight(intersection));
	}
    }
    
    private static boolean isNumberInInterval(double firstEdge, double secondEdge, double number) {
        return Math.min(firstEdge, secondEdge) <= number + LabConstants.EPSILON &&
                number <= Math.max(firstEdge, secondEdge) + LabConstants.EPSILON;
    }
    
    private static boolean areIntervalsIntersected(double a, double b, double c, double d) {
        if (a > b) {
            double temp = a;
            a = b;
            b = temp;
        }
        if (c > d) {
            double temp = c;
            c = d;
            d = temp;
        }
        return Math.max(a, c) <= Math.min(b, d) + LabConstants.EPSILON;
    }

    private SegmentsIntersector() { }
            
}