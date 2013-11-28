package mockup;

import interfaces.IObserver;

/**
 * Class only to be used for testing!
 * @author Wolfgang
 *
 */
public class ObserverMockup implements IObserver {
	
	private boolean wasNotified;
	
	/**
	 * Creates a new ObserverMockup object
	 */
	public ObserverMockup() {
		wasNotified = false;
	}

	@Override
	public void update() {
		wasNotified = true;
	}
	
	/**
	 * Checks if the observer was notified
	 * @return true, if observer was notified before
	 */
	public boolean wasNotified() {
		return wasNotified;
	}

}
