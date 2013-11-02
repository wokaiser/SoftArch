package util.observer;

import interfaces.IObserver;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/** 
 * The Observable class is the Implementation of the observer pattern.
 * @author Dennis Parlak
 */
public class Observable  {
	private Map<String, IObserver> subscribers = new HashMap<String, IObserver>();

	/** 
	 * The method adds a class, which implements IObserver, to the observer. The uuid for this
	 * Observer will be generated and returned.
	 * @param s Add a class to the Observer
	 */
	public String addObserver(IObserver s) {
		String uuid = UUID.randomUUID().toString();
		while (subscribers.containsKey(uuid)) {
			uuid = UUID.randomUUID().toString();
		}
		subscribers.put(uuid, s);
		return uuid;
	}
	
	/** 
	 * The method adds a class, which implements IObserver, to the observer with a specified uuid,
	 * which can be used to remove the Observer.
	 * @param s Add a class to the Observer
	 */
	public String addObserver(String uuid, IObserver s) {
		subscribers.put(uuid, s);
		return uuid;
	}

	/** 
	 * The method remove a class, which implements IObserver, from the observer.
	 * @param s Remove a class from the Observer
	 */
	public void removeObserver(String uuid) {
		subscribers.remove(uuid);
	}

	/** 
	 * The method removes all classes from the observer.
	 */
	public void removeAllObservers() {
		subscribers.clear();
	}

	/** 
	 * The method notifies all classes which are registered at the observer 
	 */
	public void notifyObservers() {
		for (IObserver observer : subscribers.values()) {
			observer.update();
		}
	}
}
