package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Enumeration which provides two {@link SmartScriptLexer}'s state in which
 * reading is done.
 * 
 * @author dbrcina
 * @version 1.0
 *
 */
public enum SmartScriptLexerState {

	/**
	 * Lexer is in <code><b>TEXT</b></code> state when reading from outside of a
	 * tag.
	 */
	TEXT,
	/**
	 * Lexer is in <code><b>TAG</b></code> state when reading from inside of a tag.
	 */
	TAG;
}
