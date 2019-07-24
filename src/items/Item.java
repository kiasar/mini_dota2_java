package items;

import dota.common.GameObjectID;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import map.Point;

import java.io.Serializable;

/**
 * Created by ${User} on 12/16/2015.
 */
public abstract class Item implements DotaItem , Serializable{
    public GameObjectID id;
    public final Village teamId;
    public double health;
    public boolean ruined = false;
    public Point point = new Point(-1, -1);

    //public final AA health_=new AA();

    protected Item(Village teamId, double health) {
        this.teamId = teamId;
        this.health = health;
        id = GameObjectID.create(this.getClass(), this);
    }

    public double getValue() {
        return 0;
    }

    public int go() {
        return Integer.MAX_VALUE;
    }


    @Override
    public double getHealth() {
        return health;
    }

    @Override
    public void setHealth(double health) {
    //    health_.set((health)/(this.health/health_.get()));
        this.health = health;
    }


    @Override
    public void addHealth(double healthUp) {
      //  health_.set((health+healthUp)/(health/health_.get()));
        health += healthUp;
    }


    @Override
    public void hurt(double shootPower) {
    //    health_.set((health-shootPower)/(health/health_.get()));
        health -= shootPower;
        if (health <= 0) {
            ruin();
        }
    }

    public int haveToGo() {
        return Integer.MAX_VALUE;
    }

    public boolean isAlive() {
        return !ruined;
    }

    @Override
    public Village getTeamId() {
        return teamId;
    }

}
