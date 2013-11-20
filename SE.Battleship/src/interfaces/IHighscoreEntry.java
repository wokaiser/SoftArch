package interfaces;

public interface IHighscoreEntry {
    /**
     * Return Player name
     * @return the name of the player.
     */
    String getPlayer();
    /**
     * Return the score
     * @return the score of the player.
     */
    long getScore();
}
