package hr.fer.zemris.java.custom.collections;

/**
 * A model of object that for any object checks whether the given object is
 * acceptable or not.
 * 
 * @author Darijo Brčina
 * @version 1.0
 *
 */
public interface Tester {

	/**
	 * Checks if the given {@code obj} is acceptable or not. Returns true if it is
	 * or false otherwise.
	 * 
	 * @param obj an Object that needs to be tested
	 * @return true if the {@code obj} is acceptable or false if it isn't.
	 */
	boolean test(Object obj);
}
