package items;

/**
 * Created by ${User} on 12/14/2015.
 */


public interface DotaItem  extends Life  , Ruin , Improve{
    // final int teamId


    /*
     * double health
     * boolean ruined=false
     */


    Village getTeamId();


}



interface Life {
    //  double health

    void hurt ( double ShootPower);
    double getHealth ();
    void setHealth ( double health);
    void  addHealth ( double healthUp);


}


interface Ruin {
    //  boolean ruined=false
    void ruin();
}


interface Improve {
    // TODO
}