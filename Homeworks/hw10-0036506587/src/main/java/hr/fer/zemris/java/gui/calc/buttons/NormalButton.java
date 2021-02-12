package hr.fer.zemris.java.gui.calc.buttons;

import java.awt.event.ActionListener;
import java.util.Objects;

import javax.swing.JButton;

import hr.fer.zemris.java.gui.calc.CalculatorConstants;

/**
 * 
 * Model of <i>normal-like</i> button {@link JButton}. It represents buttons:
 * <i>=, clr, reset, push, pop, . , +/-</i>.
 * 
 * <ul>
 * <li><i>=</i> - represents equals sign, operation terminator.</li>
 * <li><i>clr</i> - clears last input.</li>
 * <li><i>res</i> - resets calculator.</li>
 * <li><i>push</i> - pushes current operand on stack.</li>
 * <li><i>pop</i> - removes operand from stack.</li>
 * <li><i>.</i> - writes dot.</li>
 * <li><i>+/-</i> - flips the sign.</li>
 * </ul>
 *
 * @author dbrcina
 *
 */
public class NormalButton extends JButton {

	/**
	 * Default serial ID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Button's name.
	 */
	private String name;

	/**
	 * Button's job.
	 */
	private ActionListener listener;

	/**
	 * Constructor used for initialization.
	 * 
	 * @param name     button's name.
	 * @param listener button's job.
	 */
	public NormalButton(String name, ActionListener listener) {
		this.name = Objects.requireNonNull(name);
		this.listener = Objects.requireNonNull(listener);

		// initialize button
		initButton();
	}

	/**
	 * Helper method used for initializing this button.
	 */
	private void initButton() {
		setText(name);
		setBackground(CalculatorConstants.BUTTON_COLOR);
		// add provided job
		addActionListener(listener);
	}

}
