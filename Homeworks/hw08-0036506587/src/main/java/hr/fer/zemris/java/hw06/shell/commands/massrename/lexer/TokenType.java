package hr.fer.zemris.java.hw06.shell.commands.massrename.lexer;

/**
 * Enumeration of {@link Token} tokens type. The valid types are:
 * <ul>
 * <li>{@link #TEXT}</li>
 * <li>{@link #START_SUBSTITUTION}</li>
 * <li>{@link #END_SUBSTITUTION}</li>
 * <li>{@link #COMMA}</li>
 * <li>{@link #NUMBER}</li>
 * <li>{@link #EOF}</li>
 * </ul>
 * 
 * @author dbrcina
 *
 */
public enum TokenType {

	/**
	 * Represents regular text outside substitution expression.
	 */
	TEXT,

	/**
	 * Represents start of substitution expression.
	 */
	START_SUBSTITUTION,

	/**
	 * Represents end of substitution expression.
	 */
	END_SUBSTITUTION,

	/**
	 * Represents ',' sign inside substitution expression.
	 */
	COMMA,

	/**
	 * Represents number inside substitution expression.
	 */
	NUMBER,

	/**
	 * Represents end of file.
	 */
	EOF;
}
