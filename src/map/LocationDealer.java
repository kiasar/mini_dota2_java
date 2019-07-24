package map;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class LocationDealer {
    private int x;
    private int y;


    public DoubleProperty x_=new SimpleDoubleProperty();
    public DoubleProperty y_=new SimpleDoubleProperty();


    private Location location;
    private final Cell owner;

    public LocationDealer(int x, int y , Cell owner) {
        this.owner = owner;
        this.location=Map.mapLocations[x][y];
        setXY(x , y);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        setXY(x,y);
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        setXY(x,y);
    }


    public void setXY(int x , int y) {
        this.x = x;
        this.y = y;

        this.x_.set(x);
        this.y_.set(y);

        setLocation();
    }

    public Location getLocation() {
        return location;
    }

    private void setLocation() {
        location.removeOwner(owner);
        location = Map.mapLocations[x][y];
        location.addOwner(owner);
    }

    void move(int x,int y){
        setXY(this.x + x, this.y + y);
    }

    void ruin(){
        location.removeOwner(owner);
    }

    void build(){
        location.addOwner(owner);
    }

}