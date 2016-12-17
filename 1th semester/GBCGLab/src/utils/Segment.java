package utils;

import java.util.Objects;

public class Segment {
    
    private final Point firstPoint;
    private final Point secondPoint;

    public Segment(Point firstPoint, Point secondPoint) {
        this.firstPoint = firstPoint;
        this.secondPoint = secondPoint;
    }

    public Point getFirstPoint() {
        return firstPoint;
    }

    public Point getSecondPoint() {
        return secondPoint;
    }

    @Override
    public String toString() {
        return "Segment{" + "firstPoint=" + firstPoint + ", secondPoint=" + secondPoint + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.firstPoint);
        hash = 67 * hash + Objects.hashCode(this.secondPoint);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Segment other = (Segment) obj;
        return Objects.equals(this.firstPoint, other.firstPoint) &&
                Objects.equals(this.secondPoint, other.secondPoint);
    }

}