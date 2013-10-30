package modules;

import interfaces.IDatabase;
import com.google.inject.AbstractModule;
import database.Db4oDatabase;

public class DatabaseModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(IDatabase.class).to(Db4oDatabase.class);		
	}

}
