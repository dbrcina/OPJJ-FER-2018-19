package hr.fer.zemris.java.hw17.jvdraw.geobject.editor;

import java.awt.Point;

import javax.swing.JList;
import javax.swing.JPanel;

import hr.fer.zemris.java.hw17.jvdraw.util.Util;

/**
 * This class is used for editing geometrical object which was previously
 * focused from {@link JList} list.
 * 
 * @author dbrcina
 *
 */
public abstract class GeometricalObjectEditor extends JPanel {

	/**
	 * Default serial ID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Pattern for valid color.
	 */
	private static final String COLOR_PATTERN = "^#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{3})$";

	/**
	 * Checks whether editing results are valid.
	 * 
	 * @throws Exception if editing is invalid.
	 */
	public abstract void checkEditing() throws Exception;

	/**
	 * Accepts editing.
	 */
	public abstract void acceptEditing();

	/**
	 * Validates coordinates <i>c1</i> and <i>c2</i>.
	 * 
	 * @param c1 c1.
	 * @param c2 c2.
	 * @return new {@link Point}.
	 * @throws IllegalArgumentException if something is invalid.
	 */
	protected Point validateCoordinates(String c1, String c2) throws IllegalArgumentException {
		int x = Util.validateNumber(c1, false);
		int y = Util.validateNumber(c2, false);
//		try {
//			x = Integer.parseInt(c1);
//			y = Integer.parseInt(c2);
//		} catch (Exception e) {
//			throw new IllegalArgumentException("Coordinate needs to be an integer.");
//		}
//		if (x < 0 || y < 0) {
//			throw new IllegalArgumentException("Coordinate cannot be negative.");
//		}
		return new Point(x, y);
	}

	/**
	 * Validates radius <i>r</i>.
	 * 
	 * @param r radius.
	 * @return radius
	 * @throws IllegalArgumentException if something is invalid.
	 */
	protected int validateRadius(String r) throws IllegalArgumentException {
		return Util.validateNumber(r, false);
	}

	/**
	 * Validates color <i>color</i>.
	 * 
	 * @param color color.
	 * @return color
	 * @throws IllegalArgumentException if something is invalid.
	 */
	protected String validateColor(String color) throws IllegalArgumentException {
		if (!color.matches(COLOR_PATTERN)) {
			throw new IllegalArgumentException("Pattern for color is #XXXXXX, where X is a hexadecimal number.");
		}
		return color;
	}
}
