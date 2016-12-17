package utils;

import java.util.Objects;

public class Edge {

    private final int firstIndex, secondIndex;

    public Edge(int firstIndex, int secondIndex) {
        this.firstIndex = firstIndex;
        this.secondIndex = secondIndex;
    }

    public int getFirstIndex() {
        return firstIndex;
    }

    public int getSecondIndex() {
        return secondIndex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        return Objects.equals(this.firstIndex, edge.firstIndex) &&
                Objects.equals(this.secondIndex, edge.secondIndex);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstIndex, secondIndex);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Edge{");
        sb.append("firstIndex=").append(firstIndex);
        sb.append(", secondIndex=").append(secondIndex);
        sb.append('}');
        return sb.toString();
    }
    
    @Override
    public Edge clone() {
        return new Edge(firstIndex, secondIndex);
    }
    
}
