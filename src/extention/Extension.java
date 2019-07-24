package extention;

import buildings.Ancient;
import items.Item;
import map.Map;
import warMachines.Hero;

/**
 * Created by ${User} on 12/16/2015.
 */
public abstract class Extension extends Item {

    public Ancient ownerAncient = null ;


    protected Extension() {
        super(null, 0);
    }



    public void gifter(){
        if (!ruined && !(ownerAncient.equals(null))){
            gift();
        }
    }

    protected abstract void gift();


    public boolean birthOrDie (warMachines.Hero hero){
        if (ownerAncient == null&&!ruined){
            birth(hero);
            return true;
        }else if(ownerAncient.teamId!=hero.teamId&&!ruined){
            ruin();
            return true;
        }
        return false;
    }


    @Override
    public void ruin() {
        ownerAncient=null;
        ruined = true;
    }

    private void birth(Hero hero) {
            ownerAncient = Map.getAncient(hero.teamId).item;
    }

}


