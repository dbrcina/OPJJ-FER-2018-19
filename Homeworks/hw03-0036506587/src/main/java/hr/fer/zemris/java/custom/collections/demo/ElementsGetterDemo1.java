package hr.fer.zemris.java.custom.collections.demo;

import java.util.NoSuchElementException;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.collections.Collection;
import hr.fer.zemris.java.custom.collections.ElementsGetter;

/**
 * A simple program that demonstrates working with {@link Collections} and
 * {@link ElementsGetter} interface and it's methods. Checking whether
 * {@link ElementsGetter#getNextElement()} method will throw
 * {@link NoSuchElementException}.
 * 
 * @author Darijo Brčina
 * @version 1.0
 *
 */
public class ElementsGetterDemo1 {

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
		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement()); // true
		System.out.println("Jedan element: " + getter.getNextElement()); // Ivo
		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement()); // true
		System.out.println("Jedan element: " + getter.getNextElement()); // Ana
		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement()); // true
		System.out.println("Jedan element: " + getter.getNextElement()); // Jasna
		System.out.println("Ima nepredanih elemenata: " + getter.hasNextElement()); // false
		System.out.println("Jedan element: " + getter.getNextElement()); // NoSuchElementException
	}
}
