package agh.oop;

import agh.simulation.SimulationParameters;
import agh.statistics.StatisticsModule;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class Animal extends AbstractWorldMapElement  {
    public String uniqueID = UUID.randomUUID().toString();

    private int howManyChildren=0;
    private MapDirection orientation;
    private Vector2d position;
    private int age;
    private AbstractWorldMap map;
    private StatusOfAnimal status = StatusOfAnimal.ALIVE;
    private List<IPositionChangeObserver> observers = new ArrayList<IPositionChangeObserver>();
    private final ArrayList<MoveDirection> genotype;
    private int genLength;
    private int genCounter;
    private int lastUsedGene;
    private int lifeEnergy;
    private int costOfTheDay;
    private int minLifeEnergyToReproduce;
    private int amountOfEnergyFromParentToChild;
    private int energyFromPlant;
    private boolean mapWariant;
    private int costOfTeleport;
    private boolean mutationVariant;
    private boolean behaviourVariant;
    public StatisticsModule statisticsModule;

    public Animal(AbstractWorldMap map, Vector2d initialPosition,StatisticsModule statisticsModule){
        this.map = map;
        this.orientation = MapDirection.createRandom();
        this.position = initialPosition;
        this.statisticsModule = statisticsModule;
        this.genLength = SimulationParameters.lengthOfAnimalGenome;
        this.lifeEnergy = SimulationParameters.startAnimalEnergy;
        this.genCounter = 0;
        this.costOfTheDay = SimulationParameters.costOfTheDay;
        this.minLifeEnergyToReproduce = SimulationParameters.minLifeEnergyToReproduce;
        this.amountOfEnergyFromParentToChild = SimulationParameters.lifeEnergySpendForPropagation;
        this.energyFromPlant = SimulationParameters.plantEnergy;
        this.mapWariant = SimulationParameters.mapVariant;
        this.costOfTeleport = SimulationParameters.minLifeEnergyToReproduce;
        this.mutationVariant = SimulationParameters.mutationVariant;
        this.behaviourVariant = SimulationParameters.behaviourVariant;
        this.lastUsedGene = (int) (Math.random()*this.genLength);

        ArrayList<MoveDirection> genes = new ArrayList<>();
        for (int i = 0; i < this.genLength; i++) {
            double randNum = Math.random()*8;
            genes.add(MoveDirection.extract((int)randNum));
        }
//        System.out.println(genes);
        this.genotype = genes;
    }

    public String getUniqueID() {
        return uniqueID;
    }

    public Animal(AbstractWorldMap map, Vector2d initialPosition, StatisticsModule statisticsModule, int startingEnergy, ArrayList<MoveDirection> genotype) {
        this.map = map;
        this.orientation = MapDirection.createRandom();
        this.position=initialPosition;
        this.statisticsModule = statisticsModule;
        this.genLength=genotype.size();
        this.genotype = genotype;
        this.lifeEnergy = startingEnergy;
        this.genCounter=0;
        this.costOfTheDay = SimulationParameters.costOfTheDay;
        this.minLifeEnergyToReproduce = SimulationParameters.minLifeEnergyToReproduce;
        this.amountOfEnergyFromParentToChild = SimulationParameters.lifeEnergySpendForPropagation;
        this.energyFromPlant = SimulationParameters.plantEnergy;
        this.mapWariant = SimulationParameters.mapVariant;
        this.costOfTeleport = SimulationParameters.minLifeEnergyToReproduce;
        this.mutationVariant = SimulationParameters.mutationVariant;
        this.behaviourVariant = SimulationParameters.behaviourVariant;
        this.lastUsedGene = (int) (Math.random()*this.genLength);
    }



    public Vector2d getPosition() {
        return this.position;
    }

    public StatusOfAnimal getStatus() {
        return status;
    }

    public String getA(){
        return position+" "+orientation;
    }

    public void makeDead(){
        this.status = StatusOfAnimal.DEAD;
        map.deadAnimal(this,this.getPosition());
    }

    public void eatPlant(){
        this.setLifeEnergy(this.getLifeEnergy() + this.energyFromPlant);
    }

    public boolean isAt(Vector2d position){
        return position.equals(this.position);
    }

    public int getAge(){
            return this.age;
    }

    @Override
    public String getName() {
        return switch (this.orientation) {
            case NORTH -> "N";
            case NORTHEAST -> "NE";
            case EAST -> "E";
            case WEST -> "W";
            case SOUTHEAST -> "SE";
            case SOUTH -> "S";
            case SOUTHWEST -> "SW";
            case NORTHWEST -> "NW";
        };
    }

    public Vector2d mapMode(Vector2d newPosition) {
        int x = newPosition.x;
        int y = newPosition.y;
        if (mapWariant) {
//                System.out.println("TELEPORT");
            setLifeEnergy(getLifeEnergy() - costOfTeleport);
            return Vector2d.getRandomVectorBetween(
                    this.map.mapBoundary.lowerLeft(),
                    this.map.mapBoundary.upperRight());
        }
        else {
//                System.out.println("KULA Ziemska ");
            if (newPosition.x < 0) {
                x =  this.map.mapBoundary.upperRight().x;
            }
            else if (newPosition.x > this.map.mapBoundary.upperRight().x) {
                x = 0;
            }
            else if (newPosition.y < 0) {
                y = 0;
                turnMove(MoveDirection.TURN180);
            }
            else if (newPosition.y > this.map.mapBoundary.upperRight().y) {
                y = this.map.mapBoundary.upperRight().y;
                turnMove(MoveDirection.TURN180);
            }
            return new Vector2d(x,y);
        }
    }
    public void turnMove(MoveDirection direction){
        int n = direction.numerValue;
        for (int i = 0; i < n; i++) {
            this.orientation = this.orientation.next();
        }
    }
    public void move(MoveDirection direction){

        if (direction == MoveDirection.FORWARD) {
//            System.out.println(direction + "  " + this.uniqueID);
            Vector2d orientationVector = this.orientation.toUnitVector();
            Vector2d newPosition = this.position.add(orientationVector);

            if (this.map.canMoveTo(newPosition)) {
                newPosition = this.mapMode(newPosition);
                this.positionChanged(this.position, newPosition);
                this.position = newPosition;
            }
        } else {
            int n = direction.numerValue;
            for (int i = 0; i < n; i++) {
                this.orientation = this.orientation.next();
            }
            move(MoveDirection.FORWARD);
        }
    }

    private void positionChanged(Vector2d position, Vector2d newPosition) {
        map.positionChanged(this,position,newPosition);
    }

    public int getLifeEnergy(){
        return this.lifeEnergy;
    }

    public void setLifeEnergy(int energy){
        map.energyChanged(this,getLifeEnergy(),energy);
        this.lifeEnergy = energy;
        if (this.getLifeEnergy() <= 0) {
            makeDead();
        }
    }

    public boolean canReproduce(Animal otherAnimal) {
        return otherAnimal.getPosition().equals(this.getPosition()) &&
                this.getLifeEnergy() >= this.minLifeEnergyToReproduce &&
                otherAnimal.getLifeEnergy() >= this.minLifeEnergyToReproduce;
    }

    public List<MoveDirection> getGenotype(){
            return this.genotype;
    }

    public List<MoveDirection> getLeftSlice(int howMany){
        return this.genotype.subList(0, howMany);
    }

    public List<MoveDirection> getRightSlice(int howMany){
        return this.genotype.subList(this.genotype.size() - howMany, this.genotype.size());
    }

    public Animal reproduce(Animal otherAnimal) {
        if (!this.canReproduce(otherAnimal)) {
            throw new IllegalArgumentException("There is too little life energy to reproduce");
        }

        float precentOfGenesThisAnimal = this.getLifeEnergy() / (this.getLifeEnergy() + otherAnimal.getLifeEnergy());
        float precentOfGenesAnotherAnimal = 1 - precentOfGenesThisAnimal;

        ArrayList<MoveDirection> newGenotype  = new ArrayList<MoveDirection>();
        boolean side=Math.random() < 0.5;
            if (side) {
                newGenotype.addAll(otherAnimal.getLeftSlice((int)precentOfGenesAnotherAnimal*otherAnimal.getGenotype().size()));
                newGenotype.addAll((this.getRightSlice((int)precentOfGenesThisAnimal*this.getGenotype().size())));

            }
            else {
                newGenotype.addAll(this.getLeftSlice((int)precentOfGenesThisAnimal*this.getGenotype().size()));
                newGenotype.addAll((otherAnimal.getRightSlice((int)precentOfGenesAnotherAnimal*otherAnimal.getGenotype().size())));
            }
            if (mutationVariant){
                int ile=SimulationParameters.maxNumberOfMutations-SimulationParameters.minNumberOfMutations;
                int l= (int) (Math.random()*ile+SimulationParameters.minNumberOfMutations);
                for (int i = 0; i < l; i++) {
                    int ind= (int) (Math.random()*this.genLength);
                    if (Math.random()>0.5){
                        newGenotype.set(ind,newGenotype.get(ind).next());
                    }else{
                        newGenotype.set(ind,newGenotype.get(ind).prev());
                    }

                }
            }

        this.setLifeEnergy(this.getLifeEnergy() - this.amountOfEnergyFromParentToChild);
        otherAnimal.setLifeEnergy(otherAnimal.getLifeEnergy() - this.amountOfEnergyFromParentToChild);

        var child = new Animal( this.map, this.position, statisticsModule, this.amountOfEnergyFromParentToChild*2, newGenotype);
//System.out.println("utworzono dziecko z genotypem");
//        System.out.println(newGenotype);
//        System.out.println(newGenotype.size());
//        System.out.println(child.uniqueID);

        this.incrementChildren();
        otherAnimal.incrementChildren();
        return child;
    }

    public MapDirection getOrientation() {
        return orientation;
    }

    public String getImageSRC() {
        String path = "src/main/resources/";
        return path + "animal/" + switch (this.getOrientation()) {
            case NORTH -> "turtle0.png";
            case NORTHEAST -> "turtle45.png";
            case EAST -> "turtle90.png";
            case SOUTHEAST -> "turtle135.png";
            case SOUTH -> "turtle180.png";
            case SOUTHWEST -> "turtle225.png";
            case WEST -> "turtle270.png";
            case NORTHWEST -> "turtle315.png";
        };
    }

    public void incrementChildren(){
        this.howManyChildren++;
    }

    public int getHowManyChildren(){
        return this.howManyChildren;
    }

    public void nextDay() {
        this.setLifeEnergy(this.getLifeEnergy() - this.costOfTheDay);
        if (this.lifeEnergy <= 0) {
            this.makeDead();
        }
        else {
            this.age++;
        }
    }

    public void selectDirectionAndMove() {

        if (this.behaviourVariant){
            if (Math.random()<0.8){
                if (this.lastUsedGene+1>=this.genLength){
                    this.lastUsedGene=-1;
                }
                this.move(this.genotype.get( this.lastUsedGene+1));
                this.lastUsedGene= this.lastUsedGene+1;
            }
            else {
                int t = (int) (Math.random()*this.genLength);
                this.move(this.genotype.get(t));
                this.lastUsedGene=t;
            }
        }
        else {
            if (this.lastUsedGene+1>=this.genLength){
                this.lastUsedGene=-1;
            }
//            System.out.println(this.genotype);
//            System.out.println(this.genLength+"   "+this.lastUsedGene+"    "+this.genotype.size());
//            System.out.println(this.uniqueID+ "  "+getA());
//            System.out.println(this.genotype);
//            System.out.println(this.lastUsedGene);
            this.move(getGenotypeAt(this.lastUsedGene+1));
            this.lastUsedGene= this.lastUsedGene+1;
        }
    }
    public MoveDirection getGenotypeAt(int nr){
        return this.genotype.get(nr);
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return Objects.equals(this.uniqueID, animal.uniqueID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uniqueID);
    }

}
