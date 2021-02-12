package hr.fer.zemris.java.hw17.jvdraw.color;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JColorChooser;
import javax.swing.JComponent;

/**
 * An instance of {@link JComponent} class and implementation of
 * {@link IColorProvider} interface.
 * 
 * <p>
 * It provides user to change <b><i>foreground / background</i></b> color of
 * current model through {@link JColorChooser}.
 * </p>
 * 
 * @author dbrcina
 *
 */
public class JColorArea extends JComponent implements IColorProvider {

	/**
	 * Default serial ID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Valid name types of this component.
	 */
	private static final List<String> VALID_TYPES = Arrays.asList("foreground", "background");
	/**
	 * Default size of this component.
	 */
	private static final int DEFAULT_SIZE = 15;
	/**
	 * Currently selected color.
	 */
	private Color selectedColor;
	/**
	 * Component's name type.
	 */
	private String type;
	/**
	 * List of listeners.
	 */
	private List<ColorChangeListener> listeners = new ArrayList<>();

	/**
	 * Constructor used for initialization.
	 * 
	 * @param selectedColor selected(initial) color.
	 * @param type          type.
	 */
	public JColorArea(Color selectedColor, String type) {
		this.selectedColor = selectedColor;

		type = type.toLowerCase();
		if (!VALID_TYPES.contains(type)) {
			throw new IllegalArgumentException("\"" + type + "\" is invalid type.");
		}
		this.type = type.substring(0, 1).toUpperCase() + type.substring(1);

		setToolTipText(this.type + " color");

		onClickListener();
	}

	/**
	 * Registration of {@link MouseListener}.
	 */
	private void onClickListener() {
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Color newColor = JColorChooser.showDialog(JColorArea.this, "Change " + type + " color", selectedColor);
				if (newColor == null) {
					return;
				}
				Color oldColor = selectedColor;
				selectedColor = newColor;
				listeners.forEach(l -> l.newColorSelected(JColorArea.this, oldColor, newColor));
				repaint();
			}
		});

	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;
		Insets insets = getInsets();

		g2d.setColor(selectedColor);
		g2d.fillRect(insets.left, insets.top, DEFAULT_SIZE, DEFAULT_SIZE);
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(DEFAULT_SIZE, DEFAULT_SIZE);
	}

	@Override
	public Color getCurrentColor() {
		return selectedColor;
	}

	@Override
	public void addColorChangeListener(ColorChangeListener l) {
		listeners.add(l);
	}

	@Override
	public void removeColorChangeListener(ColorChangeListener l) {
		listeners.remove(l);
	}

	/**
	 * Getter for component's type.
	 * 
	 * @return component's type.
	 */
	public String getType() {
		return type;
	}

	@Override
	public String toString() {
		return String.format("%s color: (%d, %d %d)",
				type, 
				selectedColor.getRed(), 
				selectedColor.getGreen(),
				selectedColor.getBlue()
		);
	}
}
