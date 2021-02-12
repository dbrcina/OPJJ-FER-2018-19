package hr.fer.zemris.java.gui.layouts;

import java.util.Objects;

/**
 * Model that stores information about element's position in coordinate system
 * like <code>(row,column)</code>. It provides only two read-only properties:
 * 
 * <pre>
 * 1.{@link #row}
 * 2.{@link #column}
 * </pre>
 * 
 * @author dbrcina
 *
 */
public class RCPosition {

	/**
	 * Element's row number.
	 */
	private int row;

	/**
	 * Element's column number.
	 */
	private int column;

	/**
	 * Constructor used for initializing element's position in coordinate system.
	 * 
	 * @param row    row number.
	 * @param column column number.
	 */
	public RCPosition(int row, int column) {
		this.row = row;
		this.column = column;
	}

	/**
	 * Getter for row number.
	 * 
	 * @return {@link #row}.
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Getter for column number.
	 * 
	 * @return {@link #column}.
	 */
	public int getColumn() {
		return column;
	}

	@Override
	public int hashCode() {
		return Objects.hash(column, row);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof RCPosition))
			return false;
		RCPosition other = (RCPosition) obj;
		return column == other.column && row == other.row;
	}

	@Override
	public String toString() {
		return String.format("(%d,%d)", row, column);
	}
}
