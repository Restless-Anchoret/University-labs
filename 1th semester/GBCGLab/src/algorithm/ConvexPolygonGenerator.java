package algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import utils.Edge;
import utils.Point;
import utils.PointsWithEdges;

public class ConvexPolygonGenerator implements RandomPolygonGenerator {

    private Random random = new Random();
    
    @Override
    public PointsWithEdges generatePolygon(double tx, double ty, double a, double b, double theta) {
        Point t = new Point(tx, ty);
        List<Point> points = new ArrayList<>();
        double thetaRad = theta / 180.0 * Math.PI;
        double phi = 0.0;
        Point secondPoint = null;
        Point lastPoint = null;
        Point q = t;
        while (true) {
            phi += nextDouble(0.0, thetaRad);
            double r = nextDouble(a, b);
            if (secondPoint == null && !points.isEmpty()) {
                secondPoint = q;
            }
            points.add(q);
            lastPoint = q;
            q = new Point(q.getX() + r * Math.cos(phi), q.getY() + r * Math.sin(phi));
            if (secondPoint == null) {
                continue;
            }
            if (countDeterminant(countSubstraction(q, t), countSubstraction(secondPoint, t)) >= 0.0) {
                break;
            }
            if (countDeterminant(countSubstraction(q, lastPoint), countSubstraction(t, lastPoint)) <= 0.0) {
                break;
            }
        }
        List<Edge> edges = new ArrayList<>(points.size());
        if (nextDouble(0.0, 2.0) < 1.0) {
            for (int i = 0; i < points.size(); i++) {
                edges.add(new Edge(i, (i + 1) % points.size()));
            }
        } else {
            for (int i = points.size() - 1; i >= 0; i--) {
                edges.add(new Edge((i + 1) % points.size(), i));
            }
        }
        return new PointsWithEdges(edges, points);
    }
    
    private Point countSubstraction(Point first, Point second) {
        return new Point(first.getX() - second.getX(), first.getY() - second.getY());
    }
    
    private double countDeterminant(Point first, Point second) {
        return first.getX() * second.getY() - first.getY() * second.getX();
    }
    
    private double nextDouble(double lowEdge, double highEdge) {
        return lowEdge + random.nextDouble() * (highEdge - lowEdge);
    }

}