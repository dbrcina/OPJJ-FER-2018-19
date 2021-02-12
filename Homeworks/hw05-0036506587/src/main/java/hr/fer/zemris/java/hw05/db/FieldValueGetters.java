package hr.fer.zemris.java.hw05.db;

/**
 * This class provides a simple implementation of getters for
 * {@link StudentRecord} field values.
 * 
 * @author dbrcina
 * @version 1.0
 *
 */
public class FieldValueGetters {

	/**
	 * Constant used for {@link StudentRecord#getFirstName()}.
	 */
	public static final IFieldValueGetter FIRST_NAME = record -> record.getFirstName();

	/**
	 * Constant used for {@link StudentRecord#getLastName()}.
	 */
	public static final IFieldValueGetter LAST_NAME = record -> record.getLastName();

	/**
	 * Constant used for {@link StudentRecord#getJmbag()}.
	 */
	public static final IFieldValueGetter JMBAG = record -> record.getJmbag();

}
