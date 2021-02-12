package hr.fer.zemris.java.hw06.shell.commands.massrename.lexer;

/**
 * Enumeration of {@link Lexer} states. Valid states are:
 * <ul>
 * <li>{@link #OUTSIDE_SUBS_EXPRESSION}</li>
 * <li>{@link #INSIDE_SUBS_EXPRESSION}</li>
 * </ul>
 * 
 * @author dbrcina
 *
 */
public enum LexerState {

	/**
	 * State in which lexer returns tokens that are representing content outside of
	 * a substitution expression. This state is default.
	 */
	OUTSIDE_SUBS_EXPRESSION,

	/**
	 * State in which lexer returns tokens that are representing content inside of a
	 * substitution expression.
	 */
	INSIDE_SUBS_EXPRESSION;
}
