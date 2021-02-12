package hr.fer.zemris.java.hw07.observer2;

import hr.fer.zemris.java.hw07.observer2.IntegerStorage.IntegerStorageChange;

/**
 * An implementation of observer which counts how many times has value changed
 * since tracking has started and prints to {@link System#out}.
 * 
 * @author dbrcina
 *
 */
public class ChangeCounter implements IntegerStorageObserver {

	/**
	 * Variable that keeps track on how many times has value changed.
	 */
	private int counter;

	@Override
	public void valueChanged(IntegerStorageChange istorageChange) {
		System.out.printf("Number of value changes since tracking: %d%n", ++counter);
	}

}
