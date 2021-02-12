package hr.fer.zemris.java.custom.scripting.lexer;

import java.util.Objects;

import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;

/**
 * This class is used for parsing text into {@link SmartScriptToken} tokens. It
 * has two states: TEXT and TAG. Lexer is lazy, which means it only tokenizes
 * input when it is needed.
 * 
 * @author dbrcina
 * @version 1.0
 *
 */
public class SmartScriptLexer {

	/**
	 * Constant for \ sign.
	 */
	private static final char ESCAPE = '\\';

	/**
	 * Constant for _ sign.
	 */
	private static final char UNDERSCORE = '-';

	/**
	 * Constant for = sign.
	 */
	private static final char EQUALS_TAG = '=';

	/**
	 * Constant for @ sign.
	 */
	private static final char FUNCTION_START = '@';

	/**
	 * Constant for " sign.
	 */
	private static final char STRING_CTRL = '"';

	/**
	 * Constants for operators.
	 */
	private static final String VALID_OPERATORS = "+-*/^";

	/**
	 * Constants for spaces.
	 */
	private static final String SPACE = "\n\r\t";

	/**
	 * Input script.
	 */
	private char[] data;

	/**
	 * Current token.
	 */
	private SmartScriptToken token;

	/**
	 * Index of first unfinished token.
	 */
	private int currentIndex;

	/**
	 * Lexer's current state.
	 */
	private SmartScriptLexerState state;

	/**
	 * A constructor which initializes lexer's properties and stores input
	 * <code>text</code>. If <code>text</code> is <code>null</code>, an appropriate
	 * exception is thrown.
	 * 
	 * @param text A input that needs to be parsed.
	 * @throws NullPointerException if the given {@code text} is {@code null}.
	 */
	public SmartScriptLexer(String text) {
		Objects.requireNonNull(text, "Tekst ne smije biti null!");
		data = text.toCharArray();
		state = SmartScriptLexerState.TEXT;
	}

	/**
	 * Returns next token as described by lexer's rules.
	 * 
	 * @return next token.
	 */
	public SmartScriptToken nextToken() {

		// checks whether this method was called after EOF
		checkAfterEOF();

		// determine whether lexer needs to parse ordinary text or stuff inside a tag
		switch (state) {
		case TEXT:
			parseText();
			break;
		case TAG:
			parseTag();
			break;
		default: // this will probbably never execute, but it is here for safety reasons
			throw new SmartScriptLexerException("Nepoznato stanje!");
		}

		return token;
	}

	/**
	 * Parses text that is outside of a tag as determined by
	 * {@link SmartScriptParser}'s rules.
	 * <ul>
	 * <li>A word consist of one or more letter chars. A char is a letter if
	 * {@link Character#isLetter(char)} returns <code>true</code>.</li>
	 * <li>A number consist of one or more digits and it needs to be displayed as
	 * {@link Long}.</li>
	 * <li>A symbol is every individual sign which is obtained when every number,
	 * word and space ('\r','\n','\t', ' ') is removed from token.</li>
	 * <li>Space chars don't generate any token (they are ignored).</li>
	 * <li><code>"\\"</code> treat as '\' and <code>"\{"</code> treat as '{'. Every
	 * other sequence which starts with '\' should throw an exception.</li>
	 * </ul>
	 */
	private void parseText() {

		// checks whether there are more signs.
		if (!checkForMoreSigns()) {
			createFinalToken();
			return;
		}

		StringBuilder sb = new StringBuilder();

		while (checkForMoreSigns()) {

			// check for right escape sign
			if (data[currentIndex] == '\\') {
				currentIndex++;
				if (checkForMoreSigns() && (data[currentIndex] == '\\' || data[currentIndex] == '{')) {
					sb.append(data[currentIndex++]);
				} else {
					throw new SmartScriptLexerException("Nepravilo escapeanje teksta!");
				}
			}

			// check whether there is begintag {$
			else if (data[currentIndex] == '{') {
				if (oneMore() && data[currentIndex + 1] != '$') {
					sb.append(data[currentIndex++]);
					continue;
				}
				if (oneMore() && data[currentIndex + 1] == '$' && sb.length() != 0) {
					break;
				}
				if (oneMore() && data[currentIndex + 1] == '$' && sb.length() == 0) {
					sb.append(String.valueOf(data, currentIndex, 2));
					currentIndex += 2;
					break;
				} else {
					throw new SmartScriptLexerException("Tag nije dobro otvoren!");
				}
			} else {
				sb.append(data[currentIndex++]);
			}
		}

		String text = sb.toString();

		if (text.equals("{$")) {
			token = new SmartScriptToken(SmartScriptTokenType.BEGIN_TAG, text);
		} else {
			token = new SmartScriptToken(SmartScriptTokenType.TEXT, text);
		}
	}

