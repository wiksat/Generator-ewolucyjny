package agh.simulation;

import agh.gui.GuiParameters;
import agh.gui.GuiWorldMap;
import agh.oop.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class SimulationEngine implements Runnable {

    private final AbstractWorldMap map;
    private GuiWorldMap guiWorldMap;
    private final List<Animal> animals = new ArrayList<>();
//    private final List<IMapRefreshNeededObserver> mapRefreshNeededObservers = new ArrayList<>();
//    private final List<IAnimalLifeObserver> animalLifeObservers = new ArrayList<>();
//    private final List<INextDayObserver> nextDayObservers = new ArrayList<>();
//    private final List<ISimulationEventObserver> simulationEventObservers = new ArrayList<>();
    private int day = 0;
    private int moveDelay = SimulationParameters.simulationMoveDelay;

    public SimulationEngine(AbstractWorldMap map, GuiWorldMap guiWorldMap) {
        this.guiWorldMap = guiWorldMap;
        this.map = map;

//        MapBoundary mapBoundary = this.map.getMapBoundary();

        for (int i = 0; i < SimulationParameters.startNumberOfAnimals; i++) {
            Vector2d position;
            do {
                int x = ThreadLocalRandom.current().nextInt(0, GuiParameters.mapWidth);
                int y = ThreadLocalRandom.current().nextInt(0, GuiParameters.mapHeight);
                position = new Vector2d(x, y);
            } while (this.map.isOccupied(position));

            Animal animal = new Animal(this.map, position);
            this.map.place(animal);
            this.animals.add(animal);
        }

//        for (Animal animal : this.animals) {
//            animal.addLifeObserver(simpleStatisticsHandler);
//            animal.addEnergyObserver(simpleStatisticsHandler);
//        }
//        this.map.addGrassObserver(simpleStatisticsHandler);
//        this.addAnimalLifeObserver(simpleStatisticsHandler);
//        for (Animal animal : this.animals) {
//            animalCreated(animal);
//        }
    }

    private void feedAnimals() {
        Set<Vector2d> eatenGrassPosition = new HashSet<>();
        for (Animal animal : this.animals) {
//            if (this.map.objectAt(animal.getPosition()) instanceof Grass) {
            if (this.map.getGrassAt(animal.getPosition()) instanceof Grass) {
                eatenGrassPosition.add(animal.getPosition());

//                var animals = this.map.getAnimalsAt(animal.getPosition());
//                var theStrongestAnimal = animals.last();
//                if (animal.getLifeEnergy() == theStrongestAnimal.animalEnergy()) {
////                    var limitAnimal = new MapAnimalContainer(theStrongestAnimal.animalEnergy(), null);
//                    animal.setLifeEnergy(animal.getLifeEnergy() + SimulationParameters.plantEnergy);
//                }

            }
        }

        for (Vector2d grassPosition : eatenGrassPosition) {
            //grass element
            this.map.removeGrassAt(grassPosition);
        }
    }
    private void makeAnimalReproduce() {
        Set<Vector2d> occupiedPositions = new HashSet<>();
        List<Animal> children = new ArrayList<>();

        for (Animal animal : this.animals) {
            if (!occupiedPositions.contains(animal.getPosition())) {

                occupiedPositions.add(animal.getPosition());

                var topPair = this.map.getPairOfStrongestAnimalsAt(animal.getPosition());
                topPair.ifPresent(animalPair -> {
                    var animal1 = animalPair.getKey();
                    var animal2 = animalPair.getValue();

                    if (animal1.canReproduce(animal2)) {
                        Animal child = animal1.reproduce(animal2);
                        children.add(child);
                    }
                });
            }


        }
        for (Animal child : children) {
            this.map.place(child);
        }
        this.animals.addAll(children);
    }


    private void oneDayActions() {
        List<Animal> newDeadAnimals = new ArrayList<>();
        for (Animal animal : this.animals) {
            animal.nextDay();
            if (animal.getStatus() == StatusOfAnimal.DEAD) {
                newDeadAnimals.add(animal);
            }
        }
        System.out.println("DEADDD: " + newDeadAnimals);
        this.animals.removeAll(newDeadAnimals);


        for (Animal animal : this.animals) {
            animal.selectDirectionAndMove();
            System.out.println(animal.getPosition().toString());
        }
        System.out.println("_______");

        feedAnimals();
//        makeAnimalReproduce();

        this.map.growGrass();
    }

    @Override
    public void run() {
        while(true) {
            guiWorldMap.refresh(this.map);

            oneDayActions();
            day++;

//            guiWorldMap.refresh(this.map);
//            for (var observer : this.mapRefreshNeededObservers) {
//                observer.;
//            }


            try {
                Thread.sleep(this.moveDelay);
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}
