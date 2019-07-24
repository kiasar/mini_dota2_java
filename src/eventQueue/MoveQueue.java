package eventQueue;

import extention.GoldMine;
import items.Direction;
import map.Cell;
import map.Map;
import warMachines.Hammer;
import warMachines.PcWarier;

import java.util.ArrayList;

/**
 * Created by ali on 12/24/2015.
 */
public class MoveQueue extends EventQueue {

    public Direction direction;

    public MoveQueue(Cell cell, int remainedTime, Direction direction) {
        super(cell, remainedTime);
        this.direction = direction;
    }


    @Override
    public EventQueue command(ArrayList<EventQueue> eventQueue) {
        if (cell.item instanceof Hammer) {
            int x = cell.masterLocation.getX();
            int y =  cell.masterLocation.getY();
            Direction direction2=null;
            for (Direction direction1 : Direction.values()) {
                if (direction1 == direction.getOpposite()) continue;
                try {
                    if (Map.mapLocations[x][y].groundType.isSameLane(Map.mapLocations[x + direction1.getXvalue()][y + direction1.getYvalue()].groundType)) {
                        cell.move(direction1);
                        direction2 = direction1;
                        break;
                    }
                } catch (ArrayIndexOutOfBoundsException ignored) {
                }
            }

            if (((Hammer) cell.item).getEnemy(cell.see()) == null) {
                if(direction2==null){eventQueue.remove(this);return null;}
                return new MoveQueue(cell, ((Hammer) cell.item).getSpeed(), (direction2==null)?direction:direction2);
            } else {
                return new ShootQueue(cell,((PcWarier) cell.item).checkPriority(((PcWarier) cell.item).getEnemy(cell.see())), direction);
            }
        } else if (cell.item instanceof warMachines.Hero) {
            try {
                cell.move(direction);
            } catch (ArrayIndexOutOfBoundsException ignored) {
            }
            for (Cell cell1 : Map.goldMines) {
                if (cell1.masterLocation.getX()==cell.masterLocation.getX()&&cell1.masterLocation.getY()==cell.masterLocation.getY()) {
                    if (((GoldMine) cell1.item).birthOrDie((warMachines.Hero) cell.item)) {
                        if (cell1.item.isAlive()) {
                            return new GoldAddQueue(cell1, 1000, 100);
                        }
                    }
                }
            }
        }

        return null;
    }
}