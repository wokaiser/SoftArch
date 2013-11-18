package database;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.ektorp.support.CouchDbDocument;
import org.ektorp.support.TypeDiscriminator;

/* hibernate Annotation */
@Entity
@Table(name = "gameContent")
public class PersistentGameContent extends CouchDbDocument implements Serializable {
    /**
     * @TypeDiscriminator is used to mark properties that makes this class's
     *                    documents unique in the database.
     */
    /* couchdb annotation */
    @TypeDiscriminator
    private static final long serialVersionUID = -1748058246038695189L;
 
    /* hibernate annotation */
    @Id
    @Column(name = "id")
    private String gameContentId;

    /* hibernate annotation */
    @OneToMany(mappedBy = "gameContent")
    @Column(name = "playground1")
    private List<PersistentPlaygroundItem> playground1;
    /* hibernate annotation */
    @OneToMany(mappedBy = "gameContent")
    @Column(name = "playground2")
    private List<PersistentPlaygroundItem> playground2;
    private int rows;
    private int columns;
    private int gameType;
    private String player1;
    private String player2;

    public PersistentGameContent() {
        
    }
    
    public String getId() {
        return gameContentId;
    }

    public void setId(String id) {
        this.gameContentId = id;
    }

    public List<PersistentPlaygroundItem> getPlayground1() {
        return playground1;
    }

    public void setPlayground1(List<PersistentPlaygroundItem> playground1) {
        this.playground1 = playground1;
    }

    public void setPlayground2(List<PersistentPlaygroundItem> playground2) {
        this.playground2 = playground2;    
    }

    public List<PersistentPlaygroundItem> getPlayground2() {
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
