package agh.oop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractWorldMap implements IWorldMap,IPositionChangeObserver {
    protected List<Animal> animalsList = new ArrayList<>();
    protected Map<Vector2d, Animal> animals = new HashMap<>();

    protected Map<Vector2d, Grass> grasses = new HashMap<Vector2d, Grass>();

    protected final MapVisualizer visualize = new MapVisualizer(this);
    protected MapBoundary mapBoundary=new MapBoundary();
    public void growGrass(int noOfTuftsInJungle, int noOfTuftsInSteppes) {
        int noOfGrownGrassTufts = 0;
        for (int i = 0; i < noOfTuftsInJungle; i++) {
            if (this.jungleBoundary.area() > this.jungleBoundary.getSlotsTaken()) {
                Vector2d grassPosition;
                do {
                    grassPosition = Vector2d.getRandomVectorBetween(
                            this.getJungleBoundary().lowerLeft(),
                            this.getJungleBoundary().upperRight());
                } while (this.isOccupied(grassPosition));

                this.map.put(grassPosition, new Grass(grassPosition));
                this.incrementSlotsTaken(grassPosition);

                noOfGrownGrassTufts++;
            }
        }
        for (int i = 0; i < noOfTuftsInSteppes; i++) {
            if (this.mapBoundary.area() - this.jungleBoundary.area() > this.mapBoundary.getSlotsTaken() - this.jungleBoundary.getSlotsTaken()) {
                Vector2d grassPosition;
                do {
                    grassPosition = Vector2d.getRandomVectorBetween(
                            this.getMapBoundary().lowerLeft(),
                            this.getMapBoundary().upperRight());
                } while (
                        this.getJungleBoundary().isInside(grassPosition) || this.isOccupied(grassPosition));

                this.map.put(grassPosition, new Grass(grassPosition));
                this.incrementSlotsTaken(grassPosition);

                noOfGrownGrassTufts++;
            }
        }
//        for (var observer : this.grassActionObservers) {
//            observer.grassGrow(noOfGrownGrassTufts);
//        }
//        coś z obserwatorem
    }
    @Override
    public boolean canMoveTo(Vector2d position) {
        return (!animals.containsKey(position));
    }
    @Override
    public MapBoundary getBound(){ return this.mapBoundary; }
    @Override
    public String toString() {
        this.mapBoundary.sortuj();
        int x = this.mapBoundary.X_el.get(0).getPosition().x;
        int y = this.mapBoundary.Y_el.get(0).getPosition().y;
        Vector2d vectorL = new Vector2d(x,y);
        x = this.mapBoundary.X_el.get(this.mapBoundary.X_el.size()-1).getPosition().x;
        y = this.mapBoundary.Y_el.get(this.mapBoundary.Y_el.size()-1).getPosition().y;
        Vector2d vectorR = new Vector2d(x,y);
//        return this.visualize.draw(vectorL,vectorR);
        return "";
    }
    public Vector2d getLowerLeftDrawLimit(){
        this.mapBoundary.sortuj();
        int x = this.mapBoundary.X_el.get(0).getPosition().x;
        int y = this.mapBoundary.Y_el.get(0).getPosition().y;
        return new Vector2d(x,y);
    }
    public Vector2d getUpperRightDrawLimit(){
        this.mapBoundary.sortuj();
        int x = this.mapBoundary.X_el.get(this.mapBoundary.X_el.size()-1).getPosition().x;
        int y = this.mapBoundary.Y_el.get(this.mapBoundary.Y_el.size()-1).getPosition().y;
        return new Vector2d(x,y);
    }
    @Override
    public boolean place(Animal animal) {
        if(this.animals.get(animal.getPosition()) != null){
//            return false;
            throw new IllegalArgumentException(animal.getPosition()+ " is already occupied");
        }
        this.animalsList.add(animal);
        this.animals.put(animal.getPosition(),animal);
        mapBoundary.put(animal);
        return true;
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return this.animals.get(position) != null || this.grasses.get(position) != null;
    }

    @Override
    public Object objectAt(Vector2d position) {
        if(this.animals.get(position) != null){
            return this.animals.get(position);
        }
        if(this.grasses.get(position) != null){
            return this.grasses.get(position);
        }
        return null;
    }
    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        if(!newPosition.equals(oldPosition)) {
            Animal a = animals.remove(oldPosition);
            animals.put(newPosition, a);
        }
    }
    @Override
    public List<Animal> getAnimals(){
        return this.animalsList;
    }

}
