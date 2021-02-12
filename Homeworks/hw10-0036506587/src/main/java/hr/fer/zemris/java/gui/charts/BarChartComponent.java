package hr.fer.zemris.java.gui.charts;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.util.List;

import javax.swing.JComponent;

/**
 * Model of {@link JComponent} that creates a bar chart on specified data stored
 * in {@link BarChart} model.
 * 
 * @author dbrcina
 *
 */
public class BarChartComponent extends JComponent {

	/**
	 * Default serial ID.
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * -----------CONSTANTS USED FOR THIS COMPONENT--------------
	 */

	/**
	 * Constant representing left padding.
	 */
	private static final int LEFT_PADDING = 100;

	/**
	 * Constant representing bottom padding.
	 */
	private static final int BOTTOM_PADDING = 50;

	/**
	 * Constant representing arbitrary paddding.
	 */
	private static final int ADDITIONAL_PADDING = 30;

	/**
	 * Constant representing line overflow padding.
	 */
	private static final int OVERFLOW = 8;

	/**
	 * Default background color.
	 */
	private static final Color BACKGROUND_COLOR = Color.WHITE;

	/**
	 * Default color for grid.
	 */
	private static final Color LINE_COLOR = new Color(255, 215, 0);

	/**
	 * Default color for bars.
	 */
	private static final Color BAR_COLOR = Color.ORANGE;

	/**
	 * Default color for text.
	 */
	private static final Color TEXT_COLOR = Color.BLACK;

	/**
	 * Default color for overflow.
	 */
	private static final Color OVERFLOW_COLOR = Color.GRAY.darker();

	/**
	 * Default axis stroke.
	 */
	private static final Stroke AXIS_STROKE = new BasicStroke(2);

	/**
	 * Default lines stroke.
	 */
	private static final Stroke LINES_STROKE = new BasicStroke(1);

	//////////////////////////////////////////////////////////////////////

	/*
	 * -----------BAR CHART DATA---------------
	 */

	/**
	 * Reference of {@link BarChart}.
	 */
	private BarChart barChart;

	/**
	 * <i>x-axis</i> description.
	 */
	private String xDescription;

	/**
	 * <i>y-axis</i> description.
	 */
	private String yDescription;

	/**
	 * List of coordinates.
	 */
	private List<XYValue> coordinates;

	/**
	 * Minimum y coordinate.
	 */
	private int yMin;

	/**
	 * Maximum y coordinate.
	 */
	private int yMax;

	/**
	 * y difference.
	 */
	private int yDifference;

	//////////////////////////////////////////////////////////////////////

	/**
	 * Constructor used for initialization.
	 * 
	 * @param barChart barchart.
	 */
	public BarChartComponent(BarChart barChart) {
		this.barChart = barChart;
		initBarChartData();
	}

	/**
	 * Helper method used for initialization of bar char data.
	 */
	private void initBarChartData() {
		this.xDescription = barChart.getxDescription();
		this.yDescription = barChart.getyDescription();
		this.coordinates = barChart.getCoordinates();
		this.yMin = barChart.getyMin();
		this.yMax = barChart.getyMax();
		this.yDifference = barChart.getyDifference();

		// sort coordinates by x coordinate
		coordinates.sort((v1, v2) -> Integer.compare(v1.getX(), v2.getX()));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2D = (Graphics2D) g;
		Dimension dimension = getSize();
		Insets insets = getInsets();

		int chartWidth = dimension.width - insets.left - insets.right - 2 * ADDITIONAL_PADDING;
		int chartHeight = dimension.height - insets.top - insets.bottom - 2 * ADDITIONAL_PADDING;

		int rows = (yMax - yMin) / yDifference;
		int columns = coordinates.size();

		// default configuration
		g2D.setColor(BACKGROUND_COLOR);
		g2D.fillRect(0, 0, dimension.width, dimension.height);
		g2D.setFont(g2D.getFont().deriveFont(Font.BOLD).deriveFont(14f));

		draw(g2D, chartWidth, chartHeight, rows, columns, dimension);
	}

