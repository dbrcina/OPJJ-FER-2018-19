package hr.fer.zemris.java.gui.prim;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * Model of <i>prime numbers</i> that implements {@link ListModel}.
 * 
 * @author dbrcina
 *
 */
public class PrimListModel implements ListModel<Integer> {

	/**
	 * List of listeners.
	 */
	private List<ListDataListener> listeners;

	/**
	 * List of prim numbers.
	 */
	private List<Integer> prims;

	/**
	 * Current prim number.
	 */
	private int currentPrime;

	/**
	 * Default constructor.
	 */
	public PrimListModel() {
		listeners = new ArrayList<>();
		prims = new ArrayList<>();
		currentPrime = 1;
		prims.add(currentPrime);
	}

	/**
	 * Factory method used for generating next prim number.
	 */
	public void next() {
		for (int i = currentPrime + 1;; i++) {
			if (isPrime(i)) {
				currentPrime = i;
				prims.add(currentPrime);
				notifyListeners();
				break;
			}
		}
	}

	/**
	 * Helper method which checks whether given <code>number</code> is a prime
	 * number.
	 * 
	 * @param number number.
	 * @return {@code true} if {@code number} is prime number, otherwise
	 *         {@code false}.
	 */
	private boolean isPrime(int number) {
		for (int i = 2; i <= number / 2; i++) {
			if (number % i == 0) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Notifies all listeners that interval has been added.
	 */
	private void notifyListeners() {
		ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, getSize(), getSize());
		listeners.forEach(l -> l.intervalAdded(event));
	}

	@Override
	public int getSize() {
		return prims.size();
	}

	@Override
	public Integer getElementAt(int index) {
		return prims.get(index);
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		listeners.add(l);
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		listeners.remove(l);
	}

}
