package agh.oop;

import agh.simulation.SimulationParameters;

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
    private AbstractWorldMap map;
    private StatusOfAnimal status = StatusOfAnimal.ALIVE;
    private List<IPositionChangeObserver> observers = new ArrayList<IPositionChangeObserver>();
    private final List<MoveDirection> genotype;
    private int genLength;
    private int genCounter;
    private int lastUsedGene;

    //variables from userentry
    private int geneLength=10;


    private int costOfTheDay=2;
    private int minLifeEnergyToReproduce=5;
    private int amountOfEnergyFromParentToChild=3;
    private int energyFromPlant=2;
    private boolean mapWariant=true;  //0 kula ziamska, 1 piekielny portal
    private int costOfTeleport=3;
    private boolean mutationVariant=false;

    public Animal(AbstractWorldMap map, Vector2d initialPosition){
        this.map=map;
        this.position=initialPosition;
        List<MoveDirection> genes = new ArrayList<>();
        for (int i = 0; i < this.geneLength; i++) {
            double randNum = Math.random()*8;
            genes.add(MoveDirection.extract((int)randNum));
        }
        this.orientation=MapDirection.createRandom();
        this.lifeEnergy=10;
        this.genotype = genes;
        this.genLength=genotype.size();
        this.genCounter=0;
        this.lastUsedGene=(int) (Math.random()*this.genLength);
    }
    public Animal(AbstractWorldMap map, Vector2d initialPosition, int startingEnergy, List<MoveDirection> genotype) {
        this.position=initialPosition;
        this.map = map;
        this.lifeEnergy = startingEnergy;
        this.genotype = genotype;
        this.genLength=genotype.size();
        this.genCounter=0;
        this.lastUsedGene=(int) (Math.random()*this.genLength);
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
    public Vector2d mapMode( Vector2d newPosition) {
        int x=0;
        int y=0;
        if (mapWariant){
            if ( newPosition.x < 0){
                x =  this.map.mapBoundary.upperRight().x;
                y = newPosition.y;
            }
            else if ( newPosition.x > this.map.mapBoundary.upperRight().x){
                x =  0;
                y = newPosition.y;
            }
            else if(newPosition.y<0){
                x=newPosition.x;
                y = 1;
                move(MoveDirection.TURN180);
            }
            else if (newPosition.x > this.map.mapBoundary.lowerLeft().y){
                x= newPosition.x;
                y = this.map.mapBoundary.lowerLeft().y;
                move(MoveDirection.TURN180);
            }
            return new Vector2d(x,y);
        }
        else{
            setLifeEnergy(getLifeEnergy()-costOfTeleport);
            return Vector2d.getRandomVectorBetween(
                    this.map.mapBoundary.lowerLeft(),
                    this.map.mapBoundary.upperRight());
        }

    }
    public void move(MoveDirection direction){

        switch (direction) {
            case FORWARD -> {
                Vector2d orientationVector = this.orientation.toUnitVector();
                Vector2d newPosition = this.position.add(orientationVector);

                if (this.map.canMoveTo(newPosition)) {
                    newPosition = this.mapMode(newPosition);
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
            if (side) {
                newGenotype=(otherAnimal.getLeftSlice((int)precentOfGenesAnotherAnimal*otherAnimal.getGenotype().size()));
                newGenotype.addAll((this.getRightSlice((int)precentOfGenesThisAnimal*this.getGenotype().size())));

            }
            else {
                newGenotype=(this.getLeftSlice((int)precentOfGenesThisAnimal*this.getGenotype().size()));
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

        var child = new Animal((AbstractWorldMap) this.map, this.position, this.amountOfEnergyFromParentToChild*2, newGenotype);
        //observery
//        this.becameParent(child);
//        otherAnimal.becameParent(child);

        this.incrementChildren();
        otherAnimal.incrementChildren();
        return child;
    }

    public MapDirection getOrientation() {return orientation;}

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
            Observer.positionChanged(this,oldPosition,newPosition);
        }
    }

    public void selectDirectionAndMove(boolean behaviourVariant) {

        if (behaviourVariant){
            if (Math.random()<0.8){
                if (this.lastUsedGene+1>=this.genLength){
                    this.lastUsedGene=-1;
                }
                this.move(this.genotype.get( this.lastUsedGene+1));
                this.lastUsedGene= this.lastUsedGene+1;
            }else{
                int t= (int) (Math.random()*this.genLength);
                this.move(this.genotype.get(t));
                this.lastUsedGene=t;
            }
        }
        else{
            if (this.lastUsedGene+1>=this.genLength){
                this.lastUsedGene=-1;
            }
            this.move(this.genotype.get( this.lastUsedGene+1));
            this.lastUsedGene= this.lastUsedGene+1;
        }

    }
}
