package hr.fer.zemris.java.hw05.db;

/**
 * Interface that provides only one method {@link #satisfied(String, String)}.
 * It is used when comparing two <code>Strings</code>.
 * 
 * @author dbrcina
 * @version 1.0
 *
 */
@FunctionalInterface
public interface IComparisonOperator {

	/**
	 * Checks whether <code>value1</code> is satisfied by <code>value2</code>. In
	 * other words, methods checks whether <code>value2</code> is somehow in
	 * <code>value1</code>.
	 * 
	 * @param value1 the input string
	 * @param value2 the pattern.
	 * @return {@code true} if {@code value2} satisfies {@code value1}, otherwise
	 *         {@code false}.
	 */
	boolean satisfied(String value1, String value2);
}
