package dota.judge;

import eventQueue.EventQueue;
import dota.common.Cell;
import dota.common.GameObjectID;
import dota.common.exceptions.DotaExceptionBase;
import eventQueue.ShootQueue;
import items.Direction;
import items.GameItem;
import items.Item;
import map.Map;
import map.PathLane;
import map.Point;
import warMachines.*;

import java.util.ArrayList;
import java.util.HashMap;

import static map.Map.*;

/**
 * Created by ali on 12/27/2015.
 */

public class Judge extends JudgeAbstract {
    static int time = 0;

    @Override
    public void loadMap(int columns, int rows, ArrayList<ArrayList<Cell>> path1, ArrayList<ArrayList<Cell>> path2, ArrayList<ArrayList<Cell>> path3, Cell[][] ancient1, Cell[][] ancient2, ArrayList<Cell[][]> barraks1, ArrayList<Cell[][]> barraks2, ArrayList<Cell> goldMines) {
        MakeMap(rows, columns, cell2point(path1), cell2point(path2), cell2point(path3), cell2point(ancient1), cell2point(ancient2), cells2point(barraks1), cells2point(barraks2), cellss2point(goldMines));
    }

    public void loadMap(MapReader m1) {
        loadMap(m1.getColumns(), m1.getRows(), m1.getPath1(), m1.getPath2(),
                m1.getPath3(), m1.getAncient1(), m1.getAncient2(),
                m1.getBarracks1(), m1.getBarracks2(), m1.getGoldMines());
    }

    ArrayList<ArrayList<Point>> cell2point(ArrayList<ArrayList<Cell>> cells) {
        ArrayList<ArrayList<Point>> ans = new ArrayList<>();
        for (ArrayList<Cell> arrayList : cells) {
            ans.add(new ArrayList<>());
            for (Cell cell : arrayList) {
                ans.get(ans.size() - 1).add(new Point(cell.getColumn(), cell.getRow()));
            }
        }
        return ans;
    }

    ArrayList<Point> cellss2point(ArrayList<Cell> points) {
        ArrayList<Point> ans = new ArrayList<>();
        for (Cell o : points) {
            ans.add(new Point(o.getColumn(), o.getRow()));
        }
        return ans;
    }

