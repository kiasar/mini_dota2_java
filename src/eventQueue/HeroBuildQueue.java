package eventQueue;

import items.Hero;
import map.Cell;
import map.Map;
import map.Point;
import warMachines.HeTiny;
import warMachines.HeVenomancer;

import java.util.ArrayList;

/**
 * Created by ${User} on 12/24/2015.
 */
public class HeroBuildQueue extends EventQueue {

    public HeroBuildQueue(Cell cell, int remainedTime) {
        super(cell, remainedTime);
    }

    @Override
    public EventQueue command(ArrayList<EventQueue> eventQueue) {

        if(cell.item instanceof Hero){
            ((Hero) cell.item).birth();
            if(cell.item instanceof HeTiny)
            cell.moveTo(new Point(Map.sentinel.masterLocation.getX(),Map.sentinel.masterLocation.getY()));
            else if(cell.item instanceof HeVenomancer)
                cell.moveTo(new Point(Map.scourge.masterLocation.getX(),Map.scourge.masterLocation.getY()));
        }
        return null;
    }
}
