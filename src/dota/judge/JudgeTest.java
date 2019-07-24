package dota.judge;

import dota.common.Cell;
import dota.common.GameObjectID;
import dota.common.exceptions.DotaExceptionBase;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.HashMap;

public class JudgeTest extends TestCase {

    @Test
    public void test1() throws DotaExceptionBase {
        // TESTING UNIT CREATION AND BEHAVIOR
        MapReader m1 = new MapReader();
        m1.init1();
        Judge j1 = new Judge();
        j1.loadMap(m1.getColumns(),
                m1.getRows(), m1.getPath1(),
                m1.getPath2(),
                m1.getPath3(), m1.getAncient1(), m1.getAncient2(),
                m1.getBarracks1(), m1.getBarracks2(), m1.getGoldMines());
        GameObjectID path1 = j1.getPathID(0);
        GameObjectID[] path1Lanes = j1.getLaneID(0);
        j1.setup();
        // Start game
        GameObjectID attacker = j1.createAttacker(0, Judge.ATTACKER_TANK,
                path1, path1Lanes[0], 29, 0);
        j1.getInfo(attacker);
        HashMap<String, Integer> values;
        for (int i = 0; i < 2500; i++) {
            j1.next50milis();
        }
        values = j1.getInfo(attacker);
        int row = values.get(Judge.ROW);
        int col = values.get(Judge.COLOUMN);
        int hp = values.get(Judge.HEALTH);
        assertEquals(0, row);
        assertEquals(31, col, 3);
        assertEquals(1000, hp);
    }

    @Test
    public void test2() throws DotaExceptionBase {
        // TESTING UNIT ON UNIT WAR
        MapReader m1 = new MapReader();
        m1.init1();
        Judge j1 = new Judge();
        j1.loadMap(m1.getColumns(), m1.getRows(), m1.getPath1(), m1.getPath2(),
                m1.getPath3(), m1.getAncient1(), m1.getAncient2(),
                m1.getBarracks1(), m1.getBarracks2(), m1.getGoldMines());
        GameObjectID path1 = j1.getPathID(0);
        GameObjectID[] path1Lanes = j1.getLaneID(0);
        j1.setup();
        // Start game
        GameObjectID attacker = j1.createAttacker(Judge.TEAM_SENTINEL,
                Judge.ATTACKER_TANK, path1, path1Lanes[0], 29, 0);

        GameObjectID attacker2 = j1.createAttacker(Judge.TEAM_SCOURGE,
                Judge.ATTACKER_INFANTRY, path1, path1Lanes[0], 0, 31);
        for (int i = 0; i < 5000; i++) {
            j1.next50milis();
        }


        HashMap<String, Integer> values = j1.getInfo(attacker);
        HashMap<String, Integer> values2 = j1.getInfo(attacker2);
        int row2 = values2.get(Judge.ROW);
        int row1 = values.get(Judge.ROW);
        int col1 = values.get(Judge.COLOUMN);
        int col2 = values2.get(Judge.COLOUMN);
        int alive1 = values.get(Judge.IS_ALIVE);
        int alive2 = values2.get(Judge.IS_ALIVE);

        assertEquals(0, row1);
        assertEquals(31, col1, 3);
        assertEquals(0, row2);
        assertEquals(4, col2);
        assertEquals(1, alive1);
        assertEquals(0, alive2);
    }

    @Test
    public void test3() throws DotaExceptionBase {
        // TESTING HERO MOVE
        MapReader m1 = new MapReader();
        m1.init1();
        Judge j1 = new Judge();
        j1.loadMap(m1.getColumns(), m1.getRows(), m1.getPath1(), m1.getPath2(),
                m1.getPath3(), m1.getAncient1(), m1.getAncient2(),
                m1.getBarracks1(), m1.getBarracks2(), m1.getGoldMines());
        GameObjectID hero = j1.getHeroID(Judge.TEAM_SENTINEL, Judge.HERO_TINY);
        j1.setup();
        // Start game
        dota.common.Cell nextCell = new Cell();
        nextCell.setRow(35);
        for (int i = 0; i < 7; i++) {
            nextCell.setColumn(4 + i);
            j1.heroMove(hero, nextCell, Judge.DIRECTION_RIGHT);
            for (int j = 0; j < 9; j++) {
                j1.next50milis();
            }
        }
        HashMap<String, Integer> values = j1.getInfo(hero);
        int row1 = values.get(Judge.ROW);
        int col1 = values.get(Judge.COLOUMN);
        int alive1 = values.get(Judge.IS_ALIVE);
        assertEquals(35, row1);
        assertEquals(10, col1);
        assertEquals(1, alive1);
    }

