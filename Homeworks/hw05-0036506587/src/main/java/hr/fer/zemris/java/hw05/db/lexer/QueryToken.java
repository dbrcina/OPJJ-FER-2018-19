package hr.fer.zemris.java.hw05.db.lexer;

import java.util.Objects;

/**
 * Model of one token that is specified in {@link QueryLexer}. Token's type is
 * specified in {@link QueryTokenType} enumeration.
 * 
 * @author dbrcina
 * @version 1.0
 *
 */
public class QueryToken {

	/**
	 * Token's type.
	 */
	private QueryTokenType type;

	/**
	 * Token's value.
	 */
	private String value;

	/**
	 * Constructor that initializes token with <code>type</code> and
	 * <code>value</code>. <code>value</code> can be <code>null</code>, but
	 * <code>type</code> cannot.
	 * 
	 * @param type  a {@link QueryTokenType} variable.
	 * @param value an {@link String} value.
	 * @throws NullPointerException if the {@code type} is {@code null}.
	 */
	public QueryToken(QueryTokenType type, String value) {
		this.type = Objects.requireNonNull(type, "Tip tokena ne može biti null!");
		this.value = value;
	}

	/**
	 * Getter for {@link #type}.
	 * 
	 * @return {@link #type}.
	 */
	public QueryTokenType getType() {
		return type;
	}

	/**
	 * Getter for {@link #value}.
	 * 
	 * @return {@link #value}.
	 */
	public String getValue() {
		return value;
	}

}
