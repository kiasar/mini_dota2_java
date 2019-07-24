package buildings;

import items.GameItem;
import items.Village;
import map.Map;
import warMachines.Hammer;
import warMachines.PcWarier;

/**
 * Created by ${User} on 12/16/2015.
 */
public class Barracks extends Factory {
    Ancient ancient;
    public Barracks(Village teamId) {
        super(teamId, 5000);
        ancient=(teamId==Village.SENTINEL)? Map.sentinel.item:  Map.scourge.item;
    }

    @Override
    public PcWarier build(GameItem facItem) {
        PcWarier pcWarier = facItem.getObject(teamId);
        reciveMoney((int) pcWarier.getPrice());
        return pcWarier;
    }


    public void powUpGrade(GameItem facItem){
        PcWarier pcWarier = facItem.getObject(teamId);
        if (pcWarier instanceof Hammer){
            reciveMoney(1000 * (((Hammer)pcWarier).powerUp.get(teamId) - 1));
            ((Hammer)pcWarier).powerUP(teamId);
        }else new Exception("should be Hammer");


    }
    public void bloodUpGrade(GameItem facItem){
        PcWarier pcWarier = facItem.getObject(teamId);
        if (pcWarier instanceof Hammer){
            reciveMoney(1000 * (((Hammer)pcWarier).bloodUp.get(teamId) - 1));
            ((Hammer)pcWarier).bloodUP(teamId);
        }else new Exception("should be Hammer");

    }

    @Override
    public void ruin() {
        ruined=true;
    }
}
