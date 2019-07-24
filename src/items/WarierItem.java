package items;

import java.util.ArrayList;

/**
 * Created by ${User} on 12/14/2015.
 */
public interface WarierItem extends DotaItem {


    // double price
    // int sight
    // int shootSpeed

    /*
     * double health
     * boolean ruined=false
     * int teamId
     */




    void setPrice(double price);

    double getPrice ();

    void  setSight(int sight);

    int getSight ();

    void setShootSpeed( int shootSpeed);

    int getShootSpeed();




    boolean isEnemy (Item dotaItem);

    void shoot(ArrayList<Item> enemies);

}

