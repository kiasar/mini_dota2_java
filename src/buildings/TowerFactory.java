package buildings;

import items.GameItem;
import items.Village;
import warMachines.PcWarier;
import warMachines.Tower;

/**
 * Created by ${User} on 12/17/2015.
 */
public class TowerFactory extends Factory {

    public TowerFactory(Village teamId) {
        super(teamId, 0);
    }

    @Override
    public PcWarier build(GameItem facItem) {
        PcWarier pcWarier = facItem.getObject(teamId);
        reciveMoney((int) pcWarier.getPrice());
        return pcWarier;
    }

    public void seeUpGrade(Tower tower){
        if (tower.seeUp >= 4){
            return;
        }

        reciveMoney((int) (tower.getValue()*0.2));
        tower.seeUP();
    }

    public void powUpGrade(Tower tower){
        reciveMoney((int) (tower.getValue()*0.15));
        tower.powerUP();
    }

    @Override
    public void ruin() {}
}
