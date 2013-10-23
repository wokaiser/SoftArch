package util.other;

import interfaces.IAi;

import java.util.LinkedList;
import java.util.List;

import model.general.Constances;
import model.playground.Coordinates;
import model.playground.Playground;

/** 
 * The Class AI guesses the next Shot.
 * @author Wolfgang Kaiser
 */

public class AI_Hard extends AI_Base implements IAi {
	
	private Coordinates firstHit;
	private boolean wasHit, wasHitFirst;
	private boolean shipDestroyed;
	private List<Coordinates> ship;
	private enum Direction {None, North, East, South, West}
	private Direction direct = Direction.None;
	
	private static final int MIN_DIRECT = 1;
	private static final int TWO = 2;
	private static final int THREE = 3;
	private static final int MAX_DIRECT = 4;
	private static final int ABORT_AMOUNT = 50;
	
	/**
	 * Sets the range of the field which the AI needs to know
	 * @param r The maximum number of row to use.
	 * @param c The maximum number of column to use.
	 */
	public void initialize(int r, int c) {
		if (r <= 0 || c <= 0) {
			throw new IllegalArgumentException("Only positive arguments allowed!");
		}
		this.rows = r;
		this.columns = c;
		this.playgroundAI = new Playground(r, c);
		this.coords = new Coordinates(r, c);
		this.firstHit = new Coordinates(r, c);
		this.wasHit = false;
		this.wasHitFirst = true;
		this.ship = new LinkedList<Coordinates>();
		this.range = new LinkedList<Coordinates>();
		fillRange();
	}
	/**
	 * The AI Shoots
	 * @return Coordinates to which the AI shoots
	 */
	public Coordinates shoot() {
		Coordinates target = new Coordinates(this.rows, this.columns);
		target = aIguess();
		this.playgroundAI.shoot(target);
		removeFromRange(target);
		return target;
	}
	/**
	 * set the flags corresponding to the shootStatus
	 * @param shootStatus The status of the shot
	 * @return true if nothing was hit
	 */
	public boolean setFlags(int shootStatus) {
		if (shootStatus == Constances.SHOOT_HIT) {
			this.wasHit = true;
			return false;
		} else if (shootStatus == Constances.SHOOT_DESTROYED) {
			this.shipDestroyed = true;
			return false;
		} else {
			this.wasHit = false;
			return true;
		}		
	}
	/**
	 * AI guesses next shot
	 * @param wasHit Value if last shot was successful
	 * @return Coordinates to shot to
	 */
	private Coordinates aIguess() {
		Coordinates result = new Coordinates(rows, columns);
		/* mark the last shot on the own playground */		
		ifDestroyed();
		ifWasHitFirst();
		if (!this.wasHit && direct == Direction.None)
		{
			while (true) {
				Coordinates tmp = new Coordinates(rows, columns);
				tmp = randomCoordinates();
				result.setRow(tmp.getRow());
				result.setColumn(tmp.getColumn());
				if (!playgroundAI.alreadyShot(result)) {
					setCoords(result);
					return result;
				}
			}
		} else {
			return chooseDirection();
		}
	}
	/**
	 * Handle AI when a ship was destroyed
	 */
	private void ifDestroyed() {
		Coordinates tmp = new Coordinates(rows, columns);
		if (shipDestroyed) {
			tmp.setRow(this.coords.getRow());
			tmp.setColumn(this.coords.getColumn());
			this.ship.add(tmp);
			markAroundShip();
			this.direct = Direction.None;
			this.ship.clear();
			this.shipDestroyed = false;
			this.wasHit = false;
			this.wasHitFirst = true;
		}
	}
	/**
	 * Handle AI when ship was hit first
	 */
	private void ifWasHitFirst() {
		if (this.wasHit && wasHitFirst) {
			this.wasHitFirst = false;
			this.firstHit.setRow(this.coords.getRow());
			this.firstHit.setColumn(this.coords.getColumn());
		}
	}
	/**
	 * Chooses which direction to shoot next to
	 * @return
	 */
	private Coordinates chooseDirection() {
		Coordinates tmp = new Coordinates(rows, columns);
		Coordinates result = new Coordinates(rows, columns);
		tmp.setRow(this.coords.getRow());
		tmp.setColumn(this.coords.getColumn());
		if (wasHit) {
			ship.add(tmp);
		}
		switch (direct) {
		case None:
			result = aIguess(coords, Direction.None);
			setCoords(result);
			return result;
		case North:
			return chooseDirection(Direction.North, Direction.South, Direction.East, Direction.West);
		case East:
			return chooseDirection(Direction.East, Direction.West, Direction.North, Direction.South);
		case South:
			return chooseDirection(Direction.South, Direction.North, Direction.East, Direction.West);
		case West:
			return chooseDirection(Direction.West, Direction.East, Direction.South, Direction.North);
		default:
			throw new IllegalStateException();				
		}
	}
	/**
	 * Chooses which direction to shoot next depending on previous shots
	 * @param dA Direction
	 * @param dB Direction
	 * @param dC Direction
	 * @param dD Direction
	 * @return A new Direction
	 */
	private Coordinates chooseDirection(Direction dA, Direction dB, Direction dC, Direction dD) {
		if (wasHit)
		{
			coords = aIguess(coords, dA);
		} else {
			coords = aIguess(firstHit, dB);
			if (playgroundAI.alreadyShot(coords)) {
				coords = aIguess(firstHit, dC);
				if (playgroundAI.alreadyShot(coords)) {
					coords = aIguess(firstHit, dD);
				}
			}
		}
		return coords;
	}
	/**
	 * Ai guesses next shot
	 * @param c Coordinates of the last shot
	 * @param d Direction to shot to
	 * @return Coordinates to shot to
	 */
	private Coordinates aIguess(Coordinates c, Direction d) {
		int x, y;
		Coordinates tmp = new Coordinates(this.rows, this.columns);
		tmp.setRow(c.getRow());
		tmp.setColumn(c.getColumn());
		y = c.getRow();
		x = c.getColumn();
		Direction dir = d; 
		int i = 0;
		while(true) {
			i++;
			if (i == ABORT_AMOUNT) {
				this.wasHit = false;
				this.wasHitFirst = true;
				this.direct = Direction.None;
				this.ship.clear();
				return aIguess();
			}
			switch (dir) {
			case None:
				dir = random();
				continue;
			case North:
				this.direct = Direction.North;
				tmp.setColumn(--x);
				if (playgroundAI.alreadyShot(tmp)) {
					tmp.setColumn(++x);
					dir = random();
					continue;
				}
				return tmp;
			case East:
				this.direct = Direction.East;
				tmp.setRow(++y);
				if (playgroundAI.alreadyShot(tmp)) {
					tmp.setRow(--y);
					dir = random();
					continue;
				}
				return tmp;
			case South:
				this.direct = Direction.South;
				tmp.setColumn(++x);
				if (playgroundAI.alreadyShot(tmp)) {
					tmp.setColumn(--x);
					dir = random();
					continue;
				}
				return tmp;
			case West:
				this.direct = Direction.West;
				tmp.setRow(--y);
				if (playgroundAI.alreadyShot(tmp)) {
					tmp.setRow(++y);
					dir = random();
					continue;
				}
				return tmp;
			default:
				throw new IllegalStateException();	
			}
		}
	}
	/**
	 * Generate a random Direction
	 * @return A random direction
	 */
	private Direction random() {
		switch (random(MIN_DIRECT, MAX_DIRECT)) {
		case MIN_DIRECT:
			return Direction.North;
		case TWO:
			return Direction.East;
		case THREE:
			return Direction.South;
		case MAX_DIRECT:
			return Direction.West;
		default:
			throw new IllegalStateException();
		}
	}
	/**
	 * Mark all places surrounding a sunken ship
	 */
	private void markAroundShip() {
		Coordinates tmp = new Coordinates(rows, columns);
		int length = ship.size();
		for (int i = 0; i < length; i++) {
			markAroundShip(1, 0, i, tmp);
			markAroundShip(0, 1, i, tmp);
			markAroundShip(-1, 0, i, tmp);
			markAroundShip(0, -1, i, tmp);
			markAroundShip(1, 1, i, tmp);
			markAroundShip(1, -1, i, tmp);
			markAroundShip(-1, 1, i, tmp);
			markAroundShip(-1, -1, i, tmp);
		}
	}
	/**
	 * Mark all surrounding fields around target field
	 * @param dR Delta value to Row
	 * @param dC Delta value to Column
	 * @param i Loop variable
	 * @param c Coordinate to mark around
	 */
	private void markAroundShip(int dR, int dC, int i, Coordinates c) {
		if((c.setRow(ship.get(i).getRow() + dR)) &&
			(c.setColumn(ship.get(i).getColumn() + dC)) &&
			(!playgroundAI.alreadyShot(c))) {
			playgroundAI.shoot(c);
			removeFromRange(c);
		}
	}
}
