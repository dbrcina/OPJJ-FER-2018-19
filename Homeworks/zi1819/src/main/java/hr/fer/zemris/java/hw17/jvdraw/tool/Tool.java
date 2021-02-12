package hr.fer.zemris.java.hw17.jvdraw.tool;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw17.jvdraw.JDrawingCanvas;

/**
 * Model of <i>State</i> from <i>State Design Pattern</i>.
 * 
 * <p>
 * It provides some methods which are called upon <i>mouse motions</i> like
 * mouse clicked, mouse pressed etc. alongside with paint method which is used
 * for painting current state onto {@link JDrawingCanvas} component.
 * </p>
 * 
 * @author dbrcina
 *
 */
public interface Tool {

	/**
	 * This method is performed upon mouse pressed.
	 * 
	 * @param e event.
	 */
	void mousePressed(MouseEvent e);

	/**
	 * This methos is performed upon mouse released.
	 * 
	 * @param e event.
	 */
	void mouseReleased(MouseEvent e);

	/**
	 * This method is performed upon mouse pressed followed by mouse released.
	 * 
	 * @param e event.
	 */
	void mouseClicked(MouseEvent e);

	/**
	 * This method is performed upon mouse moved.
	 * 
	 * @param e event.
	 */
	void mouseMoved(MouseEvent e);

	/**
	 * This method is performed upon mouse dragged.
	 * 
	 * @param e event.
	 */
	void mouseDragged(MouseEvent e);

	/**
	 * Paints <b>this</b> state onto {@link JDrawingCanvas} component via
	 * <i>g2d</i>.
	 * 
	 * @param g2d graphics 2d.
	 */
	void paint(Graphics2D g2d);
}
