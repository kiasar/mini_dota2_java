package dota.common;

import java.io.Serializable;

public class Cell implements Serializable {
	private int row;
	private int column;
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getColumn() {
		return column;
	}
	public void setColumn(int column) {
		this.column = column;
	}

	public Cell(int column, int row) {
		this.column = column;
		this.row = row;
	}
	public Cell() {
	}


}
