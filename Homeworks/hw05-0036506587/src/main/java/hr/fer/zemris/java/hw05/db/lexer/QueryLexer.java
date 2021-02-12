package hr.fer.zemris.java.hw05.db.lexer;

import java.util.Objects;

import static java.lang.Character.*;

/**
 * This class is used for parsing text into {@link QueryToken} tokens. Lexer is
 * lazy, which means it only tokenizes input when it is needed. It has two
 * states: {@link QueryLexerState#READ_COMMAND} and
 * {@link QueryLexerState#SKIP_COMMAND}.
 * 
 * @author dbrcina
 * @version 1.0
 *
 */
public class QueryLexer {

	/**
	 * Constant representing query command.
	 */
	private static final String QUERY_COMMAND = "query";

	/**
	 * Constant representing field jmbag.
	 */
	private static final String JMBAG = "jmbag";

	/**
	 * Constant representing field firstName.
	 */
	private static final String FIRST_NAME = "firstName";

	/**
	 * Constant representing field lastName.
	 */
	private static final String LAST_NAME = "lastName";

	/**
	 * Constant representing AND.
	 */
	private static final String AND = "AND";

	/**
	 * Constant representing valid operators.
	 */
	private static final String VALID_OPERATORS = "!=<=>=LIKE";

	/**
	 * Constant representing " sign.
	 */
	private static final char QUOTE = '"';

	/**
	 * Input text.
	 */
	private char[] data;

	/**
	 * Current token.
	 */
	private QueryToken token;

	/**
	 * Index of first unfinished token.
	 */
	private int currentIndex;

	/**
	 * Lexer's current state.
	 */
	private QueryLexerState state;

	/**
	 * Constructor that only take input text as an argument. If <code>text</code> is
	 * <code>null</code>, an appropriate exception is thrown.
	 * 
	 * @param text the input text.
	 * @throws NullPointerException if the given {@code text} is {@code null}.
	 */
	public QueryLexer(String text) {
		data = Objects.requireNonNull(text, "Ulazni tekst ne smije biti null!").toCharArray();
		state = QueryLexerState.READ_COMMAND;
	}

	/**
	 * Getter for current lexer's state.
	 * 
	 * @return The current lexer's state.
	 */
	public QueryLexerState getState() {
		return state;
	}

	/**
	 * Method that provides user to set lexer's state to <code>state</code>.
	 * 
	 * @param state A {@link QueryTokenType} variable.
	 * @throws NullPointerException if the {@code state} is {@code null}.
	 */
	public void setState(QueryLexerState state) {
		this.state = Objects.requireNonNull(state, "State ne smije biti null!");
	}

	/**
	 * Getter for {@link #token}.
	 * 
	 * @return {@link #token};
	 */
	public QueryToken getToken() {
		return token;
	}

	/**
	 * @return next tokena that needs to be tokenized.
	 * @see {@link #generateToken()} for rules of tokenization.
	 */
	public QueryToken nextToken() {

		// checks whether this method was called after EOF
		checkAfterEOF();

		if (!checkForMoreSigns()) {
			createFinalToken();
			return token;
		}

		if (state == QueryLexerState.READ_COMMAND) {

			// checks whether command is right
			if (currentIndex == 0) {
				checkQueryCommand();
			}
		}

		// skip spaces and tabs
		skipSpaces();

		generateToken();

		return token;
	}

	/**
	 * Method that generates four types of tokens.
	 * <ul>
	 * <li>{@link QueryTokenType#FIELD} - valid field names are : {@link #JMBAG} ,
	 * {@link #FIRST_NAME} and {@link #LAST_NAME} .</li>
	 * <li>{@link QueryTokenType#OPERATOR} - valid operators are from
	 * {@link #VALID_OPERATORS} where <>=! can come together also.</li>
	 * <li>{@link QueryTokenType#STRING} - strings start and end with {@link #QUOTE}
	 * .</li>
	 * <li>{@link QueryTokenType#AND} .</li>
	 * </ul>
	 * {@link QueryTokenType#EOF} is generated in method {@link #nextToken()} and it
	 * represents the end of input text.
	 * 
	 * @throws QueryLexerException if something goes wrong with parsing.
	 */
	private void generateToken() {

		while (checkForMoreSigns()) {

			// check for AND operator
			if (String.valueOf(data[currentIndex]).toLowerCase().equals("a")) {
				token = new QueryToken(QueryTokenType.AND, parseAnd());
				return;
			}

			// check for field names
			// skips LIKE operator eventhough it starts with letter..
			else if (isLetter(data[currentIndex]) && !VALID_OPERATORS.contains(String.valueOf(data[currentIndex]))) {
				token = new QueryToken(QueryTokenType.FIELD, parseField());
				return;
			}

			// check for valid operators
			else if (VALID_OPERATORS.contains(String.valueOf(data[currentIndex]).toUpperCase())) {
				token = new QueryToken(QueryTokenType.OPERATOR, parseOperator());
				return;
			}

			// check for strings
			else if (data[currentIndex] == QUOTE) {
				token = new QueryToken(QueryTokenType.STRING, parseString());
				return;
			}

			// just in case..
			else {
				throw new QueryLexerException("Ne prepoznajem niti jedan dogovoreni dio teksta!");
			}
		}
	}

