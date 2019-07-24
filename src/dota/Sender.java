package dota;

import dota.common.Cell;
import dota.common.GameObjectID;
import items.Direction;

import javax.sql.rowset.serial.SerialArray;
import java.io.Serializable;

/**
 * Created by ali on 1/6/2016.
 */
public class Sender implements Serializable{
    SenderEnum senderType;
    int type=0;
    GameObjectID lane=null;
    int row=0;
    int col=0;
    dota.common.Cell target=null;
    GameObjectID id=null;
    int teamID;
    String msg;
    private Sender(int teamID,SenderEnum senderType,GameObjectID id, int type,GameObjectID lane, int row,int col,  Cell target,String msg) {
        this.teamID = teamID;
        this.col = col;
        this.id = id;
        this.lane = lane;
        this.row = row;
        this.senderType = senderType;
        this.target = target;
        this.type = type;
        this.msg=msg;
    }

    public static Sender sendCreateAttacker(int teamID,int attackerType, GameObjectID lane){
        return new Sender(teamID,SenderEnum.CREAT_ATTACKER,null,attackerType,lane,0,0,null,null);
    }

    public static Sender sendCreateTower(int teamID,int towerType,int row,int col){
        return new Sender(teamID,SenderEnum.CREATE_TOWER,null,towerType,null,row,col,null,null);
    }

    public static Sender sendAttackerPowerup(int teamID,int powerupType){
        return new Sender(teamID,SenderEnum.ATTACKER_POWERUP,null,powerupType,null,0,0,null,null);
    }

    public static Sender sendTowerPowerup(GameObjectID id,int powerupType){
        return new Sender(0,SenderEnum.TOWER_POWERUP,id,powerupType,null,0,0,null,null);
    }

    public static Sender sendHeroMove(int teamID, int direction){
        return new Sender(teamID,SenderEnum.HERO_MOVE,null,direction,null,0,0,null,null);
    }

    public static Sender sendHeroAttack(int teamID,Cell target){
        return new Sender(teamID,SenderEnum.HERO_ATTACK,null,0,null,0,0,target,null);
    }

    public static Sender sendPause(){
        return new Sender(0,SenderEnum.PAUSE,null,0,null,0,0,null,null);
    }
    public static Sender sendChat(String msg){
        return new Sender(0,SenderEnum.PAUSE,null,0,null,0,0,null,msg);
    }



    public static Sender sendExit(){
        return new Sender(0,SenderEnum.EXIT,null,0,null,0,0,null,null);
    }

}
