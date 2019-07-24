package map;

import buildings.Ancient;
import items.Direction;
import items.Item;
import warMachines.Warier;

import java.util.ArrayList;

public class Cell<E extends Item> {
    public final ArrayList<LocationDealer> locationDealers = new ArrayList<>();
    public E item;
    public LocationDealer masterLocation;
    private boolean ruined=false;
    public Cell( ArrayList<Point> Points, E item,int index) {
        this.item=item;
        for (Point point :Points) {
            locationDealers.add(new LocationDealer(point.x,point.y,this));
        }
        this.masterLocation=locationDealers.get(index);
        item.point.x=masterLocation.getX();
        item.point.y=masterLocation.getY();
    }

    public void ruin(){
        if(!ruined){
        ruined=true;
        for(LocationDealer l:locationDealers){
            l.ruin();
        }}
    }

    public void buildAgain(){
        for(LocationDealer l : locationDealers){
            l.build();
        }
        Map.cells.add(this);
    }
    public void moveTo(Point point){
        for (LocationDealer l : locationDealers){
            l.setXY(point.x,point.y);
        }
        item.point.x=masterLocation.getX();
        item.point.y=masterLocation.getY();
    }

    public void move(Direction direction){
        for (LocationDealer l:locationDealers) {
          l.move(direction.getXvalue(), direction.getYvalue());
        }
        item.point.x=masterLocation.getX();
        item.point.y=masterLocation.getY();
    }

    public ArrayList<ArrayList<ArrayList<Item>>> see(){
        int counter=0;
        ArrayList<ArrayList<ArrayList<Item>>>x=Map.see(masterLocation, ((Warier) item).getSight());
        if(item instanceof Warier){
            for (int i = 0; i < x.size(); i++) {
                for (int j = 0; j < x.get(i).size(); j++) {
                    counter+=x.get(i).get(j).size();
                }
            }
        }
        if(counter!=0){
            return x;
        }
    return null;
    }
}