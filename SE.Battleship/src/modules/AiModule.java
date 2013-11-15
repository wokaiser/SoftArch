package modules;

import util.other.*;
import interfaces.IAi;
import com.google.inject.AbstractModule;

public class AiModule extends AbstractModule {
    private String usedAI = null;
    
    /**
     * Set the game settings
     * @param settings The settings
     */
    public AiModule setSettings(String usedAI) {
        this.usedAI = usedAI;
        return this;
    }

    /**
     * Override of the configure method, which bind a concrete AI class.
     */
    @Override
    protected void configure() {
        if (0 == usedAI.compareTo("Computer 1 Weak")) {
            bind(IAi.class).to(AiEasy.class);
            return;
        }
        if (0 == usedAI.compareTo("Computer 2 Weak")) {
            bind(IAi.class).to(AiEasy.class);
            return;
        }

        bind(IAi.class).to(AiEasy.class);
    }
}
