package hr.fer.zemris.java.custom.collections.demo;

import java.util.Collections;

import hr.fer.zemris.java.custom.collections.*;

/**
 * A simple program that demonstrates working with {@link Collections} and
 * {@link ElementsGetter} interface and it's methods.
 * 
 * @author Darijo Brčina
 * @version 1.0
 *
 */
public class ElementsGetterDemo2 {

	/**
	 * An entry point in this program.
	 * 
	 * @param args arguments accepted through command line. Here they don't exist.
	 */
	public static void main(String[] args) {
		Collection col1 = new ArrayIndexedCollection();
		Collection col2 = new ArrayIndexedCollection();
		col1.add("Ivo");
		col1.add("Ana");
		col1.add("Jasna");
		col2.add("Jasmina");
		col2.add("Štefanija");
		col2.add("Karmela");
		ElementsGetter getter1 = col1.createElementsGetter();
		ElementsGetter getter2 = col1.createElementsGetter();
		ElementsGetter getter3 = col2.createElementsGetter();
		System.out.println("Jedan element: " + getter1.getNextElement()); // Ivo
		System.out.println("Jedan element: " + getter1.getNextElement()); // Ana
		System.out.println("Jedan element: " + getter2.getNextElement()); // Ivo
		System.out.println("Jedan element: " + getter3.getNextElement()); // Jasmina
		System.out.println("Jedan element: " + getter3.getNextElement()); // Štefanija
	}
}
