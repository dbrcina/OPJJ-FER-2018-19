package hr.fer.zemris.java.gui.layouts;

/**
 * This class inherits {@link RuntimeException}. It is thrown when something
 * unhandled has happened with {@link CalcLayout}.
 * 
 * @author dbrcina
 *
 */
public class CalcLayoutException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public CalcLayoutException() {
		super();
	}

	/**
	 * Constructor that calls upon {@link RuntimeException#RuntimeException(String)}
	 * constructor and passes <code>msg</code> as argument.
	 * 
	 * @param msg message given when exception occured.
	 */
	public CalcLayoutException(String msg) {
		super(msg);
	}

	/**
	 * Constructor that calls upon
	 * {@link RuntimeException#RuntimeException(Throwable)} constructor and passes
	 * <code>cause</code> as argument.
	 * 
	 * @param cause the cause.
	 */
	public CalcLayoutException(Throwable cause) {
		super(cause);
	}

	/**
	 * Constructor that calls upon
	 * {@link RuntimeException#RuntimeException(String,Throwable)} constructor and
	 * passes <code>msg</code> and <code>cause</code> as arguments.
	 * 
	 * @param msg   message given when exception occured.
	 * @param cause the cause.
	 */
	public CalcLayoutException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
