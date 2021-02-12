package hr.fer.zemris.java.hw03.prob1;

import java.util.Objects;

/**
 * Model of one token entered through the main application.
 * 
 * @author Darijo Brčina
 * @version 1.0
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
	private Object value;

	/**
	 * Constructor that initializes token with token's {@code type} and token's
	 * {@code value}. {@code value} can be {@code null}, but {@code type} cannot.
	 * 
	 * @param type  a {@link TokenType} variable.
	 * @param value an Object value.
	 * @throws NullPointerException if the {@code type} is {@code null}.
	 */
	public Token(TokenType type, Object value) {
		if (Objects.isNull(type)) {
			throw new NullPointerException("Type je null!");
		}
		this.type = type;
		this.value = value;
	}

	/**
	 * Getter for token's {@code value}.
	 * 
	 * @return token's {@code value}.
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Getter for token's {@code type}.
	 * 
	 * @return token's {@code type}.
	 */
	public TokenType getType() {
		return type;
	}
}
