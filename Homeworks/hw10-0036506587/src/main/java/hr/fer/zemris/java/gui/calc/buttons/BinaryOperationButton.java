package hr.fer.zemris.java.gui.calc.buttons;

import java.util.Objects;
import java.util.function.DoubleBinaryOperator;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import hr.fer.zemris.java.gui.calc.Calculator;
import hr.fer.zemris.java.gui.calc.CalculatorConstants;
import hr.fer.zemris.java.gui.calc.model.CalcModel;

/**
 * Model of <i>binary-like operator</i> button {@link JButton}. It provides user
 * to use binary operators like: <i>"/", "*", "-", "+".</i>
 * 
 * @author dbrcina
 *
 */
public class BinaryOperationButton extends JButton {

	/**
	 * Default serial ID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Operator's name.
	 */
	private String name;

	/**
	 * Reference of {@link CalcModel}.
	 */
	private CalcModel model;

	/**
	 * Reference of {@link DoubleBinaryOperator}.
	 */
	private DoubleBinaryOperator operator;

	/**
	 * Constructor used for initialization.
	 * 
	 * @param name     operator's name.
	 * @param model    model.
	 * @param operator binary operator.
	 */
	public BinaryOperationButton(String name, CalcModel model, DoubleBinaryOperator operator) {
		this.name = Objects.requireNonNull(name);
		this.model = Objects.requireNonNull(model);
		this.operator = Objects.requireNonNull(operator);

		// initialize button
		initButton();
	}

	private void initButton() {
		setText(name);
		setBackground(CalculatorConstants.BUTTON_COLOR);
		addActionListener(l -> {
			try {
				if (model.isActiveOperandSet()) {
					model.setValue(model.getPendingBinaryOperation().applyAsDouble(model.getActiveOperand(),
							model.getValue()));
				}
				model.setActiveOperand(model.getValue());
				model.setPendingBinaryOperation(operator);
				
				// set calc reset to true
				Calculator.reset = true;
			} catch (IllegalStateException e) {
				JOptionPane.showMessageDialog(getParent(), e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		});
	}

}
