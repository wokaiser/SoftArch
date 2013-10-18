package game;

import playground.Coordinates;
import junit.framework.TestCase;
import controller.AI;

public class AITest extends TestCase {

	public void testAI() throws Exception {
		try {
			new AI(0, 1);
			fail("Should have raised an IllegalArgumentException");
		} catch (IllegalArgumentException x) {
			
		}
		try {
			new AI(1, 0);
			fail("Should have raised an IllegalArgumentException");
		} catch (IllegalArgumentException x) {
			
		}
	}
	
	/**
	 * The AI Shoots
	 * @return Coordinates to which the AI shoots
	 */
	public Coordinates shoot() throws Exception {
		return null;
	}
	
	
	
}
