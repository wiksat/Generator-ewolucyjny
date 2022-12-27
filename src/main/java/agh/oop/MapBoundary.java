package agh.oop;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;


public final class MapBoundary implements IPositionChangeObserver {
    final Vector2d lowerLeft;
    final Vector2d upperRight;
    int slotsTaken = 0;

    public MapBoundary(Vector2d lowerLeft, Vector2d upperRight, int slotsTaken) {
        this.lowerLeft = lowerLeft;
        this.upperRight = upperRight;
        this.slotsTaken = slotsTaken;
    }

    public MapBoundary(Vector2d lowerLeft, Vector2d upperRight) {
        this.lowerLeft = lowerLeft;
        this.upperRight = upperRight;
    }

    public boolean isInside(Vector2d position) {
        return position != null && position.precedes(this.upperRight) && position.follows(this.lowerLeft);
    }

    public int area() {
        return (upperRight.x - lowerLeft.x+ 1) * (upperRight.y - lowerLeft.y + 1);
    }

    public Vector2d lowerLeft() {
        return lowerLeft;
    }

    public Vector2d upperRight() {
        return upperRight;
    }

    public int getSlotsTaken() {
        return slotsTaken;
    }

    public void incrementSlotsTaken() {
        this.slotsTaken += 1;
    }
    public void decrementSlotsTaken() {
        this.slotsTaken -= 1;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (MapBoundary) obj;
        return Objects.equals(this.lowerLeft, that.lowerLeft) &&
                Objects.equals(this.upperRight, that.upperRight) &&
                this.slotsTaken == that.slotsTaken;
    }

    @Override
    public int hashCode() {
        return Objects.hash(lowerLeft, upperRight, slotsTaken);
    }

    @Override
    public String toString() {
        return "Boundary[" +
                "lowerLeft=" + lowerLeft + ", " +
                "upperRight=" + upperRight + ", " +
                "slotsTaken=" + slotsTaken + ']';
    }

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {

    }
    public boolean havePlace() {
        return true;
    }
}