	/**
	 * Factory method used for delegating all methods that paint something on
	 * <i>GUI</i>.
	 * 
	 * @param g2D         graphics2D element.
	 * @param chartWidth  chart width.
	 * @param chartHeight chart height.
	 * @param rows        number of rows.
	 * @param columns     number of columns.
	 * @param dimension   component's dimension.
	 */
	private void draw(Graphics2D g2D, int chartWidth, int chartHeight, int rows, int columns, Dimension dimension) {
		int cellWidth = (chartWidth - LEFT_PADDING - ADDITIONAL_PADDING) / columns;
		int cellHeight = (chartHeight - BOTTOM_PADDING - ADDITIONAL_PADDING) / rows;

		drawVerticalLines(g2D, cellWidth, cellHeight, rows, columns, chartHeight);
		drawHorizontalLines(g2D, cellWidth, cellHeight, rows, columns, chartHeight);

		addXDescription(g2D, columns, cellWidth, chartHeight);
		addYDescription(g2D, chartHeight);

		fillBars(g2D, rows, columns, cellWidth, cellHeight, chartHeight);
	}

	/**
	 * Method used for drawing vertical lines.
	 * 
	 * @param g2D         graphics element.
	 * @param cellWidth   cell's width.
	 * @param cellHeight  cell's height.
	 * @param rows        number of rows.
	 * @param columns     number of columns.
	 * @param chartHeight chart height.
	 */
	private void drawVerticalLines(Graphics2D g2D, int cellWidth, int cellHeight, int rows, int columns,
			int chartHeight) {

		int maxHeight = chartHeight - BOTTOM_PADDING - rows * cellHeight;
		int staticY = chartHeight - BOTTOM_PADDING + OVERFLOW;
		int positionX = LEFT_PADDING;

		// draw y axis
		updateG2D(g2D, AXIS_STROKE, OVERFLOW_COLOR, positionX, maxHeight - OVERFLOW, positionX, staticY, null, null);

		for (int i = 0; i < columns; i++) {
			int number = coordinates.get(i).getX();

			// write numbers on x axis
			updateG2D(g2D, null, TEXT_COLOR, positionX + cellWidth / 2, staticY + 3 * OVERFLOW, 0, 0, null,
					number + "");

			// increment x coordinate
			positionX += cellWidth;

			// draw another line
			updateG2D(g2D, LINES_STROKE, LINE_COLOR, positionX, maxHeight - OVERFLOW, positionX, staticY, null, null);

			// draw additional overflow
			updateG2D(g2D, AXIS_STROKE, OVERFLOW_COLOR, positionX, chartHeight - BOTTOM_PADDING, positionX,
					chartHeight - BOTTOM_PADDING + OVERFLOW, null, null);
		}
	}

	/**
	 * Method used for drawing horizontal lines.
	 * 
	 * @param g2D         graphics element.
	 * @param cellWidth   cell's width.
	 * @param cellHeight  cell's height.
	 * @param rows        number of rows.
	 * @param columns     number of columns.
	 * @param chartHeight chart height.
	 */
	private void drawHorizontalLines(Graphics2D g2D, int cellWidth, int cellHeight, int rows, int columns,
			int chartHeight) {

		int maxWidth = columns * cellWidth + LEFT_PADDING + OVERFLOW;
		int staticX = LEFT_PADDING;
		int positionY = chartHeight - BOTTOM_PADDING;

		// draw x axis
		updateG2D(g2D, AXIS_STROKE, OVERFLOW_COLOR, staticX - OVERFLOW, positionY, maxWidth, positionY, null, null);

		int number = 0;
		FontMetrics fontMetrics = g2D.getFontMetrics();

		for (int i = 0; i <= rows; i++) {
			String name = number + "";

			// write numbers on y axis
			updateG2D(g2D, LINES_STROKE, TEXT_COLOR, staticX - ADDITIONAL_PADDING / 2 - fontMetrics.stringWidth(name),
					positionY + OVERFLOW, 0, 0, null, name);

			// decrement y koordinate
			positionY -= cellHeight;
			// increment number
			number += yDifference;

			if (i == rows) {
				break;
			}

			// draw line
			updateG2D(g2D, null, LINE_COLOR, staticX, positionY, maxWidth, positionY, null, null);

			// draw overflow
			updateG2D(g2D, AXIS_STROKE, OVERFLOW_COLOR, staticX - OVERFLOW, positionY, staticX, positionY, null, null);
		}

	}

