package model.playground;

import interfaces.IPlaygroundCell;

import org.junit.Test;

import model.general.Constances;
import static org.junit.Assert.*;

public class PlaygrondCellTest {
	@Test
	public void testGetterAndSetter() throws Exception {
		IPlaygroundCell a = new PlaygroundCell();
		IPlaygroundCell b = new PlaygroundCell(Constances.MATRIX_HIT, Constances.DEFAULT_SHIP_ID);
		assertTrue(a.get() == Constances.MATRIX_INIT);
		assertTrue(b.get() == Constances.MATRIX_HIT);
		assertTrue(a.getShipId() == 0);
		assertTrue(b.getShipId() == Constances.DEFAULT_SHIP_ID);
		a.set(Constances.MATRIX_HIT);
		assertFalse(a.get() == Constances.MATRIX_INIT);
		assertTrue(a.get() == Constances.MATRIX_HIT);
	}
    @Test
    public void testSetShipId() throws Exception {
        IPlaygroundCell a = new PlaygroundCell();
        IPlaygroundCell b = new PlaygroundCell(Constances.MATRIX_HIT, Constances.DEFAULT_SHIP_ID);
        try {
            a.setShipId(Constances.DEFAULT_SHIP_ID);  
        } catch (IllegalAccessError exc) {
            fail("Should not have thrown exception!");
        }
        try {
            a.setShipId(Constances.DEFAULT_SHIP_ID);  
            fail("Should throw an exception if ship id will be set a second time!");
        } catch (IllegalAccessError exc) {

        }
        try {
            b.setShipId(Constances.DEFAULT_SHIP_ID);  
            fail("Should throw an exception if ship id will be set a second time (already set at constructor!");
        } catch (IllegalAccessError exc) {

        }
    }
}
