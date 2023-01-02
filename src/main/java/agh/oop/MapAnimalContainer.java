package agh.oop;


public record MapAnimalContainer(int energyOfAnimal, Animal animal) implements Comparable<MapAnimalContainer>{

    @Override
    public int compareTo(MapAnimalContainer o) {
        if (o.animal() == null) {
            return 1;
        }
        if (this.animal() == null) {
            return -1;
        }

        if(this.energyOfAnimal() == o.energyOfAnimal()){
            if (this.animal().getAge()== o.animal().getAge()) {
                if (this.animal().getHowManyChildren() == o.animal().getHowManyChildren()) {
                    return this.animal().uniqueID.compareTo(o.animal().uniqueID);
                }
                return this.animal().getHowManyChildren() - o.animal().getHowManyChildren();
            }
            return this.animal().getAge() - o.animal().getAge();
        }


        return this.energyOfAnimal() - o.energyOfAnimal();

    }
}
