package dota.judge;

import java.io.Serializable;
import java.util.ArrayList;

import dota.common.Cell;

public class MapReader implements Serializable {
	private int columns;
	private int rows;
	private ArrayList<ArrayList<Cell>> path1;
	private ArrayList<ArrayList<Cell>> path2;
	private ArrayList<ArrayList<Cell>> path3;
	private Cell[][] ancient1;
	private Cell[][] ancient2;
	private ArrayList<Cell[][]> barracks1;
	private ArrayList<Cell[][]> barracks2;
	private ArrayList<Cell> goldMines;

	public int getColumns() {
		return columns;
	}

	public void init1() {
		columns = 41;
		rows = 41;
		init1_path1();
		init1_path2();
		init1_path3();
		init1_ancient1();
		init1_ancient2();
		init1_barracks();
		init1_goldmines();
	}

	private void init1_path3() {
		ArrayList<Cell> lane1 = new ArrayList<>();
		ArrayList<Cell> lane2 = new ArrayList<>();
		ArrayList<Cell> lane3 = new ArrayList<>();
		ArrayList<Cell> lane4 = new ArrayList<>();
		ArrayList<Cell> lane5 = new ArrayList<>();
		for (int i = 9; i <= 36; i++) {
			Cell c1 = new Cell();
			c1.setRow(36);
			c1.setColumn(i);
			lane1.add(c1);
		}
		for (int i = 9; i <= 37; i++) {
			Cell c1 = new Cell();
			c1.setRow(37);
			c1.setColumn(i);
			lane2.add(c1);
		}
		for (int i = 9; i <= 38; i++) {
			Cell c1 = new Cell();
			c1.setRow(38);
			c1.setColumn(i);
			lane3.add(c1);
		}
		for (int i = 9; i <= 39; i++) {
			Cell c1 = new Cell();
			c1.setRow(39);
			c1.setColumn(i);
			lane4.add(c1);
		}
		for (int i = 9; i <= 40; i++) {
			Cell c1 = new Cell();
			c1.setRow(40);
			c1.setColumn(i);
			lane5.add(c1);
		}
		//
		for (int i = 35; i >= 11; i--) {
			Cell c1 = new Cell();
			c1.setRow(i);
			c1.setColumn(36);
			lane1.add(c1);
		}
		for (int i = 36; i >= 11; i--) {
			Cell c1 = new Cell();
			c1.setRow(i);
			c1.setColumn(37);
			lane2.add(c1);
		}
		for (int i = 37; i >= 11; i--) {
			Cell c1 = new Cell();
			c1.setRow(i);
			c1.setColumn(38);
			lane3.add(c1);
		}
		for (int i = 38; i >= 11; i--) {
			Cell c1 = new Cell();
			c1.setRow(i);
			c1.setColumn(39);
			lane4.add(c1);
		}
		for (int i = 39; i >= 11; i--) {
			Cell c1 = new Cell();
			c1.setRow(i);
			c1.setColumn(40);
			lane5.add(c1);
		}
		ArrayList<ArrayList<Cell>> path3Cells = new ArrayList<>();
		path3Cells.add(lane1);
		path3Cells.add(lane2);
		path3Cells.add(lane3);
		path3Cells.add(lane4);
		path3Cells.add(lane5);
		path3 = path3Cells;
	}

