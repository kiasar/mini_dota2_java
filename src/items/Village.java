package items;

/**
 * Created by ${User} on 12/17/2015.
 */
public enum Village {
    SENTINEL , SCOURGE;

    public static Village getOpponent(Village teamID){
        if(teamID==null)return null;
        return (teamID==SENTINEL?SCOURGE:SENTINEL);
    }
}
