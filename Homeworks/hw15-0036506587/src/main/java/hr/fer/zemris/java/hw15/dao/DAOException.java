package hr.fer.zemris.java.hw15.dao;

/**
 * An implementation of {@link RuntimeException} which is thrown when error
 * occurs in <i>Data Access Layer - DAO</i>.
 * 
 * @author dbrcina
 *
 */
public class DAOException extends RuntimeException {

	/**
	 * Default serial ID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor which takes two arguments that delegates to super constructor,
	 * {@link RuntimeException#RuntimeException(String, Throwable)}
	 * 
	 * @param message message.
	 * @param cause   cause.
	 */
	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructo which takes one argument that delegates to super constructor,
	 * {@link RuntimeException#RuntimeException(String)}
	 * 
	 * @param message message.
	 */
	public DAOException(String message) {
		super(message);
	}
}