package modules;

import util.other.*;
import interfaces.IAi;

import com.google.inject.AbstractModule;

public class SettingsModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(IAi.class).to(AI_Hard.class);		
	}
}
