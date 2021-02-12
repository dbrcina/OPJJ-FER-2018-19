package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * A custom implementation of {@link LayoutManager2} interface.
 * 
 * @author dbrcina
 *
 */
public class CalcLayout implements LayoutManager2 {

	/**
	 * Default number of rows.
	 */
	private static final int NUM_OF_ROWS = 5;

	/**
	 * Default number of columns.
	 */
	private static final int NUM_OF_COLUMNS = 7;

	/**
	 * Default number of components.
	 */
	private static final int NUM_OF_COMPONENTS = 31;

	/**
	 * Default padding.
	 */
	private static final int DEF_PADDING = 0;

	/**
	 * Default x alignment.
	 */
	private static final float X_ALIGN = 0F;

	/**
	 * Default y alignment.
	 */
	private static final float Y_ALIGN = 0F;

	/**
	 * Padding between components.
	 */
	private int padding;

	/**
	 * Map of all added components into this layout. Key value is instance of
	 * {@link Component} and map value is instance of {@link RCPosition}.
	 */
	private HashMap<Component, RCPosition> components = new HashMap<>();

	/**
	 * Default constructor that creates new {@link #CalcLayout()} with padding
	 * between components set to 0.
	 */
	public CalcLayout() {
		this(DEF_PADDING);
	}

	/**
	 * Constructor that creates new {@link CalcLayout} with padding between
	 * components set to <code>padding</code>. Padding needs to be a non-negative
	 * number.
	 * 
	 * @param padding padding between components.
	 * @throws CalcLayoutException if <code>padding</code> is invalid.
	 */
	public CalcLayout(int padding) {
		if (padding < 0) {
			throw new CalcLayoutException("Padding needs to be a non-negative number");
		}
		this.padding = padding;
	}

	/**
	 * {@inheritDoc}
	 */
	public Dimension preferredLayoutSize(Container parent) {
		return getLayoutSize(parent, Component::getPreferredSize);
	}

	/**
	 * {@inheritDoc}
	 */
	public Dimension minimumLayoutSize(Container parent) {
		return getLayoutSize(parent, Component::getMinimumSize);
	}

	/**
	 * {@inheritDoc}
	 */
	public Dimension maximumLayoutSize(Container target) {
		return getLayoutSize(target, Component::getMaximumSize);
	}

	/**
	 * Calculates layout size of <code>parent</code> as determined by
	 * <code>action</code>.
	 * 
	 * @param parent parent container.
	 * @param action action.
	 * @return layout size as instance of {@link Dimension}.
	 */
	private Dimension getLayoutSize(Container parent, Function<Component, Dimension> action) {
		Dimension largestSize = getLargestCellSize(parent, action);
		Insets insets = parent.getInsets();
		largestSize.width = largestSize.width * NUM_OF_COLUMNS + padding * (NUM_OF_COLUMNS - 1) + insets.right
				+ insets.left;
		largestSize.height = largestSize.height * NUM_OF_ROWS + padding * (NUM_OF_ROWS - 1) + insets.top
				+ insets.bottom;
		return largestSize;
	}

	/**
	 * Calculates size of largest cell in <code>parent</code> layout as determined
	 * by <code>action</code>.
	 * 
	 * @param parent parent container.
	 * @param action action.
	 * @return size of largest cell as instance of {@link Dimension}.
	 */
	private Dimension getLargestCellSize(Container parent, Function<Component, Dimension> action) {
		Dimension max = new Dimension(0, 0);
		for (Map.Entry<Component, RCPosition> entry : components.entrySet()) {
			Dimension current = action.apply(entry.getKey());
			if (current == null) {
				continue;
			}
			if (entry.getValue().equals(new RCPosition(1, 1))) {
				current.width = current.width - 4 * padding / 5;
			}
			max.width = Math.max(max.width, current.width);
			max.height = Math.max(max.height, current.height);
		}
		return max;
	}

