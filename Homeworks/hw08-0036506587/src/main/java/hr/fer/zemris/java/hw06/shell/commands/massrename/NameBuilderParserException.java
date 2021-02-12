package hr.fer.zemris.java.hw06.shell.commands.massrename;

/**
 * This class inherits {@link RuntimeException}. It is thrown when something
 * unhandled has happened with an instance of {@link NameBuilderParser} like
 * reading after EOF sign or invalid substitute expression etc.
 * 
 * @author dbrcina
 *
 */
public class NameBuilderParserException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public NameBuilderParserException() {
		super();
	}

	/**
	 * Constructor that calls upon {@link RuntimeException#RuntimeException(String)}
	 * constructor and passes <code>msg</code> as argument.
	 * 
	 * @param msg message given when exception occured.
	 */
	public NameBuilderParserException(String msg) {
		super(msg);
	}

	/**
	 * Constructor that calls upon
	 * {@link RuntimeException#RuntimeException(Throwable)} constructor and passes
	 * <code>cause</code> as argument.
	 * 
	 * @param cause the cause.
	 */
	public NameBuilderParserException(Throwable cause) {
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
	public NameBuilderParserException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
