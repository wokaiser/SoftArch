package modules;

import util.other.*;
import interfaces.IAi;
import interfaces.IDatabase;

import com.google.inject.AbstractModule;

import database.Db4oDatabase;

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
		bind(IDatabase.class).to(Db4oDatabase.class);
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
