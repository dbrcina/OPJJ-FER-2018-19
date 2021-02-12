package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.collections.Collection;
import hr.fer.zemris.java.custom.collections.LinkedListIndexedCollection;
import hr.fer.zemris.java.custom.collections.Tester;

/**
 * A simple program that checks functionality of {@link Tester} interface and
 * it's {@link Tester#test(Object)} method. Also checks functionality of
 * {@link Collection#addAllSatisfying(Collection, Tester)} method.
 * 
 * @author Darijo Brčina
 * @version 1.0
 *
 */
public class TesterDemo2 {

	/**
	 * An entry point in this program.
	 * 
	 * @param args arguments accepted through command line. Here they don't exist.
	 */
	public static void main(String[] args) {
		class EvenIntegerTester implements Tester {
			public boolean test(Object obj) {
				if (!(obj instanceof Integer)) // check whether given obj is even Integer
					return false;
				Integer i = (Integer) obj;
				return i % 2 == 0;
			}
		}
		Collection col1 = new LinkedListIndexedCollection();
		Collection col2 = new ArrayIndexedCollection();
		col1.add(2);
		col1.add(3);
		col1.add(4);
		col1.add(5);
		col1.add(6);
		col2.add(12);
		col2.addAllSatisfying(col1, new EvenIntegerTester());
		col2.forEach(System.out::println); // 12, 2, 4, 6
	}

}
