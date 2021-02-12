package hr.fer.zemris.java.custom.collections;

/**
 * A model of object that for any object checks whether the given object is
 * acceptable or not.
 * 
 * @author dbrcina
 * @version 1.0
 * @param <T> the type of the input to the test.
 *
 */
public interface Tester<T> {

	/**
	 * Checks if the given {@code obj} is acceptable or not. Returns true if it is
	 * or false otherwise.
	 * 
	 * @param obj a T type variable that needs to be tested.
	 * @return {@code true} if the {@code obj} is acceptable or {@code false} if it
	 *         isn't.
	 */
	boolean test(T obj);
}
