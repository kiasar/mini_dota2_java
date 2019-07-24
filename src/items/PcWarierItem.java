package items;

import java.util.ArrayList;

/**
 * Created by ${User} on 12/16/2015.
 */
public interface PcWarierItem extends WarierItem , ShootAbleItem {

    // boolean canMove

    void setValue(double value);
    boolean isCanMove();
}



interface ShootAbleItem  {

    ArrayList<ArrayList<ArrayList<Item>>> getEnemy(ArrayList<ArrayList<ArrayList<Item>>> nearItems);

    ArrayList<Item> checkPriority(ArrayList<ArrayList<ArrayList<Item>>> enemies);

}
