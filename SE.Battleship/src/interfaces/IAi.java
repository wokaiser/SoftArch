package interfaces;

import model.playground.Coordinates;

public interface IAi {
	
	/**
	 * Sets the range of the field which the AI needs to know
	 * @param r The maximum number of row to use.
	 * @param c The maximum number of column to use.
	 */
	public void initialize(int r, int c);
	/**
	 * The AI Shoots
	 * @return Coordinates to which the AI shoots
	 */
	public Coordinates shoot();
	/**
	 * set the flags corresponding to the shootStatus
	 * @param shootStatus The status of the shot
	 * @return true if nothing was hit
	 */
	public boolean setFlags(int shootStatus);
}
