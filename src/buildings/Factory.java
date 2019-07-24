package buildings;

import items.Item;
import items.Village;
import map.Map;

/**
 * Created by ${User} on 12/16/2015.
 */

public abstract class Factory extends Item implements items.Factory{

    protected Factory(Village teamId, double health) {
        super(teamId, health);
    }

    public void reciveMoney(int money) {
        Map.getAncient(teamId).item.reduceGold(money);
    }

}
