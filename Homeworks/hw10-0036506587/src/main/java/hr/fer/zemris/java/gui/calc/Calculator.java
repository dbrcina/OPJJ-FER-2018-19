package hr.fer.zemris.java.gui.calc;

import static hr.fer.zemris.java.gui.calc.CalculatorConstants.*;
import static java.lang.Math.*;

import java.util.EmptyStackException;
import java.util.Stack;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.gui.calc.buttons.BinaryOperationButton;
import hr.fer.zemris.java.gui.calc.buttons.BinaryOperationInvButton;
import hr.fer.zemris.java.gui.calc.buttons.DigitButton;
import hr.fer.zemris.java.gui.calc.buttons.NormalButton;
import hr.fer.zemris.java.gui.calc.buttons.UnaryOperationButton;
import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;
import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.CalcLayoutException;
import hr.fer.zemris.java.gui.layouts.RCPosition;

/**
 * An implementation of simple <i>Windows calculator - GUI</i>. This <i>GUI</i>
 * uses custom layout {@link CalcLayout}.
 * <p>
 * It supports all basic operations such as: <i>unary operations, binary
 * operations etc.</i> This calculator is very primitive and simple, so entering
 * whole expression is not supported! It only supports one argument at a time.
 * Additionally, it provides <i>push</i> and <i>pop</i> buttons that manipulate
 * with underlaying stack.
 * </p>
 * 
 * @author dbrcina
 *
 */
public class Calculator extends JFrame {

	/**
	 * Default serial ID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Reference of {@link CalcModel}.
	 */
	private CalcModel model = new CalcModelImpl();

	/**
	 * Reference of {@link Stack}.
	 */
	private Stack<Double> stack = new Stack<>();

	/**
	 * Flag which tells whether calculator's display needs to be reseted.
	 */
	public static boolean reset;

