package hr.fer.zemris.java.hw07.observer1;

/**
 * An implementation of observer which writes new value and it's square value to
 * {@link System#out}.
 * 
 * @author dbrcina
 *
 */
public class SquareValue implements IntegerStorageObserver {

	@Override
	public void valueChanged(IntegerStorage istorage) {
		int value = istorage.getValue();
		System.out.printf("Provided new value: %d, square is %d%n", value, value * value);
	}

}
