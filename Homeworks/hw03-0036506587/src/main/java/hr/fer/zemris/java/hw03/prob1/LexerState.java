package hr.fer.zemris.java.hw03.prob1;

/**
 * Enumeration for {@link Lexer} states.
 * 
 * @author dbrcina
 * @version 1.0
 *
 */
public enum LexerState {

	/**
	 * Basic lexer's state.
	 */
	BASIC,
	/**
	 * Extended lexer's state. This state occurs when '#' appears and lexer stays in
	 * this state till next '#'.
	 */
	EXTENDED;
}
