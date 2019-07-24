package map;

import dota.GameEngine;
import eventQueue.*;
import buildings.Ancient;
import buildings.Barracks;
import buildings.TowerFactory;
import dota.judge.JudgeAbstract;
import extention.GoldMine;
import items.Direction;
import items.GameItem;
import items.Item;
import items.Village;
import warMachines.*;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by ${User} on 12/16/2015.
 */

public class Map {
    public static Location[][] mapLocations;
    public static ArrayList<Cell> cells = new ArrayList();
    public static ArrayList<EventQueue> eventQueue = new ArrayList<>();
    public static Cell<Ancient> sentinel;
    public static Cell<Ancient> scourge;
    public static Cell<HeTiny> tiny;
    public static Cell<HeVenomancer> venomancer;
    public static Cell<Barracks>[] sentinelBarracks = new Cell[3];
    public static Cell<Barracks>[] scourgeBarracks = new Cell[3];
    public static ArrayList<Cell<GoldMine>> goldMines = new ArrayList<>();
    public static ArrayList<Cell<Tower>> sentinelTowers = new ArrayList<>();
    public static ArrayList<Cell<Tower>> scourgeTowers = new ArrayList<>();


    public static PathLane[] pathANDlanes = new PathLane[18];


    public static void next50milis() {
        for (int i = 0; i < eventQueue.size(); i++) {
            if (!eventQueue.get(i).cell.item.isAlive()) {
                eventQueue.get(i).cell.ruin();
                if (!(eventQueue.get(i) instanceof HeroBuildQueue))
                    eventQueue.remove(i--);
            }
        }
        for (int i = eventQueue.size() - 1; i >= 0; i--) {
            eventQueue.get(i).pass50milis();
            eventQueue.get(i).pop(eventQueue);
        }
        for (Cell<Tower> towerCell : sentinelTowers) {
            boolean isActive = false;
            for (EventQueue queue : eventQueue) {
                if (queue.cell == towerCell) {
                    isActive = true;
                }
            }
            if (!isActive && towerCell.item.getEnemy(towerCell.see()) != null) {
                ArrayList<Item> items = towerCell.item.checkPriority(towerCell.item.getEnemy(towerCell.see()));
                eventQueue.add(new ShootQueue(towerCell, items, null));
            }
        }
        for (Cell<Tower> towerCell : scourgeTowers) {
            boolean isActive = false;
            for (EventQueue queue : eventQueue) {
                if (queue.cell == towerCell) {
                    isActive = true;
                }
            }
            if (!isActive && towerCell.item.getEnemy(towerCell.see()) != null) {
                eventQueue.add(new ShootQueue(towerCell, towerCell.item.checkPriority(towerCell.item.getEnemy(towerCell.see())), null));
            }
        }
    }

    public static Cell<Ancient> getAncient(Village teamID) {
        if (teamID == Village.SENTINEL) return sentinel;
        return scourge;
    }

    private Map(Point[][] ancient1, Point[][] ancient2, ArrayList<Point[][]> barracks1, ArrayList<Point[][]> barracks2, ArrayList<Point> goldMines) {
        sentinel = makeCell(ancient1, Ancient.makeSentinel(), 12);
        scourge = makeCell(ancient2, Ancient.makeScourge(), 12);
        eventQueue.add(new GoldAddQueue(sentinel, 1000, 10));
        eventQueue.add(new GoldAddQueue(scourge, 1000, 10));
        Point[][] points = new Point[1][1];
        points[0][0] = new Point(Map.sentinel.masterLocation.getX(), Map.sentinel.masterLocation.getY());
        tiny = makeCell(points, sentinel.item.hero, 0);
        points[0][0] = new Point(Map.scourge.masterLocation.getX(), Map.scourge.masterLocation.getY());
        venomancer = makeCell(points, scourge.item.hero, 0);

        int i = 0;
        for (Point[][] points1 : barracks1) {
            sentinelBarracks[i] = makeCell(points1, new Barracks(Village.SENTINEL), 0);
            sentinel.item.barracks[i] = sentinelBarracks[i].item;
            i++;
        }
        i = 0;
        for (Point[][] points1 : barracks2) {
            scourgeBarracks[i] = makeCell(points1, new Barracks(Village.SCOURGE), 0);
            scourge.item.barracks[i] = scourgeBarracks[i].item;
            i++;
        }
        for (Point point : goldMines) {
            Point[][] p = new Point[1][1];
            p[0][0] = point;
            this.goldMines.add(makeCell(p, new GoldMine(), 0));
        }
    }

