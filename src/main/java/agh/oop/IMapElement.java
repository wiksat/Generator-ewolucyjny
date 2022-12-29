package agh.oop;

public interface IMapElement {
    Vector2d getPosition();
    boolean isAt(Vector2d position);
    String getName();
    String getImageSRC();
}
