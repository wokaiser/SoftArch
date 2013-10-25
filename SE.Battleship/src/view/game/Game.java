package view.game;

import model.general.Constances;
import modules.SettingsModule;

import com.google.inject.Guice;
import com.google.inject.Injector;

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
		SettingsModule settings = new SettingsModule();
		settings.setSettings(SettingsModule.Settings.Easy);
		Injector inject = Guice.createInjector(settings);
		GameController controller = inject.getInstance(GameController.class);
		controller.initController(Constances.DEFAULT_ROWS, Constances.DEFAULT_COLUMNS, "Human", GameController.AI_PLAYER_1, GameController.SINGLEPLAYER);
		GameWithGui gui = new GameWithGui(controller);
		Logger logger = new Logger(controller);
		controller.addObserver(gui);
		controller.addObserver(logger);
	}
}
