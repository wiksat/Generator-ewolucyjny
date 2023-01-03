package agh.simulation;

import agh.gui.GuiParameters;
import agh.gui.GuiStatisticsModule;
import agh.gui.GuiWorldMap;
import agh.oop.*;

import agh.statistics.StatisticsModule;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class SimulationEngine implements Runnable {

    private final AbstractWorldMap map;
    private final GuiWorldMap guiWorldMap;
    private final GuiStatisticsModule guiStatisticsModule;
    private final List<Animal> animals = new ArrayList<>();
    private final List<Animal> deadAnimals = new ArrayList<>();
    private final int numberOfNewPlants;
    private int day = 0;
    private final int moveDelay;
    public StatisticsModule statisticsModule;
    private final boolean withSaveStatistics;
    public int nrOfDay = 0;
    public SimulationEngine(AbstractWorldMap map, GuiWorldMap guiWorldMap, StatisticsModule statisticsModule, GuiStatisticsModule guiStatisticsModule) throws Exception {
        this.guiWorldMap = guiWorldMap;
        this.map = map;
        this.statisticsModule = statisticsModule;
        this.guiStatisticsModule = guiStatisticsModule;
        this.numberOfNewPlants = SimulationParameters.numberOfNewPlant;
        this.moveDelay = SimulationParameters.simulationMoveDelay;
        this.withSaveStatistics = SimulationParameters.withSaveStatistics;

        for (int i = 0; i < SimulationParameters.startNumberOfAnimals; i++) {
            Vector2d position;
            do {
                int x = ThreadLocalRandom.current().nextInt(0, GuiParameters.mapWidth);
                int y = ThreadLocalRandom.current().nextInt(0, GuiParameters.mapHeight);
                position = new Vector2d(x, y);
            } while (this.map.isOccupied(position));

            Animal animal = new Animal(this.map, position, statisticsModule);
            this.map.place(animal);
            this.animals.add(animal);
        }

        this.map.growGrass(SimulationParameters.startNumberOfPlants);
        if (withSaveStatistics){
            this.statisticsModule.statisticsWriter.createFile();
        }

    }

    private void feedAnimals() {
        Set<Vector2d> eatenGrassPosition = new HashSet<>();
        for (Animal animal : this.animals) {
            if (this.map.getGrassAt(animal.getPosition()) instanceof Grass) {
                eatenGrassPosition.add(animal.getPosition());

                var animals = this.map.getAnimalsAt(animal.getPosition());
                var theStrongestAnimal = animals.last();
                if (animal.getLifeEnergy() == theStrongestAnimal.energyOfAnimal()) {
                    animal.eatPlant();
               }
            }
        }

        for (Vector2d grassPosition : eatenGrassPosition) {
            this.map.removeGrassAt(grassPosition);
        }
    }
    private void makeAnimalReproduce() {
        Set<Vector2d> occupiedPositions = new HashSet<>();
        ArrayList<Animal> children = new ArrayList<>();

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

    private void setStatistics() throws IOException {
        int sum = 0;
        for (Animal animal : animals) {
            sum += animal.getLifeEnergy();
        }
        statisticsModule.changeAverageEnergyLifeForAlive(sum,animals.size());
        statisticsModule.changeAmountOfAnimal(animals.size());
        int sumDead = 0;
        for (Animal animal : deadAnimals) {
            sumDead += animal.getAge();
        }
        statisticsModule.changeAverageAgeForDead(sumDead,deadAnimals.size());
        if (withSaveStatistics){
            statisticsModule.saveDataToLists(nrOfDay);
        }
        nrOfDay++;
    }
    private void oneDayActions() throws IOException {
        List<Animal> newDeadAnimals = new ArrayList<>();
        for (Animal animal : this.animals) {
            animal.nextDay();
            if (animal.getStatus() == StatusOfAnimal.DEAD) {
                newDeadAnimals.add(animal);
            }
        }
        this.animals.removeAll(newDeadAnimals);
        this.deadAnimals.addAll(newDeadAnimals);

        for (Animal animal : this.animals) {
            animal.selectDirectionAndMove();
        }


        feedAnimals();
        makeAnimalReproduce();
        setStatistics();
        this.map.growGrass(this.numberOfNewPlants);
    }

    @Override
    public void run() {
        while(true) {
            try {
                oneDayActions();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            day++;

            guiWorldMap.refresh(this.map);
            guiStatisticsModule.refresh();

            try {
                Thread.sleep(this.moveDelay);
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}
