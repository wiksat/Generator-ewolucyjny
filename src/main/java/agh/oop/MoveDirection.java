package agh.oop;

public enum MoveDirection {
    FORWARD(0),
    TURN45(1),
    TURN90(2),
    TURN135(3),
    TURN180(4),
    TURN225(5),
    TURN270(6),
    TURN315(7);

    public int numerValue;
    MoveDirection(int value) {
        this.numerValue = value;
    }

    public static MoveDirection extract(int value){
        switch (value){
            case 0 ->{
                return MoveDirection.FORWARD;
            }
            case 1 ->{
                return MoveDirection.TURN45;
            }
            case 2 ->{
                return MoveDirection.TURN90;
            }
            case 3 ->{
                return MoveDirection.TURN135;
            }
            case 4 ->{
                return MoveDirection.TURN180;
            }
            case 5 ->{
                return MoveDirection.TURN225;
            }
            case 6 ->{
                return MoveDirection.TURN270;
            }
            case 7 ->{
                return MoveDirection.TURN315;
            }
            default -> {
                return MoveDirection.TURN315;
            }
        }

    }
}
