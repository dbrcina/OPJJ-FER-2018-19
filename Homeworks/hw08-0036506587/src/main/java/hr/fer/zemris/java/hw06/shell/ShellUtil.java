package hr.fer.zemris.java.hw06.shell;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Character.*;

/**
 * This class is used as helper class for instances of {@link ShellCommand}. It
 * provides methods which could be used for parsing paths and names that user
 * input in console.
 * 
 * @author dbrcina
 *
 */
public class ShellUtil {

	/**
	 * Constant representing '"' sign.
	 */
	private static final char QUOTE = '"';

	/**
	 * Constant representing '\' sign.
	 */
	private static final char ESCAPE = '\\';

	/**
	 * Constant representin '$' sign.
	 */
	private static final char DOLLAR_SIGN = '$';

	/**
	 * Constant representing '}' sign.
	 */
	private static final char CLOSED_BRACKET = '}';

	/**
	 * Method used for parsing given <code>arguments</code> into independent
	 * arguments.
	 * 
	 * @param arguments arguments.
	 * @return list of independent arguments.
	 */

	// znam da sam ovu metodu trebao koristiti za sve naredbe koje primaju neki
	// argument, ali napisao sam ju tek pri radu massrename naredbe tako da nisam
	// stigao popraviti implementacije ostalih naredbi da koriste baš ovu
	// metodu..kada uhvatim vremena, popravit ću implementacije svih naredbi

	public static List<String> parseArguments(String arguments) {
		List<String> parsedArguments = new ArrayList<>();
		char[] data = arguments.toCharArray();
		int currentIndex = 0;

		while (checkForMoreSigns(currentIndex, data)) {
			StringBuilder sb = new StringBuilder();

			currentIndex = skipBlanks(currentIndex, data);

			// check if string is provided
			if (data[currentIndex] == QUOTE) {
				currentIndex++;
				while (checkForMoreSigns(currentIndex, data) && data[currentIndex] != QUOTE) {

					// check escaping
					if (data[currentIndex] == ESCAPE && checkForMoreSigns(currentIndex + 1, data)) {
						if (data[currentIndex + 1] == ESCAPE || data[currentIndex + 1] == QUOTE) {
							currentIndex++;
						}
					}
					sb.append(data[currentIndex++]);
				}

				// check if ending of quotation is valid
				if (!checkForMoreSigns(currentIndex, data)
						|| (checkForMoreSigns(currentIndex + 1, data)) && !isWhitespace(data[currentIndex + 1])) {
					throw new IllegalArgumentException("Invalid quotation!\n");
				}

				currentIndex++;
			}

			// otherwise, regular text is provided
			else {
				while (checkForMoreSigns(currentIndex, data) && !isWhitespace(data[currentIndex])) {
					if (data[currentIndex] == DOLLAR_SIGN) {
						while (checkForMoreSigns(currentIndex, data) && data[currentIndex] != CLOSED_BRACKET) {
							sb.append(data[currentIndex++]);
						}
					}
					sb.append(data[currentIndex++]);
				}
			}

			parsedArguments.add(sb.toString());
		}

		if (parsedArguments.stream().filter(s -> s.contains(String.valueOf(QUOTE))).count() % 2 != 0) {
			throw new IllegalArgumentException("Invalid quotation!\n");
		}
		return parsedArguments;
	}

	/**
	 * Helper method used for skipping blanks in given <code>data</code> array.
	 * 
	 * @param currentIndex current index.
	 * @param data         char array.
	 * @return index of first non blank char.
	 */
	private static int skipBlanks(int currentIndex, char[] data) {
		while (checkForMoreSigns(currentIndex, data) && isWhitespace(data[currentIndex])) {
			currentIndex++;
		}
		return currentIndex;
	}

	/**
	 * Helper method used for checking whether there are more signs in
	 * <code>data</code> array that needs to be parsed.
	 * 
	 * @param currentIndex current index.
	 * @param data         char array.
	 * @return {@code true} if there are more signs, otherwise {@code false}.
	 */
	private static boolean checkForMoreSigns(int currentIndex, char[] data) {
		return currentIndex < data.length;
	}

	/**
	 * Method used for parsing given <code>path</code> into valid path.
	 * 
	 * @param path path.
	 * @return {@link String} representation of {@code path}.
	 * @throws IllegalArgumentException if the given {@code path} is invalid.
	 */
	public static String parsePath(String path) {
		if (path.startsWith("\"")) {
			int currentIndex = 1;
			StringBuilder sb = new StringBuilder();

			while (true) {

				if (currentIndex == path.length()) {
					throw new IllegalArgumentException("Given path is invalid!\n");
				}

				if (path.charAt(currentIndex) == '\\' && oneMore(currentIndex, path)) {
					if (path.charAt(currentIndex + 1) == '\"' && oneMore(currentIndex + 1, path)
							|| path.charAt(currentIndex + 1) == '\\') {
						sb.append(path.charAt(++currentIndex));
					}
				}

				if (path.charAt(currentIndex) == '\"') {
					currentIndex++;
					break;
				}

				sb.append(path.charAt(currentIndex++));
			}

			if (currentIndex == path.length() || isSpaceChar(path.charAt(currentIndex))) {
				return sb.toString();
			} else {
				throw new IllegalArgumentException("Invalid quotation!\n");
			}
		}

		return path;
	}

	/**
	 * Method used for parsing given <code>line</code> into valid name.
	 * 
	 * @param line line.
	 * @return {@link String} representation of valid name.
	 */
	public static String parseName(String line) {
		if (!line.contains(" ")) {
			return line;
		}
		return line.substring(0, line.indexOf(" "));
	}

	/**
	 * Helper method that returns status of this command and print message to
	 * console.
	 * 
	 * @param env environment.
	 * @param msg message
	 * @return {@link ShellStatus#CONTINUE}.
	 */
	public static ShellStatus status(Environment env, String msg) {
		env.write(msg + "\n" + env.getPromptSymbol() + " ");
		return ShellStatus.CONTINUE;
	}

	/**
	 * Helper method that checks whether there are more chars in given
	 * <code>path</code> at <code>currentIndex + 1</code> position.
	 * 
	 * @param currentIndex representing current position.
	 * @param path         path
	 * @return {@code true} if there are more chars, otherwise {@code false}.
	 */
	private static boolean oneMore(int currentIndex, String path) {
		return currentIndex + 1 < path.length();
	}

}