    Point[][] cell2point(Cell[][] cells) {
        Point[][] ans = new Point[cells.length][cells[0].length];
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[0].length; j++) {
                ans[i][j] = new Point(cells[i][j].getColumn(), cells[i][j].getRow());
            }
        }
        return ans;
    }

    ArrayList<Point[][]> cells2point(ArrayList<Cell[][]> cells) {
        ArrayList<Point[][]> ans = new ArrayList<>();
        for (Cell[][] cells1 : cells) {
            ans.add(cell2point(cells1));
        }
        return ans;
    }

    @Override
    public int getMapWidth() {
        return mapLocations.length;
    }

    @Override
    public int getMapHeight() {
        return mapLocations[0].length;
    }

    @Override
    public GameObjectID getGoldMineID(int goldMineNumber) throws DotaExceptionBase {
        if (goldMineNumber > goldMines.size()) throw new DotaExceptionBase();
        return goldMines.get(goldMineNumber - 1).item.id;
    }

    @Override
    public GameObjectID[] getBuildingID(int teamID, int buildingType) throws DotaExceptionBase {
        if (decodeTeamID(teamID) == null) throw new DotaExceptionBase();
        GameObjectID[] ans;
        switch (buildingType) {
            case JudgeAbstract.BUILDING_TYPE_ANCIENT:
                ans = new GameObjectID[1];
                ans[0] = getAncient(decodeTeamID(teamID)).item.id;
                return ans;
            case JudgeAbstract.BUILDING_TYPE_BARRAKS:
                ans = new GameObjectID[3];
                for (int i = 0; i < ans.length; i++) {
                    ans[i] = getAncient(decodeTeamID(teamID)).item.barracks[i].id;
                }
                return ans;
            default:
                throw new DotaExceptionBase();
        }
    }

    @Override
    public GameObjectID getPathID(int pathNumber) {
        return pathANDlanes[pathNumber + 15].id;
    }

    @Override
    public GameObjectID[] getLaneID(int pathNumber) {
        GameObjectID[] ans = new GameObjectID[5];
        for (int i = 0; i < 5; i++) {
            ans[i] = pathANDlanes[(pathNumber) * 5 + i].id;
        }
        return ans;
    }

    public GameObjectID getPath(int row,int col){
        return getPathID(mapLocations[col][row].groundType.getPath());
    }

    public GameObjectID getLane(int row,int col){
        return getLaneID(mapLocations[col][row].groundType.getPath())[mapLocations[col][row].groundType.getLane()];
    }

    @Override
    public GameObjectID getHeroID(int teamID, int heroID) {
        return getAncient(decodeTeamID(teamID)).item.hero.id;
    }

    public GameObjectID getHeroID(int teamID) {
        return getAncient(decodeTeamID(teamID)).item.hero.id;
    }

    @Override
    public void setup() {
    }

    @Override
    public GameObjectID  createAttacker(int teamID, int attackerType, GameObjectID path, GameObjectID lane, int rowNumber, int colNumber) throws DotaExceptionBase {
        if (decodeTeamID(teamID) == null || (attackerType != ATTACKER_INFANTRY && attackerType != ATTACKER_TANK) || !getAncient(decodeTeamID(teamID)).item.barracks[((PathLane) path.item).groundType.getPath()].isAlive() || getAncient(decodeTeamID(teamID)).item.treasure < ((attackerType == ATTACKER_INFANTRY) ? GameItem.H_INF_NORMAL : GameItem.H_TANK_NORMAL).getPrice())
            throw new DotaExceptionBase();
        return createHammer(getAncient(decodeTeamID(teamID)).item.barracks[((PathLane) path.item).groundType.getPath()], (attackerType == ATTACKER_INFANTRY) ? GameItem.H_INF_NORMAL : GameItem.H_TANK_NORMAL, colNumber, rowNumber).item.id;
    }

    @Override
    public GameObjectID createTower(int teamID, int towerType, GameObjectID path, GameObjectID lane, int index, int rowNumber, int colNumber) throws DotaExceptionBase {
        GameItem type = null;
        switch (towerType) {
            case TOWER_BLACK:
                type = GameItem.T_BLACK;
                break;
            case TOWER_FIRE:
                type = GameItem.T_FIRE;
                break;
            case TOWER_POISON:
                type = GameItem.T_POISON;
                break;
            case TOWER_STONE:
                type = GameItem.T_STONE;
                break;
            default:
                throw new DotaExceptionBase();
        }
        if (decodeTeamID(teamID) == null || getAncient(decodeTeamID(teamID)).item.treasure < type.getPrice())
            throw new DotaExceptionBase();

        return Map.createTower(getAncient(decodeTeamID(teamID)).item.towerFactory, type, colNumber, rowNumber).item.id;
    }

    @Override
    public void purchaseAttackersPowerup(int teamID, int powerupType) throws DotaExceptionBase {
        switch (powerupType) {
            case POWERUP_ATTACKER_HEALTH:
                if (getAncient(decodeTeamID(teamID)).item.treasure < PRICE_POWERUP_ATTACKER_HEALTH_InNumberOfPowerUps * getAttackerPowerups(teamID, powerupType).size())
                    throw new DotaExceptionBase();
                Hammer.bloodUP(decodeTeamID(teamID));
                break;
            case POWERUP_ATTACKER_POWER:
                if (getAncient(decodeTeamID(teamID)).item.treasure < PRICE_POWERUP_ATTACKER_POWER_InNumberOfPowerUps * getAttackerPowerups(teamID, powerupType).size())
                    throw new DotaExceptionBase();
                Hammer.powerUP(decodeTeamID(teamID));
                break;
        }
        getAncient(decodeTeamID(teamID)).item.powerups.add(powerupType);
    }

    @Override
    public void purchaseTowerPowerup(int teamID, GameObjectID towerID, int powerupType) throws DotaExceptionBase {
        switch (powerupType) {
            case POWERUP_TOWER_POWER:
                if (getAncient(towerID.item.teamId).item.treasure < ((double) PRICE_POWERUP_TOWER_POWER_percentageOfValue) / 100 * towerID.item.getValue())
                    throw new DotaExceptionBase();
                ((Tower) towerID.item).powerUP();
                break;
            case POWERUP_TOWER_RANGE:
                if (getAncient(towerID.item.teamId).item.treasure < ((double) PRICE_POWERUP_TOWER_RANGE_percentageOfValue) / 100 * towerID.item.getValue())
                    throw new DotaExceptionBase();
                ((Tower) towerID.item).seeUP();
                break;
        }
    }

    @Override
    public GameObjectID heroMove(GameObjectID hero, Cell dest, int direction) throws DotaExceptionBase {
        for (EventQueue queue : eventQueue) {
            if (queue.cell.item.equals(hero.item)) return null;
        }
        Direction direction1 = null;
        switch (direction) {
            case DIRECTION_DOWN:
                direction1 = Direction.DOWN;
                break;
            case DIRECTION_LEFT:
                direction1 = Direction.LEFT;
                break;
            case DIRECTION_RIGHT:
                direction1 = Direction.RIGHT;
                break;
            case DIRECTION_UP:
                direction1 = Direction.UP;
                break;
        }
        Map.heroMove(hero.item.teamId, direction1);
        return null;
    }

    @Override
    public GameObjectID heroAttack(GameObjectID hero, Cell target) throws DotaExceptionBase {
        for (EventQueue queue : eventQueue) {
            if (queue.cell.item.equals(hero.item)) return null;
        }
        Map.heroAttack(hero.item.teamId, mapLocations[target.getColumn()][target.getRow()]);
        return null;
    }

    @Override
    public int getMoney(int teamID) {
        return getAncient(decodeTeamID(teamID)).item.treasure;
    }

    @Override
    public ArrayList<Integer> getAttackerPowerups(int teamID) {
        return getAncient(decodeTeamID(teamID)).item.powerups;
    }

    public ArrayList<Integer> getAttackerPowerups(int teamID, int powerUpType) {
        ArrayList<Integer> ps = new ArrayList<>();
        for (int p : getAttackerPowerups(teamID)) {
            if (p == powerUpType) {
                ps.add(p);
            }
        }
        return ps;
    }

    @Override
    public ArrayList<GameObjectID> getTeamGoldMines(int teamID) throws DotaExceptionBase {
        ArrayList<GameObjectID> ans = new ArrayList<>();
        for (map.Cell cell : goldMines) {
            if (cell.item.teamId == decodeTeamID(teamID)) {
                ans.add(cell.item.id);
            }
        }
        return ans;
    }

    @Override
    public HashMap<String, Integer> getInfo(GameObjectID id) throws DotaExceptionBase {
        HashMap<String, Integer> info = new HashMap<>();
        if (id.item instanceof Hammer) {
            info.put(RANGE, ((Hammer) id.item).getSight());
            info.put(SPEED, ((Hammer) id.item).getSpeed());
            info.put(VALUE, (int) id.item.getValue());
            info.put(ATTACK, (int) ((Hammer) id.item).getShootPower());
            info.put(RELOAD_TIME, ((Hammer) id.item).getShootSpeed());
            info.put(ROW, id.item.point.y);
            info.put(COLOUMN, id.item.point.x);
        } else if (id.item instanceof Tower) {
            info.put(RANGE, ((Hammer) id.item).getSight());
            info.put(RELOAD_TIME, ((Tower) id.item).getShootSpeed());
            info.put(VALUE, (int) id.item.getValue());
            info.put(INFANTRY_ATTACK, (int) ((Tower) id.item).shootPowerInf);
            info.put(TANK_ATTACK, (int) ((Tower) id.item).shootPowerTank);
            info.put(ROW, id.item.point.y);
            info.put(COLOUMN, id.item.point.x);
        } else if (id.item instanceof Hero) {
            info.put(RANGE, ((Hero) id.item).getSight());
            info.put(RELOAD_TIME, ((Hero) id.item).getShootSpeed());
            info.put(SPEED, ((Hero) id.item).getSpeed());
            info.put(ATTACK, (int) ((Hero) id.item).getShootPower());
            info.put(ROW, id.item.point.y);
            info.put(COLOUMN, id.item.point.x);
        } else {
            throw new DotaExceptionBase();
        }
        info.put(TEAM_ID, codeTeamID(id.item.teamId));
        info.put(HEALTH, (int) id.item.getHealth());
        info.put(IS_ALIVE, (id.item.isAlive() ? 1 : 0));
        return info;
    }

    @Override
    public GameObjectID[] getInRange(GameObjectID id) throws DotaExceptionBase {
        if (!(id.item instanceof Warier)) {
            throw new DotaExceptionBase();
        }
        map.Cell cell = null;
        for (map.Cell cell1 : cells) {
            if (cell1.item.equals(id.item)) {
                cell = cell1;
                break;
            }
        }
        ArrayList<ArrayList<ArrayList<Item>>> itemss = ((PcWarier) id.item).getEnemy(cell.see());
        ArrayList<GameObjectID> items = new ArrayList<>();
        if (itemss == null) return (GameObjectID[]) items.toArray();
        for (int i = 0; i < itemss.size(); i++) {
            for (int j = 0; j < itemss.get(i).size(); j++) {
                for (int k = 0; k < itemss.get(i).get(j).size(); k++) {
                    items.add(itemss.get(i).get(j).get(k).id);
                }
            }
        }
        return (GameObjectID[]) items.toArray();
    }

    @Override
    public GameObjectID getTarget(GameObjectID id) throws DotaExceptionBase {
        if (!(id.item instanceof Warier)) {
            throw new DotaExceptionBase();
        }
        for (EventQueue queue : eventQueue) {
            if (queue instanceof ShootQueue) {
                if (queue.cell.item.id.equals(id)) {
                    return ((ShootQueue) queue).items.get(0).id;
                }
            }
        }
        return null;
    }

    @Override
    public void next50milis() {
        time += 50;
        Map.next50milis();
    }

    @Override
    public void startTimer() {
    }

    @Override
    public void pauseTimer() {
    }

    @Override
    public float getTime() {
        return time;
    }

    @Override
    public void setMoney(int teamID, int amount) {
        getAncient(decodeTeamID(teamID)).item.setGold(amount);
    }

    @Override
    public void updateInfo(GameObjectID id, String infoKey, Integer infoValue) throws DotaExceptionBase {
        HashMap<String, Integer> newInfo = new HashMap<>();
        newInfo.put(infoKey, infoValue);
        updateInfo(id, newInfo);
    }

    @Override
    public void updateInfo(GameObjectID id, HashMap<String, Integer> newInfo) throws DotaExceptionBase {
        //TODO takmil
        HashMap<String, Integer> info = getInfo(id);
        info.putAll(newInfo);
        if (info.size() > getInfo(id).size()) throw new DotaExceptionBase();
        for (String s : info.keySet()) {
            switch (s) {
                /*case TEAM_ID:

                    break;*/
                case HEALTH:
                    if (id.item.isAlive()) {
                        id.item.health = info.get(s);
                    } else {
                        throw new DotaExceptionBase();
                    }
                    break;
               /* case IS_ALIVE:
                        
                    break;*/
                case RANGE:
                    ((Warier) id.item).sight = info.get(s);
                    break;
                case SPEED:
                    try {
                        ((Hammer) id.item).speed = info.get(s);
                    } catch (ClassCastException e) {
                        ((Hero) id.item).speed = info.get(s);
                    }
                    break;
                /*case ROW:
                        
                    break;
                case COLOUMN:

                    break;*/
                case RELOAD_TIME:
                    ((Warier) id.item).shootSpeed = info.get(s);
                    break;
                case ATTACK:
                    try {
                        ((Hammer) id.item).shootSpeed = info.get(s);
                    } catch (ClassCastException e) {
                        ((Hero) id.item).shootSpeed = info.get(s);
                    }
                    break;
               /* case VALUE:

                    break;*/
                case INFANTRY_ATTACK:
                    ((Tower) id.item).shootPowerInf = info.get(s);
                    break;
                case TANK_ATTACK:
                    ((Tower) id.item).shootPowerInf = info.get(s);
                    break;
                default:
                    throw new DotaExceptionBase();
            }
        }

    }

}
