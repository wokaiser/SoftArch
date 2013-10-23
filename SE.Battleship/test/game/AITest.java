package game;

import util.other.*;
import junit.framework.TestCase;

public class AITest extends TestCase {

	public void testAI() throws Exception {
		try {
			AI_Hard aiHard = new AI_Hard();
			aiHard.initialize(0, 1);
			fail("Should have raised an IllegalArgumentException");
		} catch (IllegalArgumentException x) {
			
		}
		try {
			AI_Weak aiWeak = new AI_Weak();
			aiWeak.initialize(1, 0);
			fail("Should have raised an IllegalArgumentException");
		} catch (IllegalArgumentException x) {
			
		}
	}	
}
