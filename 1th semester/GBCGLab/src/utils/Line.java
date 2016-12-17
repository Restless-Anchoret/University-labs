package utils;

public class Line {
    
    private double a;
    private double b;
    private double c;

    public Line(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }
    
    public Line(Point firstPoint, Point secondPoint) {
        this.a = firstPoint.getY() - secondPoint.getY();
        this.b = secondPoint.getX() - firstPoint.getX();
        this.c = -this.a * firstPoint.getX() - this.b * firstPoint.getY();
        normalize();
    }

    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }

    public double getC() {
        return c;
    }
    
    private void normalize() {
        double norm = Math.sqrt(a * a + b * b);
        if (Math.abs(norm) > LabConstants.EPSILON) {
            a /= norm;
            b /= norm;
            c /= norm;
	}
    }
    
    public double distanceFromPoint(Point point) {
        return a * point.getX() + b * point.getY() + c;
    }

}