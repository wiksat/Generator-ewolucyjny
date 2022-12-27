package agh.oop;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class Animal extends AbstractWorldMapElement  {
    String uniqueID = UUID.randomUUID().toString();
    private int lifeEnergy;
    private int howManyChildren=0;
    private MapDirection orientation;
    private Vector2d position;
    private int age;
    private IWorldMap map;
    private StatusOfAnimal status = StatusOfAnimal.ALIVE;
    private List<IPositionChangeObserver> observers = new ArrayList<IPositionChangeObserver>();
    private final List<MoveDirection> genotype;

    //variables from userentry
    private int geneLength=10;


    private int costOfTheDay=2;
    private int minLifeEnergyToReproduce=5;
    private int amountOfEnergyFromParentToChild=3;
    private int energyFromPlant=2;

    Animal(IWorldMap map, Vector2d initialPosition, List<MoveDirection> genotype){
        this.map=map;
        this.position=initialPosition;
        this.genotype = genotype;
        List<MoveDirection> genes = new ArrayList<>();
        for (int i = 0; i < this.geneLength; i++) {
            double randNum = Math.random()*8;
            genes.add(MoveDirection.extract((int)randNum));
        }
        this.orientation=MapDirection.createRandom();
        this.lifeEnergy=10;

    }
    public Animal(AbstractWorldMap map, Vector2d initialPosition, int startingEnergy, List<MoveDirection> genotype) {
        this.position=initialPosition;
        this.map = map;
        this.lifeEnergy = startingEnergy;
        this.genotype = genotype;
    }
    public Vector2d getPosition() {
        return this.position;
    }
    public String getA(){
        return position+" "+orientation;
    }
    public void makeDead(){
        this.status=StatusOfAnimal.DEAD;
        //cos z observerem trzeba bedzie zrobic
    }
    public void eatPlant(){
        this.setLifeEnergy(this.getLifeEnergy() + this.energyFromPlant);
    }
    public String toString(){
        return switch(orientation) {
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

    public void move(MoveDirection direction){

        switch (direction) {
            case FORWARD -> {
                Vector2d orientationVector = this.orientation.toUnitVector();
                Vector2d newPosition = this.position.add(orientationVector);

                if (this.map.canMoveTo(newPosition)) {
//                    newPosition = this.map.correctMovePosition(this.getPosition(), newPosition);
                    this.positionChanged(this.position, newPosition);
                    this.position = newPosition;
                }
            }
            default -> {
                int n = direction.numerValue;
                for (int i = 0; i < n; i++) {
                    this.orientation = this.orientation.next();
                }
            }
        }
    }
    public int getLifeEnergy(){
        return this.lifeEnergy;
    }
    public void setLifeEnergy(int energy){
        this.lifeEnergy=energy;
        if (this.getLifeEnergy() <= 0) {
            makeDead();
        }
    }
    public boolean canReproduce(Animal otherAnimal) {
        return otherAnimal.getPosition().equals(this.getPosition()) &&
                this.getLifeEnergy() >= this.minLifeEnergyToReproduce &&
                otherAnimal.getLifeEnergy() >= this.minLifeEnergyToReproduce
                ;
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

        List<MoveDirection> newGenotype;
        boolean side=Math.random() < 0.5;
//        if (precentOfGenesAnotherAnimal > precentOfGenesThisAnimal) {
            if (side) {
//                newGenotype = new Genotype(otherAnimal.getGenotype().getLeftSlice(precentOfGenesAnotherAnimal),
//                        this.getGenotype().getRightSlice(precentOfGenesThisAnimal));
                newGenotype=(otherAnimal.getLeftSlice((int)precentOfGenesAnotherAnimal*otherAnimal.getGenotype().size()));
                newGenotype.addAll((this.getRightSlice((int)precentOfGenesThisAnimal*this.getGenotype().size())));

            }
            else {
//                newGenotype = new Genotype(this.getGenotype().getLeftSlice(precentOfGenesThisAnimal),
//                        otherAnimal.getGenotype().getRightSlice(precentOfGenesAnotherAnimal));
                newGenotype=(this.getLeftSlice((int)precentOfGenesThisAnimal*this.getGenotype().size()));
                newGenotype.addAll((otherAnimal.getRightSlice((int)precentOfGenesAnotherAnimal*otherAnimal.getGenotype().size())));
            }
//        }
//        else {
//            if (side) {
////                newGenotype = new Genotype(this.getGenotype().getLeftSlice(precentOfGenesAnotherAnimal),
////                        otherAnimal.getGenotype().getRightSlice(precentOfGenesThisAnimal));
//
//                newGenotype=(this.getLeftSlice((int)precentOfGenesAnotherAnimal*otherAnimal.getGenotype().size()));
//                newGenotype.addAll((otherAnimal.getRightSlice((int)precentOfGenesThisAnimal*this.getGenotype().size())));
//            }
//            else {
////                newGenotype = new Genotype(otherAnimal.getGenotype().getLeftSlice(precentOfGenesThisAnimal),
////                        this.getGenotype().getRightSlice(precentOfGenesAnotherAnimal));
//                newGenotype=(otherAnimal.getLeftSlice((int)precentOfGenesThisAnimal*this.getGenotype().size()));
//                newGenotype.addAll((this.getRightSlice((int)precentOfGenesAnotherAnimal*otherAnimal.getGenotype().size())));
//            }
//        }

//        int thisEnergyCost = (int) (this.getEnergy() * SimulationConfig.procreationEnergyCostFraction);
//        int otherEnergyCost = (int) (otherAnimal.getEnergy() * SimulationConfig.procreationEnergyCostFraction);
        this.setLifeEnergy(this.getLifeEnergy() - this.amountOfEnergyFromParentToChild);
        otherAnimal.setLifeEnergy(otherAnimal.getLifeEnergy() - this.amountOfEnergyFromParentToChild);

        var child = new Animal((AbstractWorldMap) this.map, this.position, this.amountOfEnergyFromParentToChild*2, newGenotype);
        //observery
//        this.becameParent(child);
//        otherAnimal.becameParent(child);

        this.incrementChildren();
        otherAnimal.incrementChildren();
        return child;
    }
    public void incrementChildren(){
        this.howManyChildren++;
    }
    public int getHowManyChildren(){
        return this.howManyChildren;
    }
    public void nextDay() {
        this.setLifeEnergy(this.getLifeEnergy() - this.costOfTheDay);
        if (this.status == StatusOfAnimal.ALIVE) {
            this.age++;
        }
    }
    public void addObserver(IPositionChangeObserver observer){
        this.observers.add(observer);
    }
    public void removeObserver(IPositionChangeObserver observer){
        this.observers.remove(observer);
    }
    void positionChanged(Vector2d oldPosition, Vector2d newPosition){
//        this.map.getBound().sortuj();
        for (IPositionChangeObserver Observer: this.observers) {
            Observer.positionChanged(oldPosition,newPosition);
        }
    }
}
