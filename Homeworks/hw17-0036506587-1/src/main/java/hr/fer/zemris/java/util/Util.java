package hr.fer.zemris.java.util;

/**
 * Utility class which provides {@link #parseWord(String)} method.
 * 
 * @author dbrcina
 *
 */
public class Util {

//	/**
//	 * Method used for reading content from provided <i>file</i> and executing
//	 * provided <i>action</i>.
//	 * 
//	 * @param file   file that needs to be read.
//	 * @param action action which needs to be executed.
//	 * @throws IOException if something goes wrong while reading from a file.
//	 */
//	public static void readFromFile(Path file, Consumer<String> action) throws IOException {
//		try (BufferedReader br = new BufferedReader(Files.newBufferedReader(file))) {
//			String line = null;
//			while ((line = br.readLine()) != null) {
//				String[] words = line.split("\\s+");
//				for (String word : words) {
//					if (word.isEmpty()) {
//						continue;
//					}
//					word = word.toLowerCase().trim();
//					String[] parsedWord = parseWord(word);
//					for (String result : parsedWord) {
//						if (result.isEmpty() || stopwords.contains(result)) {
//							continue;
//						}
//						action.accept(result);
//					}
//				}
//			}
//		}
//	}

	/**
	 * Method which is used for parsing provided <i>word</i>.<br>
	 * Valid characters are all characters for which
	 * {@link Character#isAlphabetic(int)} method returns <i>true</i>.<br>
	 * For instance; word <i>"pero27strasni"</i> should be parsed into two words:
	 * <i>pero</i> and <i>strasni</i> and thats why a return type is an instance of
	 * array.
	 * 
	 * @param word word.
	 * @return an array of parsed word(s).
	 */
	public static String[] parseWord(String word) {
		if (word.isEmpty()) {
			return new String[0];
		}
		StringBuilder sb = new StringBuilder();
		char[] data = word.toCharArray();
		boolean addSpace = false;
		for (char c : data) {
			if (Character.isAlphabetic(c)) {
				sb.append(c);
				if (!addSpace) {
					addSpace = true;
				}
			} else {
				if (addSpace) {
					sb.append("\n");
					addSpace = false;
				}
			}
		}
		return sb.toString().split("\n");
	}
}
