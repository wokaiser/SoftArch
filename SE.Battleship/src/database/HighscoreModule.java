package database;

import highscore.DummyHighscore;
import interfaces.IHighscore;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public class HighscoreModule extends AbstractModule {
    /**
     * Override of the configure method, which bind a concrete Database class as a singleton.
     */
    @Override
    protected void configure() {
        bind(IHighscore.class).to(DummyHighscore.class).in(Singleton.class);
    }
}
