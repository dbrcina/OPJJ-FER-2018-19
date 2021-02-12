package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * This class inherits {@link RuntimeException}. It is thrown when something
 * unhandled has happened with in instance of {@link SmartScriptLexer} like
 * reading after EOF sign.
 * 
 * @author dbrcina
 * @version 1.0
 *
 */
public class SmartScriptLexerException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public SmartScriptLexerException() {
		super();
	}

	/**
	 * Constructor that calls upon {@link RuntimeException#RuntimeException(String)}
	 * constructor and passes <code>msg</code> as argument.
	 * 
	 * @param msg message given when exception occured.
	 */
	public SmartScriptLexerException(String msg) {
		super(msg);
	}

	/**
	 * Constructor that calls upon
	 * {@link RuntimeException#RuntimeException(Throwable)} constructor and passes
	 * <code>cause</code> as argument.
	 * 
	 * @param cause the cause.
	 */
	public SmartScriptLexerException(Throwable cause) {
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
	public SmartScriptLexerException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
