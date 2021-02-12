package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.collections.Collection;
import hr.fer.zemris.java.custom.collections.ElementsGetter;

/**
 * A simple program that demonstrates working with {@link Collections} and
 * {@link ElementsGetter} interface and it's methods. Checking functionality of
 * {@link ElementsGetter#processRemaining(Processor)}.
 * 
 * @author Darijo Brčina
 * @version 1.0
 *
 */
public class ElementsGetterDemo4 {

	/**
	 * An entry point in this program.
	 * 
	 * @param args arguments accepted through command line. Here they don't exist.
	 */
	public static void main(String[] args) {
		Collection col = new ArrayIndexedCollection();
		col.add("Ivo");
		col.add("Ana");
		col.add("Jasna");
		ElementsGetter getter = col.createElementsGetter();
		getter.getNextElement();
		getter.processRemaining(System.out::println); // Ana, Jasna
	}

}
