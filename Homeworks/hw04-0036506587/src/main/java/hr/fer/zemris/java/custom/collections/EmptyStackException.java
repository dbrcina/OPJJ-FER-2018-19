package hr.fer.zemris.java.custom.collections;

/**
 * {@code EmptyStackException} is an exception inherited from
 * {@link RuntimeException}. It is thrown when someone tires to take an element
 * from an emty stack.
 * 
 * @author Darijo Brčina
 * @version 1.0
 *
 */
public class EmptyStackException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * A default constructor for {@link EmptyStackException}.
	 */
	public EmptyStackException() {
		super();
	}

	/**
	 * Constructor for {@link EmptyStackException} that calls super constructor and
	 * forwards the {@code message} to it.
	 * 
	 * @param message message that is send when the this exception appears.
	 */
	public EmptyStackException(String message) {
		super(message);
	}
}
