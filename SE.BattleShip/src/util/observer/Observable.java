package util.observer;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

/** 
 * The Observable class is the Implementation of the observer pattern.
 * @author Dennis Parlak
 */
public class Observable  {

	private List<IObserver> subscribers = new ArrayList<IObserver>();

	/** 
	 * The method adds a class, which implements IObserver, to the observer.
	 * @param s Add a class to the Observer
	 */
	public void addObserver(IObserver s) {
		subscribers.add(s);
	}

	/** 
	 * The method remove a class, which implements IObserver, from the observer.
	 * @param s Remove a class from the Observer
	 */
	public void removeObserver(IObserver s) {
		subscribers.remove(s);
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
		for ( Iterator<IObserver> iter = subscribers.iterator(); iter.hasNext();) {
			IObserver observer = iter.next();
			observer.update();
		}
	}
}