	private void init1_path2() {
		ArrayList<Cell> lane1 = new ArrayList<>();
		ArrayList<Cell> lane2 = new ArrayList<>();
		ArrayList<Cell> lane3 = new ArrayList<>();
		ArrayList<Cell> lane4 = new ArrayList<>();
		ArrayList<Cell> lane5 = new ArrayList<>();
		for (int i = 9; i <= 18; i++) {
			Cell c1 = new Cell();
			c1.setRow(30);
			c1.setColumn(i);
			lane1.add(c1);
		}
		for (int i = 9; i <= 19; i++) {
			Cell c1 = new Cell();
			c1.setRow(31);
			c1.setColumn(i);
			lane2.add(c1);
		}
		for (int i = 9; i <= 20; i++) {
			Cell c1 = new Cell();
			c1.setRow(32);
			c1.setColumn(i);
			lane3.add(c1);
		}
		for (int i = 9; i <= 21; i++) {
			Cell c1 = new Cell();
			c1.setRow(33);
			c1.setColumn(i);
			lane4.add(c1);
		}
		for (int i = 9; i <= 22; i++) {
			Cell c1 = new Cell();
			c1.setRow(34);
			c1.setColumn(i);
			lane5.add(c1);
		}
		//
		for (int i = 29; i >= 6; i--) {
			Cell c1 = new Cell();
			c1.setRow(i);
			c1.setColumn(18);
			lane1.add(c1);
		}
		for (int i = 30; i >= 7; i--) {
			Cell c1 = new Cell();
			c1.setRow(i);
			c1.setColumn(19);
			lane2.add(c1);
		}
		for (int i = 31; i >= 8; i--) {
			Cell c1 = new Cell();
			c1.setRow(i);
			c1.setColumn(20);
			lane3.add(c1);
		}
		for (int i = 32; i >= 9; i--) {
			Cell c1 = new Cell();
			c1.setRow(i);
			c1.setColumn(21);
			lane4.add(c1);
		}
		for (int i = 33; i >= 10; i--) {
			Cell c1 = new Cell();
			c1.setRow(i);
			c1.setColumn(22);
			lane5.add(c1);
		}
		//
		for (int i = 19; i <= 31; i++) {
			Cell c1 = new Cell();
			c1.setRow(6);
			c1.setColumn(i);
			lane1.add(c1);
		}
		for (int i = 20; i <= 31; i++) {
			Cell c1 = new Cell();
			c1.setRow(7);
			c1.setColumn(i);
			lane2.add(c1);
		}
		for (int i = 21; i <= 31; i++) {
			Cell c1 = new Cell();
			c1.setRow(8);
			c1.setColumn(i);
			lane3.add(c1);
		}
		for (int i = 22; i <= 31; i++) {
			Cell c1 = new Cell();
			c1.setRow(9);
			c1.setColumn(i);
			lane4.add(c1);
		}
		for (int i = 23; i <= 31; i++) {
			Cell c1 = new Cell();
			c1.setRow(10);
			c1.setColumn(i);
			lane5.add(c1);
		}
		ArrayList<ArrayList<Cell>> path2Cells = new ArrayList<>();
		path2Cells.add(lane1);
		path2Cells.add(lane2);
		path2Cells.add(lane3);
		path2Cells.add(lane4);
		path2Cells.add(lane5);
		path2 = path2Cells;
	}

	private void init1_goldmines() {
		Cell c1 = new Cell();
		Cell c2 = new Cell();
		Cell c3 = new Cell();
		Cell c4 = new Cell();
		ArrayList<Cell> golds = new ArrayList<>();
		c1.setRow(10);
		c1.setColumn(10);
		c2.setRow(21);
		c2.setColumn(10);
		c3.setRow(19);
		c3.setColumn(30);
		c4.setRow(30);
		c4.setColumn(30);
		golds.add(c1);
		golds.add(c2);
		golds.add(c3);
		golds.add(c4);
		goldMines = golds;
	}

