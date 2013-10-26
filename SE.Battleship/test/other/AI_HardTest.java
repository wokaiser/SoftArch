package other;

import static org.junit.Assert.*;
import model.general.Constances;
import model.playground.Coordinates;

import org.junit.*;

import util.other.AI_Hard;

public class AI_HardTest {
	
	private AI_Hard ai;

	@Before
	public void setUp() {
		ai = new AI_Hard();
	}
	
	@Test
	public void initialize() {
		try {
			ai.initialize(0, 0);
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
			new AI_Hard();
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
	}
}
