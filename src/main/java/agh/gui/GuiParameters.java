package agh.gui;

public interface GuiParameters {
    int mapHight = 10;
    int mapWidth = 10;
    boolean mapVariant = false;  /* false - kula ziemska, true - magiczny portal */

    int startNumberOfPlants = 20;
    int plantEnergy = 5;
    int numberOfNewPlant = 10;
    boolean plantGrowthVariant = false; /* false - zalesione równiki, true - toksyczne trupt */

    int startNumberOfAnimals = 10;
    int startAnimalEnergy = 20;
    int animalEnergyToBeFull = 10;
    int animalEnergyNeededToPropagation = 10;

    int minNumberOfMutations = 0;
    int maxNumberOfMutations = 1;
    boolean mutationVariant = false; /* false - pełna losowość, true - lekka korekta */
    int lengthOfAnimalGenome = 16;
    boolean behaviourVariant = false; /* false - pełna predestynacja, true - nieco szaleństwa */

}
