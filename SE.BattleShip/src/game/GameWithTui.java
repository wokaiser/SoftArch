package game;

import interfaces.IObserver;

import java.util.*;

import model.general.Constances;
import model.playground.Coordinates;
import controller.GameController;

/** 
 * The GameWithTui class supports playing Battleships over a textual user interface.
 * @author Dennis Parlak
 */
public class GameWithTui implements IObserver {
	private static final Scanner INPUT = new Scanner(System.in);
	private GameController controller;
	private int gametype;
	private String player1;
	private String player2;
	
	/**
	 * Creates a GameWithTui Object
	 */
	public GameWithTui(final GameController controller) {
		this.controller = controller;
	}
	
	/**
	 * The loop should be called to keep the tui running, until the game finished.
	 */
	public void loop() {
		selectGameType();
		enterNames();
		controller.newController(Constances.DEFAULT_ROWS, Constances.DEFAULT_COLUMNS, player1, player2, gametype);
		update();
		while (!controller.gameFinished()) {
			shoot();
		}
		output();
	}
	
	/**
	 * Lets the user choose the game type
	 */
	private void selectGameType() {
		System.out.println("\nWelcome to BATTLESHIPS !!!\n\n");
		while (true) {
			try {
				System.out.println("What kind of game would you like to start?");
				System.out.println("(1) Player vs. Player");
				System.out.println("(2) Player vs. AI");
				gametype = INPUT.nextInt();
				if (GameController.MULTIPLAYER != gametype && GameController.SINGLEPLAYER != gametype) {
					throw new IllegalArgumentException();
				}
				break;
			} catch (InputMismatchException x) {
				System.err.println("Invalid input! Only use numbers!");
				INPUT.next();
			} catch (IllegalArgumentException x) {
				System.err.println("Invalid number entered! Only the given Options are allowed!");
			}
		}
	}	
	
	/**
	 * Read in the names of the players from stdin
	 */
	private void enterNames() {
		switch (gametype) {
		case GameController.SINGLEPLAYER:
			System.out.println("Please enter your name:");
			player1 = INPUT.next();
			player2 = GameController.AI_PLAYER_1;
			break;
		case GameController.MULTIPLAYER:
			System.out.println("Player 1, please enter your Name:");
			player1 = INPUT.next();
			System.out.println("Player 2, please enter your Name:");
			player2 = INPUT.next();
			break;
		}
	}	
	
	/**
	 * Actual player shoot to the enemy playground. If the actual player is a AI the
	 * coordinates to shoot will be generated by the AI, otherwise the coordinates
	 * will be readout from stdin.
	 */
	private void shoot() {
		Coordinates target = new Coordinates(controller.getRows(), controller.getColumns());

		if (!controller.isAI()) {
			target = readInCoordinates();
		}
		controller.shoot(target);
	}

	/**
	 * Read in coordinates from stdin for actual active user
	 */
	private Coordinates readInCoordinates() {
		Coordinates target = new Coordinates(controller.getRows(), controller.getColumns());
		int x, y;
		while (true) {
			try {
				System.out.println("Please input Coordinates:");
				x = INPUT.nextInt();
				y = INPUT.nextInt();
				if (!target.setColumn(x))
				{
					System.err.println("Invalid input! Only use numbers that are in the playfield!");
					continue;
				}
				if (!target.setRow(y))
				{
					System.err.println("Invalid input! Only use numbers that are in the playfield!");
					continue;
				}
				break;
			} catch (InputMismatchException z) {
				System.err.println("Invalid input! Only use numbers!");
				INPUT.next();
			}
		}
		return target;
	}
		
	/**
	 * Output information about the program status from the controller.
	 */
	public void output() {
		if (0 < controller.getStatus().getErrorCount()) {
			System.err.println(controller.getStatus().getError());	
		}

		if (0 < controller.getStatus().getTextCount()) {
			System.out.println(controller.getStatus().getText());	
		}
		controller.getStatus().clear();
	}
	
	/**
	 * Implementation of update method to be observable.
	 */
	@Override
	public void update() {
		output();
		System.out.println("****************************************");
		System.out.println("Own playground ("+controller.getActivePlayer()+")");
		System.out.println(controller.getOwnPlaygroundAsString());
		System.out.println("Enemy playground ("+controller.getEnemyPlayer()+")");
		System.out.println(controller.getEnemyPlaygroundAsString());
		System.out.println("****************************************");
	}
	
	/**
	 * main method.
	 */
	public static void main( String[] args ) {
		GameController controller = new GameController(Constances.DEFAULT_ROWS, Constances.DEFAULT_COLUMNS, "Player 1", GameController.AI_PLAYER_1, GameController.SINGLEPLAYER);
		GameWithTui tui = new GameWithTui(controller);
		controller.addObserver(tui);
		tui.loop();
	}
}

