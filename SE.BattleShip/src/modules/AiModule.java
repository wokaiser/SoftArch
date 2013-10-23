package modules;

import util.other.AI_Hard;
import interfaces.IAi;

import com.google.inject.AbstractModule;

public class AiModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(IAi.class).to(AI_Hard.class);		
	}
}
