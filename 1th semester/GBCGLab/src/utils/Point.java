package utils;

import java.util.Objects;

public class Point implements Comparable<Point> {
    
    public static Point max(Point firstPoint, Point secondPoint) {
        if (firstPoint.compareTo(secondPoint) > 0) {
            return firstPoint;
        } else {
            return secondPoint;
        }
    }
    
    public static Point min(Point firstPoint, Point secondPoint) {
        if (firstPoint.compareTo(secondPoint) < 0) {
            return firstPoint;
        } else {
            return secondPoint;
        }
    }
    
    public static double countDeterminant(double a, double b, double c, double d) {
        return a * d - b * c;
    }
    
    private final double x;
    private final double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return Math.abs(this.x - point.x) < LabConstants.EPSILON &&
                Math.abs(this.y - point.y) < LabConstants.EPSILON;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Point{" + "x=" + x + ", y=" + y + '}';
    }

    @Override
    public Point clone() {
        return new Point(x, y);
    }

    @Override
    public int compareTo(Point otherPoint) {
        if (Math.abs(this.x - otherPoint.getX()) > LabConstants.EPSILON) {
            return Double.compare(this.x, otherPoint.getX());
        } else if (Math.abs(this.y - otherPoint.getY()) > LabConstants.EPSILON) {
            return Double.compare(this.y, otherPoint.getY());
        } else {
            return 0;
        }
    }
    
    public Point add(Point otherPoint) {
        return new Point(this.x + otherPoint.getX(), this.y + otherPoint.getY());
    }
    
    public Point substract(Point otherPoint) {
        return new Point(this.x - otherPoint.getX(), this.y - otherPoint.getY());
    }
    
    public double countDeterminantWithPoint(Point otherPoint) {
        return countDeterminant(this.x, this.y, otherPoint.getX(), otherPoint.getY());
    }
    
}
