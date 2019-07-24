package eventQueue;

import items.Direction;
import items.Hero;
import items.Item;
import map.Cell;
import map.Map;
import map.Point;
import warMachines.Hammer;
import warMachines.PcWarier;
import warMachines.Warier;

import java.util.ArrayList;

/**
 * Created by ali on 12/24/2015.
 */
public class ShootQueue extends EventQueue {

    public Direction direction;
    public ArrayList<Item> items;

    public ShootQueue(Cell cell,ArrayList<Item> items, Direction direction) {
        super(cell, ((Warier) cell.item).getShootSpeed());
        this.items = items;
        this.direction = direction;
    }

    @Override
    public EventQueue command(ArrayList<EventQueue> eventQueue) {
        if(items.size()==0){
            if(cell.item instanceof Hero)System.out.println("kk");
            eventQueue.remove(this);
            return null;
        }
        if(Map.distance(cell.item.point,items.get(0).point)<= ((Warier) cell.item).getSight()){
            ((Warier) cell.item).shoot(items);
        }
        if (cell.item instanceof PcWarier) {
            if (((PcWarier) cell.item).getEnemy(((PcWarier) cell.item).getEnemy(cell.see())) == null) {
                if (cell.item instanceof Hammer) {
                    return new MoveQueue(cell, ((Hammer) cell.item).getSpeed(), direction);
                }
            } else {
                return new ShootQueue(cell,((PcWarier) cell.item).checkPriority(((PcWarier) cell.item).getEnemy(cell.see())), direction);
            }
        }
        return null;
    }

}
