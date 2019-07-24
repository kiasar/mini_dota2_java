package eventQueue;

import buildings.Ancient;
import extention.GoldMine;
import map.Cell;

import java.util.ArrayList;

/**
 * Created by ali on 12/27/2015.
 */
public class GoldAddQueue extends EventQueue{
    public int goldToBeAdded;
    public GoldAddQueue(Cell cell, int remainedTime,int goldToBeAdded) {
        super(cell, remainedTime);
        this.goldToBeAdded = goldToBeAdded;
    }

    @Override
    public EventQueue command(ArrayList<EventQueue> eventQueue) {
        if(cell.item instanceof Ancient){
        ((Ancient) cell.item).addGold(goldToBeAdded);
        }
        else{

            ((GoldMine) cell.item).gifter();
        }
        return new GoldAddQueue(cell,1000,goldToBeAdded);
    }
}
