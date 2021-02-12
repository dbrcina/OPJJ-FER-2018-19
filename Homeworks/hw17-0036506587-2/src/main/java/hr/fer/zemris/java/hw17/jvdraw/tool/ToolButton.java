package hr.fer.zemris.java.hw17.jvdraw.tool;

import java.awt.BasicStroke;

import javax.swing.BorderFactory;
import javax.swing.JRadioButton;

/**
 * An implementation of {@link JRadioButton}.<br>
 * It represents {@link Tool} objects.
 * 
 * @author dbrcina
 *
 */
public class ToolButton extends JRadioButton {

	/**
	 * Default serial ID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor used for initialization.
	 * 
	 * @param text text.
	 */
	public ToolButton(String text) {
		super(text);
		setToolTipText(text + " tool button");
		setBorder(BorderFactory.createStrokeBorder(new BasicStroke()));
		setContentAreaFilled(false);
		setFocusable(false);
	}

}
