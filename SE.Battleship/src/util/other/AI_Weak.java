package util.other;

import java.util.LinkedList;

import model.general.Constances;
import model.playground.Coordinates;
import model.playground.Playground;
import interfaces.IAi;

public class AI_Weak extends AI_Base implements IAi {

	private Coordinates coords;
	
	/**
	 * Sets the range of the field which the AI needs to know
	 * @param r The maximum number of row to use.
	 * @param c The maximum number of column to use.
	 */
	public void initialize(int r, int c) {
		if (r <= 0 || c <= 0) {
			throw new IllegalArgumentException("Only positive arguments allowed!");
		}
		setRows(r);
		setColumns(c);
		setPlaygroundAI(new Playground(r, c));
		coords = new Coordinates(r, c);
		setRange(new LinkedList<Coordinates>());
		fillRange();
	}
	/**
	 * The AI Shoots
	 * @return Coordinates to which the AI shoots
	 */
	public Coordinates shoot() {
		Coordinates target = new Coordinates(getRows(), getColumns());
		target = aIguess();
		getPlaygroundAI().shoot(target);
		removeFromRange(target);
		return target;
	}
	/**
	 * set the flags corresponding to the shootStatus
	 * @param shootStatus The status of the shot
	 * @return true if nothing was hit
	 */
	public boolean setFlags(int shootStatus) {
		if (shootStatus == Constances.SHOOT_HIT || shootStatus == Constances.SHOOT_DESTROYED) {
			return false;
		}
		return true;
	}	
	/**
	 * AI guesses next shot
	 * @param wasHit Value if last shot was successful
	 * @return Coordinates to shot to
	 */
	private Coordinates aIguess() {
		Coordinates result = new Coordinates(getRows(), getColumns());
		while (true) {
			Coordinates tmp = new Coordinates(getRows(), getColumns());
			tmp = randomCoordinates();
			result.setRow(tmp.getRow());
			result.setColumn(tmp.getColumn());
			if (!getPlaygroundAI().alreadyShot(result)) {
				setCoords(result);
				return result;
			}
		}
	}
	/**
	 * Set the coordinates of this Object
	 * @param The value to be set
	 */
	private void setCoords(Coordinates input) {
		coords.setRow(input.getRow());
		coords.setColumn(input.getColumn());
	}
}
