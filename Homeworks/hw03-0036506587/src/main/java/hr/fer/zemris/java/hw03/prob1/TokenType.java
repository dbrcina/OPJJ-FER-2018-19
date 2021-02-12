package hr.fer.zemris.java.hw03.prob1;

/**
 * Enum that represents a token type.
 * 
 * @author Darijo Brčina
 * @version 1.0
 *
 */
public enum TokenType {

	/**
	 * Type when there are no more tokens.
	 */
	EOF,
	/**
	 * Represents a word in a token.
	 */
	WORD,
	/**
	 * Represents a number in a token.
	 */
	NUMBER,
	/**
	 * Represents a symbol in a token.
	 */
	SYMBOL;
}
