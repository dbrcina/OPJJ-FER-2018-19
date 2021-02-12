package hr.fer.zemris.java.custom.collections.demo;

import java.util.ConcurrentModificationException;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.collections.Collection;
import hr.fer.zemris.java.custom.collections.ElementsGetter;
import hr.fer.zemris.java.custom.collections.LinkedListIndexedCollection;

/**
 * A simple program that demonstrates working with {@link Collections} and
 * {@link ElementsGetter} interface and it's methods. Checking whether
 * {@link ElementsGetter#getNextElement()} method will throw
 * {@link ConcurrentModificationException}.
 * 
 * @author Darijo Brčina
 * @version 1.0
 *
 */
public class ElementsGetterDemo3 {

	/**
	 * An entry point in this program.
	 * 
	 * @param args arguments accepted through command line. Here they don't exist.
	 */
	public static void main(String[] args) {
		System.out.println("Ispis za ArrayIndexedCollection:");
		Collection col = new ArrayIndexedCollection();
		col.add("Ivo");
		col.add("Ana");
		col.add("Jasna");
		ElementsGetter getter = col.createElementsGetter();
		System.out.println("Jedan element: " + getter.getNextElement()); // Ivo
		System.out.println("Jedan element: " + getter.getNextElement()); // Ana
		col.clear();
		try {
			System.out.println("Jedan element: " + getter.getNextElement()); // ConcurrentModificationException
		} catch (ConcurrentModificationException e) {
			e.printStackTrace();
		}
		
		System.out.println("----------------");
		
		System.out.println("Ispis za LinkedListIndexedCollection:");
		Collection col1 = new LinkedListIndexedCollection();
		col1.add("Ivo");
		col1.add("Ana");
		col1.add("Jasna");
		ElementsGetter getter1 = col1.createElementsGetter();
		System.out.println("Jedan element: " + getter1.getNextElement()); // Ivo
		System.out.println("Jedan element: " + getter1.getNextElement()); // Ana
		col1.clear();
		try {
			System.out.println("Jedan element: " + getter1.getNextElement()); // ConcurrentModificationException
		} catch (ConcurrentModificationException e) {
			e.printStackTrace();
		}

	}
}
