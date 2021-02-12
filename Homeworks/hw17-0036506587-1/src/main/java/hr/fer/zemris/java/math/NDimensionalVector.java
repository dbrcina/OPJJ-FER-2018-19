package hr.fer.zemris.java.math;

/**
 * Model of a <i>n-dimensional</i> vector.<br>
 * It provides some generic functions like calculating scalar product, vector's
 * norm, cosinus of angle between two vectors alongside with appropriate get
 * methods.
 * 
 * @author dbrcina
 *
 */
public class NDimensionalVector {

	/**
	 * An array of vector values.
	 */
	private double[] values;

	/**
	 * Constructor which takes one argument, <i>size</i>, which is used for
	 * preparing underlaying array.
	 * 
	 * @param size initial size.
	 */
	public NDimensionalVector(int size) {
		this.values = new double[size];
	}

	/**
	 * Getter for vector values.
	 * 
	 * @return an array of values.
	 */
	public double[] getValues() {
		return values;
	}

	/**
	 * Getter for vector value at position <i>index</i>.<br>
	 * If <i>index</i> is invalid, an appropriate exception is thrown.
	 * 
	 * @param index index.
	 * @return value at position <i>index</i>.
	 */
	public double getValue(int index) {
		return values[checkIndex(index)];
	}

	/**
	 * Getter for vector's size.
	 * 
	 * @return vector's size.
	 */
	public int size() {
		return values.length;
	}

	/**
	 * Registers provided <i>value</i> into this vector at position
	 * <i>index</i>.<br>
	 * If <i>index</i> is invalid, an appropriate exception is thrown.
	 * 
	 * @param value value.
	 * @param index position.
	 */
	public void registerValue(double value, int index) {
		this.values[checkIndex(index)] = value;
	}

	/**
	 * Performs <i>scalar product</i> between <b>this</b> vector and <i>other.</i>
	 * 
	 * @param other other vector.
	 * @return scalar product.
	 */
	public double dot(NDimensionalVector other) {
		double result = 0;
		for (int i = 0; i < this.values.length; i++) {
			result += this.values[i] * other.values[i];
		}
		return result;
	}

	/**
	 * Calculates vector's norm.
	 * 
	 * @return vector's norm.
	 */
	public double norm() {
		double result = 0;
		for (double i : values) {
			result += i * i;
		}
		return Math.sqrt(result);
	}

	/**
	 * Calculates <i>cosinus</i> of angle between <b>this</b> and <i>other</i>
	 * vector.
	 * 
	 * @param other other vector.
	 * @return cosinus angle.
	 */
	public double cosAngle(NDimensionalVector other) {
		return this.dot(other) / (this.norm() * other.norm());
	}

	/**
	 * Checks whether provided <i>index</i> is valid.
	 * 
	 * @param index index.
	 * @return index.
	 * @throws IndexOutOfBoundsException if <i>index</i> is invalid.
	 */
	private int checkIndex(int index) {
		if (index < 0 || index > values.length - 1) {
			throw new IndexOutOfBoundsException("Index is invalid - " + index);
		}
		return index;
	}
}
