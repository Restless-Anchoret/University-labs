package algorithm;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import utils.Edge;
import utils.Point;
import utils.PointsWithEdges;
import utils.Segment;

public class UnionAlgorithmImplementation implements UnionAlgorithm {

    @Override
    public PointsWithEdges unionPolygons(PointsWithEdges firstPolygon, PointsWithEdges secondPolygon) {
        List<Point> firstPointsSequence = convertPolygonToPointsSequence(firstPolygon);
        List<Point> secondPointsSequence = convertPolygonToPointsSequence(secondPolygon);
        
        List<Segment> firstExternalAmputation = makeExternalAmputation(firstPointsSequence, secondPointsSequence);
        List<Segment> secondExternalAmputation = makeExternalAmputation(secondPointsSequence, firstPointsSequence);
        
        PointsWithEdges union = mergePolygonsParts(firstExternalAmputation, secondExternalAmputation);
        return union;
    }
    
    private List<Point> convertPolygonToPointsSequence(PointsWithEdges polygon) {
        int pointsQuantity = polygon.getPoints().size();
        List<Point> pointsSequence = new ArrayList<>(pointsQuantity);
        if (pointsQuantity == 0) {
            return pointsSequence;
        }
        
        List<List<Integer>> pointsNearbours = new ArrayList<>(pointsQuantity);
        for (int i = 0; i < pointsQuantity; i++) {
            pointsNearbours.add(new ArrayList<>());
        }
        for (Edge edge: polygon.getEdges()) {
            pointsNearbours.get(edge.getFirstIndex()).add(edge.getSecondIndex());
            pointsNearbours.get(edge.getSecondIndex()).add(edge.getFirstIndex());
        }
        
        boolean[] visited = new boolean[pointsQuantity];
        dfs(0, pointsSequence, polygon.getPoints(), pointsNearbours, visited);
        return convertToClockwise(pointsSequence);
    }
    
    private void dfs(int vertice, List<Point> pointsSequence, List<Point> originalPoints,
            List<List<Integer>> pointsNearbours, boolean[] visited) {
        pointsSequence.add(originalPoints.get(vertice));
        visited[vertice] = true;
        for (int nearbour: pointsNearbours.get(vertice)) {
            if (!visited[nearbour]) {
                dfs(nearbour, pointsSequence, originalPoints, pointsNearbours, visited);
            }
        }
    }
    
    private List<Point> convertToClockwise(List<Point> pointsSequence) {
        double doubleArea = 0.0;
        Point firstPoint = pointsSequence.get(0);
        for (int i = 1; i < pointsSequence.size() - 1; i++) {
            Point secondPoint = pointsSequence.get(i);
            Point thirdPoint = pointsSequence.get(i + 1);
            doubleArea += secondPoint.substract(firstPoint)
                    .countDeterminantWithPoint(thirdPoint.substract(firstPoint));
        }
        if (doubleArea > 0.0) {
            return pointsSequence;
        } else {
            List<Point> rotatedPointsSequence = new ArrayList<>(pointsSequence.size());
            for (int i = pointsSequence.size() - 1; i >= 0; i--) {
                rotatedPointsSequence.add(pointsSequence.get(i));
            }
            return rotatedPointsSequence;
        }
    }
    
    private List<Segment> makeExternalAmputation(List<Point> mainPolygon, List<Point> divisionPolygon) {
        List<Segment> segmentsList = new ArrayList<>();
        int mainSegmentsQuantity = mainPolygon.size();
        int divisionSegmentsQuantity = divisionPolygon.size();
        for (int i = 0; i < mainSegmentsQuantity; i++) {
            Segment mainSegment = new Segment(mainPolygon.get(i),
                    mainPolygon.get((i + 1) % mainSegmentsQuantity));
            Comparator<Point> pointsComparator = 
                    (mainSegment.getFirstPoint().compareTo(mainSegment.getSecondPoint()) < 0 ?
                    Comparator.naturalOrder() : Comparator.reverseOrder());
            Set<Point> foundPoints = new TreeSet<>(pointsComparator);
            foundPoints.add(mainSegment.getFirstPoint());
            foundPoints.add(mainSegment.getSecondPoint());
            for (int j = 0; j < divisionSegmentsQuantity; j++) {
                Segment divisionSegment = new Segment(divisionPolygon.get(j),
                        divisionPolygon.get((j + 1) % divisionSegmentsQuantity));
                SegmentsIntersection segmentsIntersection = SegmentsIntersector
                        .findSegmentsIntersection(mainSegment, divisionSegment);
                switch (segmentsIntersection.getIntersectionResult()) {
                    case INTERSECT_AT_POINT_TURNING_RIGHT:
                    case INTERSECT_AT_POINT_TURNING_LEFT:
                        foundPoints.add(segmentsIntersection.getIntersection());
                        break;
                    case INTERSECT_AT_SEGMENT:
                        foundPoints.add(segmentsIntersection.getFirstIntersectionEndPoint());
                        foundPoints.add(segmentsIntersection.getSecondIntersectionEndPoint());
                        break;
                }
            }
            Point previousPoint = null;
            for (Point currentPoint: foundPoints) {
                if (previousPoint != null) {
                    Point middlePoint = new Point((previousPoint.getX() + currentPoint.getX()) / 2.0,
                            (previousPoint.getY() + currentPoint.getY()) / 2.0);
                    PointToPolygonBelongingAlgorithm.PointLocation pointLocation =
                            PointToPolygonBelongingAlgorithm.findPointLocationInRelationToPolygon(middlePoint, divisionPolygon);
                    if (pointLocation != PointToPolygonBelongingAlgorithm.PointLocation.INSIDE_POLYGON) {
                        segmentsList.add(new Segment(previousPoint, currentPoint));
                    }
                }
                previousPoint = currentPoint;
            }
        }
        return segmentsList;
    }
    
    private PointsWithEdges mergePolygonsParts(List<Segment> firstPolygonPart,
            List<Segment> secondPolygonPart) {
        Set<Segment> segmentsSet = new HashSet<>();
        segmentsSet.addAll(firstPolygonPart);
        segmentsSet.addAll(secondPolygonPart);

        List<Point> points = new ArrayList<>();
        Set<Edge> edgesSet = new HashSet<>();
        
        Map<Point, Integer> pointsToIndices = new TreeMap<>();
        int indexCounter = 0;
        
        for (Segment segment: segmentsSet) {
            Point firstPoint = segment.getFirstPoint();
            Integer firstPointIndex = pointsToIndices.get(firstPoint);
            if (firstPointIndex == null) {
                firstPointIndex = indexCounter;
                pointsToIndices.put(firstPoint, firstPointIndex);
                points.add(firstPoint);
                indexCounter++;
            }
            Point secondPoint = segment.getSecondPoint();
            Integer secondPointIndex = pointsToIndices.get(secondPoint);
            if (secondPointIndex == null) {
                secondPointIndex = indexCounter;
                pointsToIndices.put(secondPoint, secondPointIndex);
                points.add(secondPoint);
                indexCounter++;
            }
            Edge reversedEdge = new Edge(secondPointIndex, firstPointIndex);
            if (edgesSet.contains(reversedEdge)) {
                edgesSet.remove(reversedEdge);
            } else {
                edgesSet.add(new Edge(firstPointIndex, secondPointIndex));
            }
        }
        
        List<Edge> edges = new ArrayList<>(edgesSet);
        return new PointsWithEdges(edges, points);
    }

}