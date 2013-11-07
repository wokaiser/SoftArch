package modules;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

import database.Db4oDatabase;
import database.IDatabase;

public class DatabaseModule extends AbstractModule {
	/**
	 * Override of the configure method, which bind a concrete Database class as a singleton.
	 */
	@Override
	protected void configure() {
		bind(IDatabase.class).to(Db4oDatabase.class).in(Singleton.class);
	}

}
