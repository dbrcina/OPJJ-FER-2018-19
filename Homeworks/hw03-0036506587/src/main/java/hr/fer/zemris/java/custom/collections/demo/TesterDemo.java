package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.Tester;

/**
 * A simple program that checks functionality of {@link Tester} interface and
 * it's {@link Tester#test(Object)} method.
 * 
 * @author Darijo Brčina
 * @version 1.0
 *
 */
public class TesterDemo {

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
		Tester t = new EvenIntegerTester();
		System.out.println(t.test("Ivo")); // false
		System.out.println(t.test(22)); // true
		System.out.println(t.test(3)); // false
	}
}
