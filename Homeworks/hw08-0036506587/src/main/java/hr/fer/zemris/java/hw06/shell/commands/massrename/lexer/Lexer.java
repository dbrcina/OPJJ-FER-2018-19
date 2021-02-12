package hr.fer.zemris.java.hw06.shell.commands.massrename.lexer;

import java.util.Objects;

import static java.lang.Character.*;

/**
 * This class is used for parsing text into {@link Token} tokens. Lexer is lazy,
 * which means it only tokenizes input when it is needed. It has two states:
 * <ul>
 * <li>{@link LexerState#OUTSIDE_SUBS_EXPRESSION}</li>
 * <li>{@link LexerState#INSIDE_SUBS_EXPRESSION}</li>
 * </ul>
 * 
 * @author dbrcina
 *
 */
public class Lexer {

	/**
	 * Constant representing '"' sign.
	 */
	private static final char QUOTE = '"';

	/**
	 * Constant representing '\' sign.
	 */
	private static final char ESCAPE = '\\';

	/**
	 * Constant representing ',' sign.
	 */
	private static final char COMMA = ',';

	/**
	 * Constant representing '$' sign.
	 */
	private static final char DOLLAR_SIGN = '$';

	/**
	 * Constant representing '{' sign.
	 */
	private static final char OPEN_BRACKET = '{';

	/**
	 * Constant representing '}' sign.
	 */
	private static final char CLOSED_BRACKET = '}';

	/**
	 * Constant representing begining of expression, "${".
	 */
	private static final String BEGIN_EXPRESSION = "${";

	/**
	 * Input text.
	 */
	private char[] data;

	/**
	 * Current token.
	 */
	private Token token;

	/**
	 * Index of first unfinished token.
	 */
	private int currentIndex;

	/**
	 * Lexer's current state.
	 */
	private LexerState state = LexerState.OUTSIDE_SUBS_EXPRESSION;

	/**
	 * Flag representing whether provided input is in quotes.
	 */
	private boolean inQuotes;

	/**
	 * Constructor that only take input text as an argument. If <code>text</code> is
	 * <code>null</code>, an appropriate exception is thrown.
	 * 
	 * @param text the input text.
	 * @throws NullPointerException if the given {@code text} is {@code null}.
	 */
	public Lexer(String text) {
		data = Objects.requireNonNull(text, "Input text for lexer cannot be null!\n").toCharArray();
		inQuotes = data[currentIndex] == QUOTE;
	}

	/**
	 * Getter for current lexer's state.
	 * 
	 * @return The current lexer's state.
	 */
	public LexerState getState() {
		return state;
	}

	/**
	 * Method that provides user to set lexer's state to <code>state</code>.
	 * 
	 * @param state A {@link TokenType} variable.
	 * @throws NullPointerException if the {@code state} is {@code null}.
	 */
	public void setState(LexerState state) {
		this.state = Objects.requireNonNull(state, "Lexer's state cannot be null!\n");
	}

	/**
	 * Getter for current token.
	 * 
	 * @return {@link #token};
	 */
	public Token getToken() {
		return token;
	}

	/**
	 * @return next tokenized token.
	 */
	public Token nextToken() {

		// checks whether this method was called after EOF
		checkAfterEOF();

		if (!checkForMoreSigns()) {
			token = new Token(TokenType.EOF, null);
			return token;
		}

		if (state == LexerState.OUTSIDE_SUBS_EXPRESSION) {
			parseOutside();
		} else if (state == LexerState.INSIDE_SUBS_EXPRESSION) {
			parseInside();
		} else {
			throw new LexerException("Lexer's state is unknown!\n");
		}

		return token;
	}

	/**
	 * Helper method used for parsing content outside of a substitute expression.
	 */
	private void parseOutside() {
		StringBuilder sb = new StringBuilder();

		while (checkForMoreSigns()) {

			// escaping sequence
			if (data[currentIndex] == ESCAPE && inQuotes && checkNextSign()) {
				if (data[currentIndex + 1] == ESCAPE || data[currentIndex + 1] == QUOTE) {
					currentIndex++;
				}
			}

			// check begining of expression
			else if (data[currentIndex] == DOLLAR_SIGN && checkNextSign() && data[currentIndex + 1] == OPEN_BRACKET) {
				if (sb.length() != 0) {
					break;
				}
				token = new Token(TokenType.START_SUBSTITUTION, BEGIN_EXPRESSION);
				currentIndex += 2;
				return;
			}

			else {
				sb.append(data[currentIndex++]);
			}
		}
		token = new Token(TokenType.TEXT, sb.toString());
	}

	/**
	 * Helper method used for parsing content inside of a substitute expression.
	 */
	private void parseInside() {
		StringBuilder sb = new StringBuilder();
		skipSpaces();

		// parse numbers
		if (Character.isDigit(data[currentIndex])) {
			sb.append(data[currentIndex++]);
			while (checkForMoreSigns() && Character.isDigit(data[currentIndex])) {
				sb.append(data[currentIndex++]);
			}
			token = new Token(TokenType.NUMBER, sb.toString());
			return;
		}

		// parse split argument
		else if (data[currentIndex] == COMMA) {
			token = new Token(TokenType.COMMA, String.valueOf(COMMA));
			currentIndex++;
			return;
		}

		// parse end of expression
		else if (data[currentIndex] == CLOSED_BRACKET) {
			token = new Token(TokenType.END_SUBSTITUTION, String.valueOf(CLOSED_BRACKET));
			currentIndex++;
			return;
		}

		else {
			throw new LexerException("Invalid substitution expression. Type help massrename for info!\n");
		}
	}

	/**
	 * Checks whether {@link #nextToken()} was called after everything was
	 * read(EOF).
	 * 
	 * @throws LexerException if user tries to read after {@link TokenType#EOF}.
	 */
	private void checkAfterEOF() {
		if (Objects.nonNull(token) && token.getType() == TokenType.EOF) {
			throw new LexerException("Reading after EOF!\n");
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
	 * Checks whether on {@link #currentIndex} + 1 position there is a sign.
	 * 
	 * @return {@code true} if there is a sign, otherwise {@code false}.
	 */
	private boolean checkNextSign() {
		return currentIndex + 1 < data.length;
	}
}