	/**
	 * Parses current token into AND if it is possible.
	 * 
	 * @return {@link #AND};
	 * @throws QueryLexerException if operator AND is invalid.
	 */
	private String parseAnd() {
		int startIndex = currentIndex;
		while (checkForMoreSigns() && isLetter(data[currentIndex])) {
			currentIndex++;
		}
		if (new String(data, startIndex, currentIndex - startIndex).toUpperCase().equals(AND)) {
			return AND;
		}
		throw new QueryLexerException("AND operator nije dobro zadan!");
	}

	/**
	 * Parses fields if it is possible.
	 * 
	 * @return {@link #JMBAG} , {@link #FIRST_NAME} or {@link #LAST_NAME} .
	 * @throws QueryLexerException if the field name is invalid.
	 */
	private String parseField() {
		int startIndex = currentIndex;
		while (checkForMoreSigns() && isLetter(data[currentIndex])) {
			currentIndex++;
		}
		String field = new String(data, startIndex, currentIndex - startIndex);
		if (field.equals(JMBAG) || field.equals(FIRST_NAME) || field.equals(LAST_NAME)) {
			return field;
		}
		throw new QueryLexerException("Fieldovi nisu dobro zadani!");
	}

	/**
	 * Parses operators if it is possible.
	 * 
	 * @return operator made from {@link #VALID_OPERATORS}.
	 * @throws QueryLexerException if the operator is invalid.
	 */
	private String parseOperator() {
		int startIndex = currentIndex;
		while (checkForMoreSigns() && !isSpaceChar(data[currentIndex])) {
			if (data[currentIndex] == QUOTE) {
				break;
			}
			currentIndex++;
		}
		String operator = new String(data, startIndex, currentIndex - startIndex);
		if (VALID_OPERATORS.contains(operator)) {
			return operator;
		}
		throw new QueryLexerException("Operator nije dobro zadan!");
	}

	/**
	 * Parses strings if it is possible.
	 * 
	 * @return text inputed in quotes.
	 */
	private String parseString() {
		// skip initial quote sign
		currentIndex++;
		int startIndex = currentIndex;
		while (checkForMoreSigns() && data[currentIndex] != QUOTE) {
			currentIndex++;
		}
		return new String(data, startIndex, currentIndex++ - startIndex);

	}

	/**
	 * Checks whether {@link #nextToken()} was called after everything was
	 * read(EOF).
	 * 
	 * @throws QueryLexerException if user tries to read after
	 *                             {@link QueryTokenType#EOF}.
	 */
	private void checkAfterEOF() {
		if (Objects.nonNull(token) && token.getType() == QueryTokenType.EOF) {
			throw new QueryLexerException("Nema vise tokena!");
		}
	}

	/**
	 * Checks whether input text starts with rigth query command.
	 * 
	 * @throws QueryLexerException if it does not.
	 */
	private void checkQueryCommand() {
		skipSpaces();
		int startIndex = currentIndex;
		while (!isSpaceChar(data[currentIndex])) {
			currentIndex++;
		}
		if (!new String(data, startIndex, currentIndex - startIndex).equals(QUERY_COMMAND)) {
			throw new QueryLexerException("Input ne započinje s query command!");
		}
	}

	/**
	 * Skips all spaces and tabs.
	 */
	private void skipSpaces() {
		while (checkForMoreSigns()) {
			while (checkForMoreSigns() && isSpaceChar(data[currentIndex])) {
				currentIndex++;
			}
			break;
		}
	}

	/**
	 * Checks whether there are more signs to be tokenized.
	 * 
	 * @return {@code true} if there are more signs for tokenization, otherwise
	 *         {@code false}.
	 */
	private boolean checkForMoreSigns() {
		return currentIndex < data.length;
	}

	/**
	 * Creates the last token representing {@link SmartScriptTokenType#EOF}.
	 */
	private void createFinalToken() {
		token = new QueryToken(QueryTokenType.EOF, null);
	}
}
