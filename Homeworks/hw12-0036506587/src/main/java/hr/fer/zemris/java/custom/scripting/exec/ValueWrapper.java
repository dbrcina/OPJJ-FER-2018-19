package hr.fer.zemris.java.custom.scripting.exec;

import java.util.function.BiFunction;

/**
 * <p>
 * Structure that provides user to wrap objects of any class. However, methods
 * of this class only support instances of {@link String} , {@link Double} or
 * {@link Integer} type values.
 * </p>
 * 
 * <p>
 * If <code>null</code> is provided as an argument when creating a new instance
 * of {@link ValueWrapper} , {@link #value} is set to <code>0</code> as an
 * instance of {@link Integer}. {@link String} values must be parsable as
 * {@link Double} or {@link Integer} values, otherwise {@link RuntimeException}
 * is thrown.
 * </p>
 * 
 * @author dbrcina
 *
 */
public class ValueWrapper {

	/**
	 * Wrapped value.
	 */
	private Object value;

	/**
	 * Constructor that takes only one argument, <code>value</code>.
	 * 
	 * @param value value.
	 */
	public ValueWrapper(Object value) {
		this.value = value;
	}

	/**
	 * Adds the current {@link #value} by given <code>incValue</code>.
	 * 
	 * @param incValue increment value.
	 */
	public void add(Object incValue) {
		operation(incValue, (v1, v2) -> v1 + v2);
	}

	/**
	 * Substracts the current {@link #value} by given <code>decValue</code>.
	 * 
	 * @param decValue decrement value.
	 */
	public void substract(Object decValue) {
		operation(decValue, (v1, v2) -> v1 - v2);
	}

	/**
	 * Multiplies the current {@link #value} by given <code>mulValue</code>.
	 * 
	 * @param mulValue multiply value.
	 */
	public void multiply(Object mulValue) {
		operation(mulValue, (v1, v2) -> v1 * v2);
	}

	/**
	 * Divides the current {@link #value} by given <code>divValue</code>.
	 * 
	 * @param divValue divide value.
	 * @throws RuntimeException if division by {@code 0} happens between
	 *                          {@link Integer} type.
	 */
	public void divide(Object divValue) {
		if (divValue instanceof Integer && ((Integer) divValue).intValue() == 0
				|| divValue instanceof String && divValue.equals("0")) {
			throw new RuntimeException("Cannot divide integers with zero!");
		}
		operation(divValue, (v1, v2) -> v1 / v2);
	}

	/**
	 * Compares the current {@link #value} by given <code>withValue</code>. If both
	 * values are <code>null</code>, they are consider as equal.
	 * 
	 * @param withValue value.
	 * @return {@code 0} if values are equal, {@code -1} if current value i less
	 *         then provided, {@code 1} otherwise.
	 * @see Double#compare(double, double)
	 * @see Double#compareTo(Double)
	 */
	public int numCompare(Object withValue) {
		return Double.compare((double) createToken(this.value).getValue(), (double) createToken(withValue).getValue());
	}

	/**
	 * Getter for {@link #value}.
	 * 
	 * @return {@link #value}.
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Setter for {@link #value}.
	 * 
	 * @param value value.
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	@Override
	public String toString() {
		if (value instanceof String) {
			return (String)value;
		}
		if (value instanceof Integer) {
			return ((Integer)value).toString();
		}
		return ((Double)value).toString();
	}
	
	/**
	 * Helper method used for creating new instance of {@link NumberToken}.
	 * 
	 * @param value value.
	 * @return new instance of {@link NumberToken}.
	 * @throws RuntimeException if given {@code value} is invalid.
	 */
	private NumberToken createToken(Object value) {
		if (value == null) {
			return new NumberToken(Integer.valueOf(0), NumberType.INTEGER);
		} else if (value instanceof Integer) {
			return new NumberToken((int) value, NumberType.INTEGER);
		} else if (value instanceof Double) {
			return new NumberToken((double) value, NumberType.DOUBLE);
		} else if (value instanceof String) {
			Object parsedValue = parseString((String) value);
			return createToken(parsedValue);
		} else {
			throw new RuntimeException("Argument " + value + " is invalid!");
		}
	}

	/**
	 * Helper method used for executing given <code>action</code>.
	 * 
	 * @param value  value.
	 * @param action an instance of {@link BiFunction}.
	 */
	private void operation(Object value, BiFunction<Double, Double, Double> action) {
		NumberToken firstNum = createToken(this.value);
		NumberToken secondNum = createToken(value);
		double result = action.apply(firstNum.getValue(), secondNum.getValue());
		if (firstNum.getType() == NumberType.INTEGER && secondNum.getType() == NumberType.INTEGER) {
			this.value = (int) result;
		} else {
			this.value = result;
		}
	}

	/**
	 * Helper method used for parsing {@link String} type values. If the given
	 * <code>value</code> contains '.', "e" or "E", it needs to parsed to
	 * {@link Double}, otherwise to {@link Integer}. If parsing fails, an
	 * appropriate exception is thrown.
	 * 
	 * @param value value.
	 * @return an instance of {@link Double} or {@link Integer}.
	 * @throws RuntimeException if {@code value} is invalid.
	 */
	private Object parseString(String value) {
		try {
			if (value.matches(".*[.eE].*")) {
				return Double.parseDouble(value);
			}
			return Integer.parseInt(value);
		} catch (NumberFormatException e) {
			throw new RuntimeException(
					"Provided String value could not be parsed into Double or Integer, so it's invalid!");
		}
	}

	/**
	 * Helper structure used for storage of number's value and number's type.
	 * 
	 * @author dbrcina
	 *
	 */
	private static class NumberToken {

		/**
		 * Number's value.
		 */
		private Object value;

		/**
		 * Number's type.
		 */
		private NumberType type;

		/**
		 * Constructor that takes two arguments, <code>value</code> and
		 * <code>type</code>.
		 * 
		 * @param value value.
		 * @param type  type.
		 */
		public NumberToken(Object value, NumberType type) {
			this.value = value;
			this.type = type;
		}

		/**
		 * Getter for {@link #value}.
		 * 
		 * @return {@link #value}.
		 */
		public double getValue() {
			if (value instanceof Integer) {
				return (double) ((Integer) value).intValue();
			}
			return (double) value;
		}

		/**
		 * Getter for {@link #type}.
		 * 
		 * @return {@link #type}.
		 */
		public NumberType getType() {
			return type;
		}
	}

	/**
	 * Private enumeration that has two types: {@link NumberType#INTEGER} and
	 * {@link NumberType#DOUBLE} which determine number's type.
	 * 
	 * @author dbrcina
	 *
	 */
	private enum NumberType {
		/**
		 * Constant representing {@link Integer} type.
		 */
		INTEGER,

		/**
		 * Constant representing {@link Double} type.
		 */
		DOUBLE;
	}
}
