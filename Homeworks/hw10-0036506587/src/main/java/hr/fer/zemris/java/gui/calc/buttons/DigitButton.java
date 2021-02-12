package hr.fer.zemris.java.gui.calc.buttons;

import java.util.Objects;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import hr.fer.zemris.java.gui.calc.Calculator;
import hr.fer.zemris.java.gui.calc.CalculatorConstants;
import hr.fer.zemris.java.gui.calc.model.CalcModel;

/**
 * Model of <i>digit-like</i> {@link JButton} button. It provides user to click
 * a number on {@link Calculator}.
 * 
 * @author dbrcina
 *
 */
public class DigitButton extends JButton {

	/**
	 * Default serial ID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Number clicked on gui.
	 */
	private int number;

	/**
	 * Reference of {@link CalcModel}.
	 */
	private CalcModel model;

	/**
	 * Constructor used for initializiation.
	 * 
	 * @param number number.
	 * @param model  calculator model.
	 */
	public DigitButton(int number, CalcModel model) {
		this.number = number;
		this.model = Objects.requireNonNull(model);
		
		// initialize button
		initButton();
	}

	/**
	 * Helper method used for initialization of this button.
	 */
	private void initButton() {
		setText(number + "");
		setBackground(CalculatorConstants.BUTTON_COLOR);
		setFont(getFont().deriveFont(CalculatorConstants.DERIVE_FONT));
		addActionListener(l -> {
			try {
				if (Calculator.reset) {
					// clear calc display
					model.clear();
					Calculator.reset = false;
				}
				model.insertDigit(number);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(getParent(), e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		});
	}

}
