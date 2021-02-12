package hr.fer.zemris.java.gui.charts;

import java.util.Objects;

/**
 * Model that stores two <i>read-only</i> properties: <i>x</i> coordinate and
 * <i>y</i> coordinate.
 * 
 * @author dbrcina
 *
 */
public class XYValue {

	/**
	 * <i>x</i> coordinate.
	 */
	private int x;

	/**
	 * <i>y</i> coordinate.
	 */
	private int y;

	/**
	 * Constructor used for initialization.
	 * 
	 * @param x x coordinate.
	 * @param y y coordinate.
	 */
	public XYValue(int x, int y) {
		this.x = Objects.requireNonNull(x, "Coordinate x cannot be null!");
		this.y = Objects.requireNonNull(y, "Coordinate y cannot be null!");
	}

	/**
	 * Getter for x coordinate.
	 * 
	 * @return x coordinate.
	 */
	public int getX() {
		return x;
	}

	/**
	 * Getter for y coordinate.
	 * 
	 * @return y coordinate.
	 */
	public int getY() {
		return y;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof XYValue))
			return false;
		XYValue other = (XYValue) obj;
		return x == other.x && y == other.y;
	}

	@Override
	public String toString() {
		return String.format("(%d,%d)", x, y);
	}
}
