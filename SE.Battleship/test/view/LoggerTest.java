package view;

import static org.junit.Assert.*;
import model.general.Constances;
import modules.SettingsModule;

import org.junit.*;

import view.game.Logger;

import com.google.inject.Guice;
import com.google.inject.Injector;

import controller.GameController;

public class LoggerTest {
	
	private GameController controller;
	private Logger logger;
	
	@Before
	public void setUp() {
		SettingsModule settings = new SettingsModule();
		settings.setSettings(SettingsModule.Settings.Easy);
		Injector inject = Guice.createInjector(settings);
		controller = inject.getInstance(GameController.class);
		controller.initController(Constances.DEFAULT_ROWS, Constances.DEFAULT_COLUMNS, "Human", GameController.AI_PLAYER_1, GameController.SINGLEPLAYER);
		logger = new Logger(controller);		
	}
	
	@Test
	public void testLogger() {
		try {
			new Logger(controller);
		} catch (Exception exc) {
			fail("Logger should not throw exception at constructor");
		}
	}

	@Test
	public void testupdate() {
		if(logger == null) {
			fail("Logger should not be null");
		}
		try {
			logger.update();
		} catch (Exception exc) {
			fail("Logger should not throw exception at this method");
		}
	}

}
