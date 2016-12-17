package algorithm;

import utils.PointsWithEdges;

@FunctionalInterface
public interface UnionAlgorithm {

    PointsWithEdges unionPolygons(PointsWithEdges firstPolygon, PointsWithEdges secondPolygon);
    
}
