package extention;

/**
 * Created by ${User} on 12/16/2015.
 */
public class GoldMine extends Extension {

    @Override
    protected void gift() {
        double goldGift = 100;
        ownerAncient.addGold((int)goldGift);
    }
}
