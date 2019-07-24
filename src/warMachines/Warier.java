package warMachines;

import items.Item;
import items.Village;
import items.WarierItem;

import java.util.ArrayList;

/**
 * Created by User on 12/14/2015.
 */


public abstract  class Warier extends Item implements WarierItem {

    public static double price;
    public int sight;
    public int shootSpeed;

    Warier(Village teamId, double health, double price, int sight, int shootSpeed) {
        super(teamId, health);
        this.price = price;
        this.sight = sight;
        this.shootSpeed = shootSpeed;
    }


    @Override
    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public void setSight(int sight) {
        this.sight = sight;
    }

    @Override
    public int getSight() {
        return sight;
    }

    @Override
    public void setShootSpeed(int shootSpeed) {
        this.shootSpeed = shootSpeed;
    }

    @Override
    public int getShootSpeed() {
        return shootSpeed;
    }

    @Override
    public boolean isEnemy(Item dotaItem) {
        if (this.getTeamId() == dotaItem.getTeamId()) {
            return false;
        }
        return true;
    }
}
