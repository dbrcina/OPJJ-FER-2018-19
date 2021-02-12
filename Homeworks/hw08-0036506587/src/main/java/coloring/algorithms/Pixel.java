package coloring.algorithms;

import java.util.Objects;

/**
 * Model of one picture pixel.
 * 
 * @author dbrcina
 *
 */
public class Pixel {

	/**
	 * Pixel's x coordinate.
	 */
	public int x;
	
	/**
	 * Pixel's y coordinate.
	 */
	public int y;

	/**
	 * Constructor.
	 * @param x x axis.
	 * @param y y axis.
	 */
	public Pixel(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public String toString() {
		return String.format("(%d,%d)", x, y);
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Pixel))
			return false;
		Pixel other = (Pixel) obj;
		return x == other.x && y == other.y;
	}

}
