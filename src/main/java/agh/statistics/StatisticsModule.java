package agh.statistics;

import agh.oop.Animal;
import javafx.scene.layout.VBox;

public class StatisticsModule {
    int amountOfAnimals;
    int amountOfGrasses;
    int amountOfFreePlaces;
    double averageEnergyLifeForAlive;
    double averageAgeForDead;

    public int getAmountOfAnimals() {
        return amountOfAnimals;
    }
    public int getAmountOfGrasses() {
        return amountOfGrasses;
    }
    public int getAmountOfFreePlaces() {
        return amountOfFreePlaces;
    }
    public double getAverageEnergyLifeForAlive() {
        return averageEnergyLifeForAlive;
    }
    public double getAverageAgeForDead() {
        return averageAgeForDead;
    }

    public void incrementGrasses() {
        amountOfGrasses++;
    }
    public void decrementGrasses() {
        amountOfGrasses--;
    }
    public void changeAmountOfFreePlaces(int amount){
        amountOfFreePlaces = amount;
    }

    public void changeAmountOfAnimal(int amount) {
        this.amountOfAnimals = amount;
    }

    public void changeAverageEnergyLifeForAlive(int sumOfAlive,int amountAlive) {
        this.averageEnergyLifeForAlive = (double) sumOfAlive/amountAlive;
    }
    public void changeAverageAgeForDead(int sumOfDead,int amountDead) {
        this.averageAgeForDead = (double) sumOfDead/amountDead;
    }
}
