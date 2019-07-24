package items;

/**
 * Created by ${User} on 12/15/2015.
 */
public interface Hero extends DotaItem , Control, GetExtension {

    /*
    * Direction direction
    * double shootPower
    * int speed
         * */

    void birth ();


    void setShootPower(double shootPower);
    double getShootPower();

    void setSpeed(int speed);
    int getSpeed();

}

interface Control {

    // Direction direction

    void setDirection ( Direction direction);


}


interface GetExtension {
// TODO
}


