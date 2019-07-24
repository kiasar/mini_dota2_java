package warMachines;

import eventQueue.HeroBuildQueue;
import items.Direction;
import items.Village;
import map.Map;

public abstract class Hero extends Warier implements items.Hero {
    Direction direction;
    public double shootPower;
    public int speed;

    Hero(Village teamId, double health, int shootSpeed, double shootPower, int speed) {
        super(teamId, health, 0, 7, shootSpeed);
        this.shootPower = shootPower;
        this.speed = speed;
    }


    @Override
    public int getSpeed() {
        return speed;
    }

    @Override
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    @Override
    public double getShootPower() {
        return shootPower;
    }

    @Override
    public void setShootPower(double shootPower) {
        this.shootPower = shootPower;
    }

    @Override
    public void birth() {

        ruined=false;
        if (getTeamId().equals(Village.SCOURGE)){
            health=4000;
        }else {
            health=5000;
        }
    }

    @Override
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public void ruin() {
        ruined = true ;
        Map.eventQueue.add(new HeroBuildQueue(Map.ruinCellByItem(this),30000));
    }
}
