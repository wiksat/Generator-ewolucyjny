package agh.oop;

import javafx.util.Pair;

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

    public AbstractWorldMap(int width, int height, int jungleHeight) {
        this.mapBoundary = new MapBoundary(new Vector2d(0, 0), new Vector2d(width-1, height-1));
        int centerX = width / 2;
        int centerY = height / 2;

        Vector2d jungleLowerLeft = new Vector2d(0, centerY - jungleHeight / 2);

        this.jungleBoundary = new MapBoundary(jungleLowerLeft,
                jungleLowerLeft.add(new Vector2d(width-1, jungleHeight-1)));
    }


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
    public NavigableSet<MapAnimalContainer> getAnimalsAt(Vector2d position) {
        return this.animals.getOrDefault(position, new TreeSet<>());
    }
    public MapBoundary getJungleBoundary() {
        return jungleBoundary;
    }
    public MapBoundary getMapBoundary() {
        return mapBoundary;
    }
    public AbstractWorldMapElement getTopWorldMapElementAt(Vector2d position) {
        var animals = this.getAnimalsAt(position);
        if (!animals.isEmpty()) {
            return animals.last().animal();
        }
        return this.grasses.get(position);
    }
    private void removeAnimalsEntryIfPossible(Vector2d position) {
        if (this.animals.get(position).isEmpty()) {
            this.animals.remove(position);
//            this.decrementSlotsTaken(position);
        }
    }
    public void energyChanged(Animal animal, int oldEnergy, int newEnergy) {
        MapAnimalContainer oldMapAnimalContainer = new MapAnimalContainer(oldEnergy, animal);
        var positionSet = this.animals.get(animal.getPosition());
        positionSet.remove(oldMapAnimalContainer);

        MapAnimalContainer newMapAnimalContainer = new MapAnimalContainer(newEnergy, animal);
        positionSet.add(newMapAnimalContainer);
    }
    public Optional<Pair<Animal, Animal>> getPairOfStrongestAnimalsAt(Vector2d position) {
        NavigableSet<MapAnimalContainer> allAnimals = this.getAnimalsAt(position);

        if (allAnimals.size() >= 2) {
            var iterator = allAnimals.descendingIterator();

            return Optional.of(new Pair<>(iterator.next().animal(), iterator.next().animal()));
        }

        return Optional.empty();
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
//        cos z obserwatorem
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

    public Object getGrassAt(Vector2d position){
        if(this.grasses.get(position) != null){
            return this.grasses.get(position);
        }
        return null;
    }

    public void removeGrassAt(Vector2d position) {
        var obj = this.grasses.remove(position);
        if (obj != null) {
//            this.decrementSlotsTaken(position);

            if (obj instanceof Grass) {
//                for (var observer : grassActionObservers) {
//                    observer.grassEaten();
//                }
                //observery
            }
        }
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
