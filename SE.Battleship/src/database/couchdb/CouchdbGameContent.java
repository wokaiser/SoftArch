package database.couchdb;

import org.ektorp.support.CouchDbDocument;
import org.ektorp.support.TypeDiscriminator;
import java.util.List;

public class CouchdbGameContent extends CouchDbDocument {
    
    private static final long serialVersionUID = 5513586160711602429L;

    /**
     * @TypeDiscriminator is used to mark properties that makes this class's
     *                    documents unique in the database.
     */
    @TypeDiscriminator
    private String gameContentId;

    private List<CouchdbPlaygroundItem> playground1;
    private List<CouchdbPlaygroundItem> playground2;
    private int rows;
    private int columns;
    private int gameType;
    private String player1;
    private String player2;

    public CouchdbGameContent() {
    }

    public String getId() {
        return gameContentId;
    }

    public void setId(String id) {
        this.gameContentId = id;
    }

    public List<CouchdbPlaygroundItem> getPlayground1() {
        return playground1;
    }

    public void setPlayground1(List<CouchdbPlaygroundItem> playground1) {
        this.playground1 = playground1;
    }

    public void setPlayground2(List<CouchdbPlaygroundItem> playground2) {
        this.playground2 = playground2;    
    }

    public List<CouchdbPlaygroundItem> getPlayground2() {
        return playground2;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(Integer columns) {
        this.columns = columns;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public int getGameType() {
        return gameType;
    }

    public void setGameType(int gameType) {
        this.gameType = gameType;
    }

    public String getPlayer1() {
        return player1;
    }

    public void setPlayer1(String player1) {
        this.player1 = player1;
    }

    public String getPlayer2() {
        return player2;
    }

    public void setPlayer2(String player2) {
        this.player2 = player2;
    }
}
