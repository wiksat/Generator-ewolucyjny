package agh.statistics;

import agh.oop.Animal;

public class StatisticsModule {
    int amountOfAnimals;
    int amountOfGrasses;
    int amountOfFreePlaces;
    float averageEnergyLifeForAlive;
    float averageAgeForDead;

    public void incrementGrasses() {
        amountOfGrasses++;
    }
    public void decrementGrasses() {
        amountOfGrasses--;
    }

    public void incrementAmountOfAnimal() {
        amountOfAnimals++;
    }
    public void changeAverageEnergyLifeForAlive() {

    }
    public void changeAverageAgeForDead() {

    }
}
