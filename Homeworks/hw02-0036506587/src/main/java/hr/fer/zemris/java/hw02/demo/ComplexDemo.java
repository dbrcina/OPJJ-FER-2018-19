package hr.fer.zemris.java.hw02.demo;

import hr.fer.zemris.java.hw02.ComplexNumber;

/**
 * A simple aplication that simulates {@link ComplexNumber} methods. Parameters
 * for this program are not given through command line.
 * 
 * @author Darijo Brčina
 * @version 1.0
 *
 */
public class ComplexDemo {

	/**
	 * An entry point of this program.
	 * 
	 * @param args arguments accepted through command line.
	 */
	public static void main(String[] args) {
		ComplexNumber c1 = new ComplexNumber(2, 3);
		ComplexNumber c2 = ComplexNumber.parse("2.5-3i");
		ComplexNumber c3 = c1.add(ComplexNumber.fromMagnitudeAndAngle(2, 1.57)).div(c2).power(3).root(2)[1];
		System.out.println(c3);
	}
}