	/**
	 * Parses text that is inside of a tag as determined by
	 * {@link SmartScriptParser}'s rules.
	 * <ul>
	 * <li>When parsing content inside tag, lexer should extract as many characters
	 * as possible into each token.</li>
	 * <li>Spaces between tokens are ignorable and generally not required.</li>
	 * <li>Decimal numbers are only recognized in <b>digits-dot-digits</b> format,
	 * but not in scientific notation.</l>
	 * <li>Minus sign treat as symbol if immediately after it there is no digit.
	 * Only when immediately after it(no spaces between) a digit follows, treat it
	 * as a part of negative number.</li>
	 * <li>In strings which are part of tags(and only in strings!) the following
	 * escaping should be accepted:
	 * <ul>
	 * <li>"\\" sequence treat as a single strint character '\'</li>
	 * <li>"\"" treat as a single string character " (and not the end of the
	 * string)</li>
	 * <li>'\n', '\r' and '\t' have its usual meaning.</li>
	 * <li>Every other sequence which starts with '\' should be treated as invalid
	 * and throw an exception.</li>
	 * </ul>
	 * </li>
	 * </ul>
	 * 
	 */
	private void parseTag() {

		// checks whether tag is empty
		if (data.length == 0) {
			throw new SmartScriptLexerException("Nema ničeg u tagu!");
		}

		// checks whether there are more signs in tag.
		if (!checkForMoreSigns()) {
			createFinalToken();
			return;
		}

		skipBlanks();

		// check for tag name = or variable
		if (checkForMoreSigns() && (data[currentIndex] == EQUALS_TAG || Character.isLetter(data[currentIndex]))) {
			token = new SmartScriptToken(
					data[currentIndex] == EQUALS_TAG ? SmartScriptTokenType.EQUALS_SIGN : SmartScriptTokenType.VARIABLE,
					parseVariable());
			return;
		}

		// check for numbers
		// here lexer checks whether current sign is a number or current sign is - and
		// second some number
		else if (checkForMoreSigns() && Character.isDigit(data[currentIndex])
				|| data[currentIndex] == '-' && oneMore() && Character.isDigit(data[currentIndex + 1])) {
			String number = parseNumber();
			try {
				if (number.contains(".")) {
					token = new SmartScriptToken(SmartScriptTokenType.DOUBLE, Double.parseDouble(number));
				} else {
					token = new SmartScriptToken(SmartScriptTokenType.INTEGER, Integer.parseInt(number));
				}
			} catch (NumberFormatException e) {
				throw new SmartScriptLexerException("Brojevi se ne mogu parsirat!");
			}
		}

		// check for function
		else if (checkForMoreSigns() && data[currentIndex] == FUNCTION_START) {
			token = new SmartScriptToken(SmartScriptTokenType.FUNCTION, parseFunction());
			return;
		}

		// check for string
		else if (checkForMoreSigns() && data[currentIndex] == STRING_CTRL) {
			token = new SmartScriptToken(SmartScriptTokenType.STRING, parseString());
			return;
		}

		// check for operators
		else if (checkForMoreSigns() && VALID_OPERATORS.contains(Character.toString(data[currentIndex]))) {
			token = new SmartScriptToken(SmartScriptTokenType.OPERATOR, Character.toString(data[currentIndex++]));
			return;
		}

		// check for end tag $}
		else if (checkForMoreSigns() && data[currentIndex] == '$' && oneMore() && data[currentIndex + 1] == '}') {
			currentIndex += 2;
			token = new SmartScriptToken(SmartScriptTokenType.END_TAG, "$}");
			return;
		}

		else {
			throw new SmartScriptLexerException("Ne prepoznajem tipove token!");
		}
	}

	/**
	 * Helper method that parses the current variable.
	 * 
	 * @return String representation of right variable.
	 * @throws SmartScriptLexerException if the variable name is invalid.
	 */
	private String parseVariable() throws SmartScriptLexerException {
		// check whether '=' sign..
		if (data[currentIndex] == EQUALS_TAG) {
			currentIndex++;
			return "=";
		}

		int startIndex = currentIndex;

		if (Character.isLetter(data[currentIndex])) {
			currentIndex++;
			while (checkForMoreSigns() && rightName()) {
				currentIndex++;
			}
		}

		// if name is invalid, this exception is thrown
		if (startIndex == currentIndex) {
			throw new SmartScriptLexerException("Neispravno ime za varijablu/funkciju!");
		}

		return new String(data, startIndex, currentIndex - startIndex);
	}

	/**
	 * Helper method which parses current function.
	 * 
	 * @return String representation of right function.
	 * @throws SmartScriptLexerException if the function name is invalid.
	 */
	private String parseFunction() throws SmartScriptLexerException {
		// skip @ sign
		currentIndex++;
		return parseVariable();
	}

