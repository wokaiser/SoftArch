package view.game;

import model.general.Constances;
import modules.DatabaseModule;

import com.google.inject.Guice;
import com.google.inject.Injector;

import controller.GameContent;
import controller.GameController;

public final class Game {
	/**
	 * Made this a singleton to please Sonar :)
	 */
	private Game() { }
	/**
	 * Starts a new GUI game with an Console logger
	 * @param args
	 */
	public static void main(String[] args) {
		Injector inject = Guice.createInjector(new DatabaseModule());
		GameController controller = inject.getInstance(GameController.class);
		controller.newController(Constances.DEFAULT_ROWS, Constances.DEFAULT_COLUMNS, "Human", GameController.AI_PLAYER_1, GameContent.SINGLEPLAYER);
		GameWithGui gui = new GameWithGui(controller);
		Logger logger = new Logger(controller);
		controller.addObserver(gui);
		controller.addObserver(logger);
	}
}
