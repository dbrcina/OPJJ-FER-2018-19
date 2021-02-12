package hr.fer.zemris.java.hw05.db.lexer;

import hr.fer.zemris.java.hw05.db.QueryParser;

/**
 * Enumeration {@link QueryLexer} states.
 * 
 * @author dbrcina
 * @version 1.0
 *
 */
public enum QueryLexerState {

	/**
	 * Lexer's state in which initial command is parsed. This is a defualt state.
	 */
	READ_COMMAND,

	/**
	 * Lexer's state in which initial command is skiped. The input text is given
	 * through {@link QueryParser}, so user knows what he entered.
	 */
	SKIP_COMMAND;
}
