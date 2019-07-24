package buildings;

import items.Hero;
import items.Item;
import items.Village;
import warMachines.HeTiny;
import warMachines.HeVenomancer;

import java.util.ArrayList;

/**
 * Created by ${User} on 12/16/2015.
 */
public class Ancient extends Item {
    public static boolean sentinelMade=false;
    public static boolean scourgeMade=false;
    public int treasure=5000;
    public Barracks[] barracks=new Barracks[3];
    public ArrayList<Integer> powerups=new ArrayList<>();
    public warMachines.Hero hero;
    public TowerFactory towerFactory;

    private Ancient(Village teamId) {
        super(teamId, 10000);
        towerFactory=new TowerFactory(teamId);
        if(teamId==Village.SENTINEL){
            hero=new HeTiny();
        }
        else if (teamId==Village.SCOURGE){
            hero=new HeVenomancer();
        }
    }
    public static Ancient makeSentinel(){
        sentinelMade=true;
        return new Ancient(Village.SENTINEL);
    }
    public static Ancient makeScourge(){
        scourgeMade=true;
        return new Ancient(Village.SCOURGE);
    }

    public void addGold(int gold){
        treasure+=gold;
    }
    public void setGold(int gold){treasure=gold;}
    public void reduceGold(int gold){
        treasure-=gold;
    }

    @Override
    public void ruin()throws GameOver{
        ruined=true;
        throw new GameOver(teamId);
        // TODO
    }

}

class GameOver extends RuntimeException{
    GameOver(Village loser){
        if(loser==Village.SCOURGE){
            System.out.println("Game Over \n" + "The winner is : " + Village.SENTINEL);
        }
        else if(loser==Village.SENTINEL){
            System.out.println("Game Over \n" + "The winner is : " + Village.SCOURGE);
        }
        System.exit(0);
    }
}