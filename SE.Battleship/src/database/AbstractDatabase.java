package database;

import model.general.Status;
import interfaces.IDatabase;

public abstract class AbstractDatabase implements IDatabase {
    protected static final String SAVEGAME_NAME_EXIST = "Savegame already taken, choose another one!";
    protected static final String SAVEGAME_NOT_EXIST = "Savegame does no longer exist.";
    protected Status status;
    
    protected AbstractDatabase() {
        status = new Status();
    }
    
    @Override
    public Status getSatus() {
        return status;
    }
}
