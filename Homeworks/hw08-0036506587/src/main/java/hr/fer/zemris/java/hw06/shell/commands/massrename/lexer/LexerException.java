package hr.fer.zemris.java.hw06.shell.commands.massrename.lexer;

/**
 * This class inherits {@link RuntimeException}. It is thrown when something
 * unhandled has happened with in instance of {@link Lexer} like reading after
 * EOF sign.
 * 
 * @author dbrcina
 *
 */
public class LexerException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public LexerException() {
		super();
	}

	/**
	 * Constructor that calls upon {@link RuntimeException#RuntimeException(String)}
	 * constructor and passes <code>msg</code> as argument.
	 * 
	 * @param msg message given when exception occured.
	 */
	public LexerException(String msg) {
		super(msg);
	}

	/**
	 * Constructor that calls upon
	 * {@link RuntimeException#RuntimeException(Throwable)} constructor and passes
	 * <code>cause</code> as argument.
	 * 
	 * @param cause the cause.
	 */
	public LexerException(Throwable cause) {
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
	public LexerException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
