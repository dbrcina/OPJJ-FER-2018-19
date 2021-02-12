package searching.slagalica;

import java.util.Arrays;

/**
 * Configuration of one state of {@link Slagalica}.
 * 
 * @author dbrcina
 *
 */
public class KonfiguracijaSlagalice {

	/**
	 * Constant representing number 0 as a space value.
	 */
	private static final int SPACE_VALUE = 0;

	/**
	 * Private configuration.
	 */
	private int[] configuration;

	/**
	 * Variable that keeps track where is space value.
	 */
	private int indexOfSpace = -1;

	/**
	 * Constructor.
	 * 
	 * @param configuration configuration.
	 */
	public KonfiguracijaSlagalice(int[] configuration) {
		this.configuration = configuration;
		findSpaceIndex();
	}

	/**
	 * Helper method used for finding index of space value.
	 */
	private void findSpaceIndex() {
		for (int i = 0; i < configuration.length; i++) {
			if (configuration[i] == SPACE_VALUE) {
				indexOfSpace = i;
				return;
			}
		}
	}

	/**
	 * Getter for slagalica's configuration.
	 * 
	 * @return a copy of {@link #configuration}.
	 */
	public int[] getPolje() {
		return Arrays.copyOf(configuration, configuration.length);
	}

	/**
	 * Getter for index of space value.
	 * 
	 * @return index of {@link #SPACE_VALUE} inside a configuration, otherwise
	 *         {@code -1}.
	 */
	public int indexOfSpace() {
		return indexOfSpace;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < configuration.length; i++) {
			sb.append((configuration[i] == 0 ? "*" : configuration[i]) + " ");
			if (i % 3 == 2) {
				sb.append("\n");
			}
		}
		return sb.toString();
	}
}
