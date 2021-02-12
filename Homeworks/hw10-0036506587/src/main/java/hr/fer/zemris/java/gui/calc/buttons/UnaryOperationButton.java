package hr.fer.zemris.java.gui.calc.buttons;

import java.util.Objects;
import java.util.function.UnaryOperator;

import javax.swing.JButton;
import javax.swing.JCheckBox;

import hr.fer.zemris.java.gui.calc.Calculator;
import hr.fer.zemris.java.gui.calc.CalculatorConstants;
import hr.fer.zemris.java.gui.calc.model.CalcModel;

/**
 * Model of <i>unary-like operator</i> button {@link JButton}. It provides user
 * to use unary operators like: <i>sin, cos, tan, ctg, log, ln etc.</i> and
 * their inverses.
 * 
 * @author dbrcina
 *
 */
public class UnaryOperationButton extends JButton {

	/**
	 * Default serial ID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Flag which represent whether provided operator is regular or it's inverse.
	 */
	public static boolean isInverse;

	/**
	 * Operator's name.
	 */
	private String name;

	/**
	 * Operator's inverse name.
	 */
	private String inverseName;

	/**
	 * Reference of {@link CalcModel}.
	 */
	private CalcModel model;

	/**
	 * Regular operator.
	 */
	private UnaryOperator<Double> operator;

	/**
	 * Regular's inverse operator.
	 */
	private UnaryOperator<Double> inverseOperator;

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
	public UnaryOperationButton(String name, String inverseName, CalcModel model, UnaryOperator<Double> operator,
			UnaryOperator<Double> inverseOperator, JCheckBox inverseCheckBox) {

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
			double value = model.getValue();
			model.setValue(isInverse ? inverseOperator.apply(value) : operator.apply(value));

			// set calc reset to true
			Calculator.reset = true;
		});
	}

}
