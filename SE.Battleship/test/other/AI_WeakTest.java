package other;

import static org.junit.Assert.*;
import model.general.Constances;
import model.playground.Coordinates;

import org.junit.*;

import util.other.AI_Weak;

public class AI_WeakTest {
	
	private AI_Weak ai;

	@Before
	public void setUp() {
		ai = new AI_Weak();
	}
	
	@Test
	public void initialize() {
		try {
			ai.initialize(0, Constances.DEFAULT_COLUMNS);
			fail("Should have thrown an IllegalArgumentException!");
		} catch (Exception exc) {
			
		}
		try {
			ai.initialize(Constances.DEFAULT_ROWS, 0);
			fail("Should have thrown an IllegalArgumentException!");
		} catch (Exception exc) {
			
		}
		try {
			ai.initialize(Constances.DEFAULT_ROWS, Constances.DEFAULT_COLUMNS);
		} catch (Exception exc) {
			fail("Should not throw an excpetion at this point");
		}
	}
	
	@Test
	public void testAI_Weak() {
		try {
			new AI_Weak();
		} catch (Exception exc) {
			fail("There should not be an exception thrown at the constructor of AI_Weak");
		}
	}
	
	@Test
	public void testshoot() {
		Coordinates result;
		int x, y;
		
		ai.initialize(Constances.DEFAULT_ROWS, Constances.DEFAULT_COLUMNS);
		result = ai.shoot();
		x = result.getRow();
		y = result.getColumn();
		
		assertTrue(x >= 0 && x <= Constances.DEFAULT_ROWS);
		assertTrue(y >= 0 && y <= Constances.DEFAULT_COLUMNS);
	}
	
	@Test
	public void testsetFlags() {
		assertTrue(ai.setFlags(Constances.SHOOT_MISS));
		assertFalse(ai.setFlags(Constances.SHOOT_HIT));
		assertFalse(ai.setFlags(Constances.SHOOT_DESTROYED));
	}
}
