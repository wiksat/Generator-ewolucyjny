package agh.statistics;

import java.io.IOException;

public class StatisticsModule {

   public StatisticsWriter statisticsWriter;
    int amountOfAnimals;
//    ArrayList<Integer> amountOfAnimalsList = new ArrayList<>();
    int amountOfGrasses;
//    ArrayList<Integer> amountOfGrassesList = new ArrayList<>();
    int amountOfFreePlaces;
//    ArrayList<Integer> amountOfFreePlacesList = new ArrayList<>();
    double averageEnergyLifeForAlive;
//    ArrayList<Double> averageEnergyLifeForAliveList = new ArrayList<>();
    double averageAgeForDead;
//    ArrayList<Double> averageAgeForDeadList = new ArrayList<>();

    public StatisticsModule(StatisticsWriter statisticsWriter) {
        this.statisticsWriter=statisticsWriter;
    }

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
    public void saveDataToLists(int nrOfDay) throws IOException {
//        amountOfAnimalsList.add(amountOfAnimals);
//        amountOfGrassesList.add(amountOfGrasses);
//        amountOfFreePlacesList.add(amountOfFreePlaces);
//        averageEnergyLifeForAliveList.add(averageEnergyLifeForAlive);
//        averageAgeForDeadList.add(averageAgeForDead);
        statisticsWriter.save(nrOfDay,amountOfAnimals,amountOfGrasses,amountOfFreePlaces,averageEnergyLifeForAlive,averageAgeForDead);
    }

}
