package items;

public enum Direction {
    UP(0,-1) , DOWN(0,1) , LEFT(-1,0) , RIGHT(1,0);

    private int Xvalue;
    private int Yvalue;

    Direction(int xvalue, int yvalue) {
        Xvalue = xvalue;
        Yvalue = yvalue;
    }

    public Direction getOpposite(){
        switch (this) {
            case UP:
                return DOWN;
            case DOWN:
                return UP;
            case LEFT:
                return RIGHT;
            case RIGHT:
                return LEFT;
        }
        return null;
    }

    public int getXvalue() {
        return Xvalue;
    }

    public int getYvalue() {
        return Yvalue;
    }
}
