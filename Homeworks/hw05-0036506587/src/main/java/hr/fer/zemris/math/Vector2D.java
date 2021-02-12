package hr.fer.zemris.math;

import java.util.Objects;

/**
 * This class represents vector in two-dimensional space (2D).
 * 
 * @author dbrcina
 * @version 1.0
 *
 */
public class Vector2D {

	/**
	 * Constant that is used to check whether two doubles are equal.
	 */
	private static final double DELTA = 1e-6;

	/**
	 * Vector's x-axis.
	 */
	private double xAxis;
	/**
	 * Vector's y-axis
	 */
	private double yAxis;

	/**
	 * Vector's constructor.
	 * 
	 * @param xAxis the input argument for {@link #xAxis}
	 * @param yAxis the input argument for {@link #yAxis}
	 */
	public Vector2D(double xAxis, double yAxis) {
		this.xAxis = xAxis;
		this.yAxis = yAxis;
	}

	/**
	 * Getter for vectors's x-axis.
	 * 
	 * @return {@link #xAxis}.
	 */
	public double getX() {
		return xAxis;
	}

	/**
	 * Getter for vectors's x-axis.
	 * 
	 * @return {@link #yAxis}.
	 */
	public double getY() {
		return yAxis;
	}

	/**
	 * Translates vector by given <code>offset</code> vector.
	 * 
	 * @param offset Vector used for translation.
	 * @throws NullPointerException if the given {@code offset} is {@code null}.
	 */
	public void translate(Vector2D offset) {
		Objects.requireNonNull(offset, "Ulazni vektor je null!");
		xAxis += offset.xAxis;
		yAxis += offset.yAxis;
	}

	/**
	 * Translates vector by given <code>offset</code> vector.
	 * 
	 * @param offset the input vector.
	 * @return new instance of {@link Vector2D} translated by given {@code offset}.
	 * @throws NullPointerException if the given {@code offset} vector is
	 *                              {@code null}.
	 */
	public Vector2D translated(Vector2D offset) {
		Objects.requireNonNull(offset, "Ulazni vektor je null!");
		Vector2D translated = this.copy();
		translated.translate(offset);
		return translated;
	}

	/**
	 * Rotates vector by given <code>angle</code>.
	 * 
	 * @param angle the angle in radians.
	 */
	public void rotate(double angle) {
		double rotatedX = xAxis * Math.cos(angle) - yAxis * Math.sin(angle);
		double rotatedY = xAxis * Math.sin(angle) + yAxis * Math.cos(angle);
		xAxis = rotatedX;
		yAxis = rotatedY;
	}

	/**
	 * Rotates vector by given <code>angle</code>.
	 * 
	 * @param angle the angle in radians.
	 * @return new instance of {@link Vector2D} rotated by given {@code angle}.
	 */
	public Vector2D rotated(double angle) {
		Vector2D rotated = this.copy();
		rotated.rotate(angle);
		return rotated;
	}

	/**
	 * Scales {@link #xAxis} and {@link #yAxis} by the given <code>scaler</code>.
	 * 
	 * @param scaler the input used for scaling.
	 */
	public void scale(double scaler) {
		xAxis *= scaler;
		yAxis *= scaler;
	}

	/**
	 * Scales {@link #xAxis} and {@link #yAxis} by the given <code>scaler</code>.
	 * 
	 * @param scaler the input used for scaling.
	 * @return new instance of {@link Vector2D} scaled by give {@code scaler}.
	 */
	public Vector2D scaled(double scaler) {
		Vector2D scaled = this.copy();
		scaled.scale(scaler);
		return scaled;
	}

	/**
	 * Copies vector.
	 * 
	 * @return new instance of {@link Vector2D} with the same {@link #xAxis} and
	 *         {@link #yAxis} as a vector that called the method.
	 */
	public Vector2D copy() {
		return new Vector2D(xAxis, yAxis);
	}

//	/**
//	 * Normalizes vector.
//	 * 
//	 * @return new normalized {@link Vector2D}.
//	 */
//	public Vector2D normalized() {
//		double norm = Math.sqrt(xAxis * xAxis + yAxis * yAxis);
//		return norm == 0 ? new Vector2D(xAxis, yAxis) : new Vector2D(xAxis / norm, yAxis / norm);
//	}

	@Override
	public int hashCode() {
		return Objects.hash(xAxis, yAxis);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Vector2D))
			return false;
		Vector2D other = (Vector2D) obj;
		return Math.abs(xAxis - other.xAxis) <= DELTA && Math.abs(yAxis - other.yAxis) <= DELTA;
	}

}
