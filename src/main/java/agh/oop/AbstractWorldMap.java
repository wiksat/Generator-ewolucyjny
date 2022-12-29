package agh.oop;

import agh.simulation.SimulationParameters;
import agh.statistics.StatisticsModule;
import javafx.util.Pair;

import java.util.*;

public class AbstractWorldMap implements IWorldMap {
    protected List<Animal> animalsList = new ArrayList<>();
    protected final Map<Vector2d, NavigableSet<MapAnimalContainer>> animals = new LinkedHashMap<>();

    protected final Map<Vector2d, AbstractWorldMapElement> grasses = new LinkedHashMap<>();

    protected MapBoundary mapBoundary;
    protected MapBoundary jungleBoundary;
    private final StatisticsModule statisticsModule;

    private int numberOfPlant;

    public AbstractWorldMap(int width, int height, int jungleHeight, StatisticsModule statisticsModule) {
        this.mapBoundary = new MapBoundary(new Vector2d(0, 0), new Vector2d(width-1, height-1));
        int centerX = width / 2;
        int centerY = height / 2;

        Vector2d jungleLowerLeft = new Vector2d(0, centerY - jungleHeight / 2);
        this.statisticsModule=statisticsModule;
        this.jungleBoundary = new MapBoundary(jungleLowerLeft,
                jungleLowerLeft.add(new Vector2d(width-1, jungleHeight-1)));

        this.numberOfPlant = SimulationParameters.numberOfNewPlant;
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
                statisticsModule.incrementGrasses();
//                this.incrementSlotsTaken(grassPosition);
                numberOfGrown++;
            }
        }
    }



    @Override
    public boolean canMoveTo(Vector2d position) {
        return (position != null);
    }
    @Override
    public MapBoundary getBound(){ return this.mapBoundary; }


    @Override
    public String toString() {
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
        if (this.grasses.get(position)!=null) {
            this.grasses.remove(position);
            statisticsModule.decrementGrasses();
            }
        }



    @Override
    public boolean positionChanged(Animal animal, Vector2d oldPosition, Vector2d newPosition) {
//        if (worldMapElement instanceof Animal animal) {
        System.out.println("zmiana pozycji się wykonuje");
            MapAnimalContainer mapAnimalContainer = new MapAnimalContainer(animal.getLifeEnergy(), animal);
            this.animals.get(oldPosition).remove(mapAnimalContainer);
            removeAnimalsEntryIfPossible(oldPosition);
            if (!this.animals.containsKey(newPosition)) {
                this.animals.put(newPosition, new TreeSet<>());
//                this.incrementSlotsTaken(newPosition);
            }
            this.animals.get(newPosition).add(mapAnimalContainer);

//        }
//        else {
//            this.grasses.remove(oldPosition);
////            this.decrementSlotsTaken(oldPosition);
//            this.grasses.put(newPosition, worldMapElement);
////            this.incrementSlotsTaken(newPosition);
//        }

        return true;
    }
    @Override
    public List<Animal> getAnimals(){
        return this.animalsList;
    }

}
