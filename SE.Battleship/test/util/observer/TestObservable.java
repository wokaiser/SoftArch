package util.observer;

import static org.junit.Assert.*;

import mockup.ObserverMockup;

import org.junit.*;

public class TestObservable {
	private Observable obs;
	
	@Before
	public void setUp() {
		obs = new Observable();
	}

	@Test
    public void testAddObserver() {
        String tmpA = obs.addObserver(new ObserverMockup());
        assertNotNull(tmpA);
        String tmpB = obs.addObserver(new ObserverMockup());
        assertNotEquals(tmpA, tmpB);
    }

    @Test
    public void testRemoveObserver() {
    	ObserverMockup[] observers = new ObserverMockup[] { new ObserverMockup(), new ObserverMockup(), new ObserverMockup() };
    	for (ObserverMockup observer: observers) {
			obs.addObserver(observer);
		}
    	ObserverMockup tmp = new ObserverMockup();
    	String uuid = obs.addObserver(tmp);
    	obs.removeObserver(uuid);
    	obs.notifyObservers();
    	for (ObserverMockup observer : observers) {
			assertTrue(observer.wasNotified());
		}
    	assertFalse(tmp.wasNotified());
    }

    @Test
    public void testRemoveAllObservers() {
    	ObserverMockup[] observers = new ObserverMockup[] { new ObserverMockup(), new ObserverMockup(), new ObserverMockup() };
    	for (ObserverMockup observer: observers) {
			obs.addObserver(observer);
		}
    	obs.removeAllObservers();
    	obs.notifyObservers();for (ObserverMockup observer : observers) {
			assertFalse(observer.wasNotified());
		}
    }

    @Test
    public void testNotifyObservers() {    	
    	ObserverMockup[] observers = new ObserverMockup[] { new ObserverMockup(), new ObserverMockup(), new ObserverMockup() };
    	for (ObserverMockup observer: observers) {
			obs.addObserver(observer);
		}
    	obs.notifyObservers();
    	for (ObserverMockup observer : observers) {
			assertTrue(observer.wasNotified());
		}
    }
	
}
