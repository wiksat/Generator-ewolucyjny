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
    private int usedPlaces=0;
    private boolean growVarinat;
    private int width;
    private int height;

    public AbstractWorldMap(int width, int height, int jungleHeight, StatisticsModule statisticsModule) {
        this.mapBoundary = new MapBoundary(new Vector2d(0, 0), new Vector2d(width-1, height-1));

        int centerY = height / 2;
        this.width=width;
        this.height=height;

        Vector2d jungleLowerLeft = new Vector2d(0, centerY - jungleHeight / 2);
        this.statisticsModule=statisticsModule;
        this.jungleBoundary = new MapBoundary(jungleLowerLeft,
                jungleLowerLeft.add(new Vector2d(width-1, jungleHeight-1)));

        this.numberOfPlant = SimulationParameters.numberOfNewPlant;
        this.growVarinat=SimulationParameters.plantGrowthVariant;
    }


    @Override

    public boolean place(Animal animal) {
        if (animal == null) throw new IllegalArgumentException("null can't be placed on the worldMap");
        if (animal.getPosition() == null) {
            throw new IllegalArgumentException("Object can't be placed on position " + animal.getPosition());
        }

        if (!this.animals.containsKey(animal.getPosition())) {
            this.animals.put(animal.getPosition(), new TreeSet<>());
            usedPlaces++;
            statisticsModule.changeAmountOfFreePlaces(this.usedPlaces);
        }
        this.animals.get(animal.getPosition()).add(new MapAnimalContainer(animal.getLifeEnergy(), animal));

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
            usedPlaces--;
            statisticsModule.changeAmountOfFreePlaces(this.usedPlaces);
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
    public boolean havePlace(){
        return usedPlaces < width * height;
    }
    public void growGrass() {
        for (int i = 0; i < numberOfPlant; i++) {
            if (this.havePlace()) {
                Vector2d grassPosition;
                if (growVarinat){
                    do {

                        grassPosition = Vector2d.getRandomVectorBetween(
                                this.mapBoundary.lowerLeft(),
                                this.mapBoundary.upperRight());


                    } while (this.isOccupied(grassPosition));
                }
                else{
                    do {
                        double los = Math.random();
                        if (los<0.8){
                            grassPosition = Vector2d.getRandomVectorBetween(
                                    this.jungleBoundary.lowerLeft(),
                                    this.jungleBoundary.upperRight());
                        }
                        else{
                            grassPosition = Vector2d.getRandomVectorBetween(
                                    this.mapBoundary.lowerLeft(),
                                    this.mapBoundary.upperRight());
                        }

                    } while (this.isOccupied(grassPosition));
                }

                this.grasses.put(grassPosition, new Grass(grassPosition));
                statisticsModule.incrementGrasses();
                usedPlaces++;
                statisticsModule.changeAmountOfFreePlaces(this.usedPlaces);
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
            usedPlaces--;
            statisticsModule.changeAmountOfFreePlaces(this.usedPlaces);
            }
        }

    public void deadAnimal(Animal animal, Vector2d position) {

        MapAnimalContainer mapAnimalContainer = new MapAnimalContainer(animal.getLifeEnergy(), animal);
        if ( this.animals.get(position)!=null){
            this.animals.get(position).remove(mapAnimalContainer);
            removeAnimalsEntryIfPossible(position);
        }


    }

    @Override
    public boolean positionChanged(Animal animal, Vector2d oldPosition, Vector2d newPosition) {

            MapAnimalContainer mapAnimalContainer = new MapAnimalContainer(animal.getLifeEnergy(), animal);
            this.animals.get(oldPosition).remove(mapAnimalContainer);
            removeAnimalsEntryIfPossible(oldPosition);
            if (!this.animals.containsKey(newPosition)) {
                this.animals.put(newPosition, new TreeSet<>());
                usedPlaces++;
                statisticsModule.changeAmountOfFreePlaces(this.usedPlaces);
            }
            this.animals.get(newPosition).add(mapAnimalContainer);

        return true;
    }
    @Override
    public List<Animal> getAnimals(){
        return this.animalsList;
    }

}
