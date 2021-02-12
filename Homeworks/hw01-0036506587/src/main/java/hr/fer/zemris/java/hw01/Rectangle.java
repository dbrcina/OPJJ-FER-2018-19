package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * A program that calculates the perimeter and the area of specified rectangle.
 * 
 * @author Darijo Brčina
 *
 */
public class Rectangle {

	/**
	 * Rectangle's width.
	 */
	public static double width;
	/**
	 * Rectangle's height.
	 */
	public static double height;

	public static void main(String[] args) {
		if (args.length != 0 && args.length != 2) {
			System.err.println(
					"Broj argumenata u komandnoj liniji nije niti nula niti dva, što znači da pravokutnik nije specificiran i program se završava!");
			System.exit(0);
		}
		if (args.length == 2) {
			System.out.println("Argumenti su zadani kroz komandnu liniju.");
			try {
				// lets assume that the first argument through command line is rectangle's width
				// and the second rectangle's height.
				width = Double.parseDouble(args[0]);
				height = Double.parseDouble(args[1]);

				if (width < 1) {
					throw new IllegalArgumentException("Zadana širina je <= 0!");
				} else if (height < 1) {
					throw new IllegalArgumentException("Zadana visina je <= 0!");
				}

				print();
			} catch (NumberFormatException ex) {
				System.err.println("Jedan od argumenata nije broj pa se program zavšava!");
			} catch (IllegalArgumentException illArgEx) {
				System.err.println(illArgEx.getMessage());
			}
		} else {
			try (Scanner sc = new Scanner(System.in)) {
				width = userInput("širinu", sc);
				height = userInput("visinu", sc);
				print();
			}
		}
	}

	/**
	 * A method which checks whether the input from keyboard is right or wrong for
	 * the given property.
	 * 
	 * @param property can be width or height.
	 * @param sc       a variable for entering the input.
	 * @return a value for specified property.
	 */
	private static double userInput(String property, Scanner sc) {
		double number = 0;
		System.out.print("Unesi " + property + " > ");
		while (sc.hasNext()) {
			String token = sc.next();
			try {
				// when an user enters the right number, break the while loop
				number = Double.parseDouble(token);
				if (number < 1) {
					throw new IllegalArgumentException("Unijeli ste broj koji je <= 0!");
				}
				break;
			} catch (NumberFormatException numForEx) {
				System.out.println("'" + token + "' se ne može protumačiti kao broj!");
			} catch (IllegalArgumentException illArgEx) {
				System.out.println(illArgEx.getMessage());
			}
			System.out.print("Unesi " + property + " > ");
		}
		return number;
	}

	/**
	 * A simple method that calculates rectangle's perimeter.
	 * 
	 * @param width
	 * @param height
	 * @return perimeter of the given rectangle.
	 */
	private static double perimeter(double width, double height) {
		return 2 * (width + height);
	}

	/**
	 * A simple method that calculates rectangle's area.
	 * 
	 * @param width
	 * @param height
	 * @returns area of the given rectangle.
	 */
	private static double area(double width, double height) {
		return width * height;
	}

	/**
	 * A method that prints details of specified rectangle.
	 */
	private static void print() {
		System.out.println("Pravokutnik širine " + Double.toString(width) + " i visine " + Double.toString(height)
				+ " ima površinu " + Double.toString(area(width, height)) + " i opseg "
				+ Double.toString(perimeter(width, height)) + ".");
	}
}
