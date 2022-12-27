package agh.oop;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MapBoundary implements IPositionChangeObserver{
    public List<AbstractWorldMapElement> X_el = new ArrayList<>();
    public List<AbstractWorldMapElement> Y_el = new ArrayList<>();
    Comparator<AbstractWorldMapElement> yComparator = (o1, o2) -> {
        if (o1.getPosition().y == o2.getPosition().y)
            return Integer.compare(o1.getPosition().x, o2.getPosition().x);
        return Integer.compare(o1.getPosition().y, o2.getPosition().y);
    };
    Comparator<AbstractWorldMapElement> xComparator = (o1, o2) -> {
        if (o1.getPosition().x == o2.getPosition().x)
            return Integer.compare(o1.getPosition().y, o2.getPosition().y);
        return Integer.compare(o1.getPosition().x, o2.getPosition().x);
    };
    public void printer(){
        for(AbstractWorldMapElement el: X_el){
            System.out.print(el.getPosition() + " " + el);
        }
        System.out.print('\n');
        for(AbstractWorldMapElement el: Y_el){
            System.out.print(el.getPosition() + " " + el);
        }
        System.out.print('\n');
    }

    public void put(AbstractWorldMapElement Element){
        this.X_el.add(Element);
        this.Y_el.add(Element);
    }
    public void sortuj(){
        X_el.sort(xComparator);
        Y_el.sort(yComparator);
    }
    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {

    }
}