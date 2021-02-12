package hr.fer.zemris.java.custom.scripting.lexer;

public enum SmartScriptTokenType {

	/**
	 * Represents the end of a text.
	 */
	EOF,
	/**
	 * Represents the begin tag {$.
	 */
	BEGIN_TAG,
	/**
	 * Represents the end tag $}.
	 */
	END_TAG,
	/**
	 * Represents the text.
	 */
	TEXT,
	/**
	 * Represents Integer value.
	 */
	INTEGER,
	/**
	 * Represents Double value.
	 */
	DOUBLE,
	/**
	 * Represents string in tag.
	 */
	STRING,
	/**
	 * Represents operators in tag.
	 */
	OPERATOR,
	/**
	 * Represents functions in tag.
	 */
	FUNCTION,
	/**
	 * Represents variables in tag and also tag names.
	 */
	VARIABLE,
	/**
	 * Represents tag name '='.
	 */
	EQUALS_SIGN;

}
