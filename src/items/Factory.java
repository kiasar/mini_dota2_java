package items;

import warMachines.PcWarier;

/**
 * Created by ${User} on 12/15/2015.
 */
public interface Factory extends DotaItem {

    /*
     * double health
     * boolean ruined=false
     * int teamId
     */

    PcWarier build( GameItem facItem );

    void reciveMoney ( int money);


}


