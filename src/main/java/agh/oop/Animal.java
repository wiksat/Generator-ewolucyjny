package agh.oop;

import java.util.ArrayList;
import java.util.List;

public class Animal extends AbstractWorldMapElement  {
    private int lifeEnergy;
    private MapDirection orientation;
    private Vector2d position;
    private int age;
    private IWorldMap map;
    private StatusOfAnimal status = StatusOfAnimal.ALIVE;
    private List<IPositionChangeObserver> observers = new ArrayList<IPositionChangeObserver>();

    //variables from userentry
    private int geneLength=10;
    private final Genotype genotype;
    private int costOfTheDay=2;
    private int minLifeEnergyToReproduce=5;
    private int amountOfEnergyFromParentToChild=3;

//    Animal(IWorldMap map){
//        this.map=map;
//    }
    Animal(IWorldMap map, Vector2d initialPosition){
        this.map=map;
        this.position=initialPosition;
        List<MoveDirection> genes = new ArrayList<>();
        for (int i = 0; i < this.geneLength; i++) {
            double randNum = Math.random()*8;
            genes.add(MoveDirection.extract((int)randNum));
        }
        this.orientation=MapDirection.createRandom();
        this.lifeEnergy=10;

    }
    public Animal(AbstractWorldMap map, Vector2d initialPosition, int startingEnergy, Genotype genotype) {
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
    public boolean canReproduce(Animal otherAnimal) {
        return otherAnimal.getPosition().equals(this.getPosition()) &&
                this.getLifeEnergy() >= this.minLifeEnergyToReproduce &&
                otherAnimal.getLifeEnergy() >= this.minLifeEnergyToReproduce
                ;
    }

    public Animal procreate(Animal otherAnimal) {
        if (!this.canReproduce(otherAnimal)) {
            throw new IllegalArgumentException("There is too little life energy to reproduce");
        }

        int precentOfGenesThisAnimal = this.getLifeEnergy() / (this.getLifeEnergy() + otherAnimal.getLifeEnergy());
        int precentOfGenesAnotherAnimal = 1 - precentOfGenesThisAnimal;

        Genotype newGenotype;
        boolean side=Math.random() < 0.5;
        if (precentOfGenesAnotherAnimal > precentOfGenesThisAnimal) {
            if (side) {
                newGenotype = new Genotype(otherAnimal.getGenotype().getLeftSlice(precentOfGenesAnotherAnimal),
                        this.getGenotype().getRightSlice(precentOfGenesThisAnimal));
            }
            else {
                newGenotype = new Genotype(this.getGenotype().getLeftSlice(precentOfGenesThisAnimal),
                        otherAnimal.getGenotype().getRightSlice(precentOfGenesAnotherAnimal));
            }
        }
        else {
            if (side) {
                newGenotype = new Genotype(this.getGenotype().getLeftSlice(precentOfGenesAnotherAnimal),
                        otherAnimal.getGenotype().getRightSlice(precentOfGenesThisAnimal));
            }
            else {
                newGenotype = new Genotype(otherAnimal.getGenotype().getLeftSlice(precentOfGenesThisAnimal),
                        this.getGenotype().getRightSlice(precentOfGenesAnotherAnimal));
            }
        }

//        int thisEnergyCost = (int) (this.getEnergy() * SimulationConfig.procreationEnergyCostFraction);
//        int otherEnergyCost = (int) (otherAnimal.getEnergy() * SimulationConfig.procreationEnergyCostFraction);
        this.setEnergy(this.getEnergy() - this.amountOfEnergyFromParentToChild);
        otherAnimal.setEnergy(otherAnimal.getEnergy() - this.amountOfEnergyFromParentToChild);

        var child = new Animal(this.map, this.position, this.amountOfEnergyFromParentToChild*2, newGenotype);
        this.becameParent(child);
        otherAnimal.becameParent(child);
        return child;
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
