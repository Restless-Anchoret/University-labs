package com.ran.dissertation.ui;

import com.ran.dissertation.algebraic.vector.TwoIntVector;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

public class PaintDelegate {

    private Graphics2D graphics = null;

    public Graphics2D getGraphics() {
        return graphics;
    }

    public void setGraphics(Graphics2D graphics) {
        this.graphics = graphics;
    }
    
    public void setColor(Color color) {
        graphics.setColor(color);
    }
    
    public void setPaintWidth(float paintWidth) {
        graphics.setStroke(new BasicStroke(paintWidth));
    }
    
    public void putPixel(TwoIntVector point, Color color, float paintWidth) {
        setColor(color);
        setPaintWidth(paintWidth);
        putPixel(point);
    }
    
    public void putPixel(TwoIntVector point) {
        putLine(point, point);
    }
    
    public void putLine(TwoIntVector firstPoint, TwoIntVector secondPoint, Color color, float paintWidth) {
        setColor(color);
        setPaintWidth(paintWidth);
        putLine(firstPoint, secondPoint);
    }
    
    public void putLine(TwoIntVector firstPoint, TwoIntVector secondPoint) {
        graphics.drawLine(firstPoint.getX(), firstPoint.getY(),
                secondPoint.getX(), secondPoint.getY());
    }
    
    public void putCircle(TwoIntVector center, int radius, Color color) {
        setColor(color);
        putCircle(center, radius);
    }
    
    public void putCircle(TwoIntVector center, int radius) {
        graphics.fillOval(center.getX() - radius, center.getY() - radius, radius * 2, radius * 2);
    }
    
}