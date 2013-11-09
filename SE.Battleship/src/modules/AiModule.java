package modules;

import util.other.*;
import interfaces.IAi;
import com.google.inject.AbstractModule;

public class AiModule extends AbstractModule {
	String AI = null;
	
	/**
	 * Set the game settings
	 * @param settings The settings
	 */
	public AiModule setSettings(String AI) {
		this.AI = AI;
		return this;
	}

	/**
	 * Override of the configure method, which bind a concrete AI class.
	 */
	@Override
	protected void configure() {
		if (0 == AI.compareTo("Computer 1 Weak")) {
			bind(IAi.class).to(AI_Weak.class);
			return;
		}
		if (0 == AI.compareTo("Computer 1 Hard")) {
			bind(IAi.class).to(AI_Weak.class);
			return;
		}

		if (0 == AI.compareTo("Computer 2 Weak")) {
			bind(IAi.class).to(AI_Weak.class);
			return;
		}
		if (0 == AI.compareTo("Computer 2 Hard")) {
			bind(IAi.class).to(AI_Weak.class);
			return;
		}	
		bind(IAi.class).to(AI_Weak.class);
	}
}
