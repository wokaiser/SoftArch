package util.other;

import java.util.List;

import model.playground.Coordinates;
import model.playground.Playground;

public abstract class AI_Base {
	
	private int rows, columns;
	private List<Coordinates> range;
	private Playground playgroundAI;
	
	/**
	 * @return Returns the number of rows in the playfield
	 */
	protected int getRows() {
		return rows;
	}
	/**
	 * @return Returns the number of columns in the playfield
	 */
	protected int getColumns() {
		return columns;
	}
	/**
	 * Sets the number of rows in the playfield
	 */
	protected void setRows(int rows) {
		this.rows = rows;
	}
	/**
	 * Sets the number of columnds in the playfield
	 */
	protected void setColumns(int columns) {
		this.columns = columns;
	}
	/**
	 * Sets the range from which the ai chooses its random guess
	 */
	protected void setRange(List<Coordinates> range) {
		this.range = range;
	}
	/**
	 * @return Returns the playground the ai marks its hits and misses for guessing
	 */
	protected Playground getPlaygroundAI() {
		return playgroundAI;
	}
	/**
	 * Sets the playground where the ai marks its hits and misses for guessing
	 */
	protected void setPlaygroundAI(Playground playgroundAI) {
		this.playgroundAI = playgroundAI;
	}
	/**
	 * Chooses a random coordinate
	 * @return A coordinate
	 */
	protected Coordinates randomCoordinates() {
		Coordinates tmp = new Coordinates(rows, columns);
		int r = random(0, range.size() - 1);
		tmp.setRow(range.get(r).getRow());
		tmp.setColumn(range.get(r).getColumn());
		return tmp;
	}
	/**
	 * Generate Random numbers in a range (min, max)
	 * @param min Minimum number (inclusive)
	 * @param max Maximum number (inclusive)
	 * @return A random number
	 */
	protected int random(int min, int max) {
		int tmp = max;
		tmp++;
		return (int) (Math.random() * (tmp - min) + min); 
	}
	/**
	 * Fills the Range of all availible fields
	 */
	protected void fillRange() {
		for (int i = 0; i < this.rows; i++) {
			for (int j = 0; j < this.columns; j++) {
				Coordinates tmp = new Coordinates(this.rows, this.columns);
				tmp.setRow(i);
				tmp.setColumn(j);
				range.add(tmp);
			}
		}
	}
	/**
	 * Remove a field from the range
	 * @param c The Coordinates to be removed
	 */
	protected void removeFromRange(Coordinates c) {
		for (int i = 0; i < range.size() - 1; i++) {
			if ((c.getRow() == range.get(i).getRow()) && (c.getColumn() == range.get(i).getColumn())) {
				range.remove(i);
			}
		}
	}
}
