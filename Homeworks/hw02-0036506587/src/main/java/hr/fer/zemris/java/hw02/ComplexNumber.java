package hr.fer.zemris.java.hw02;

import static java.lang.Math.*;

/**
 * An implementation that supports operations between complex numbers like
 * adding, substracting, multiplying, dividing and calculating power and root of
 * a complex number.
 * 
 * @author Darijo Brčina
 * @version 1.0
 *
 */
public class ComplexNumber {

	/**
	 * Real part of a {@link ComplexNumber}.
	 */
	private double real;
	/**
	 * Imaginary part of a {@link ComplexNumber}.
	 */
	private double imaginary;
	/**
	 * {@link ComplexNumber}'s magnitude. It is defined like this:
	 * {@code Math.sqrt(real^2 +
	 * imaginary^2)}.
	 */
	private double magnitude;
	/**
	 * {@link ComplexNumber}'s angle in radians, from 0 to 2 {@code PI}. It is
	 * defined like this: {@code Math.atan(imaginary / real)}. There are always 2
	 * values for an angle so it depends in which quadrant are the value parts of a
	 * complex number.
	 */
	private double angle;

	/**
	 * A public constructor which accepts two arguments: {@code real} and
	 * {@code imaginary} part of a new {@link ComplexNumber}. It initializes
	 * {@link #real} and {@link #imaginary} with the given arguments and also
	 * calculates complex number's {@link #magnitude} and {@link #angle}.
	 * 
	 * @param real      a real part of a complex number.
	 * @param imaginary an imaginary part of a complex number.
	 */
	public ComplexNumber(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
		this.magnitude = calculateMagnitude();
		this.angle = calculateAngle();
	}

	/**
	 * A method that creates a new instance of {@link ComplexNumber} defined with
	 * {@code real} value given through argument.
	 * 
	 * @param real a real part of a complex number.
	 * @return new instance of {@link ComplexNumber} formed with {@code real} value.
	 */
	public static ComplexNumber fromReal(double real) {
		return new ComplexNumber(real, 0);
	}

	/**
	 * A method that creates a new instance of {@link ComplexNumber} defined with
	 * {@code imaginary} value given through argument.
	 * 
	 * @param imaginary an imaginary part of a complex number.
	 * @return new instance of {@link ComplexNumber} formed with {@code imaginary}
	 *         value.
	 */
	public static ComplexNumber fromImaginary(double imaginary) {
		return new ComplexNumber(0, imaginary);
	}

