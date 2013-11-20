package interfaces;

import java.util.List;

public interface IHighscore {
    /**
     * Add a new highscore
     * @param the name of the player
     * @param the score of the player
     */
    void add(IHighscoreEntry entry);
    /**
     * Get a list with all highscores
     * @return all highscores
     */
    List<IHighscoreEntry> getAll();
}
