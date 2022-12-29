package agh.statistics;

import agh.oop.Animal;

public class StatisticsModule {
    int amountOfAnimals;
    int amountOfGrasses;
    int amountOfFreePlaces;
    double averageEnergyLifeForAlive;
    double averageAgeForDead;

    public void incrementGrasses() {
        amountOfGrasses++;
    }
    public void decrementGrasses() {
        amountOfGrasses--;
    }
    public void changeAmountOfFreePlaces(int amount){
        amountOfFreePlaces=amount;
    }

    public void changeAmountOfAnimal(int amount) {
        this.amountOfAnimals=amount;
    }

    public void changeAverageEnergyLifeForAlive(int sumOfAlive,int amountAlive) {
    this.averageEnergyLifeForAlive=(double) sumOfAlive/amountAlive;
    }
    public void changeAverageAgeForDead(int sumOfDead,int amountDead) {
    this.averageAgeForDead=(double) sumOfDead/amountDead;
    }
}
