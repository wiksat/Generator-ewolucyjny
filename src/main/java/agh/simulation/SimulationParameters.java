package agh.simulation;

public class SimulationParameters {
    public static int startNumberOfPlants = 20;
    public static int plantEnergy = 5;
    public static int numberOfNewPlant = 10;

    public static int startNumberOfAnimals = 10;
    public static int startAnimalEnergy = 10;
    public static int minLifeEnergyToReproduce = 10;
    public static int lifeEnergySpendForPropagation = 5;

    public static int minNumberOfMutations = 0;
    public static int maxNumberOfMutations = 1;
    public static int lengthOfAnimalGenome = 16;

    public static int costOfTheDay = 1;
    public static int simulationMoveDelay = 500;

    public static boolean mapVariant = false;  /* false - kula ziemska, true - magiczny portal */
    public static boolean plantGrowthVariant = false; /* false - zalesione rowniki, true - toksyczne trupt */
    public static boolean mutationVariant = false; /* false - pelna losowosc, true - lekka korekta */
    public static boolean behaviourVariant = false; /* false - pelna predestynacja, true - nieco szaleństwa */

}
