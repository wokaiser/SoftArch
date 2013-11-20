package highscore;

import interfaces.IHighscoreEntry;

public class HtwgHighscoreEntry implements IHighscoreEntry {
    private final String player;
    private final long score;

    /**
     * Create a HighscoreEntry object.
     * @param name of the player which own this highscore
     * @param score of the player
     */
    public HtwgHighscoreEntry(String player, long score) {
        this.player = player;
        this.score = score;
    }
    
    public String getPlayer() {
        return player;
    }

    public long getScore() {
        return score;
    }

}