	/**
	 * Helper method which parses string inside of a tag.
	 * 
	 * @return String representation of a string text in a tag.
	 * @throws SmartScriptLexerException if string is invalid.
	 */
	private String parseString() {
		StringBuilder sb = new StringBuilder();

		// skip first '"'
		currentIndex++;

		while (checkForMoreSigns()) {

			// check escape in string
			if (data[currentIndex] == ESCAPE) {
				currentIndex++;
				try {
					sb = rightEscape(sb);
				} catch (Exception e) {
					throw new SmartScriptLexerException(e.getMessage());
				}
			}

			// check if string is over
			else if (data[currentIndex] == STRING_CTRL) {
				currentIndex++;
				break;
			}

			else {
				sb.append(data[currentIndex++]);
			}
		}

		String text = sb.toString();

		if (text.length() == 0) {
			throw new SmartScriptLexerException("String u tagu je nepravilno zadan!");
		} else {
			return text;
		}
	}

	/**
	 * Helper method that parses numbers inside of a tag.
	 * 
	 * @return String representation of a number.
	 */
	private String parseNumber() {
		int startIndex = currentIndex;
		if (data[currentIndex] == '-') {
			currentIndex++;
		}

		while (checkForMoreSigns() && Character.isDigit(data[currentIndex])
				|| data[currentIndex] == '.' && oneMore() && Character.isDigit(data[currentIndex + 1])) {
			currentIndex++;
		}

		return new String(data, startIndex, currentIndex - startIndex);
	}

	/**
	 * Helper method that checks whether escaping in string is correct.
	 * 
	 * @param sb StringBuilder
	 * @return StringBuilder filled with rigth sign.
	 * @throws SmartScriptLexerException if escaping is incorrect.
	 */
	private StringBuilder rightEscape(StringBuilder sb) throws SmartScriptLexerException {
		if (checkForMoreSigns() && (data[currentIndex] == ESCAPE || data[currentIndex] == STRING_CTRL
				|| Character.toString(data[currentIndex]).matches("[nrt]"))) {
			if (data[currentIndex] == 'n') {
				sb.append('\n');
			} else if (data[currentIndex] == 'r') {
				sb.append('\r');
			} else if (data[currentIndex] == 't') {
				sb.append('\t');
			} else {
				sb.append(data[currentIndex]);
			}
			currentIndex++;
			return sb;
		} else {
			throw new SmartScriptLexerException("Nepravilno escapeanje!");
		}
	}

	/**
	 * Checks whether sign's name is valid. Valid name stars by letter and after
	 * follows zero or more letters, digits or underscores.
	 * 
	 * @return {@code true} if the name is valid, otherwise {@code false}.
	 */
	private boolean rightName() {
		char sign = data[currentIndex];
		return (Character.isLetter(sign) || Character.isDigit(sign) || sign == UNDERSCORE)
				&& !Character.toString(sign).contains(SPACE) && sign != STRING_CTRL
				&& !VALID_OPERATORS.contains(String.valueOf(sign));
	}

	/**
	 * Checks whether {@link #nextToken()} was called after everything was
	 * read(EOF).
	 * 
	 * @throws SmartScriptLexerException if user tries to read after
	 *                                   {@link SmartScriptTokenType#EOF}.
	 */
	private void checkAfterEOF() {
		if (Objects.nonNull(token) && token.getType() == SmartScriptTokenType.EOF) {
			throw new SmartScriptLexerException("Nema vise tokena!");
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
	 * Checks whether there are more signs on position
	 * <code>{@link #currentIndex} + 1</code>.
	 * 
	 * @return {@code true} if there are more signs, otherwise {@code false}.
	 */
	private boolean oneMore() {
		return currentIndex + 1 < data.length;
	}

	/**
	 * Creates the last token representing {@link SmartScriptTokenType#EOF}.
	 */
	private void createFinalToken() {
		token = new SmartScriptToken(SmartScriptTokenType.EOF, null);
	}

	/**
	 * Method that skips all the blanks in the input text.
	 */
	private void skipBlanks() {
		while (currentIndex < data.length) {
			char c = data[currentIndex];
			if (c == ' ' || c == '\n' || c == '\r' || c == '\t') {
				currentIndex++;
				continue;
			}
			break;
		}
	}

	/**
	 * Getter for current token.
	 * 
	 * @return The current token.
	 */
	public SmartScriptToken getToken() {
		return token;
	}

	/**
	 * Getter for current lexer's state.
	 * 
	 * @return The current lexer's state.
	 */
	public SmartScriptLexerState getState() {
		return state;
	}

	/**
	 * Method that provides user to set lexer's state to <code>state</code>.
	 * 
	 * @param state A {@link SmartScriptTokenType} variable.
	 * @throws NullPointerException if the {@code state} is {@code null}.
	 */
	public void setState(SmartScriptLexerState state) {
		Objects.requireNonNull(state, "State ne smije biti null!");
		this.state = state;
	}

}
