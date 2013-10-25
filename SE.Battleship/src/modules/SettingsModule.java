package modules;

import util.other.*;
import interfaces.IAi;

import com.google.inject.AbstractModule;

public class SettingsModule extends AbstractModule {
	
	public enum Settings { Easy, Hard };
	
	private Settings settings = Settings.Easy;
	
	/**
	 * Set the game settings
	 * @param settings The settings
	 */
	public void setSettings(Settings settings) {
		this.settings = settings;
	}

	@Override
	protected void configure() {
		switch(settings) {
		case Easy:
			bind(IAi.class).to(AI_Weak.class);
			break;
		case Hard:
			bind(IAi.class).to(AI_Hard.class);
			break;
		default:
			throw new IllegalArgumentException();
		}				
	}
}
