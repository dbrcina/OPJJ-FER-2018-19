package hr.fer.zemris.java.hw05.db;

/**
 * This class provides an implementation of operators for comparison of two
 * <code>String</code> type values.
 * 
 * @author dbrcina
 * @version 1.0
 *
 */
public class ComparisonOperators {

	/**
	 * Constant representing sign * used for {@link #LIKE} operator.
	 */
	private static final String WILDCARD = "*";

	/**
	 * Constant representing operator (<).
	 */
	public static final IComparisonOperator LESS = (s1, s2) -> s1.compareTo(s2) < 0;

	/**
	 * Constant representing operator (<=).
	 */
	public static final IComparisonOperator LESS_OR_EQUALS = (s1, s2) -> s1.compareTo(s2) <= 0;

	/**
	 * Constant representing operator (>).
	 */
	public static final IComparisonOperator GREATER = (s1, s2) -> s1.compareTo(s2) > 0;

	/**
	 * Constant representing operator (>=).
	 */
	public static final IComparisonOperator GREATER_OR_EQUALS = (s1, s2) -> s1.compareTo(s2) >= 0;

	/**
	 * Constant representing operator (=).
	 */
	public static final IComparisonOperator EQUALS = String::equals;

	/**
	 * Constant representing operator (!=).
	 */
	public static final IComparisonOperator NOT_EQUALS = (s1, s2) -> !s1.equals(s2);

	/**
	 * Constant representing operator (like) which checks whether given pattern
	 * <code>s2</code> is in <code>s1</code>.
	 */
	public static final IComparisonOperator LIKE = (s1, s2) -> {
		int indexOfWildcard = s2.indexOf(WILDCARD);

		// if there is a wildcard sign in patter..
		if (indexOfWildcard != -1) {

			// check whether there are more than one wildcard sign.
			if (s2.lastIndexOf(WILDCARD) != indexOfWildcard) {
				throw new IllegalArgumentException(
						"U patternu postoji više od jednog wildcard(" + WILDCARD + ") znaka!");
			}

			// check whether pattern has more chars.
			if (s1.length() < s2.length() - 1) {
				return false;
			}

			// check whether pattern is only wildcard sign.
			if (indexOfWildcard == 0 && s2.length() == 1) {
				return true;
			}

			boolean equals = true;

			// if the wildcard is at the begining.
			if (s2.startsWith(WILDCARD)) {
				// chars at the end have to be equal
				equals &= s1.charAt(s1.length() - 1) == s2.charAt(s2.length() - 1);

				String subS = s1.substring(1, s1.length());

				for (int i = 1; i < s2.length() - 1; i++) {
					equals &= subS.contains(String.valueOf(s2.charAt(i)));
				}
			}

			// at the end.
			else if (s2.endsWith(WILDCARD)) {
				// chars at the begining have to be equal
				equals &= s1.charAt(0) == s2.charAt(0);

				for (int i = 1; i < s2.length() - 1; i++) {
					equals &= s1.charAt(i) == s2.charAt(i);
				}
			}

			// somwhere in the middle.
			else {
				// chars at the begining have to be equal
				equals &= s1.charAt(0) == s2.charAt(0);

				// chars at the end have to be equal
				equals &= s1.charAt(s1.length() - 1) == s2.charAt(s2.length() - 1);

				// substring from 2nd char to index of wildcard
				String subS = s1.substring(1, indexOfWildcard);

				for (int i = 1; i < indexOfWildcard; i++) {
					equals &= subS.contains(String.valueOf(s2.charAt(i)));
				}

				// substring from index of wildcard to the one before end
				subS = s1.substring(indexOfWildcard, s1.length());

				for (int i = indexOfWildcard + 1; i < s2.length() - 2; i++) {
					equals &= subS.contains(String.valueOf(s2.charAt(i)));
				}
			}
			return equals;
		}

		// there is not a wildcard sign in a pattern.
		else {
			return s1.equals(s2);
		}
	};

}
