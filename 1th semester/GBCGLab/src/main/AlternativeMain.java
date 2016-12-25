package main;

import algorithm.AlternativeUnionAlgorithmImplementation;
import algorithm.AnyPolygonGenerator;
import algorithm.ConvexPolygonGenerator;
import algorithm.PolygonGridValidator;
import controller.MainController;
import java.awt.EventQueue;
import java.util.List;
import utils.LabConstants;
import utils.Point;

public class AlternativeMain {

    public static void main(String[] args) {
//        Segment firstSegment = new Segment(new Point(-2, 0), new Point(2, 0));
//        Segment secondSegment = new Segment(new Point(0, -2), new Point(0, 2));
//        SegmentsIntersection segmentsIntersection = SegmentsIntersector
//                .findSegmentsIntersection(firstSegment, secondSegment);
//        System.out.println(segmentsIntersection.getIntersectionResult());
//        System.out.println(segmentsIntersection.getIntersection());
        EventQueue.invokeLater(() -> {
            MainController mainController = new MainController(
                    new AlternativeUnionAlgorithmImplementation(),
                    new PolygonGridValidator(),
                    new AnyPolygonGenerator(),
                    new ConvexPolygonGenerator()
            );
            mainController.startApplication();
        });
//        Point point = new Point(0, 0);
//        List<Point> polygon = Arrays.asList(new Point(2, 2), new Point(-2, 2), new Point(-2, -2), new Point(2, -2));
//        System.out.println(isPointInsidePolygon(point, polygon));
    }
    
    private static boolean isPointInsidePolygon(Point point, List<Point> polygonPoints) {
        double polygonDoubleArea = 0.0;
        Point startPoint = polygonPoints.get(0);
        for (int i = 1; i < polygonPoints.size() - 1; i++) {
            Point firstPoint = polygonPoints.get(i);
            Point secondPoint = polygonPoints.get(i + 1);
            polygonDoubleArea += Math.abs(firstPoint.substract(startPoint)
                    .countDeterminantWithPoint(secondPoint.substract(startPoint)));
        }
        double polygonWithPointDoubleArea = 0.0;
        for (int i = 0; i < polygonPoints.size(); i++) {
            Point firstPoint = polygonPoints.get(i);
            Point secondPoint = polygonPoints.get((i + 1) % polygonPoints.size());
            polygonWithPointDoubleArea += Math.abs(firstPoint.substract(point)
                    .countDeterminantWithPoint(secondPoint.substract(point)));
        }
        return Math.abs(polygonWithPointDoubleArea - polygonDoubleArea) < LabConstants.EPSILON;
    }
    
}