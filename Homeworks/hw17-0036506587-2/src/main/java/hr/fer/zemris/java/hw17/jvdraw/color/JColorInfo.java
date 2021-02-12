package hr.fer.zemris.java.hw17.jvdraw.color;

import java.awt.BasicStroke;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 * Model of {@link JLabel} component which is implemented to show
 * <b><i>RGB</i></b> values of <b><i>foreground / background</i></b> color.
 * 
 * @author dbrcina
 *
 */
public class JColorInfo extends JLabel {

	/**
	 * Default serial ID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor used for initialization.
	 * 
	 * @param fgColor foreground color.
	 * @param bgColor background color.
	 */
	public JColorInfo(JColorArea fgColor, JColorArea bgColor) {
		setHorizontalAlignment(SwingConstants.CENTER);
		setBorder(BorderFactory.createStrokeBorder(new BasicStroke()));
		updateText(fgColor, bgColor);

		fgColor.addColorChangeListener((source, oldColor, newColor) -> {
			updateText(fgColor, bgColor);
		});
		bgColor.addColorChangeListener((source, oldColor, newColor) -> {
			updateText(fgColor, bgColor);
		});

	}

	/**
	 * Updates label text with {@link JColorArea#toString()} representations.
	 * 
	 * @param fgColor foreground color.
	 * @param bgColor background color.
	 */
	private void updateText(JColorArea fgColor, JColorArea bgColor) {
		setText(fgColor.toString() + "; " + bgColor.toString());
	}

}