    @Test
    public void test4() throws DotaExceptionBase {
        // TESTING GOLD MINES AND MONEY
        MapReader m1 = new MapReader();
        m1.init1();
        Judge j1 = new Judge();
        j1.loadMap(m1.getColumns(), m1.getRows(), m1.getPath1(), m1.getPath2(),
                m1.getPath3(), m1.getAncient1(), m1.getAncient2(),
                m1.getBarracks1(), m1.getBarracks2(), m1.getGoldMines());
        GameObjectID hero = j1.getHeroID(Judge.TEAM_SENTINEL, Judge.HERO_TINY);
        j1.setup();
        // Start game
        int money = j1.getMoney(Judge.TEAM_SENTINEL);
        assertEquals(5000, money, 10);
        for (int i = 0; i < 2000; i++) {
            j1.next50milis();
        }
        money = j1.getMoney(Judge.TEAM_SENTINEL);
        assertEquals(6000, money, 20);


        dota.common.Cell nextCell = new Cell();
        nextCell.setRow(35);
        for (int i = 0; i < 7; i++) {
            nextCell.setColumn(4 + i);
            j1.heroMove(hero, nextCell, Judge.DIRECTION_RIGHT);
            for (int j = 0; j < 9; j++) {
                j1.next50milis();
            }
        }
        for (int i = 0; i < 14; i++) {
            nextCell.setRow(34 - i);
            j1.heroMove(hero, nextCell, Judge.DIRECTION_UP);
            for (int j = 0; j < 9; j++) {
                j1.next50milis();
            }
        }

        for (int i = 0; i < 2000; i++) {
            j1.next50milis();
        }
        money = j1.getMoney(Judge.TEAM_SENTINEL);
        assertEquals(17100, money, 1000);
    }

    @Test
    public void test5() throws DotaExceptionBase {
        // HERO DEATH AND TOWER ATTACKING
        MapReader m1 = new MapReader();
        m1.init1();
        Judge j1 = new Judge();
        j1.loadMap(m1.getColumns(), m1.getRows(), m1.getPath1(), m1.getPath2(),
                m1.getPath3(), m1.getAncient1(), m1.getAncient2(),
                m1.getBarracks1(), m1.getBarracks2(), m1.getGoldMines());
        GameObjectID hero = j1.getHeroID(Judge.TEAM_SENTINEL, Judge.HERO_TINY);
        GameObjectID path1 = j1.getPathID(0);
        GameObjectID[] path1Lanes = j1.getLaneID(0);
        j1.setup();
        // Start game
        j1.createTower(Judge.TEAM_SCOURGE, Judge.TOWER_BLACK, path1,
                path1Lanes[2], 45, 2, 20);
        dota.common.Cell nextCell = new Cell();
        nextCell.setColumn(3);
        for (int i = 0; i < 33; i++) {
            nextCell.setRow(34 - i);
            j1.heroMove(hero, nextCell, Judge.DIRECTION_UP);
            for (int j = 0; j < 9; j++) {
                j1.next50milis();
            }
        }
        for (int i = 0; i < 12; i++) {
            nextCell.setColumn(4 + i);
            j1.heroMove(hero, nextCell, Judge.DIRECTION_RIGHT);
            for (int j = 0; j < 9; j++) {
                j1.next50milis();
            }
        }
        for (int i = 0; i < 970; i++) {
            j1.next50milis();
        }

        HashMap<String, Integer> values = j1.getInfo(hero);
        int row1 = values.get(Judge.ROW);
        int col1 = values.get(Judge.COLOUMN);
        int alive1 = values.get(Judge.IS_ALIVE);
        int hp = values.get(Judge.HEALTH);
        assertEquals(3000, hp, 100);
        assertEquals(2, row1);
        assertEquals(15, col1);
        assertEquals(1, alive1);
        for (int i = 0; i < 1700; i++) {
            j1.next50milis();
        }
        values = j1.getInfo(hero);
        alive1 = values.get(Judge.IS_ALIVE);
        hp = values.get(Judge.HEALTH);

        assertEquals(0, alive1);
        assertEquals(0, hp);
        for (int i = 0; i < 1000; i++) {
            j1.next50milis();
        }
        values = j1.getInfo(hero);
        alive1 = values.get(Judge.IS_ALIVE);
        hp = values.get(Judge.HEALTH);
        assertEquals(1, alive1);
        assertEquals(5000, hp);
    }

}
