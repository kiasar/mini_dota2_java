package warMachines;

import items.Item;
import items.Village;

import java.util.ArrayList;

/**
 * Created by ${User} on 12/16/2015.
 */
public class HeTiny extends Hero {

    public HeTiny() {
        super(Village.SENTINEL, 5000, 400, 400, 400);
    }

    @Override
    public void shoot(ArrayList<Item> enemies) {
        if (enemies == null) {
            return;
        }
        for (Item enemy : enemies) {
            enemy.hurt(shootPower);
        }
    }

}
