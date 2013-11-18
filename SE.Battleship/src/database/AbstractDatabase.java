package database;

import util.Status;
import interfaces.IDatabase;

public abstract class AbstractDatabase implements IDatabase {
    protected static final String SAVEGAME_NAME_EXIST = "Savegame already taken, choose another one!";
    protected static final String SAVEGAME_NOT_EXIST = "Savegame does no longer exist.";
    private Status status;
    
    protected AbstractDatabase() {
        status = new Status();
    }
    
    @Override
    public Status getStatus() {
        return status;
    }
}