	private void init1_barracks() {
		Cell[][] barracks11 = new Cell[3][5];
		Cell[][] barracks23 = new Cell[3][5];
		Cell[][] barracks12 = new Cell[5][3];
		Cell[][] barracks13 = new Cell[5][3];
		Cell[][] barracks21 = new Cell[5][3];
		Cell[][] barracks22 = new Cell[5][3];
		ArrayList<Cell[][]> barracksTeam1 = new ArrayList<>();
		ArrayList<Cell[][]> barracksTeam2 = new ArrayList<>();

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 5; j++) {
				Cell c1 = new Cell();
				Cell c2 = new Cell();
				c2.setRow(8 + i);
				c2.setColumn(36 + j);
				c1.setRow(30 + i);
				c1.setColumn(j);
				barracks11[i][j] = c1;
				barracks23[i][j] = c2;
			}
		}
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 3; j++) {
				Cell c12 = new Cell();
				Cell c13 = new Cell();
				Cell c21 = new Cell();
				Cell c22 = new Cell();
				c12.setRow(i + 30);
				c13.setRow(i + 36);
				c12.setColumn(6 + j);
				c13.setColumn(6 + j);
				c21.setRow(i);
				c22.setRow(6 + i);
				c21.setColumn(32 + j);
				c22.setColumn(32 + j);
				barracks12[i][j] = c12;
				barracks13[i][j] = c13;
				barracks21[i][j] = c21;
				barracks22[i][j] = c22;
			}
		}
		barracksTeam1.add(barracks11);
		barracksTeam1.add(barracks12);
		barracksTeam1.add(barracks13);
		barracksTeam2.add(barracks21);
		barracksTeam2.add(barracks22);
		barracksTeam2.add(barracks23);
		barracks1 = barracksTeam1;
		barracks2 = barracksTeam2;
	}

	private void init1_ancient1() {
		Cell[][] ancient1Cells = new Cell[5][5];
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				Cell c1 = new Cell();
				c1.setRow(33 + i);
				c1.setColumn(1 + j);
				ancient1Cells[i][j] = c1;
			}
		}
		ancient1 = ancient1Cells;
	}

	private void init1_ancient2() {
		Cell[][] ancient2Cells = new Cell[5][5];
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 5; j++) {
				Cell c1 = new Cell();
				c1.setRow(3 + i);
				c1.setColumn(35 + j);
				ancient2Cells[i][j] = c1;
			}
		}
		ancient2 = ancient2Cells;
	}

	private void init1_path1() {
		ArrayList<Cell> lane11 = new ArrayList<>();
		for (int i = 29; i >= 0; i--) {
			Cell c1 = new Cell();
			c1.setColumn(0);
			c1.setRow(i);
			lane11.add(c1);
		}
		for (int i = 1; i < 32; i++) {
			Cell c1 = new Cell();
			c1.setColumn(i);
			c1.setRow(0);
			lane11.add(c1);
		}
		ArrayList<Cell> lane12 = new ArrayList<>();
		for (int i = 29; i >= 1; i--) {
			Cell c1 = new Cell();
			c1.setColumn(1);
			c1.setRow(i);
			lane12.add(c1);
		}
		for (int i = 2; i < 32; i++) {
			Cell c1 = new Cell();
			c1.setColumn(i);
			c1.setRow(1);
			lane12.add(c1);
		}
		ArrayList<Cell> lane13 = new ArrayList<>();
		for (int i = 29; i >= 2; i--) {
			Cell c1 = new Cell();
			c1.setColumn(2);
			c1.setRow(i);
			lane13.add(c1);
		}
		for (int i = 3; i < 32; i++) {
			Cell c1 = new Cell();
			c1.setColumn(i);
			c1.setRow(2);
			lane13.add(c1);
		}
		ArrayList<Cell> lane14 = new ArrayList<>();
		for (int i = 29; i >= 3; i--) {
			Cell c1 = new Cell();
			c1.setColumn(3);
			c1.setRow(i);
			lane14.add(c1);
		}
		for (int i = 4; i < 32; i++) {
			Cell c1 = new Cell();
			c1.setColumn(i);
			c1.setRow(3);
			lane14.add(c1);
		}
		ArrayList<Cell> lane15 = new ArrayList<>();
		for (int i = 29; i >= 4; i--) {
			Cell c1 = new Cell();
			c1.setColumn(4);
			c1.setRow(i);
			lane15.add(c1);
		}
		for (int i = 5; i < 32; i++) {
			Cell c1 = new Cell();
			c1.setColumn(i);
			c1.setRow(4);
			lane15.add(c1);
		}
		ArrayList<ArrayList<Cell>> path11 = new ArrayList<>();
		path11.add(lane11);
		path11.add(lane12);
		path11.add(lane13);
		path11.add(lane14);
		path11.add(lane15);
		path1 = path11;
	}

	public void setColumns(int columns) {
		this.columns = columns;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public ArrayList<ArrayList<Cell>> getPath1() {
		return path1;
	}

	public void setPath1(ArrayList<ArrayList<Cell>> path1) {
		this.path1 = path1;
	}

	public ArrayList<ArrayList<Cell>> getPath3() {
		return path3;
	}

	public void setPath3(ArrayList<ArrayList<Cell>> path3) {
		this.path3 = path3;
	}

	public ArrayList<ArrayList<Cell>> getPath2() {
		return path2;
	}

	public void setPath2(ArrayList<ArrayList<Cell>> path2) {
		this.path2 = path2;
	}

	public Cell[][] getAncient1() {
		return ancient1;
	}

	public void setAncient1(Cell[][] ancient1) {
		this.ancient1 = ancient1;
	}

	public Cell[][] getAncient2() {
		return ancient2;
	}

	public void setAncient2(Cell[][] ancient2) {
		this.ancient2 = ancient2;
	}

	public ArrayList<Cell[][]> getBarracks1() {
		return barracks1;
	}

	public void setBarracks1(ArrayList<Cell[][]> barracks1) {
		this.barracks1 = barracks1;
	}

	public ArrayList<Cell[][]> getBarracks2() {
		return barracks2;
	}

	public void setBarracks2(ArrayList<Cell[][]> barracks2) {
		this.barracks2 = barracks2;
	}

	public ArrayList<Cell> getGoldMines() {
		return goldMines;
	}

	public void setGoldMines(ArrayList<Cell> goldMines) {
		this.goldMines = goldMines;
	}

	public void log() {
		System.out.println("rows: " + rows);
		System.out.println("cols: " + columns);
		System.out.println("path1:");
		for (int i = 0; i < 5; i++) {
			System.out.println("\tlane" + i);
			System.out.print("\t\t");
			for (Cell cell : path1.get(i)) {
				System.out.print("(" + cell.getRow() + "," + cell.getColumn()
						+ ") ");
			}
			System.out.println();
		}
		System.out.println("path2:");
		for (int i = 0; i < 5; i++) {
			System.out.println("\tlane" + i);
			System.out.print("\t\t");
			for (Cell cell : path2.get(i)) {
				System.out.print("(" + cell.getRow() + "," + cell.getColumn()
						+ ") ");
			}
			System.out.println();
		}
		System.out.println("path3:");
		for (int i = 0; i < 5; i++) {
			System.out.println("\tlane" + i);
			System.out.print("\t\t");
			for (Cell cell : path3.get(i)) {
				System.out.print("(" + cell.getRow() + "," + cell.getColumn()
						+ ") ");
			}
			System.out.println();
		}
	}
}
