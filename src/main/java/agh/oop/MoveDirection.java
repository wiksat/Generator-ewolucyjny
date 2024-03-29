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

    public final int numerValue;
    MoveDirection(int value) {
        this.numerValue = value;
    }

    public MoveDirection next() {
        return switch (this) {
            case FORWARD -> MoveDirection.TURN45;
            case TURN45 -> MoveDirection.TURN90;
            case TURN90 -> MoveDirection.TURN135;
            case TURN135 -> MoveDirection.TURN180;
            case TURN180 -> MoveDirection.TURN225;
            case TURN225 -> MoveDirection.TURN270;
            case TURN270 -> MoveDirection.TURN315;
            case TURN315 -> MoveDirection.FORWARD;
        };
    }
    public MoveDirection prev() {
        return switch (this) {
            case FORWARD -> MoveDirection.TURN315;
            case TURN45 -> MoveDirection.FORWARD;
            case TURN90 -> MoveDirection.TURN45;
            case TURN135 -> MoveDirection.TURN90;
            case TURN180 -> MoveDirection.TURN135;
            case TURN225 -> MoveDirection.TURN180;
            case TURN270 -> MoveDirection.TURN225;
            case TURN315 -> MoveDirection.TURN270;
        };
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
            default -> {
                return MoveDirection.TURN315;
            }
        }

    }
}
