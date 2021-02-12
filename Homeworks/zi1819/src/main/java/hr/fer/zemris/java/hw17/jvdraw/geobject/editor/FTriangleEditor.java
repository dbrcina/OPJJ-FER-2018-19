package hr.fer.zemris.java.hw17.jvdraw.geobject.editor;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JTextArea;

import hr.fer.zemris.java.hw17.jvdraw.color.JColorArea;
import hr.fer.zemris.java.hw17.jvdraw.geobject.FTriangle;
import hr.fer.zemris.java.hw17.jvdraw.util.Util;

public class FTriangleEditor extends GeometricalObjectEditor {

	private FTriangle triangle;
	private JTextArea outline;
	private JTextArea fill;
	private String validOutline;
	private String validFill;

	public FTriangleEditor(FTriangle triangle) {
		this.triangle = triangle;
		preparePanel();
	}

	private void preparePanel() {
		setLayout(new GridLayout(0, 2));
		Color fg = triangle.getFg();
		outline = new JTextArea(Util.colorToHexidecimal(fg));
		add(new JLabel("foreground"));
		add(outline);
		Color bg = triangle.getBg();
		fill = new JTextArea(Util.colorToHexidecimal(bg));
		add(new JLabel("background"));
		add(fill);
	}

	@Override
	public void checkEditing() throws Exception {
		validOutline = validateColor(outline.getText());
		validFill = validateColor(fill.getText());
	}

	@Override
	public void acceptEditing() {
		triangle.setFg(Color.decode(validOutline));
		triangle.setBg(Color.decode(validFill));
	}

}
