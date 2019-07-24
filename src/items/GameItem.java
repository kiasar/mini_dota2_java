package items;

import warMachines.*;

import java.util.ArrayList;

public enum GameItem {
    T_FIRE(FacType.TOWER), T_STONE(FacType.TOWER), T_BLACK(FacType.TOWER), T_POISON (FacType.TOWER),
    H_INF_NORMAL(FacType.HAMMER), H_TANK_NORMAL(FacType.HAMMER) , HE_TINY(null) , HE_VENOMANCER(null) , BARRACKS(null) , ANCIENT(null) ;

    FacType facType;
    static GameItem[] getTowers(){

        ArrayList<GameItem> itemArrayList = new ArrayList<>();

        for (GameItem gameItem :GameItem.values()) {
            if (gameItem.facType.equals(FacType.TOWER)){
                itemArrayList.add(gameItem);
            }
        }

        return (GameItem[]) itemArrayList.toArray();
    }

    static GameItem[] getHammers(){

        ArrayList<GameItem> itemArrayList = new ArrayList<>();

        for (GameItem gameItem :GameItem.values()) {
            if (gameItem.facType.equals(FacType.HAMMER)){
                itemArrayList.add(gameItem);
            }
        }

        return (GameItem[]) itemArrayList.toArray();
    }

    public PcWarier getObject(Village village){
        switch (this){
            case T_FIRE:
                return new TFire(village);
            case T_STONE:
                return new TStone(village);
            case T_BLACK:
                return new TBlack(village);
            case T_POISON:
                return new TPoison(village);
            case H_INF_NORMAL:
                return new InfNormal(village);
            case H_TANK_NORMAL:
                return new TankNormal(village);
        }
        return null;
    }
    public double getPrice(){
        switch (this){
            case T_FIRE:
                return TFire.price;
            case T_STONE:
                return TStone.price;
            case T_BLACK:
                return TBlack.price;
            case T_POISON:
                return TPoison.price;
            case H_INF_NORMAL:
                return InfNormal.price;
            case H_TANK_NORMAL:
                return TankNormal.price;
        }
        return 0;
    }

    GameItem(FacType facType){
        this.facType = facType;
    }

}
