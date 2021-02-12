package hr.fer.zemris.java.hw05.db;

/**
 * Interface that provides only one method {@link #get(StudentRecord)}. It is
 * responsible for obtaining a requested field value from given
 * {@link StudentRecord}.
 * 
 * @author dbrcina
 * @version 1.0
 *
 */
@FunctionalInterface
public interface IFieldValueGetter {

	/**
	 * This method takes one {@link StudentRecord} <code>record</code> as an
	 * argument and returns the requested field value from it.
	 * 
	 * @param record student's record
	 * @return requested field value.
	 * @throws NullPointerException if {@code record} is {@code null}.
	 */
	String get(StudentRecord record);
}
