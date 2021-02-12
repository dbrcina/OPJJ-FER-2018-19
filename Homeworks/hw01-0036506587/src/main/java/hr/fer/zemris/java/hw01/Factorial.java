package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * A program that illustrates factoring calculator.
 * 
 * @author Darijo Brčina
 *
 */
public class Factorial {

	/**
	 * An int type variable which stores a value that user inputs.
	 */
	private static int number;

	public static void main(String[] args) {
		try (Scanner sc = new Scanner(System.in)) {
			System.out.print("Unesite broj > ");
			while (sc.hasNext()) {
				String token = sc.next();
				if (token.toLowerCase().equals("kraj")) {
					System.out.println("Doviđenja.");
					break;
				}
				try {
					number = Integer.parseInt(token);
					if (number < 3 || number > 20) {
						throw new IllegalArgumentException("Broj nije u dozvoljenom intervalu!");
					}
					long factorial = calculateFactorial(number);
					System.out.println(number + "! = " + factorial);
				} catch (NumberFormatException numForEx) {
					System.out.println("'" + token + "' nije cijeli broj!");
				} catch (IllegalArgumentException ex) {
					System.out.println(ex.getMessage());
				}
				System.out.print("Unesite broj > ");
			}
		}
	}

	/**
	 * A simple method which calculates factorial from given number or throws an
	 * exception if the token is not a whole number inside the [0,20].
	 * 
	 * @param number a number for calculating the factorial if it is possible.
	 * @throws IllegalArgumentException if the number is not inside the given interval.
	 */
	public static long calculateFactorial(int number) {
		if (number < 0 || number > 20) {
			throw new IllegalArgumentException("Ne mogu izračunati faktorijel!");
		}
		long factorial = 1;
		for (int i = 2; i <= number; i++) {
			factorial *= i;
		}
		return factorial;
	}
}
