package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.collections.Collection;
import hr.fer.zemris.java.custom.collections.LinkedListIndexedCollection;
import hr.fer.zemris.java.custom.collections.List;

/**
 * A simple application that checks relationships between interfaces and
 * structures that inherit those interfaces.
 * 
 * @author Darijo Brčina
 * @version 1.0
 * @see hr.fer.zemris.java.custom.collections.Collection
 * @see hr.fer.zemris.java.custom.collections.List
 *
 */
public class ListTestDemo {

	/**
	 * An entry point in this program.
	 * 
	 * @param args arguments accepted through command line. Here they don't exist.
	 */
	public static void main(String[] args) {
		List col1 = new ArrayIndexedCollection();
		List col2 = new LinkedListIndexedCollection();
		col1.add("Ivana");
		col2.add("Jasna");

		Collection col3 = col1;
		Collection col4 = col2;

		col1.get(0);
		col2.get(0);
//		col3.get(0); this two commented lines wont compile becuase method get(index) is not declared in Collection interface and we are watching col1 and col2 "through Collection glasses"
//		col4.get(0); and we are watching col1 and col2 "through Collection glasses" which means we can only use methods declared in that interface

		col1.forEach(System.out::println); // Ivana
		col2.forEach(System.out::println); // Jasna
		col3.forEach(System.out::println); // Ivana
		col4.forEach(System.out::println); // Jasna
	}
}
