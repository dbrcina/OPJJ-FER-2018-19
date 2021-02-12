package hr.fer.zemris.math;

import java.util.Arrays;

/**
 * Utility class that provides {@link #parse(String)} method.
 * 
 * @author dbrcina
 *
 */
public class ComplexUtil {

	/**
	 * Parses given String {@code s} to a {@link Complexr} using regural
	 * expressions. This method throws IllegalArgumentException if the String
	 * {@code s} cannot be parsed into a legit complex number.
	 * 
	 * @param s a String that needs to be parsed.
	 * @return a new instance of {@link Complex} if the parsing is successful.
	 * @throws IllegalArgumentException if the parsing failed.
	 */
	public static Complex parse(String s) {
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
				} catch (Exception e) {
					imaginaryPart = parts.length == 0 ? 1 : -1;
				}
			} else {
				realPart = Double.parseDouble(s);
			}
		} else {
			String[] parts = s.split("\\d+");
			realSign = parts[0].isEmpty() ? "+" : "-";
			int indexForImag = s.contains(".") ? 2 : 1;
			imaginarySign = parts[indexForImag].trim().replace("i", "").trim();
			parts = s.split("[+-]");
			if (parts[0].isEmpty()) {
				parts = Arrays.copyOfRange(parts, 1, 3);
			}
			realPart = Double.parseDouble(parts[0]);
			if (parts[1].equals("i")) {
				imaginaryPart = 1;
			} else {
				imaginaryPart = Double.parseDouble(parts[1].substring(parts[1].indexOf("i") + 1));
			}
			if (realSign.equals("-")) {
				realPart *= -1;
			}
			if (imaginarySign.equals("-")) {
				imaginaryPart *= -1;
			}
		}

		return new Complex(realPart, imaginaryPart);
	}
}
