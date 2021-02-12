package hr.fer.zemris.java.hw07.observer2;

import hr.fer.zemris.java.hw07.observer2.IntegerStorage.IntegerStorageChange;

/**
 * An implementation of observer which writes {@link IntegerStorage} current
 * value multiplied by 2 to {@link System#out} only for {@link #counter} times.
 * 
 * @author dbrcina
 *
 */
public class DoubleValue implements IntegerStorageObserver {

	/**
	 * Variable used counting how many operations should be done.
	 */
	private int counter;

	/**
	 * Constructor that takes only one argument <code>counter</code> used to set
	 * {@link #counter}.
	 * 
	 * @param counter counter.
	 */
	public DoubleValue(int counter) {
		this.counter = counter;
	}

	@Override
	public void valueChanged(IntegerStorageChange istorageChange) {
		if (counter != 0) {
			System.out.printf("Double value: %d%n", istorageChange.getNewValue() * 2);
			counter--;
		} else {
			istorageChange.getIstorage().removeObserver(this);
		}

	}

}
