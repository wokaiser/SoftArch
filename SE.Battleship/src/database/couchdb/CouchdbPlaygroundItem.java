package database.couchdb;

import org.ektorp.support.CouchDbDocument;
import org.ektorp.support.TypeDiscriminator;


public class CouchdbPlaygroundItem extends CouchDbDocument {
    private static final long serialVersionUID = -4563925819245689140L;

    /**
     * @TypeDiscriminator is used to mark properties that makes this class's
     *                    documents unique in the database.
     */
    @TypeDiscriminator
    private String id;
    
    private int playground;
    private int rowcell;
    private int columncell;
    private char status;
    private char shipId;
    
    public CouchdbPlaygroundItem(int playground, int rowcell, int columncell, char status, char shipId) {
        this.playground = playground;
        this.rowcell = rowcell;
        this.columncell = columncell;
        this.status = status;
        this.shipId = shipId;
    }
    public CouchdbPlaygroundItem() {
        
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public char getShipId() {
        return shipId;
    }
    public void setShipId(char shipId) {
        this.shipId = shipId;
    }    
}