	/**
	 * Main entry of this program.
	 * 
	 * @param args arguments through command line.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new Calculator().setVisible(true));
	}
	
	/**
	 * Constructor used for initialization.
	 */
	public Calculator() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Calculator");
		setLocation(100, 100);
		setSize(700,500);
		initGUI();
	}

	/**
	 * Helper method used for initialization of <i>Calculator - GUI</i>.
	 */
	private void initGUI() {
		getContentPane().setLayout(new CalcLayout(3));
		initDisplay();
		initButtons();
	}

	/**
	 * Helper method used for initializing <i>Calculator's display</i>.
	 */
	private void initDisplay() {
		JLabel display = new JLabel(INITIAL_VALUE, SwingConstants.TRAILING);
		display.setBorder(BORDER);
		display.setOpaque(true);
		display.setBackground(DISPLAY_COLOR);
		display.setFont(display.getFont().deriveFont(DERIVE_FONT));
		model.addCalcValueListener(l -> display.setText(model.toString()));
		add(display, "1,1");
	}

	/**
	 * Helper method used for initializing <i>Calculator's content - buttons</i>.
	 */
	private void initButtons() {
		initNormalButtons();
		initDigitButtons();

		// create inv check box
		JCheckBox inverseCheckBox = createInvCheckBox();
		add(inverseCheckBox, "5,7");

		initUnaryOperationButtons(inverseCheckBox);
		initBinaryOperationButtons(inverseCheckBox);

	}

	/**
	 * Helper method used for initializing {@link NormalButton} buttons.
	 */
	private void initNormalButtons() {
		add(
			new NormalButton(EQUALS, l -> equalsAction()), 
			"1,6"
		);
		add(
			new NormalButton(CLR, l -> model.clear()), 
			"1,7"
		);
		add(
			new NormalButton(RES, l -> model.clearAll()),
			"2,7"
		);
		add(
			new NormalButton(PUSH, l -> stack.push(model.getValue())),
			"3,7"
		);
		add(
			new NormalButton(POP, l -> popAction()), 
			"4,7"
		);
		add(
			new NormalButton(DOT, l -> insertPointAction()),
			"5,5"
		);
		add(
			new NormalButton(SWITCH_SIGN, l -> switchSignAction()),
			"5,4"
		);
	}

	/**
	 * Helper method used for initializing {@link DigitButton} buttons.
	 */
	private void initDigitButtons() {
		add(new DigitButton(0, model), "5,3");
		int number = 1;
		for (int i = 4; i > 1; i--) {
			for (int j = 3; j < 6; j++) {
				add(
					new DigitButton(number++, model),
					new RCPosition(i, j)
				);
			}
		}
	}

	/**
	 * Helper method used for initializing {@link UnaryOperationButton} buttons.
	 * 
	 * @param iCheckBox inverse check box.
	 */
	private void initUnaryOperationButtons(JCheckBox iCheckBox) {
		add(
			new UnaryOperationButton(RATIONAL, RATIONAL, model, x -> 1.0 / x, x -> 1.0 / x, iCheckBox), 
			"2,1"
		);
		add(
			new UnaryOperationButton(LOG, INV_LOG, model, x -> log10(x), x -> pow(10, x), iCheckBox), 
			"3,1"
		);
		add(
			new UnaryOperationButton(LN, INV_LN, model, x -> log(x), x -> exp(x), iCheckBox), 
			"4,1"
		);
		add(
			new UnaryOperationButton(SIN, ASIN, model, x -> sin(x), x -> asin(x), iCheckBox), 
			"2,2"
		);
		add(
			new UnaryOperationButton(COS, ACOS, model, x -> cos(x), x -> acos(x), iCheckBox),
			"3,2"
		);
		add(
			new UnaryOperationButton(TAN, ATAN, model, x -> tan(x), x -> atan(x), iCheckBox),
			"4,2"
		);
		add(
			new UnaryOperationButton(CTG, ACTG, model, x -> 1.0 / tan(x), x -> PI / 2 - atan(x), iCheckBox), 
			"5,2"
		);
	}

	/**
	 * Helper method used for initializing {@link BinaryOperationButton} and
	 * {@link BinaryOperationInvButton} buttons.
	 * 
	 * @param iCheckBox inverse check box.
	 */
	private void initBinaryOperationButtons(JCheckBox iCheckBox) {
		add(
			new BinaryOperationButton(DIV, model, (a, b) -> a / b), 
			"2,6"
		);
		add(
			new BinaryOperationButton(MUL, model, (a, b) -> a * b),
			"3,6"
		);
		add(
			new BinaryOperationButton(SUB, model, (a, b) -> a - b),
			"4,6"
		);
		add(
			new BinaryOperationButton(ADD, model, (a, b) -> a + b),
			"5,6"
		);
		add(
			new BinaryOperationInvButton(POT, INV_POT, model, (a, b) -> pow(a, b), (a, b) -> pow(a, 1.0 / b), iCheckBox),
			"5,1"
		);
	}

	/**
	 * Performs <i>"="</i> button action.
	 */
	private void equalsAction() {
		try {
			if (model.isActiveOperandSet()) {
				double result = model.getPendingBinaryOperation().applyAsDouble(model.getActiveOperand(),
						model.getValue());
				model.setValue(result);
				model.clearActiveOperand();
				model.setPendingBinaryOperation(null);
				reset = true;
			}
		} catch (CalcLayoutException | IllegalStateException e) {
			generateError(e.getMessage());
		}
	}

	/**
	 * Performs <i>"pop"</i> button action.
	 */
	private void popAction() {
		try {
			model.setValue(stack.pop());
		} catch (EmptyStackException e) {
			generateError("Stack is empty!");
		}
	}

	/**
	 * Performs <i>"."</i> button action.
	 */
	private void insertPointAction() {
		try {
			model.insertDecimalPoint();
		} catch (CalculatorInputException e) {
			generateError(e.getMessage());
		}
	}

	/**
	 * Performs <i>"+/-"</i> button action.
	 */
	private void switchSignAction() {
		try {
			model.swapSign();
		} catch (CalculatorInputException e) {
			generateError(e.getMessage());
		}
	}

	/**
	 * Creates a new instance of {@link JCheckBox} used for inverting unary
	 * operators.
	 * 
	 * @return new instance of {@link JCheckBox}.
	 */
	private JCheckBox createInvCheckBox() {
		JCheckBox box = new JCheckBox("Inv");
		box.setText("Inv");
		box.setBackground(CalculatorConstants.BUTTON_COLOR);
		box.setFocusPainted(false);
		box.addActionListener(l -> {
			UnaryOperationButton.isInverse = !UnaryOperationButton.isInverse;
			BinaryOperationInvButton.isInverse = !BinaryOperationInvButton.isInverse;
		});
		return box;
	}

	/**
	 * Helper method used for creating error window {@link JOptionPane}.
	 * 
	 * @param msg message.
	 */
	private void generateError(String msg) {
		JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
	}

}
