package util.other;

import java.util.List;

import model.playground.Coordinates;
import model.playground.Playground;

public abstract class AI_Base {
	
	protected int rows, columns;
	protected List<Coordinates> range;
	protected Playground playgroundAI;
	protected Coordinates coords;

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
	 * Set the coordinates of this Object
	 * @param The value to be set
	 */
	protected void setCoords(Coordinates input) {
		this.coords.setRow(input.getRow());
		this.coords.setColumn(input.getColumn());
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
