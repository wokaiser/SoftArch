package database;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.ektorp.support.CouchDbDocument;
import org.ektorp.support.TypeDiscriminator;

/* hibernate Annotation */
@Entity
@Table(name = "playgroundItem")
public class PersistentPlaygroundItem extends CouchDbDocument implements Serializable  {
    private static final long serialVersionUID = -4563925819245689140L;

    /**
     * @TypeDiscriminator is used to mark properties that makes this class's
     *                    documents unique in the database.
     */
    /* couchdb annotation */
    @TypeDiscriminator
    private String couchdbId;
    /* hibernate Annotation */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int hibernateId;
    
    private int playground;
    private int rowcell;
    private int columncell;
    private char status;
    private char shipId;
    /* hibernate Annotation */
    @ManyToOne
    @JoinColumn(name = "gameContentid")
    private PersistentGameContent gameContent;
    
    /* couchdb constructor */
    public PersistentPlaygroundItem(int playground, int rowcell, int columncell, char status, char shipId) {
        this.playground = playground;
        this.rowcell = rowcell;
        this.columncell = columncell;
        this.status = status;
        this.shipId = shipId;
    }
    
    /* hibernate constructor */
    public PersistentPlaygroundItem(PersistentGameContent gameContent, int playground, int rowcell, int columncell, char status, char shipId) {
        this.gameContent = gameContent;
        this.playground = playground;
        this.rowcell = rowcell;
        this.columncell = columncell;
        this.status = status;
        this.shipId = shipId;
    }
    /* empty constructor is required for hibernate and couchdb internally. */
    public PersistentPlaygroundItem() {
        
    }
    public PersistentGameContent getGameContent() {
        return gameContent;
    }
    public void setGameContent(PersistentGameContent gameContent) {
        this.gameContent = gameContent;
    }
    public int getRowcell() {
        return rowcell;
    }
    public void setRowcell(int rowcell) {
        this.rowcell = rowcell;
    }
    public int getColumncell() {
        return columncell;
    }
    public void setColumncell(int columncell) {
        this.columncell = columncell;
    }
    public char getStatus() {
        return status;
    }
    public void setStatus(char status) {
        this.status = status;
    }
    public int getPlayground() {
        return playground;
    }
    public void setPlayground(int playground) {
        this.playground = playground;
    }
    public String getCouchdbId() {
        return couchdbId;
    }
    public void setCouchdbId(String id) {
        this.couchdbId = id;
    }
    public int getHibernateId() {
        return hibernateId;
    }
    public void setHibernateId(int id) {
        hibernateId = id;
    }
    public char getShipId() {
        return shipId;
    }
    public void setShipId(char shipId) {
        this.shipId = shipId;
    }    
}
