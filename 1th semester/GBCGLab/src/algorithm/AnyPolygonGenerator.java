package algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import utils.Edge;
import utils.Point;
import utils.PointsWithEdges;

public class AnyPolygonGenerator implements RandomPolygonGenerator {

    private Random random = new Random();
    
    @Override
    public PointsWithEdges generatePolygon(double tx, double ty, double a, double b, double theta) {
        double thetaRad = theta / 180.0 * Math.PI;
        List<Point> points = new ArrayList<>();
        double phi = 0.0;
        while (phi < 2.0 * Math.PI) {
            double r = nextDouble(a, b);
            double x = tx + r * Math.cos(phi);
            double y = ty + r * Math.sin(phi);
            points.add(new Point(x, y));
            phi += nextDouble(0.0, thetaRad);
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
    
    private double nextDouble(double lowEdge, double highEdge) {
        return lowEdge + random.nextDouble() * (highEdge - lowEdge);
    }

}