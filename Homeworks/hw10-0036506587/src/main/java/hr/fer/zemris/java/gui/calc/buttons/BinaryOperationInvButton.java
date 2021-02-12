package hr.fer.zemris.java.gui.calc.buttons;

import java.util.Objects;
import java.util.function.DoubleBinaryOperator;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;

import hr.fer.zemris.java.gui.calc.Calculator;
import hr.fer.zemris.java.gui.calc.CalculatorConstants;
import hr.fer.zemris.java.gui.calc.model.CalcModel;

/**
 * Model of <i>binary-like operator</i> button {@link JButton} like
 * {@link BinaryOperationButton} but this one is used for <i>x^n and it's
 * inverse x^(1/n)</i>.
 * 
 * @author dbrcina
 *
 */
public class BinaryOperationInvButton extends JButton {

	/**
	 * Default serial ID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Flag which represent whether provided operator is regular or it's inverse.
	 */
	public static boolean isInverse;

	/**
	 * Regular operator name.
	 */
	private String name;

	/**
	 * Inverse operator name.
	 */
	private String inverseName;

	/**
	 * Reference of {@link CalcModel}.
	 */
	private CalcModel model;

	/**
	 * Regular operator.
	 */
	private DoubleBinaryOperator operator;

	/**
	 * Inverse operator.
	 */
	private DoubleBinaryOperator inverseOperator;

	/**
	 * Reference of inverse {@link JCheckBox}.
	 */
	private JCheckBox inverseCheckBox;

	/**
	 * Constructor used for initialization.
	 * 
	 * @param name            regular name.
	 * @param inverseName     inverse name.
	 * @param model           model.
	 * @param operator        regular operator.
	 * @param inverseOperator inverse operator.
	 * @param inverseCheckBox inverse check box.
	 */
	public BinaryOperationInvButton(String name, String inverseName, CalcModel model, DoubleBinaryOperator operator,
			DoubleBinaryOperator inverseOperator, JCheckBox inverseCheckBox) {

		this.name = Objects.requireNonNull(name);
		this.inverseName = Objects.requireNonNull(inverseName);
		this.model = Objects.requireNonNull(model);
		this.operator = Objects.requireNonNull(operator);
		this.inverseOperator = Objects.requireNonNull(inverseOperator);
		this.inverseCheckBox = Objects.requireNonNull(inverseCheckBox);

		// initialize button
		initButton();
	}

	/**
	 * Helper method used for initialization of this button.
	 */
	private void initButton() {
		setText(name);
		setBackground(CalculatorConstants.BUTTON_COLOR);
		// flip operators
		inverseCheckBox.addActionListener(l -> setText(inverseCheckBox.isSelected() ? inverseName : name));
		addActionListener(l -> {
			try {
				if (model.isActiveOperandSet()) {
					model.setValue(model.getPendingBinaryOperation().applyAsDouble(model.getActiveOperand(),
							model.getValue()));
					model.setActiveOperand(model.getValue());
				}
				model.setActiveOperand(model.getValue());
				model.setPendingBinaryOperation(isInverse ? inverseOperator : operator);

				// set calc reset to true
				Calculator.reset = true;
			} catch (IllegalStateException e) {
				JOptionPane.showMessageDialog(getParent(), e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
		});
	}
}
