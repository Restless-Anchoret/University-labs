package algorithm;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;
import utils.Edge;
import utils.LabConstants;
import utils.Point;
import utils.PointsWithEdges;
import utils.Segment;

public class AlternativeUnionAlgorithmImplementation implements UnionAlgorithm {

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
        if (doubleArea < 0.0) {
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
            Map<Point, List<Direction>> pointsToDirections = new TreeMap<>(pointsComparator);
            addDirectionToMapForPoint(pointsToDirections, mainSegment.getFirstPoint(), Direction.RIGHT_DIRECTION);
            for (int j = 0; j < divisionSegmentsQuantity; j++) {
                Segment divisionSegment = new Segment(divisionPolygon.get(j),
                        divisionPolygon.get((j + 1) % divisionSegmentsQuantity));
                SegmentsIntersection segmentsIntersection = SegmentsIntersector
                        .findSegmentsIntersection(mainSegment, divisionSegment);
                switch (segmentsIntersection.getIntersectionResult()) {
                    case INTERSECT_AT_POINT_TURNING_RIGHT:
                        addDirectionToMapForPoint(pointsToDirections,
                                segmentsIntersection.getIntersection(), Direction.LEFT_DIRECTION);
                        break;
                    case INTERSECT_AT_POINT_TURNING_LEFT:
                        addDirectionToMapForPoint(pointsToDirections,
                                segmentsIntersection.getIntersection(), Direction.RIGHT_DIRECTION);
                        break;
                    case INTERSECT_AT_SEGMENT:
                        addDirectionToMapForPoint(pointsToDirections,
                                segmentsIntersection.getFirstIntersectionEndPoint(), Direction.INNER_DIRECTION);
                        addDirectionToMapForPoint(pointsToDirections,
                                segmentsIntersection.getSecondIntersectionEndPoint(), Direction.INNER_DIRECTION);
                        break;
                }
            }
            addDirectionToMapForPoint(pointsToDirections, mainSegment.getSecondPoint(), Direction.LEFT_DIRECTION);
            if (pointsToDirections.size() == 2) {
                if (!isPointInsidePolygon(mainSegment.getFirstPoint(), divisionPolygon)) {
                    segmentsList.add(mainSegment);
                }
            } else {
                Point previousPoint = null;
                for (Point currentPoint: pointsToDirections.keySet()) {
                    if (previousPoint != null) {
                        List<Direction> previousDirections = pointsToDirections.get(previousPoint);
                        Direction previousLastDirection = previousDirections.get(previousDirections.size() - 1);
                        List<Direction> currentDirections = pointsToDirections.get(currentPoint);
                        Direction currentFirstDirection = currentDirections.get(0);
                        if (previousLastDirection == Direction.RIGHT_DIRECTION &&
                                currentFirstDirection == Direction.LEFT_DIRECTION ||
                                previousLastDirection == Direction.INNER_DIRECTION &&
                                currentFirstDirection == Direction.INNER_DIRECTION) {
                            segmentsList.add(new Segment(previousPoint, currentPoint));
                        }
                    }
                    previousPoint = currentPoint;
                }
            }
        }
        return segmentsList;
    }
    
    private void addDirectionToMapForPoint(Map<Point, List<Direction>> map, Point point, Direction direction) {
        if (!map.containsKey(point)) {
            map.put(point, new ArrayList<>());
        }
        map.get(point).add(direction);
    }
    
    private boolean isPointInsidePolygon(Point point, List<Point> polygonPoints) {
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
    
    private PointsWithEdges mergePolygonsParts(List<Segment> firstPolygonPart,
            List<Segment> secondPolygonPart) {
        Set<Segment> segmentsSet = convertToSetExceptingOddSegments(firstPolygonPart, secondPolygonPart);
        List<Point> points = new ArrayList<>();
        List<Edge> edges = new ArrayList<>();
        
        Map<Point, Integer> pointsToIndices = new HashMap<>();
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
            edges.add(new Edge(firstPointIndex, secondPointIndex));
        }
        return new PointsWithEdges(edges, points);
    }
    
    private Set<Segment> convertToSetExceptingOddSegments(List<Segment> firstList, List<Segment> secondList) {
        Set<Segment> reversedSecondSet = secondList.stream()
                .map(segment -> new Segment(segment.getSecondPoint(), segment.getFirstPoint()))
                .collect(Collectors.toSet());
        Set<Segment> segmentsToExcept = new HashSet<>(firstList);
        segmentsToExcept.retainAll(reversedSecondSet);
        Set<Segment> resultSet = new HashSet<>();
        resultSet.addAll(firstList);
        resultSet.addAll(secondList);
        resultSet.removeAll(segmentsToExcept);
        return resultSet;
    }
    
    private static enum Direction {
        LEFT_DIRECTION(1),
        INNER_DIRECTION(2),
        RIGHT_DIRECTION(3);
        
        public int value;

        private Direction(int value) {
            this.value = value;
        }
    }

}