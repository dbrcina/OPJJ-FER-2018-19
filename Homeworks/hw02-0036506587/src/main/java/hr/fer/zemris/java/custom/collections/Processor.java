package hr.fer.zemris.java.custom.collections;

/**
 * Class Processor here represents an conceptual contract between clients which
 * will have objects to be processed, and each concrete Processor which knows
 * how to perform the selected operation. Each concrete Processor will be
 * defined as a new class which inherits from the class Processor.
 * 
 * @author Darijo Brčina
 * @version 1.0
 *
 */
public class Processor {

	/**
	 * A method that is used to perform something necessary for the given Object.
	 * @param value an Object type value.
	 */
	public void proces(Object value) {
	}

}
