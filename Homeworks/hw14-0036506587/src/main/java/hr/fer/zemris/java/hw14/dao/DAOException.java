package hr.fer.zemris.java.hw14.dao;

/**
 * This class inherits from {@link RuntimeException} and represents a simple
 * model of exception that is thrown when error occures while working with an
 * implementation of {@link DAO}.
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
	 * Default constructor.
	 */
	public DAOException() {
	}

	/**
	 * Delegates super constructors.
	 * 
	 * @param message             message.
	 * @param cause               cause.
	 * @param enableSuppression.
	 * @param writableStackTrace.
	 */
	public DAOException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * Delegates super constructors.
	 * 
	 * @param message message.
	 * @param cause   cause.
	 */
	public DAOException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Delegates super constructors.
	 * 
	 * @param message message.
	 */
	public DAOException(String message) {
		super(message);
	}

	/**
	 * Delegates super constructors.
	 * 
	 * @param cause cause.
	 */
	public DAOException(Throwable cause) {
		super(cause);
	}
}