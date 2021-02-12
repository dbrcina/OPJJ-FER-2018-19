package hr.fer.zemris.java.hw07.observer1;

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
	public void valueChanged(IntegerStorage istorage) {
		System.out.printf("Number of value changes since tracking: %d%n", ++counter);
	}

}
