package warMachines;


import buildings.Ancient;
import items.Item;
import items.PcWarierItem;
import items.Village;
import map.Map;

import java.util.ArrayList;


public abstract class PcWarier extends Warier implements PcWarierItem {
    boolean canMove;
    double value;

    PcWarier(Village teamId, double health, double price, int sight, int shootSpeed) {
        super(teamId, health, price, sight, shootSpeed);
        this.value = price * 0.8;
    }

    @Override
    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public double getValue() {
        return value;
    }

    @Override
    public boolean isCanMove() {
        return canMove;
    }

    @Override
    public ArrayList<ArrayList<ArrayList<Item>>> getEnemy(ArrayList<ArrayList<ArrayList<Item>>> nearItems) {

        boolean hasEnemy=false;
        if(nearItems==null)return null;

        for (int i = 0; i < nearItems.size(); i++) {
            for (int j = 0; j < nearItems.get(i).size() ; j++) {
                for (int k = 0; k < nearItems.get(i).get(j).size() ; k++) {
                    if (nearItems.get(i).get(j).get(k).getTeamId().equals(getTeamId())) {
                        nearItems.get(i).get(j).remove(k--);
                    }else{
                        hasEnemy=true;
                    }
                }
            }
        }
        if(!hasEnemy)return null;
        return nearItems;
    }

    @Override
    public void ruin() {
        if (getTeamId().equals(Village.SCOURGE)) {
            Map.sentinel.item.addGold((int) getValue());
        }else {
            Map.sentinel.item.addGold((int)getValue());
        }
        ruined = true ;
    }
}



