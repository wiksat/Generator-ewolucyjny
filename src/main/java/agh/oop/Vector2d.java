package agh.oop;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class Vector2d {
    public final int x;
    public final int y;
    public Vector2d(int x, int y){
        this.x = x;
        this.y = y;
    }
    public String toString(){
        return "("+ this.x +","+ this.y +")";
    }
    public boolean precedes(Vector2d other){
        return this.x <= other.x && this.y <= other.y;
    }
    public boolean follows(Vector2d other){
        return this.x >= other.x && this.y >= other.y;
    }
    public Vector2d add(Vector2d other){
        return new Vector2d(this.x + other.x, this.y + other.y);
    }

    public Vector2d upperRight(Vector2d other){
        return new Vector2d(Math.max(this.x,other.x), Math.max(this.y, other.y));
    }
    public Vector2d lowerLeft(Vector2d other){
        return new Vector2d(Math.min(this.x,other.x), Math.min(this.y, other.y));
    }

    public static Vector2d getRandomVectorBetween(Vector2d lowerLeft, Vector2d upperRight) {
        int x = ThreadLocalRandom.current().nextInt(lowerLeft.x, upperRight.x + 1);
        int y = ThreadLocalRandom.current().nextInt(lowerLeft.y, upperRight.y + 1);

        return new Vector2d(x, y);
    }
    @Override
    public boolean equals(Object other){
        if (this == other)
            return true;
        if (!(other instanceof Vector2d that))
            return false;
        return that.x == this.x && that.y == this.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