    public static Cell makeCell(Point[][] points, Item item, int index) {
        ArrayList<Point> points1 = new ArrayList<Point>();
        int i = 0;
        for (Point[] points2 : points) {
            points1.addAll(Arrays.asList(points2));
            i += points2.length;
        }
        Cell cell = new Cell(points1, item, index);
        cells.add(cell);
        return cell;
    }

    public static Village decodeTeamID(int teamID) {
        if (teamID == JudgeAbstract.TEAM_SENTINEL) return Village.SENTINEL;
        else if (teamID == JudgeAbstract.TEAM_SCOURGE) return Village.SCOURGE;
        return null;
    }

    public static int codeTeamID(Village teamID) {
        if (teamID == Village.SENTINEL) return JudgeAbstract.TEAM_SENTINEL;
        return JudgeAbstract.TEAM_SCOURGE;
    }

    public static Cell ruinCellByItem(Item item) {
        for (Cell cell : cells) {
            if (cell.item.equals(item)) {
                cell.ruin();
                return cell;
            }
        }
        return null;
    }

    public static Map MakeMap(int rows, int colomns, ArrayList<ArrayList<Point>> path1, ArrayList<ArrayList<Point>> path2, ArrayList<ArrayList<Point>> path3, Point[][] ancien1, Point[][] ancient2, ArrayList<Point[][]> barracks1, ArrayList<Point[][]> barracks2, ArrayList<Point> goldMines) {

        mapLocations = new Location[rows][colomns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < colomns; j++) {
                mapLocations[i][j] = null;
            }
        }
        pathANDlanes[15] = new PathLane(0, -1);
        for (int i = 0; i < path1.size(); i++) {
            pathANDlanes[i] = new PathLane(0, i);
            pathANDlanes[i].first = new Point(path1.get(i).get(0).x, path1.get(i).get(0).y);
            pathANDlanes[i].last = new Point(path1.get(i).get(path1.get(i).size() - 1).x, path1.get(i).get(path1.get(i).size() - 1).y);
            for (int j = 0; j < path1.get(i).size(); j++) {
                mapLocations[path1.get(i).get(j).x][path1.get(i).get(j).y] = new Location(path1.get(i).get(j).x, path1.get(i).get(j).y, GroundType.makePath(0, i));
            }
        }
        pathANDlanes[16] = new PathLane(1, -1);
        for (int i = 0; i < path2.size(); i++) {
            pathANDlanes[5 + i] = new PathLane(1, i);
            pathANDlanes[5 + i].first = new Point(path2.get(i).get(0).x, path2.get(i).get(0).y);
            pathANDlanes[5 + i].last = new Point(path2.get(i).get(path2.get(i).size() - 1).x, path2.get(i).get(path2.get(i).size() - 1).y);
            for (int j = 0; j < path2.get(i).size(); j++) {
                mapLocations[path2.get(i).get(j).x][path2.get(i).get(j).y] = new Location(path2.get(i).get(j).x, path2.get(i).get(j).y, GroundType.makePath(1, i));
            }
        }
        pathANDlanes[17] = new PathLane(2, -1);
        for (int i = 0; i < path3.size(); i++) {
            pathANDlanes[10 + i] = new PathLane(2, i);
            pathANDlanes[10 + i].first = new Point(path3.get(i).get(0).x, path3.get(i).get(0).y);
            pathANDlanes[10 + i].last = new Point(path3.get(i).get(path3.get(i).size() - 1).x, path3.get(i).get(path3.get(i).size() - 1).y);
            for (int j = 0; j < path3.get(i).size(); j++) {
                mapLocations[path3.get(i).get(j).x][path3.get(i).get(j).y] = new Location(path3.get(i).get(j).x, path3.get(i).get(j).y, GroundType.makePath(2, i));
            }
        }
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < colomns; j++) {
                if (mapLocations[i][j] == null)
                    mapLocations[i][j] = new Location(i, j, GroundType.makeGrass());
            }
        }
        return new Map(ancien1, ancient2, barracks1, barracks2, goldMines);
    }

    static ArrayList<ArrayList<ArrayList<Item>>> see(LocationDealer locationDealer, int sight) {
        ArrayList<ArrayList<ArrayList<Item>>> ans = new ArrayList<>();
        for (int i = 0; i < sight + 1; i++) {
            ans.add(new ArrayList<>());
        }
        for (int i = 0; i < ans.size(); i++) {
            for (int j = 0; j < ((i == 0) ? 1 : 8 * i); j++) {
                ans.get(i).add(new ArrayList<>());
            }
        }
        int[] k = new int[sight + 1];
        for (int i = -sight; i <= sight; i++) {
            for (int j = -sight; j <= sight; j++) {
                k[Integer.max(Math.abs(i), Math.abs(j))]++;
                try {
                    for (int l = 0; l < mapLocations[locationDealer.getX() + i][locationDealer.getY() + j].getOwners().size(); l++) {
                        Item item = mapLocations[locationDealer.getX() + i][locationDealer.getY() + j].getOwners().get(l).item;
                        if (item.isAlive() && (item instanceof Warier || item instanceof Barracks || item instanceof Hero || item instanceof Ancient) && mapLocations[locationDealer.getX() + i][locationDealer.getY() + j].groundType.isSamePath(locationDealer.getLocation().groundType)) {
                            ans.get(Integer.max(Math.abs(i), Math.abs(j))).get(k[Integer.max(Math.abs(i), Math.abs(j))]).
                                    add(mapLocations[locationDealer.getX() + i][locationDealer.getY() + j].getOwners().get(l).item);
                        }
                    }
                } catch (IndexOutOfBoundsException ignored) {
                }
            }
        }
        return ans;
    }

    public static Cell createHammer(Barracks barracks, GameItem type, int x, int y) {
        Point[][] points = new Point[1][1];
        points[0][0] = new Point(x, y);
        Cell<Hammer> hammer = makeCell(points, barracks.build(type), 0);
        for (Direction direction : Direction.values()) {
            try {
                if (Map.mapLocations[x][y].groundType.isSameLane(Map.mapLocations[x + direction.getXvalue()][y + direction.getYvalue()].groundType)) {
                    eventQueue.add(new MoveQueue(hammer, hammer.item.getSpeed(), direction));
                    break;
                }
            } catch (ArrayIndexOutOfBoundsException ignored) {
            }
        }


        return hammer;
    }

    public static Cell createTower(TowerFactory towerFactory, GameItem type, int x, int y) {
        Point[][] points = new Point[1][1];
        points[0][0] = new Point(x, y);
        Cell<Tower> cell = makeCell(points, towerFactory.build(type), 0);
        ArrayList<Cell<Tower>> towers = (towerFactory.teamId == Village.SENTINEL ? sentinelTowers : scourgeTowers);
        towers.add(cell);
        return cell;
    }

    public static void heroMove(Village teamID, Direction direction) {
        Cell hero = (teamID == Village.SENTINEL) ? tiny : venomancer;
        boolean isMoving = false;
        for (EventQueue queue : eventQueue) {
            if ((queue instanceof MoveQueue || queue instanceof ShootQueue) && queue.cell == hero) {
                isMoving = true;
            }
        }
        if (!isMoving)
            eventQueue.add(new MoveQueue(hero, ((Hero) hero.item).getSpeed(), direction));
    }

    public static void heroAttack(Village teamID, Location location) {
        Cell hero = (teamID == Village.SENTINEL) ? tiny : venomancer;

        if (distance(new Point(hero.item.point.y, hero.item.point.x), new Point(location.getY(), location.getX())) <= ((Hero) hero.item).getSight()) {
            ArrayList<Item> items = new ArrayList<>();
            for (Cell<Item> itemCell : location.getOwners()) {
                if (itemCell.item.teamId != teamID) {
                    items.add(itemCell.item);
                }
            }

            eventQueue.add(new ShootQueue(hero, items, null));
        }
    }

    public static int distance(Point cell1, Point cell2) {
        int dx = cell1.x - cell2.x;
        int dy = cell1.y - cell2.y;
        if (dx < 0) dx = -dx;
        if (dy < 0) dy = -dy;
        return Integer.max(dx, dy);
    }
}
