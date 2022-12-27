package agh.oop;

import java.util.*;

public abstract class AbstractWorldMap implements IWorldMap,IPositionChangeObserver {
    protected List<Animal> animalsList = new ArrayList<>();
    protected final Map<Vector2d, NavigableSet<MapAnimalContainer>> animals = new LinkedHashMap<>();

    protected final Map<Vector2d, AbstractWorldMapElement> grasses = new LinkedHashMap<>();

//    protected final MapVisualizer visualize = new MapVisualizer(this);
    protected MapBoundary mapBoundary;
    protected MapBoundary jungleBoundary;



    //variable from user
    int numberOfPlant=10;

    @Override

    public boolean place(Animal animal) {
        if (animal == null) throw new IllegalArgumentException("null can't be placed on the worldMap");
        if (animal.getPosition() == null) {
            throw new IllegalArgumentException("Object can't be placed on position " + animal.getPosition());
        }

        if (!this.animals.containsKey(animal.getPosition())) {
            this.animals.put(animal.getPosition(), new TreeSet<>());
//            this.incrementSlotsTaken(animal.getPosition());
        }
        this.animals.get(animal.getPosition()).add(new MapAnimalContainer(animal.getLifeEnergy(), animal));
//jakies obserwatory
//        animal.addPositionObserver(this);
//        animal.addEnergyObserver(this);
//        animal.addLifeObserver(this);
        return true;
    }

    public void growGrass() {
        int numberOfGrown = 0;
        for (int i = 0; i < numberOfPlant; i++) {
            if (this.mapBoundary.havePlace()) {
                Vector2d grassPosition;
                do {
                    double los = Math.random();
                    if (los<0.8){
                        grassPosition = Vector2d.getRandomVectorBetween(
                                this.mapBoundary.lowerLeft(),
                                this.mapBoundary.upperRight());
                    }
                    else{
                        grassPosition = Vector2d.getRandomVectorBetween(
                                this.jungleBoundary.lowerLeft(),
                                this.jungleBoundary.upperRight());
                    }

                } while (this.isOccupied(grassPosition));

                this.grasses.put(grassPosition, new Grass(grassPosition));
//                this.incrementSlotsTaken(grassPosition);
                numberOfGrown++;
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
//        this.mapBoundary.sortuj();
//        int x = this.mapBoundary.X_el.get(0).getPosition().x;
//        int y = this.mapBoundary.Y_el.get(0).getPosition().y;
//        Vector2d vectorL = new Vector2d(x,y);
//        x = this.mapBoundary.X_el.get(this.mapBoundary.X_el.size()-1).getPosition().x;
//        y = this.mapBoundary.Y_el.get(this.mapBoundary.Y_el.size()-1).getPosition().y;
//        Vector2d vectorR = new Vector2d(x,y);
//        return this.visualize.draw(vectorL,vectorR);
        return "";
    }
    public Vector2d getLowerLeftDrawLimit() {
        return this.mapBoundary.lowerLeft();
    }
    public Vector2d getUpperRightDrawLimit() {
        return this.mapBoundary.upperRight();
    }


    @Override
    public boolean isOccupied(Vector2d position) {
        return this.grasses.containsKey(position) || this.animals.containsKey(position);
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
//            Animal a = animals.remove(oldPosition);
//            animals.put(newPosition, a);
        }
    }
    @Override
    public List<Animal> getAnimals(){
        return this.animalsList;
    }

}
