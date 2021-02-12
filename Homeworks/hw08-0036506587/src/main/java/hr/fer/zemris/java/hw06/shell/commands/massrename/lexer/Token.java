package hr.fer.zemris.java.hw06.shell.commands.massrename.lexer;

import java.util.Objects;

/**
 * Model of one token that is specified in {@link Lexer}. Token's type is
 * specified in {@link TokenType} enumeration.
 * 
 * @author dbrcina
 *
 */
public class Token {

	/**
	 * Token's type.
	 */
	private TokenType type;

	/**
	 * Token's value.
	 */
	private String value;

	/**
	 * Constructor that initializes token with <code>type</code> and
	 * <code>value</code>. <code>value</code> can be <code>null</code>, but
	 * <code>type</code> cannot.
	 * 
	 * @param type  a {@link TokenType} variable.
	 * @param value an {@link String} value.
	 * @throws NullPointerException if the {@code type} is {@code null}.
	 */
	public Token(TokenType type, String value) {
		this.type = Objects.requireNonNull(type, "Token type cannot be null!");
		this.value = value;
	}

	/**
	 * Getter for token's type.
	 * 
	 * @return {@link #type}.
	 */
	public TokenType getType() {
		return type;
	}

	/**
	 * Getter for token's value.
	 * 
	 * @return {@link #value}.
	 */
	public String getValue() {
		return value;
	}

}
