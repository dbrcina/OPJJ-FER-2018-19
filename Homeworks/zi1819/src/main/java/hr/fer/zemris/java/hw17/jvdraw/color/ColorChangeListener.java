package hr.fer.zemris.java.hw17.jvdraw.color;

import java.awt.Color;

/**
 * An implementation of <i>Observer</i> from <i>Observer Design Pattern</i>
 * which listens on {@link IColorProvider} type objects.
 * 
 * @author dbrcina
 *
 */
public interface ColorChangeListener {

	/**
	 * Notifies listener about change of color.
	 * 
	 * @param source   instance of {@link IColorProvider}.
	 * @param oldColor old color.
	 * @param newColor new color.
	 */
	void newColorSelected(IColorProvider source, Color oldColor, Color newColor);
}
