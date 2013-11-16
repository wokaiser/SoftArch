package modules;

import interfaces.IDatabase;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

import database.DummyDatabase;

public class DatabaseModule extends AbstractModule {
    /**
     * Override of the configure method, which bind a concrete Database class as a singleton.
     */
    @Override
    protected void configure() {
        bind(IDatabase.class).to(DummyDatabase.class).in(Singleton.class);
    }

}
