package algorithm;

import utils.PointsWithEdges;

@FunctionalInterface
public interface RandomPolygonGenerator {

    PointsWithEdges generatePolygon(double tx, double ty, double a, double b, double theta);
    
}