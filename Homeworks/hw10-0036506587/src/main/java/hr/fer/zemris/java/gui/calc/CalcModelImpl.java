package hr.fer.zemris.java.gui.calc;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.DoubleBinaryOperator;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalcValueListener;
import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;

/**
 * An implementation of {@link CalcModel} interface.
 * 
 * @author dbrcina
 *
 */
public class CalcModelImpl implements CalcModel {

	/**
	 * Flag which tells whether calculator is editable.
	 */
	private boolean isEditable = true;

	/**
	 * Flag which tells whether provided digit is positive.
	 */
	private boolean isPositive = true;

	/**
	 * String representation of value.
	 */
	private String stringValue = "";

	/**
	 * Double value.
	 */
	private double doubleValue;

	/**
	 * Active operand. Default value is {@link Double#MIN_VALUE}.
	 */
	private double activeOperand = Double.MIN_VALUE;

	/**
	 * Instance of {@link DoubleBinaryOperator}.
	 */
	private DoubleBinaryOperator pendingOperation;

	/**
	 * List of {@link CalcValueListener}.
	 */
	private List<CalcValueListener> listeners = new ArrayList<>();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addCalcValueListener(CalcValueListener l) {
		listeners.add(Objects.requireNonNull(l));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeCalcValueListener(CalcValueListener l) {
		listeners.remove(Objects.requireNonNull(l));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		if (stringValue.isEmpty()) {
			return isPositive ? "0" : "-0";
		}
		if (Double.isNaN(doubleValue)) {
			return "NaN";
		}
		if (Double.isInfinite(doubleValue)) {
			return isPositive ? "Infinity" : "-Infinity";
		}

		// remove leading zeroes
		if (!stringValue.contains(".")) {
			stringValue = stringValue.replaceFirst("^0+(?!$)", "");
		}
		
		return isPositive ? stringValue : "-" + stringValue;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double getValue() {
		return doubleValue * (isPositive ? 1 : -1);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setValue(double value) {
		isEditable = false;
		doubleValue = Math.abs(value);
		if (value == Double.NEGATIVE_INFINITY) {
			isPositive = false;
		} else if (value == Double.POSITIVE_INFINITY) {
			isPositive = true;
		} else {
			isPositive = value >= 0 ? true : false;
		}
		stringValue = Double.toString(value);
		if (stringValue.startsWith("-")) {
			stringValue = stringValue.replace("-", "");
		}
		notifyListeners();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isEditable() {
		return isEditable;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clear() {
		stringValue = "";
		doubleValue = 0;
		isPositive = true;
		isEditable = true;
		notifyListeners();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clearAll() {
		clear();
		activeOperand = Double.MIN_VALUE;
		pendingOperation = null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void swapSign() throws CalculatorInputException {
		if (!isEditable) {
			throw new CalculatorInputException("State is not editable!");
		}
		isPositive = !isPositive;
		notifyListeners();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void insertDecimalPoint() throws CalculatorInputException {
		if (stringValue.isEmpty() || stringValue.contains(".")) {
			throw new CalculatorInputException("Cannot insert decimal point into emty number!");
		}
		stringValue += ".";
		notifyListeners();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void insertDigit(int digit) throws CalculatorInputException, IllegalArgumentException {
		if (!isEditable) {
			throw new CalculatorInputException("Calculator is not editable!");
		}
		if (digit < 0 || digit > 9) {
			throw new IllegalArgumentException("Invalid digit!");
		}
		try {
			String s = stringValue + digit;
			double value = Double.parseDouble(s);
			if (Double.isInfinite(value)) {
				throw new CalculatorInputException("Number is too big!");
			}
			stringValue = s;
			doubleValue = value;
		} catch (IllegalArgumentException e) {
			throw new CalculatorInputException("Provided digit is invalid!");
		}
		notifyListeners();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isActiveOperandSet() {
		return activeOperand != Double.MIN_VALUE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double getActiveOperand() throws IllegalStateException {
		if (!isActiveOperandSet()) {
			throw new IllegalStateException("Active operand is not set!");
		}
		return activeOperand;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setActiveOperand(double activeOperand) {
		this.activeOperand = activeOperand;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void clearActiveOperand() {
		activeOperand = Double.MIN_VALUE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public DoubleBinaryOperator getPendingBinaryOperation() {
		return pendingOperation;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setPendingBinaryOperation(DoubleBinaryOperator op) {
		pendingOperation = op;
	}

	/**
	 * Helper method used to notify every {@link CalcValueListener} from
	 * {@link #listeners}.
	 */
	private void notifyListeners() {
		listeners.forEach(l -> l.valueChanged(this));
	}
}
