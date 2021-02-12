package hr.fer.zemris.java.hw05.db;

/**
 * Interface that provides only one method {@link #accepts(StudentRecord)}. It
 * is used for filtering data.
 * 
 * @author dbrcina
 * @version 1.0
 *
 */
@FunctionalInterface
public interface IFilter {

	/**
	 * Checks whether given {@link StudentRecord} <code>record</code> is acceptable.
	 * 
	 * @param record student's record.
	 * @return {@code true} if {@code record} is acceptable, otherwise
	 *         {@code false}.
	 */
	boolean accepts(StudentRecord record);
}
