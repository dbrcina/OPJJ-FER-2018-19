package hr.fer.zemris.java.hw03.prob1;

import java.util.Objects;

/**
 * This class is used for parsing text into {@link Token} tokens. It has two
 * states: BASIC(default one) and EXTENDED. Lexer is lazy, which means it only
 * tokenizes input when it is needed.
 * 
 * @author dbrcina
 * @version 1.0
 *
 */
public class Lexer {

	/**
	 * Input text.
	 */
	private char[] data;

	/**
	 * Temporary token.
	 */
	private Token token;

	/**
	 * Index of the first unfinished token.
	 */
	private int currentIndex;

	/**
	 * Lexer's state.
	 */
	private LexerState state;

	/**
	 * Construct that accepts a {@code text} as {@link String} that needs to be
	 * tokenized. {@code text} cannot be {@code null}.
	 * 
	 * @param text text that needs to be tokenized.
	 * @throws NullPointerException if the {@code text} is {@code null}.
	 */
	public Lexer(String text) {
		Objects.requireNonNull(text, "Tekst je null!");
		data = text.toCharArray();
		state = LexerState.BASIC;
	}

	/**
	 * Generates and returns the next token. Throws an appropriate exception if
	 * something goes wrong with reading (LexerException).
	 * 
	 * @return next token.
	 * @throws LexerException if there are no more tokens.
	 */
	public Token nextToken() {
		tokenizeNextToken();
		return token;
	}

	/**
	 * Getter for current token.
	 * 
	 * @return current token
	 */
	public Token getToken() {
		return token;
	}

	/**
	 * Setter for lexer's state.
	 * 
	 * @param state To be set.
	 * @throws NullPointerException if the {@code state} is {@code null}.
	 */
	public void setState(LexerState state) {
		Objects.requireNonNull(state);
		this.state = state;
	}

	/**
	 * Tokenizes next token as determined by {@link #state}.
	 */
	private void tokenizeNextToken() {
		if (Objects.nonNull(token) && token.getType() == TokenType.EOF) {
			throw new LexerException("Nema vise tokena!");
		}

		skipBlanks();

		// checks whether there are more signs.
		if (!checkForMoreSigns()) {
			createFinalToken();
			return;
		}

		switch (state) {
		case BASIC:
			basicState();
			break;
		case EXTENDED:
			extendedState();
			break;
		default:
			throw new LexerException("Neodređeno stanje");
		}

	}

	/**
	 * Method that parses text as determined by {@link LexerState#BASIC}.
	 * <ul>
	 * <li>A word is every sequence that contains one or more signs over whom
	 * {@link Character#isLetter(char)} returns true.</li>
	 * <li>A number is every sequence that contains one or more digits and it needs
	 * to be displayed as {@link Long}.</li>
	 * <li>A symbol is every individual sign which is obtained when every number,
	 * word and space ('\r','\n','\t', ' ') is removed from token.</li>
	 * <li>Space chars don't generate any token (they are ignored).</li>
	 * <li>"\1" is acceptable and it is a part of a word. This works for every
	 * number.</li>
	 * <li>"\\" is acceptable and it represents '\'. Every other kind of escaping is
	 * not supported.</li>
	 * </ul>
	 * 
	 * @throws LexerException if something is wrong.
	 */
	private void basicState() {
		int startIndex = currentIndex;
		int[] toSkip = new int[data.length];
		startIndex = fillToken(startIndex, toSkip, 0);
		if (startIndex == currentIndex) {
			while (checkForMoreSigns() && Character.isDigit(data[currentIndex])) {
				currentIndex++;
			}
			if (startIndex == currentIndex) {
				token = new Token(TokenType.SYMBOL, data[currentIndex++]);
			} else {
				try {
					token = new Token(TokenType.NUMBER,
							Long.parseLong(new String(data, startIndex, currentIndex - startIndex)));
				} catch (NumberFormatException e) {
					throw new LexerException("Broj se ne može prikazati pomoću Long razreda!");
				}
			}
		} else {
			String text = "";
			for (int i = 0; i < toSkip.length; i++) {
				if (toSkip[i] == 0) {
					break;
				}
				text += new String(data, startIndex, toSkip[i] - startIndex);
				startIndex += toSkip[i] - startIndex + 1;
			}
			text += new String(data, startIndex, currentIndex - startIndex);
			token = new Token(TokenType.WORD, text);
		}
	}

	/**
	 * Method that parses text as determined by {@link LexerState#EXTENDED}.
	 * <ul>
	 * <li>Every word,number or symbol is now treated as a one sequence.</li>
	 * <li>There are no escape signs becuase every sign is equal.</li>
	 * <li>Words are separated by spaces.</li>
	 */
	private void extendedState() {
		if (data[currentIndex] == '#') {
			token = new Token(TokenType.SYMBOL, data[currentIndex++]);
			return;
		}
		int startIndex = currentIndex;
		while (checkForMoreSigns() && data[currentIndex + 1] != '#' && !Character.isSpaceChar(data[currentIndex + 1])) {
			currentIndex++;
		}
		token = new Token(TokenType.WORD, new String(data, startIndex, ++currentIndex - startIndex));

	}

	/**
	 * Helper method which keeps track on where are escape signs if they exist and
	 * returns <code>startingIndex</code> from where token's value should be.
	 * 
	 * @param startIndex Index from where chars need to be copied.
	 * @param toSkip     Array that keeps indexes of escape signs.
	 * @param counter
	 * @return {@code startIndex} from where chars need to be copied.
	 */
	private int fillToken(int startIndex, int[] toSkip, int counter) {
		while (checkForMoreSigns() && Character.isLetter(data[currentIndex])) {
			currentIndex++;
		}
		if (!checkForMoreSigns()) {
			return startIndex;
		}
		while (data[currentIndex] == '\\') {
			toSkip[counter++] = currentIndex;
			currentIndex++;
			if (!checkForMoreSigns()) {
				throw new LexerException();
			}
			if (Character.isDigit(data[currentIndex])) {
				try {
					Long.parseLong(new String(data, currentIndex, 1));
				} catch (NumberFormatException e) {
					throw new LexerException(
							"Broj " + data[currentIndex] + " se ne može prikazati pomoću Long razreda!");
				}
			} else if (data[currentIndex] != '\\') {
				throw new LexerException();
			}
			currentIndex++;
		}
		if (Character.isLetter(data[currentIndex])) {
			startIndex = fillToken(startIndex, toSkip, counter);
		}
		return startIndex;
	}

	/**
	 * Checks whether there are any signs left to be tokenize.
	 * 
	 * @return {@code true} if there are more signs, otherwise {@code false};
	 */
	private boolean checkForMoreSigns() {
		return currentIndex < data.length;
	}

	/**
	 * Creates the token whose type represents {@link TokenType#EOF};
	 */
	private void createFinalToken() {
		token = new Token(TokenType.EOF, null);
	}

	/**
	 * Method that skips all the blanks in the input text.
	 */
	private void skipBlanks() {
		while (currentIndex < data.length) {
			char c = data[currentIndex];
			if (c == '\r' || c == '\n' || c == '\t' || c == ' ') {
				currentIndex++;
				continue;
			}
			break;
		}
	}

}
