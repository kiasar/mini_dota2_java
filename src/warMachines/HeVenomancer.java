package warMachines;

import items.Item;
import items.Village;

import java.util.ArrayList;

/**
 * Created by ${User} on 12/16/2015.
 */
public class HeVenomancer extends Hero {
    public HeVenomancer() {
        super(Village.SCOURGE, 4000, 300, 300, 250);
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
