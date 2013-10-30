package modules;

import com.google.inject.AbstractModule;

import database.Db4oDatabase;
import database.IDatabase;

public class DatabaseModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(IDatabase.class).to(Db4oDatabase.class);		
	}

}
