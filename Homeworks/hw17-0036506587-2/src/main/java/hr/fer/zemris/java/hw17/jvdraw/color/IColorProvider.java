package hr.fer.zemris.java.hw17.jvdraw.color;

import java.awt.Color;

/**
 * Interface <i>(i.e. color provider)</i> which provides currently selected
 * color.<br>
 * It also provides (de)registration of certain {@link ColorChangeListener}
 * listeners.
 * 
 * @author dbrcina
 *
 */
public interface IColorProvider {

	/**
	 * Getter for currently selected color.
	 * 
	 * @return current color.
	 */
	public Color getCurrentColor();

	/**
	 * Registers provided listener <i>l</i>.
	 * 
	 * @param l color change listener.
	 */
	public void addColorChangeListener(ColorChangeListener l);

	/**
	 * Deregisters provided listener <i>l</i>.
	 * 
	 * @param l color change listener.
	 */
	public void removeColorChangeListener(ColorChangeListener l);
}
