package ai;

import static org.junit.Assert.*;
import interfaces.ICoordinates;
import model.playground.Coordinates;

import org.junit.Test;

public class AiEasyTest {
    @Test
    public void testSetShipId() throws Exception {
        AiEasy ai = new AiEasy();
        ai.initialize(1, 1);
        ICoordinates coord = ai.getCoordinates();
        ICoordinates expected = new Coordinates(0, 0);
        assertTrue(coord.equals(expected));
    
        try {
            coord = ai.getCoordinates();
            fail("Exception expected, because AI used all coordinates.");
        }  catch (IllegalStateException exc) {

        }
    }
    @Test
    public void testSetShipId2() throws Exception {
        AiEasy ai = new AiEasy();
        ai.initialize(1, 2);   
        ICoordinates coord = ai.getCoordinates();
        ICoordinates expected1 = new Coordinates(0, 0);
        ICoordinates expected2 = new Coordinates(0, 1);
        assertTrue(coord.equals(expected1) || coord.equals(expected2));
        coord = ai.getCoordinates();
        assertTrue(coord.equals(expected1) || coord.equals(expected2));
    }    
    @Test
    public void testSetShipId3() throws Exception {
        AiEasy ai = new AiEasy();
        try {
            ai.getCoordinates();
            fail("Exception expected, because AI used all coordinates.");
        }  catch (IllegalStateException exc) {

        }
    }
    @Test
    public void testSetShipId4() throws Exception {
        AiEasy ai = new AiEasy();
        ai.initialize(1, 2);   
        ICoordinates coord = ai.getCoordinates();
        ICoordinates expected1 = new Coordinates(0, 0);
        ICoordinates expected2 = new Coordinates(0, 1);
        assertTrue(coord.equals(expected1) || coord.equals(expected2));
        coord = ai.getCoordinates();
        assertTrue(coord.equals(expected1) || coord.equals(expected2));
    }
    @Test
    public void shotResult() throws Exception {
        AiEasy ai = new AiEasy();
        /* shot result should do nothing on easy AI */
        ai.shotResult(0);
    }
}
