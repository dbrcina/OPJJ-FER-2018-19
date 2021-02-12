package hr.fer.zemris.java.gui.charts;

import java.util.List;

/**
 * Model of simple <i>Bar chart</i>. It's construction takes six arguments:
 * 
 * <ul>
 * <li>List of coordinates({@link XYValue})</li>
 * <li>x-axis description.</li>
 * <li>y-axis description.</li>
 * <li>Minimum <b>NON-NEGATIVE</b> y coordinate.</li>
 * <li>Maximum y coordinate.</li>
 * <li>Difference between two y values on chart.</li>
 * </ul>
 * 
 * If one of the property is invalid, an appropriate exception is thrown.
 * 
 * @author dbrcina
 *
 */
public class BarChart {

	/*
	 * ------------------- Constants used for error messages -------------------
	 */

	/**
	 * Message thrown if minimum y is negative.
	 */
	private static final String YMIN_NEGATIVE = "Minimum y cannot be negative!";

	/**
	 * Message thrown if maximum y is not greater than minimum y.
	 */
	private static final String YMAX_LOWER_THAN_YMIN = "Maximum y needs to be greater than minimum y!";

	/**
	 * Message thrown if some y is lower than minimum y.
	 */
	private static final String Y_LOWER_THAN_YMIN = "One of the provided coordinates have lower y than minimum y!";

	/**
	 * Message thrown if difference between y values is negative.
	 */
	private static final String DIFFERENCE_NEGATIVE = "Difference between two y cannot be negative!";

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * List of coordinates.
	 */
	private List<XYValue> coordinates;

	/**
	 * <i>x - axis</i> description.
	 */
	private String xDescription;

	/**
	 * <i>y - axis</i> description.
	 */
	private String yDescription;

	/**
	 * Minimum <i>y</i> coordinate.
	 */
	private int yMin;

	/**
	 * Maximum <i>y</i> coordinate.
	 */
	private int yMax;

	/**
	 * Distance between two y values.
	 */
	private int yDifference;

	/**
	 * Constructor used for initialization of <i>bar chart</i>.
	 * 
	 * @param coordinates  list of coordinates.
	 * @param xDescription x description.
	 * @param yDescription y description.
	 * @param yMin         minimum y.
	 * @param yMax         maximum y.
	 * @param yDifference  difference between two y values.
	 */
	public BarChart(List<XYValue> coordinates, String xDescription, String yDescription, int yMin, int yMax,
			int yDifference) {
		this.coordinates = coordinates;
		this.xDescription = xDescription;
		this.yDescription = yDescription;
		this.yMin = yMin;
		this.yMax = yMax;
		this.yDifference = yDifference;

		checkConstraints();
		checkCoordinates();
	}

	/**
	 * Getter for list of coordinates.
	 * 
	 * @return list of coordinates.
	 */
	public List<XYValue> getCoordinates() {
		return coordinates;
	}

	/**
	 * Getter for <i>x-axis</i> description.
	 * 
	 * @return <i>x-axis</i> description.
	 */
	public String getxDescription() {
		return xDescription;
	}

	/**
	 * Getter for <i>y-axis</i> description.
	 * 
	 * @return <i>y-axis</i> description.
	 */
	public String getyDescription() {
		return yDescription;
	}

	/**
	 * Getter for minimum y coordinate.
	 * 
	 * @return minimum y coordinate.
	 */
	public int getyMin() {
		return yMin;
	}

	/**
	 * Getter for maximum y coordinate.
	 * 
	 * @return maximum y coordinate.
	 */
	public int getyMax() {
		return yMax;
	}

	/**
	 * Getter for difference between two y values.
	 * 
	 * @return difference between two y values.
	 */
	public int getyDifference() {
		return yDifference;
	}

	/**
	 * Helper method used for validation of provided properties: {@link #yMin},
	 * {@link #yMax} and {@link #yDifference} .
	 */
	private void checkConstraints() {
		if (yMin < 0) {
			generateException(YMIN_NEGATIVE);
		}
		if (yMax <= yMin) {
			generateException(YMAX_LOWER_THAN_YMIN);
		}
		if (yDifference < 0) {
			generateException(DIFFERENCE_NEGATIVE);
		}
		if ((yMax - yMin) % yDifference != 0) {
			yDifference = yMin + 1;
		}
	}

	/**
	 * Helper method that checks if there is any y coordinate lower than
	 * {@link #yMin}.
	 */
	private void checkCoordinates() {
		coordinates.forEach(c -> {
			if (c.getY() < yMin)
				generateException(Y_LOWER_THAN_YMIN);
		});
	}

	/**
	 * Method factory for instances of {@link IllegalArgumentException}.
	 * 
	 * @param msg message passed to exception's constructor.
	 */
	private void generateException(String msg) {
		throw new IllegalArgumentException(msg);
	}
}
