package agh.oop;


public final class MapBoundary {
    final Vector2d lowerLeft;
    final Vector2d upperRight;

    public MapBoundary(Vector2d lowerLeft, Vector2d upperRight) {
        this.lowerLeft = lowerLeft;
        this.upperRight = upperRight;
    }

    public boolean isInside(Vector2d position) {
        return position != null && position.precedes(this.upperRight) && position.follows(this.lowerLeft);
    }

    public Vector2d lowerLeft() {
        return lowerLeft;
    }

    public Vector2d upperRight() {
        return upperRight;
    }


}