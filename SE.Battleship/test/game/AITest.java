package game;

import org.junit.Test;
import util.other.*;

import static org.junit.Assert.*;

public class AITest {

	@Test
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
