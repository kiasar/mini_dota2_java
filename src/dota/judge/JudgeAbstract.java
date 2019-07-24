package dota.judge;

import java.util.ArrayList;
import java.util.HashMap;

import dota.common.Cell;
import dota.common.GameObjectID;
import dota.common.exceptions.DotaExceptionBase;

public abstract class JudgeAbstract {
	// Tower types
	public static final int TOWER_FIRE = 0;
	public static final int TOWER_STONE = 1;
	public static final int TOWER_BLACK = 2;
	public static final int TOWER_POISON = 3;

	// Building types
	public static final int BUILDING_TYPE_ANCIENT = 6;
	public static final int BUILDING_TYPE_BARRAKS = 7;

	// Attacker types
	public static final int ATTACKER_INFANTRY = 8;
	public static final int ATTACKER_TANK = 9;

	// Hero types
	public static final int HERO_TINY = 10;
	public static final int HERO_VENOMANCER = 11;

	// Team IDs
	public static final int TEAM_SENTINEL = 0;
	public static final int TEAM_SCOURGE = 1;

	// Powerup types
	public static final int POWERUP_TOWER_POWER = 0;
	public static final int POWERUP_TOWER_RANGE = 1;
	public static final int POWERUP_ATTACKER_POWER = 2;
	public static final int POWERUP_ATTACKER_HEALTH = 3;

	// info fields
	public static final String HEALTH = "health";
	public static final String RANGE = "range";
	public static final String RELOAD_TIME = "time";
	public static final String ATTACK = "attack";
	public static final String SPEED = "speed";
	public static final String ROW = "row";
	public static final String COLOUMN = "col";
	public static final String VALUE = "value";
	public static final String TEAM_ID = "id";
	public static final String IS_ALIVE = "alive";
	public static final String TANK_ATTACK = "TA";
	public static final String INFANTRY_ATTACK = "IA";
	
	//Directions
	public static final int DIRECTION_UP = 0;
	public static final int DIRECTION_RIGHT = 1;
	public static final int DIRECTION_DOWN = 2;
	public static final int DIRECTION_LEFT = 3;

	//Power Up Price
	public static final int PRICE_POWERUP_TOWER_POWER_percentageOfValue = 15;
	public static final int PRICE_POWERUP_TOWER_RANGE_percentageOfValue = 20;
	public static final int PRICE_POWERUP_ATTACKER_POWER_InNumberOfPowerUps = 1000;
	public static final int PRICE_POWERUP_ATTACKER_HEALTH_InNumberOfPowerUps = 500;

	// Map functions

	public abstract void loadMap(int columns, int rows,
			ArrayList<ArrayList<Cell>> path1, ArrayList<ArrayList<Cell>> path2,
			ArrayList<ArrayList<Cell>> path3, Cell[][] ancient1,
			Cell[][] ancient2, ArrayList<Cell[][]> barraks1,
			ArrayList<Cell[][]> barraks2, ArrayList<Cell> goldMines);

	public abstract int getMapWidth();

	public abstract int getMapHeight();

	
	public abstract GameObjectID getGoldMineID(int goldMineNumber)
			throws DotaExceptionBase;

	public abstract GameObjectID[] getBuildingID(int teamID, int buildingType)
			throws DotaExceptionBase;

	public abstract GameObjectID getPathID(int pathNumber);

	public abstract GameObjectID[] getLaneID(int pathNumber);

	public abstract GameObjectID getHeroID(int teamID, int heroID);

	public abstract void setup();

	// Creators
	public abstract GameObjectID createAttacker(int teamID, int attackerType,
			GameObjectID path, GameObjectID lane, int rowNumber, int colNumber)
			throws DotaExceptionBase;

	public abstract GameObjectID createTower(int teamID, int towerType,
			GameObjectID path, GameObjectID lane, int index, int rowNumber,
			int colNumber) throws DotaExceptionBase;

	// Powerups
	public abstract void purchaseAttackersPowerup(int teamID, int powerupType)
			throws DotaExceptionBase;

	public abstract void purchaseTowerPowerup(int teamID, GameObjectID towerID,
			int powerupType) throws DotaExceptionBase;

	// Heros
	public abstract GameObjectID heroMove(GameObjectID hero, Cell dest,int direction)
			throws DotaExceptionBase;

	public abstract GameObjectID heroAttack(GameObjectID hero, Cell target)
			throws DotaExceptionBase;

	// Info
	public abstract int getMoney(int teamID);

	public abstract ArrayList<Integer> getAttackerPowerups(int teamID);

	public abstract ArrayList<GameObjectID> getTeamGoldMines(int teamID)
			throws DotaExceptionBase;

	public abstract HashMap<String, Integer> getInfo(GameObjectID id)
			throws DotaExceptionBase;

	public abstract GameObjectID[] getInRange(GameObjectID id)
			throws DotaExceptionBase;

	public abstract GameObjectID getTarget(GameObjectID id)
			throws DotaExceptionBase;

	// Controller
	public abstract void next50milis();

	public abstract void startTimer();

	public abstract void pauseTimer();

	public abstract float getTime();

	// Judge cheat functions
	public abstract void setMoney(int teamID, int amount);

	public abstract void updateInfo(GameObjectID id, String infoKey,
			Integer infoValue) throws DotaExceptionBase;

	public abstract void updateInfo(GameObjectID id,
			HashMap<String, Integer> newInfo) throws DotaExceptionBase;
}
