package hr.fer.zemris.java.custom.scripting.lexer;

import java.util.Objects;

/**
 * Model of one token entered.
 * 
 * @author dbrcina
 * @version 1.0
 *
 */
public class SmartScriptToken {

	/**
	 * Token's type.
	 */
	private SmartScriptTokenType type;

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
	public SmartScriptToken(SmartScriptTokenType type, Object value) {
		Objects.requireNonNull(type);
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
	public SmartScriptTokenType getType() {
		return type;
	}
}
