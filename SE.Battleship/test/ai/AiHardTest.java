package ai;

import static org.junit.Assert.*;
import model.playground.Coordinates;

import org.junit.Test;

public class AiHardTest {
	
	private AiHard ai;

	@Test 
	public void testAiHard() {
		try {
			ai = new AiHard();
		} catch (Exception exc) {
			fail("Should not throw exception at this point.");
		}
	}

	@Test
    public void testInitialize() {
		ai = new AiHard();
		try {
			ai.initialize(12, 12);
		} catch (Exception exc) {
			fail("Should not have throw exception at this point.");
		}
    }

    @Test
    public void testGetCoordinates() {
    	ai = new AiHard();
    	ai.initialize(12, 12);
    	Coordinates coords = ai.getCoordinates();
    	assertNotNull(coords);    	
    }
    
    @Test
    public void testShotResult() {
    	ai = new AiHard();
    	ai.initialize(12, 12);
    	try {
	    	ai.shotResult(0);
    	} catch (Exception exc){
    		fail("Should not throw exception at this point.");
    	}
    }
	
}
