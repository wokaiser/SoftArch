package database;

import model.general.Constances;

import org.junit.Test;

import static org.junit.Assert.*;

public class PersistentPlaygroundItemTest {
	private final int PLAYGROUND = 1;
    private final int ROWCELL = 2;
    private final int COLUMNCELL = 3;
    private final char STATUS = Constances.MATRIX_INIT;
    private final char SHIP_ID = Constances.DEFAULT_SHIP_ID;
    private final int HIBERNATE_ID = 99;
    private final String COUCHDB_ID = "COUCHDB";
    
    @Test
	public void testSetterAndGetter() throws Exception {
        PersistentGameContent gameContent = new PersistentGameContent();
        PersistentPlaygroundItem cell = new PersistentPlaygroundItem();
        
        assertFalse(cell.getGameContent() == gameContent);
        assertFalse(cell.getPlayground() == PLAYGROUND);
        assertFalse(cell.getRowcell() == ROWCELL);
        assertFalse(cell.getColumncell() == COLUMNCELL);
        assertFalse(cell.getStatus() == STATUS);
        assertFalse(cell.getShipId() == SHIP_ID);
        assertFalse(cell.getHibernateId() == HIBERNATE_ID);
        assertFalse(cell.getCouchdbId() == COUCHDB_ID);
        
        cell.setGameContent(gameContent);
        cell.setPlayground(PLAYGROUND);
        cell.setRowcell(ROWCELL);
        cell.setColumncell(COLUMNCELL);
        cell.setStatus(STATUS);
        cell.setShipId(SHIP_ID);
        cell.setCouchdbId(COUCHDB_ID);
        cell.setHibernateId(HIBERNATE_ID);
        
        assertTrue(cell.getGameContent() == gameContent);
        assertTrue(cell.getPlayground() == PLAYGROUND);
        assertTrue(cell.getRowcell() == ROWCELL);
        assertTrue(cell.getColumncell() == COLUMNCELL);
        assertTrue(cell.getStatus() == STATUS);
        assertTrue(cell.getShipId() == SHIP_ID);
        assertTrue(cell.getHibernateId() == HIBERNATE_ID);
        assertTrue(cell.getCouchdbId() == COUCHDB_ID);
	}
    
    @Test 
    public void testConstructor() throws Exception {
        PersistentGameContent gameContent = new PersistentGameContent();
        PersistentPlaygroundItem cell = new PersistentPlaygroundItem(gameContent, PLAYGROUND, ROWCELL, COLUMNCELL, STATUS, SHIP_ID);
        
        assertTrue(cell.getGameContent() == gameContent);
        assertTrue(cell.getPlayground() == PLAYGROUND);
        assertTrue(cell.getRowcell() == ROWCELL);
        assertTrue(cell.getColumncell() == COLUMNCELL);
        assertTrue(cell.getStatus() == STATUS);
        assertTrue(cell.getShipId() == SHIP_ID);
    }
}
