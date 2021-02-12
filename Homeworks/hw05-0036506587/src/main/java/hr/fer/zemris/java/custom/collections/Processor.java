package hr.fer.zemris.java.custom.collections;

/**
 * Processor here represents an conceptual contract between clients which will
 * have objects to be processed, and each concrete Processor which knows how to
 * perform the selected operation.
 * 
 * @author dbrcina
 * @version 1.0
 * @param <T> the type of the input to the operation.
 *
 */
@FunctionalInterface
public interface Processor<T> {

	/**
	 * A method that is used to perform this operation for the given
	 * <code>value</code>.
	 * 
	 * @param value a T type input argument.
	 */
	void process(T value);

}
