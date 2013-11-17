package modules;

import util.other.*;
import interfaces.IAi;

import com.google.inject.AbstractModule;

import controller.GameContent;

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
        if (0 == usedAI.compareTo(GameContent.AI_PLAYER_1_EASY)) {
            bind(IAi.class).to(AiEasy.class);
        } else if (0 == usedAI.compareTo(GameContent.AI_PLAYER_2_EASY)) {
            bind(IAi.class).to(AiEasy.class);
        } else if (0 == usedAI.compareTo(GameContent.AI_PLAYER_1_HARD)) {
            bind(IAi.class).to(AiEasy.class);
        } else if (0 == usedAI.compareTo(GameContent.AI_PLAYER_2_HARD)) {
            bind(IAi.class).to(AiEasy.class);
        }
    }
}
