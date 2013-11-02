package view.game;

import interfaces.IObserver;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import controller.GameController;

/**
 * @author Wolfgang Kaiser
 */
public class Logger implements IObserver {
	
	private static final String FILENAME = "log.txt";
	
	private GameController controller;
	private int count;
	
	/**
	 * Logs the game movements to a log file
	 */
	public Logger(final GameController controller) {
		this.controller = controller;
		initLogger();
	}
	/**
	 * Start a new log of the game
	 */
	private void initLogger() {
		count = 0;
		File log = new File(FILENAME);
		if(log.exists()) {
			log.delete();
		}
		printMessage(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()));
	}
	/**
	 * Prints the messsage to the console
	 */
	private void printMessage(String message) {
		PrintWriter printWriter = null;
		try {
			printWriter = new PrintWriter(new BufferedWriter(new FileWriter(FILENAME, true)));
			printWriter.println(message);
		} catch (Exception exc) {

		} finally {
			if(printWriter != null) {
				printWriter.close();
			}
		}
	}	
	/**
	 * Implementation of update method to be observable.
	 */
	@Override
	public void update() {
		if(count > 0) {
			printMessage(String.format("%der Zug.", count));
		}
		printMessage("****************************************");
		printMessage("Own playground (" + controller.getActivePlayer() + ")");
		printMessage(controller.getOwnPlaygroundAsString());
		printMessage("Enemy playground (" + controller.getEnemyPlayer() + ")");
		printMessage(controller.getEnemyPlaygroundAsString());
		printMessage("****************************************");
		count++;
	}
}

