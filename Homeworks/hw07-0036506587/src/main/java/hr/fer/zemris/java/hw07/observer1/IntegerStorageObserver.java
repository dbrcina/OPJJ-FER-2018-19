package hr.fer.zemris.java.hw07.observer1;

/**
 * This interface represents <i>Observer</i> from <i>Observer design
 * pattern</i>.
 * 
 * @author dbrcina.
 *
 */
public interface IntegerStorageObserver {

	/**
	 * Method used for notifying every observer that the value has been changed.
	 * 
	 * @param istorage instance of {@link IntegerStorage}.
	 */
	void valueChanged(IntegerStorage istorage);
}