	/**
	 * Writes the description of <i>x axis</i>.
	 * 
	 * @param g2D         graphics element.
	 * @param columns     number of columns.
	 * @param cellWidth   cell width.
	 * @param chartHeight chart height.
	 */
	private void addXDescription(Graphics2D g2D, int columns, int cellWidth, int chartHeight) {
		Font defaultFont = g2D.getFont();

		updateG2D(g2D, null, TEXT_COLOR, columns * (cellWidth + OVERFLOW) / 2, chartHeight + BOTTOM_PADDING / 3, 0, 0,
				g2D.getFont().deriveFont(Font.PLAIN).deriveFont(15f), xDescription);

		g2D.setFont(defaultFont);
	}

	/**
	 * Writes the description of <i>y axis</i>.
	 * 
	 * @param g2D         graphics element.
	 * @param chartHeight chart height.
	 */
	private void addYDescription(Graphics2D g2D, int chartHeight) {
		AffineTransform defaultAt = g2D.getTransform();
		Font defaultFont = g2D.getFont();

		AffineTransform at = new AffineTransform();
		at.rotate(-Math.PI / 2);

		g2D.setTransform(at);

		updateG2D(g2D, null, null, (int) (-chartHeight / 1.35), LEFT_PADDING / 2, 0, 0,
				g2D.getFont().deriveFont(Font.PLAIN).deriveFont(20f), yDescription);

		g2D.setTransform(defaultAt);
		g2D.setFont(defaultFont);
	}

	/**
	 * Method used for painting bars.
	 * 
	 * @param g2D         graphics element.
	 * @param rows        number of rows.
	 * @param columns     number of columns.
	 * @param cellWidth   cell width.
	 * @param cellHeight  cell height.
	 * @param chartHeight chart height.
	 */
	private void fillBars(Graphics2D g2D, int rows, int columns, int cellWidth, int cellHeight, int chartHeight) {
		for (int i = 0, x = LEFT_PADDING; i < columns; i++, x += cellWidth) {
			g2D.setColor(BAR_COLOR);
			XYValue value = coordinates.get(i);
			int y = value.getY() / yDifference * cellHeight;
			g2D.fillRect(x, chartHeight - BOTTOM_PADDING - y, cellWidth, cellHeight * value.getY() / yDifference);

			// draw white lines between bars
			updateG2D(g2D, null, BACKGROUND_COLOR, x + cellWidth - 1, chartHeight - BOTTOM_PADDING - y,
					x + cellWidth - 1, chartHeight - BOTTOM_PADDING, null, null);
		}
	}

	/**
	 * Helper method used to update graphics element. If one of the arguments is
	 * <code>null</code>, that property does not need to be updated.
	 * 
	 * @param g2D    graphics element.
	 * @param stroke stroke.
	 * @param color  color.
	 * @param x      x coordinate.
	 * @param y      y coordinate.
	 * @param width  width.
	 * @param height height.
	 * @param font   font.
	 * @param string string name.
	 */
	private void updateG2D(Graphics2D g2D, Stroke stroke, Color color, int x, int y, int width, int height, Font font,
			String string) {

		if (stroke != null) {
			g2D.setStroke(stroke);
		}

		if (color != null) {
			g2D.setColor(color);
		}

		if (font != null) {
			g2D.setFont(font);
		}

		if (string != null) {
			g2D.drawString(string, x, y);
		} else {
			g2D.drawLine(x, y, width, height);
		}
	}
}
