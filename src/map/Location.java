package map;

import java.util.ArrayList;

public class Location{

    private final int x;
    private final int y;
    public GroundType groundType;
    private ArrayList<Cell> owners =new ArrayList<>();

    Location(int x, int y,GroundType groundType){
        this.x = x;
        this.y = y;
        this.groundType = groundType;
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void addOwner(Cell owner) {
        this.owners.add(owner);
    }

    public void removeOwner(Cell owner){
        this.owners.remove(owner);
    }

    public ArrayList<Cell> getOwners() {
        return owners;
    }

}
