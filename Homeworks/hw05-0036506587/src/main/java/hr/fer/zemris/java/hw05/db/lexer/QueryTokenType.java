package hr.fer.zemris.java.hw05.db.lexer;

import hr.fer.zemris.java.hw05.db.ComparisonOperators;
import hr.fer.zemris.java.hw05.db.StudentRecord;

/**
 * Enumeration of {@link QueryToken} tokens type.
 * 
 * @author dbrcina
 * @version 1.0
 *
 */
public enum QueryTokenType {

	/**
	 * Represents {@link StudentRecord} fields.
	 */
	FIELD,

	/**
	 * Represents a {@link ComparisonOperators} operator.
	 */
	OPERATOR,

	/**
	 * Represents text in quotes.
	 */
	STRING,

	/**
	 * Represents logical operator AND.
	 */
	AND,

	/**
	 * Represents end of file.
	 */
	EOF;
}
