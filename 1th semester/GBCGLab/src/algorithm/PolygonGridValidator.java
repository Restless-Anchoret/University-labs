package algorithm;

import java.util.Arrays;
import utils.Edge;
import utils.PointsWithEdges;

public class PolygonGridValidator implements GridValidator {

    @Override
    public boolean validateGrid(PointsWithEdges grid) {
        int n = grid.getPoints().size();
        int[][] adjucentMatrix = new int[n][n];
        for (Edge edge: grid.getEdges()) {
            adjucentMatrix[edge.getFirstIndex()][edge.getSecondIndex()] = 1;
            adjucentMatrix[edge.getSecondIndex()][edge.getFirstIndex()] = 1;
        }
        if (Arrays.stream(adjucentMatrix).anyMatch(
                row -> Arrays.stream(row).sum() != 2)) {
            return false;
        }
        
        int[] labeledByDfs = new int[n];
        dfs(0, adjucentMatrix, labeledByDfs);
        return Arrays.stream(labeledByDfs).sum() == n;
    }
    
    private void dfs(int v, int[][] adjucentMatrix, int[] labeledByDfs) {
        labeledByDfs[v] = 1;
        for (int i = 0; i < adjucentMatrix[v].length; i++) {
            if (labeledByDfs[i] == 0 && adjucentMatrix[v][i] == 1) {
                dfs(i, adjucentMatrix, labeledByDfs);
            }
        }
    }

}