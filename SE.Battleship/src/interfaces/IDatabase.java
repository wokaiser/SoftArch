package interfaces;

import java.util.List;

import model.general.Status;
import controller.GameContent;

public interface IDatabase {
    /**
     * Loads the GameContent with the given name
     * @param name The name of the GameContent to be loaded
     * @return The desired GameContent
     */
    GameContent load(String name);
    
    /**
     * Gets all names of the stored GameContents
     * @return A list of Strings containing the stored GameContent names
     */
    List<String> getAll();
    
    /**
     * Saves a GameContent to the database
     * @param name The name under which the GameContent should be stored
     * @param content The GameGontent to be stored
     * @return True if saving was successfull
     */
    void save(String name, GameContent content);
    
    /**
     * Deletes the GameContent with the given name
     * @param name The name of the GameContent which should be deleted
     */
    void delete(String name);
   
    /**
     * Get the status object, which will be used for information/error logging
     * @return The status object
     */
    Status getSatus();
}
