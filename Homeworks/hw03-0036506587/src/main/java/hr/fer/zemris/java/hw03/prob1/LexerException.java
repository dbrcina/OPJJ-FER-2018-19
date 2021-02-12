package hr.fer.zemris.java.hw03.prob1;

/**
 * Exeption that extends {@link RuntimeException}. It is thrown when a problem
 * occurs with {@link Lexer} and its methods.
 * 
 * @author Darijo Brčina
 * @version 1.0
 *
 */
public class LexerException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LexerException() {
		super();
	}

	public LexerException(String msg) {
		super(msg);
	}

	public LexerException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public LexerException(Throwable cause) {
		super(cause);
	}
}
