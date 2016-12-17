package controller;

import utils.PointsWithEdges;
import utils.Edge;
import utils.Point;
import java.util.ArrayList;
import java.util.List;
import ui.ImagePanel;
import ui.ImagePanelListener;
import utils.LabConstants;

public class SettingEdgeController implements ImagePanelListener {

    private SimplePaintStrategy paintStrategy;

    public SettingEdgeController(SimplePaintStrategy paintStrategy) {
        this.paintStrategy = paintStrategy;
    }
    
    @Override
    public void mouseLeftClicked(ImagePanel imagePanel, int x, int y, int width, int height) {
        PointsWithEdges pointsWithEdges = paintStrategy.getPointsWithEdges();
        int chosenPointIndex = paintStrategy.getChosenPointIndex();
        int pointIndex = findPointIndex(x, y);
        if (chosenPointIndex == -1) {
            if (pointIndex == -1) {
                Point newPoint = new Point(x, y);
                pointsWithEdges.getPoints().add(newPoint);
            } else {
                paintStrategy.setChosenPointIndex(pointIndex);
            }
        } else {
            if (pointIndex == -1) {
                Point newPoint = new Point(x, y);
                pointsWithEdges.getPoints().add(newPoint);
                Edge newEdge = new Edge(chosenPointIndex, pointsWithEdges.getPoints().size() - 1);
                pointsWithEdges.getEdges().add(newEdge);
            } else if (pointIndex != chosenPointIndex) {
                Edge newEdge = new Edge(chosenPointIndex, pointIndex);
                pointsWithEdges.getEdges().add(newEdge);
            }
            paintStrategy.setChosenPointIndex(-1);
        }
        imagePanel.repaint();
    }

    @Override
    public void mouseMiddleClicked(ImagePanel imagePanel, int x, int y, int width, int height) {
    }

    @Override
    public void mouseRightClicked(ImagePanel imagePanel, int x, int y, int width, int height) {
        paintStrategy.setChosenPointIndex(-1);
        PointsWithEdges pointsWithEdges = paintStrategy.getPointsWithEdges();
        int pointIndex = findPointIndex(x, y);
        if (pointIndex != -1) {
            List<Edge> newEdges = new ArrayList<>();
            for (Edge edge: pointsWithEdges.getEdges()) {
                if (edge.getFirstIndex() != pointIndex && edge.getSecondIndex() != pointIndex) {
                    int newFirstIndex = (edge.getFirstIndex() > pointIndex ? edge.getFirstIndex() - 1 : edge.getFirstIndex());
                    int newSecondIndex = (edge.getSecondIndex()> pointIndex ? edge.getSecondIndex() - 1 : edge.getSecondIndex());
                    newEdges.add(new Edge(newFirstIndex, newSecondIndex));
                }
            }
            pointsWithEdges.getEdges().clear();
            pointsWithEdges.getEdges().addAll(newEdges);
            pointsWithEdges.getPoints().remove(pointIndex);
            imagePanel.repaint();
            return;
        }
        
        int edgeIndex = findEdgeIndex(x, y);
        if (edgeIndex != -1) {
            pointsWithEdges.getEdges().remove(edgeIndex);
            imagePanel.repaint();
        }
    }

    @Override
    public void mouseLeftDragged(ImagePanel imagePanel, int previousX, int previousY, int x, int y, int width, int height) {
        paintStrategy.setChosenPointIndex(-1);
        int pointIndex = findPointIndex(previousX, previousY);
        if (pointIndex == -1) {
            return;
        }
        paintStrategy.getPointsWithEdges().getPoints().set(pointIndex, new Point(x, y));
        paintStrategy.setCursorX(x);
        paintStrategy.setCursorY(y);
        imagePanel.repaint();
    }

    @Override
    public void mouseMiddleDragged(ImagePanel imagePanel, int previousX, int previousY, int x, int y, int width, int height) {
    }

    @Override
    public void mouseRightDragged(ImagePanel imagePanel, int previousX, int previousY, int x, int y, int width, int height) {
    }

    @Override
    public void mouseMovedWithoutPressedButtons(ImagePanel imagePanel, int previousX, int previousY, int x, int y, int width, int height) {
        paintStrategy.setCursorX(x);
        paintStrategy.setCursorY(y);
        imagePanel.repaint();
    }
    
    private int findPointIndex(int x, int y) {
        PointsWithEdges pointsWithEdges = paintStrategy.getPointsWithEdges();
        for (int i = 0; i < pointsWithEdges.getPoints().size(); i++) {
            Point point = pointsWithEdges.getPoints().get(i);
            double diffX = x - point.getX();
            double diffY = y - point.getY();
            double dist = Math.sqrt(diffX * diffX + diffY * diffY);
            if (dist < LabConstants.VERTICE_CAPTURE_RADIUS) {
                return i;
            }
        }
        return -1;
    }

    private int findEdgeIndex(int x, int y) {
        PointsWithEdges pointsWithEdges = paintStrategy.getPointsWithEdges();
        for (int edgeIndex = 0; edgeIndex < pointsWithEdges.getEdges().size(); edgeIndex++) {
            Edge edge = pointsWithEdges.getEdges().get(edgeIndex);
            Point firstPoint = pointsWithEdges.getPoints().get(edge.getFirstIndex());
            Point secondPoint = pointsWithEdges.getPoints().get(edge.getSecondIndex());
            double a = secondPoint.getY() - firstPoint.getY();
            double b = firstPoint.getX() - secondPoint.getX();
            double c = firstPoint.getY() * (secondPoint.getX() - firstPoint.getX()) +
                    firstPoint.getX() * (firstPoint.getY() - secondPoint.getY());
            double d = Math.abs(a * x + b * y + c) / Math.sqrt(a * a + b * b);
            if (d <= LabConstants.EDGE_CAPTURE_DISTANCE &&
                    isInRange(firstPoint.getX(), x, secondPoint.getX(), LabConstants.EDGE_CAPTURE_DISTANCE) &&
                    isInRange(firstPoint.getY(), y, secondPoint.getY(), LabConstants.EDGE_CAPTURE_DISTANCE)) {
                return edgeIndex;
            }
        }
        return -1;
    }
    
    private boolean isInRange(double firstBound, double number, double secondBound, double fringe) {
        double smallBound = Math.min(firstBound, secondBound) - fringe;
        double bigBound = Math.max(firstBound, secondBound) + fringe;
        return smallBound <= number && number <= bigBound;
    }
    
}