	/**
	 * A method that creates a new instance of {@link ComplexNumber} defined with
	 * {@code magnitude} and {@code angle} as method arguments. Those two arguments
	 * present complex number in a Polar coordinate system and this method
	 * calculates those polar coordinates to Cartesian coordinate system.
	 * 
	 * @param magnitude a complex number's magnitude.
	 * @param angle     a complex number's angle.
	 * @return
	 */
	public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle) {
		return new ComplexNumber(magnitude * cos(angle), magnitude * sin(angle));
	}

	/**
	 * A simple private method that calculates complex number's {@link #magnitude}
	 * with the help of some math functions like {@link Math#sqrt(double)} and
	 * {@link Math#pow(double,double)}.
	 * 
	 * @return a complex number's {@link #magnitude}.
	 */
	private double calculateMagnitude() {
		return sqrt(pow(real, 2) + pow(imaginary, 2));
	}

	/**
	 * A simple private method that calculates complex number's {@link #angle} with
	 * the help of math function {@link Math#atan2(double, double)}.
	 * 
	 * @return a complex number's {@link #angle}.
	 */
	private double calculateAngle() {
		return atan2(imaginary, real);
	}

	/**
	 * Adds two complex numbers: {@link #real} parts from both complex numbers are
	 * added alongside with both {@link #imaginary} parts.
	 * 
	 * @param other an instance of {@link ComplexNumber}.
	 * @return a new instance of {@link ComplexNumber}.
	 */
	public ComplexNumber add(ComplexNumber other) {
		return new ComplexNumber(this.real + other.real, this.imaginary + other.imaginary);
	}

	/**
	 * Substracts two complex numbers: {@link #real} parts from both complex numbers
	 * are substracted alongside with both {@link #imaginary} parts.
	 * 
	 * @param other an instance of {@link ComplexNumber}.
	 * @return new instance of {@link ComplexNumber}.
	 */
	public ComplexNumber sub(ComplexNumber other) {
		return new ComplexNumber(this.real - other.real, this.imaginary - other.imaginary);
	}

	/**
	 * Multiplies two complex numbers: {@link #magnitude}'s are multiplied and
	 * {@link #angle}'s are added.
	 * 
	 * @param other an instance of {@link ComplexNumber}.
	 * @return new instance of {@link ComplexNumber}.
	 */
	public ComplexNumber mul(ComplexNumber other) {
		return fromMagnitudeAndAngle(this.magnitude * other.magnitude, this.angle + other.angle);
	}

	/**
	 * Divides two complex numbers: {@link #magnitude}'s are divided and
	 * {@link #angle}'s are substracted.
	 * 
	 * @param other an instance of {@link ComplexNumber}.
	 * @return new instance of {@link ComplexNumber}.
	 */
	public ComplexNumber div(ComplexNumber other) {
		return fromMagnitudeAndAngle(this.magnitude / other.magnitude, this.angle - other.angle);
	}

	/**
	 * A method that calculates power of a complex number with the help of
	 * {@link Math#pow(double,double)} function. An exponent {@code n} needs to be
	 * >= 0 or appropriate exception is thrown(IllegalArgumentException).
	 * 
	 * @param n an exponent used to power the complex number.
	 * @return new instance of {@link ComplexNumber}.
	 * @throws IllegalArgumentException if the given exponent {@code n} is < 0.
	 */
	public ComplexNumber power(int n) {
		checkForExponent(n);
		return fromMagnitudeAndAngle(pow(magnitude, n), angle * n);
	}

	/**
	 * A method that finds all complex number's roots only if the given {@code n}
	 * value is > 0 or else appropriate exception needs to be
	 * thrown(IllegalArgumentException).
	 * 
	 * @param n a number of complex number's roots.
	 * @return an array filled with new instances of {@link ComplexNumber}.
	 * @throws IllegalArgumentException if the given {@code n} is < 0.
	 */
	public ComplexNumber[] root(int n) {
		checkForExponent(n);
		double newMagnitude = pow(magnitude, 1.0 / n);
		ComplexNumber[] comNumbers = new ComplexNumber[n];
		for (int i = 0; i < n; i++) {
			comNumbers[i] = fromMagnitudeAndAngle(newMagnitude, (angle + 2 * i * PI) / n);
		}
		return comNumbers;
	}

	/**
	 * Checks whether given {@code n} is in appropriate interval: >= 0 for
	 * {@link ComplexNumber#power(int)} method and > 0 for
	 * {@link ComplexNumber#root(int)}.
	 * 
	 * @param n a value to be tested.
	 * @throws IllegalArgumentException if the given {@code n} is < 0.
	 */
	private void checkForExponent(int n) {
		if (n < 0) {
			throw new IllegalArgumentException("Potencija mora biti > 0!");
		}
	}

	/**
	 * Parses given String {@code s} to a {@link ComplexNumber} using regural
	 * expressions. This method throws IllegalArgumentException if the String
	 * {@code s} cannot be parsed into a legit complex number.
	 * 
	 * @param s a String that needs to be parsed.
	 * @return a new instance of {@link ComplexNumber} if the parsing is successful.
	 * @throws IllegalArgumentException if the parsing failed.
	 */
	public static ComplexNumber parse(String s) {
		if (s.isEmpty() || s.matches("^[+-]*[i]+.+") || s.matches(".*[+-][+-]+.*") || s.matches("[^i0-9]+")) {
			throw new IllegalArgumentException("Pogrešan unos kompleksnog broja!");
		}
		double realPart = 0;
		double imaginaryPart = 0;
		String realSign = null;
		String imaginarySign = null;
		if (!s.matches("^[+-]?.+[+-].+")) {
			if (s.endsWith("i")) {
				String[] parts = s.split("i");
				try {
					imaginaryPart = Double.parseDouble(parts[0]);
				} catch (NumberFormatException e) {
					imaginaryPart = parts[0].isEmpty() ? 1 : -1;
				}
			} else {
				realPart = Double.parseDouble(s);
			}
		} else {
			String[] parts = s.split("\\d+");
			realSign = parts[0].isEmpty() ? "+" : "-";
			int indexForImag = s.contains(".") ? 2 : 1;
			imaginarySign = parts[indexForImag];
			parts = s.split("[+-]");
			realPart = Double.parseDouble(parts[0]);
			if (parts[1].equals("i")) {
				imaginaryPart = 1;
			} else {
				imaginaryPart = Double.parseDouble(parts[1].substring(0, parts[1].indexOf("i")));
			}
			if (realSign.equals("-")) {
				realPart *= -1;
			}
			if (imaginarySign.equals("-")) {
				imaginaryPart *= -1;
			}
		}

		return new ComplexNumber(realPart, imaginaryPart);
	}

	@Override
	public String toString() {
		String operation = imaginary < 0 ? "-" : "+";
		return String.format("%s %s %si", Double.toString(real), operation, Double.toString(abs(magnitude)));
	}

	/**
	 * Public getter for {@link #real} part of a complex number.
	 * 
	 * @return a {@link #real} part of a complex number.
	 */
	public double getReal() {
		return real;
	}

	/**
	 * Public getter for {@link #imaginary} part of a complex number.
	 * 
	 * @return a {@link #imaginary} part of a complex number.
	 */
	public double getImaginary() {
		return imaginary;
	}

	/**
	 * Public getter for a complex number's {@link #magnitude}.
	 * 
	 * @return complex number's {@link #magnitude}.
	 */
	public double getMagnitude() {
		return magnitude;
	}

	/**
	 * Public getter for a complex number's {@link #angle}.
	 * 
	 * @return complex number's {@link #angle}.
	 */
	public double getAngle() {
		return angle;
	}

}
