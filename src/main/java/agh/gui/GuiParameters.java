package agh.gui;

public interface GuiParameters {

    int gridCellWidth = 50;
    int gridCellHeight = 50;
    
    int mapHeight = 10;
    int mapWidth = 10;
    int jungleHight = 2;
    boolean mapVariant = false;  /* false - kula ziemska, true - magiczny portal */

    int startNumberOfPlants = 20;
    int plantEnergy = 5;
    int numberOfNewPlant = 10;
    boolean plantGrowthVariant = false; /* false - zalesione rowniki, true - toksyczne trupt */

    int startNumberOfAnimals = 10;
    int startAnimalEnergy = 20;
    int animalEnergyToReproduce = 10;
    int animalEnergySpendForPropagation = 10;

    int minNumberOfMutations = 0;
    int maxNumberOfMutations = 1;
    boolean mutationVariant = false; /* false - pelna losowosc, true - lekka korekta */
    int lengthOfAnimalGenome = 16;
    boolean behaviourVariant = false; /* false - pelna predestynacja, true - nieco szaleństwa */

}
