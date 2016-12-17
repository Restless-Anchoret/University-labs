package controller;

import utils.PointsWithEdges;
import utils.Edge;
import utils.Point;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import ui.ImagePanelPaintStrategy;
import utils.LabConstants;

public class SimplePaintStrategy implements ImagePanelPaintStrategy {

    private final static Color MAIN_POLYGON_COLOR = Color.BLACK;
    private final static Color EXTRA_POLYGON_COLOR = Color.GRAY;
    
    private PointsWithEdges pointsWithEdges;
    private PointsWithEdges extraPointsWithEdges;
    private int chosenPointIndex = -1;
    private int cursorX;
    private int cursorY;

    public SimplePaintStrategy(PointsWithEdges pointsWithEdges, PointsWithEdges extraPointsWithEdges) {
        this.pointsWithEdges = pointsWithEdges;
        this.extraPointsWithEdges = extraPointsWithEdges;
    }

    public SimplePaintStrategy(PointsWithEdges pointsWithEdges) {
        this(pointsWithEdges, new PointsWithEdges(new ArrayList<>(), new ArrayList<>()));
    }

    public PointsWithEdges getPointsWithEdges() {
        return pointsWithEdges;
    }

    public void setPointsWithEdges(PointsWithEdges pointsWithEdges) {
        this.pointsWithEdges = pointsWithEdges;
    }

    public int getChosenPointIndex() {
        return chosenPointIndex;
    }

    public void setChosenPointIndex(int chosenPointIndex) {
        this.chosenPointIndex = chosenPointIndex;
    }

    public int getCursorX() {
        return cursorX;
    }

    public void setCursorX(int cursorX) {
        this.cursorX = cursorX;
    }

    public int getCursorY() {
        return cursorY;
    }

    public void setCursorY(int cursorY) {
        this.cursorY = cursorY;
    }

    @Override
    public void paint(Graphics2D graphics, int width, int height) {
        paintGrid(graphics, extraPointsWithEdges, EXTRA_POLYGON_COLOR);
        paintGrid(graphics, pointsWithEdges, MAIN_POLYGON_COLOR);
        if (chosenPointIndex != -1) {
            Point chosenPoint = pointsWithEdges.getPoints().get(chosenPointIndex);
            graphics.drawLine(cursorX, cursorY, (int)chosenPoint.getX(), (int)chosenPoint.getY());
        }
    }
    
    private void paintGrid(Graphics2D graphics, PointsWithEdges pointsWithEdges, Color color) {
        graphics.setColor(color);
        for (Edge edge: pointsWithEdges.getEdges()) {
            Point first = pointsWithEdges.getPoints().get(edge.getFirstIndex());
            Point second = pointsWithEdges.getPoints().get(edge.getSecondIndex());
            graphics.drawLine((int)first.getX(), (int)first.getY(),
                    (int)second.getX(), (int)second.getY());
        }
        int verticeRadius = LabConstants.VERTICE_SHOW_RADIUS;
        int verticeDiameter = verticeRadius * 2;
        for (Point point: pointsWithEdges.getPoints()) {
            graphics.fillOval((int)point.getX() - verticeRadius, (int)point.getY() - verticeRadius,
                    verticeDiameter, verticeDiameter);
        }
    }
    
}