	/**
	 * {@inheritDoc}
	 */
	public void layoutContainer(Container parent) {
		Insets insets = parent.getInsets();
		Dimension prefComponentSize = getLargestCellSize(parent, Component::getPreferredSize);
		Dimension prefLayoutSize = preferredLayoutSize(parent);
		int width = prefComponentSize.width * parent.getWidth() / prefLayoutSize.width;
		int height = prefComponentSize.height * parent.getHeight() / prefLayoutSize.height;
		
		for (Map.Entry<Component, RCPosition> entry : components.entrySet()) {
			Component c = entry.getKey();
			RCPosition position = entry.getValue();
			if (position.equals(new RCPosition(1, 1))) {
				c.setBounds(insets.left, insets.top, width * 5 + 4 * padding, height);
			} else {
				c.setBounds(insets.left + (position.getColumn() - 1) * (width + padding),
						insets.top + (position.getRow() - 1) * (height + padding), width, height);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void addLayoutComponent(Component comp, Object constraints) {
		if (components.size() > NUM_OF_COMPONENTS) {
			throw new CalcLayoutException("Maximum number of components is " + NUM_OF_COMPONENTS + "!");
		}

		RCPosition position;

		if (!(constraints instanceof String) && !(constraints instanceof RCPosition)) {
			throw new CalcLayoutException(
					"Invalid constraints. Expecting instance of RCPosition or parsable pair (int,int)");
		}

		if (constraints instanceof String) {
			String[] parts = ((String) constraints).split(",");
			try {
				position = new RCPosition(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
			} catch (NumberFormatException e) {
				throw new CalcLayoutException("Cannot parse provided constraints!");
			}
		}

		else {
			position = (RCPosition) constraints;
		}

		checkConstraints(position.getRow(), position.getColumn());

		if (components.containsValue(position)) {
			throw new CalcLayoutException("On position " + position + " component already exists!");
		}

		components.put(comp, position);
	}

	/**
	 * Helper method that check whether <code>row</code> and <code>column</code> are
	 * valid.
	 * 
	 * @param row    row.
	 * @param column column.
	 */
	private void checkConstraints(int row, int column) {
		checkRow(row);
		checkColumn(column);
		if (row == 1) {
			checkFirstRow(column);
		}
	}

	/**
	 * Checks whether <code>row</code> is between
	 * <code>[1, {@value #NUM_OF_ROWS}]</code>.
	 * 
	 * @param row row.
	 * @throws CalcLayoutException if row is invalid.
	 */
	private void checkRow(int row) {
		if (row < 1 || row > NUM_OF_ROWS) {
			throw new CalcLayoutException("Row needs to be from [1," + NUM_OF_ROWS + "]!");
		}
	}

	/**
	 * Checks whether <code>column</code> is between
	 * <code>[1, {@value #NUM_OF_COLUMNS}]</code>.
	 * 
	 * @param column column..
	 * @throws CalcLayoutException if column is invalid.
	 */
	private void checkColumn(int column) {
		if (column < 1 || column > NUM_OF_COLUMNS) {
			throw new CalcLayoutException("Column needs to be from [1," + NUM_OF_COLUMNS + "]!");
		}
	}

	/**
	 * Checks whether first row is valid. Valid position in first row are
	 * <code>(1,1), (1,6), (1,7)</code>
	 * 
	 * @param column column.
	 * @throws CalcLayoutException if first row is invalid.
	 */
	private void checkFirstRow(int column) {
		if (column > 1 && column < 6) {
			throw new CalcLayoutException("Valid position in first row are (1,1), (1,6) and (1,7)!");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void addLayoutComponent(String name, Component comp) {
		throw new UnsupportedOperationException();
	}

	/**
	 * {@inheritDoc}
	 */
	public void removeLayoutComponent(Component comp) {
		components.remove(comp);
	}

	/**
	 * {@inheritDoc}
	 */
	public float getLayoutAlignmentX(Container target) {
		return X_ALIGN;
	}

	/**
	 * {@inheritDoc}
	 */
	public float getLayoutAlignmentY(Container target) {
		return Y_ALIGN;
	}

	/**
	 * {@inheritDoc}
	 */
	public void invalidateLayout(Container target) {
	}

}
