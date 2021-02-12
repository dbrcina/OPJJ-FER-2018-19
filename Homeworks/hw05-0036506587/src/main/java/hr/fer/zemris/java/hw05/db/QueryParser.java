package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw05.db.lexer.*;

/**
 * This class represents a simple query parser that parses given command into
 * {@link ConditionalExpression}.
 * 
 * @author dbrcina
 * @version 1.0
 * @see {@link QueryLexer#nextToken()} for rules when parsing command.
 *
 */
public class QueryParser {

	/**
	 * Lexer used for tokenization.
	 */
	private QueryLexer lexer;

	/**
	 * Internal storage used for instances of {@link ConditionalExpression}.
	 */
	private List<ConditionalExpression> expressions;

	/**
	 * Constructor that initializies {@link QueryParser} fields and calls
	 * {@link #parse()} method.
	 * 
	 * @param text the input text that needs to be parsed.
	 * @throws NullPointerException if {@code text} is {@code null}.
	 */
	public QueryParser(String text) {
		lexer = new QueryLexer(text);
		lexer.setState(QueryLexerState.SKIP_COMMAND);
		expressions = new ArrayList<>();

		try {
			parse();
		} catch (Exception e) {
			throw new QueryParserException(e.getMessage());
		}
	}

	/**
	 * Checks whether query command is direct. Direct query is represented by only
	 * one field name {@link QueryLexer#JMBAG}, only one operator
	 * {@link ComparisonOperators#EQUALS} and only one {@link String} text.
	 * 
	 * @return {@code true} if query is direct, otherwise {@code false}.
	 */
	public boolean isDirectQuery() {
		if (expressions.size() != 1) {
			return false;
		}
		ConditionalExpression expression = expressions.get(0);
		return expression.getFieldValueGetter() == FieldValueGetters.JMBAG
				&& expression.getOperator() == ComparisonOperators.EQUALS;
	}

	/**
	 * If query is direct as determined by {@link #isDirectQuery()}, queried jmbag
	 * is returned, otherwise an appropriate exception is thrown.
	 * 
	 * @return queried jmbag entered in query command.
	 * @throws IllegalStateException if query is not direct.
	 */
	public String getQueriedJmbag() {
		if (!isDirectQuery()) {
			throw new IllegalStateException("Query nije direct!");
		}
		return expressions.get(0).getPattern();
	}

	/**
	 * @return list of {@link ConditionalExpression}.
	 */
	public List<ConditionalExpression> getQuery() {
		return expressions;
	}

	/**
	 * Private method used for parsing input text that si tokenized by
	 * {@link #lexer}. Tokens are parsed as determined by
	 * {@link QueryLexer#nextToken()} method.
	 * 
	 * @throws QueryParserException if something goes wrong in parser.
	 */
	private void parse() throws QueryParserException {
		IFieldValueGetter fieldValue = null;
		String pattern = null;
		IComparisonOperator operator = null;

		while (true) {
			QueryToken token = lexer.nextToken();

			if (isEOF(token)) {
				break;
			}

			// parse fields
			if (token.getType() == QueryTokenType.FIELD) {
				fieldValue = parseField(token);
				token = lexer.nextToken();
			}

			// parse operators
			if (token.getType() == QueryTokenType.OPERATOR) {
				operator = parseOperator(token);
				token = lexer.nextToken();
			}

			// parse strings
			if (token.getType() == QueryTokenType.STRING) {
				pattern = token.getValue();
				token = lexer.nextToken();
			}

			expressions.add(new ConditionalExpression(fieldValue, pattern, operator));

			// check for EOF..
			// it is possible that query is direct!
			if (isEOF(token)) {
				break;
			}

			// do nothing if there is AND token..
			if (token.getType() == QueryTokenType.AND)
				;
		}
	}

	/**
	 * Helper method used for checking whether field name is valid.
	 * 
	 * @param token token whose value is checked.
	 * @return {@link IFieldValueGetter} type variable that represents
	 *         {@link StudentRecord} field.
	 * @throws QueryParserException if field name is invalid.
	 */
	private IFieldValueGetter parseField(QueryToken token) throws QueryParserException {
		switch (token.getValue()) {
		case "jmbag":
			return FieldValueGetters.JMBAG;
		case "firstName":
			return FieldValueGetters.FIRST_NAME;
		case "lastName":
			return FieldValueGetters.LAST_NAME;
		default:
			throw new QueryParserException("Field je krivo zadan");
		}
	}

	/**
	 * Helper method used for checking whether operator is valid.
	 * 
	 * @param token token whose value is checked.
	 * @return {@link IComparisonOperator} type variable that represents operation.
	 * @throws QueryParserException if operator is invalid.
	 */
	private IComparisonOperator parseOperator(QueryToken token) {
		switch (token.getValue()) {
		case "<":
			return ComparisonOperators.LESS;
		case "<=":
			return ComparisonOperators.LESS_OR_EQUALS;
		case ">":
			return ComparisonOperators.GREATER;
		case ">=":
			return ComparisonOperators.GREATER_OR_EQUALS;
		case "=":
			return ComparisonOperators.EQUALS;
		case "!=":
			return ComparisonOperators.NOT_EQUALS;
		case "LIKE":
			return ComparisonOperators.LIKE;
		default:
			throw new QueryParserException("Operator nije dobro zadan!");
		}
	}

	/**
	 * Helper method that checks whether <code>token's</code> type is
	 * {@link QueryTokenType#EOF}.
	 * 
	 * @param token token whose type is checked.
	 * @return {@code true} if it is, othwerwis {@code false}.
	 */
	private boolean isEOF(QueryToken token) {
		return token.getType() == QueryTokenType.EOF;
	}
}
