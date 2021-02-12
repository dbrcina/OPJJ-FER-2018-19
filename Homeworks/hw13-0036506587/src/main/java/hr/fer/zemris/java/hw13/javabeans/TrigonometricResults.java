package hr.fer.zemris.java.hw13.javabeans;

import java.io.Serializable;
import java.text.DecimalFormat;

/**
 * <i>Java bean</i> model of trigonometric result.
 * 
 * <p>
 * Each model has <i>angle</i> property (in <b>degrees</b>) and provides
 * calculation of some generic trigonometric functions like <i>sinus</i> and
 * <i>cosinus</i> etc.<br>
 * Those values are accessible through public <i>getters</i>.
 * </p>
 * 
 * @author dbrcina
 *
 */
public class TrigonometricResults implements Serializable {

	/**
	 * Default serial ID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Angle in degrees.
	 */
	private int angle;
	/**
	 * Sinus value of angle.
	 */
	private String sinAngle;
	/**
	 * Cosinus value of angle.
	 */
	private String cosAngle;
	/**
	 * Decimal formatter.
	 */
	private DecimalFormat df = new DecimalFormat("#0.000000");

	/**
	 * Default constructor.
	 */
	public TrigonometricResults() {
		this(0);
	}

	/**
	 * Constructor which takes <i>angle</i> in <b>degrees</b> as an argument.
	 * 
	 * @param angle angle.
	 */
	public TrigonometricResults(int angle) {
		this.angle = angle;
		this.sinAngle = df.format(Math.sin(Math.toRadians(angle)));
		this.cosAngle = df.format(Math.cos(Math.toRadians(angle)));
	}

	/**
	 * Getter for angle.
	 * 
	 * @return angle.
	 */
	public int getAngle() {
		return angle;
	}

	/**
	 * Getter for sinus value of angle.
	 * 
	 * @return sinus value.
	 */
	public String getSinAngle() {
		return sinAngle;
	}

	/**
	 * Getter for cosinus value of angle.
	 * 
	 * @return cosinus angle.
	 */
	public String getCosAngle() {
		return cosAngle;
	}

}
