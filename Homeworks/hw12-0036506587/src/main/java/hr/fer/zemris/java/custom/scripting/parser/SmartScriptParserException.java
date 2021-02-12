package hr.fer.zemris.java.custom.scripting.parser;

/**
 * This class inherits {@link RuntimeException}. It is thrown when something
 * unhandled has happened with an instance of {@link SmartScriptParser} like
 * parsing something invalid.
 * 
 * @author dbrcina
 * @version 1.0
 *
 */
public class SmartScriptParserException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public SmartScriptParserException() {
		super();
	}

	/**
	 * Constructor that calls upon {@link RuntimeException#RuntimeException(String)}
	 * constructor and passes <code>msg</code> as argument.
	 * 
	 * @param msg message given when exception occured.
	 */
	public SmartScriptParserException(String msg) {
		super(msg);
	}

	/**
	 * Constructor that calls upon
	 * {@link RuntimeException#RuntimeException(Throwable)} constructor and passes
	 * <code>cause</code> as argument.
	 * 
	 * @param cause the cause.
	 */
	public SmartScriptParserException(Throwable cause) {
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
	public SmartScriptParserException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
