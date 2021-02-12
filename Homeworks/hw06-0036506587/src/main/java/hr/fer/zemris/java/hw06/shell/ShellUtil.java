package hr.fer.zemris.java.hw06.shell;

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

				if (currentIndex == 1 && !Character.isLetter(path.charAt(currentIndex))
						|| currentIndex == path.length()) {
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

			if (currentIndex == path.length() || Character.isSpaceChar(path.charAt(currentIndex))) {
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
