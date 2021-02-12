package hr.fer.zemris.java.gui.calc;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

/**
 * Utility class that provides all constants for {@link Calculator}.
 * 
 * @author dbrcina
 *
 */
public class CalculatorConstants {

	/*
	 * ----- This is a default configuration for calculator's components -----
	 */

	/**
	 * Initial value for calculator's display.
	 */
	public static final String INITIAL_VALUE = "0";

	/**
	 * Default border used for components.
	 */
	public static final Border BORDER = BorderFactory.createLineBorder(Color.BLACK);

	/**
	 * Default derive font.
	 */
	public static final float DERIVE_FONT = 30f;

	/**
	 * Default button color.
	 */
	public static final Color BUTTON_COLOR = new Color(96, 149, 209);

	/**
	 * Default display color.
	 */
	public static final Color DISPLAY_COLOR = Color.ORANGE;

	//////////////////////////////////////////////////////////////////////////////////

	/*
	 * ------------------ Normal button name ------------------
	 */

	/**
	 * Represents "=" button name.
	 */
	public static final String EQUALS = "=";

	/**
	 * Represents "clr" button name.
	 */
	public static final String CLR = "clr";

	/**
	 * Represents "res" button name.
	 */
	public static final String RES = "res";

	/**
	 * Represents "push" button name.
	 */
	public static final String PUSH = "push";

	/**
	 * Represents "Inv" button name.
	 */
	public static final String INV = "Inv";

	/**
	 * Represents "pop" button name.
	 */
	public static final String POP = "pop";

	/**
	 * Represents "." button name.
	 */
	public static final String DOT = ".";

	/**
	 * Represents "+/-" button name.
	 */
	public static final String SWITCH_SIGN = "+/-";

	//////////////////////////////////////////////////////////////////////////////////

	/*
	 * ------------------ Binary operator button name ------------------
	 */

	/**
	 * Represents "/" button name.
	 */
	public static final String DIV = "/";

	/**
	 * Represents "*" button name.
	 */
	public static final String MUL = "*";

	/**
	 * Represents "-" button name.
	 */
	public static final String SUB = "-";

	/**
	 * Represents "+" button name.
	 */
	public static final String ADD = "+";

	/**
	 * Represents "x^n" button name.
	 */
	public static final String POT = "x^n";

	/**
	 * Represents "x^(1/n)" button name.
	 */
	public static final String INV_POT = "x^(1/n)";

	//////////////////////////////////////////////////////////////////////////////////

	/*
	 * ------------------ Unary operator button name ------------------
	 */

	/**
	 * Represents "1/x" button name.
	 */
	public static final String RATIONAL = "1/x";

	/**
	 * Represents "log" button name.
	 */
	public static final String LOG = "log";

	/**
	 * Represents "10^x" button name.
	 */
	public static final String INV_LOG = "10^x";

	/**
	 * Represents "ln" button name.
	 */
	public static final String LN = "ln";

	/**
	 * Represents "e^x" button name.
	 */
	public static final String INV_LN = "e^x";

	/**
	 * Represents "sin" button name.
	 */
	public static final String SIN = "sin";

	/**
	 * Represents "arcsin" button name.
	 */
	public static final String ASIN = "arcsin";

	/**
	 * Represents "cos" button name.
	 */
	public static final String COS = "cos";

	/**
	 * Represents "arccos" button name.
	 */
	public static final String ACOS = "arccos";

	/**
	 * Represents "tan" button name.
	 */
	public static final String TAN = "tan";

	/**
	 * Represents "arctan" button name.
	 */
	public static final String ATAN = "arctan";

	/**
	 * Represents "ctg" button name.
	 */
	public static final String CTG = "ctg";

	/**
	 * Represents "arcctg" button name.
	 */
	public static final String ACTG = "arcctg";
}
