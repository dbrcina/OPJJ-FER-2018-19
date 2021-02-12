package hr.fer.zemris.java.hw07.observer2;

import hr.fer.zemris.java.hw07.observer2.IntegerStorage.IntegerStorageChange;

/**
 * An implementation of observer which writes new value and it's square value to
 * {@link System#out}.
 * 
 * @author dbrcina
 *
 */
public class SquareValue implements IntegerStorageObserver {

	@Override
	public void valueChanged(IntegerStorageChange istorageChange) {
		int newValue = istorageChange.getNewValue();
		System.out.printf("Provided new value: %d, square is %d%n", newValue, newValue * newValue);
	}

}
