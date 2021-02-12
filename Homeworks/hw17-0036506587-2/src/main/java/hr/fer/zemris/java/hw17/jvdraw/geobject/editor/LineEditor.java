package hr.fer.zemris.java.hw17.jvdraw.geobject.editor;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.JLabel;
import javax.swing.JTextArea;

import hr.fer.zemris.java.hw17.jvdraw.geobject.Line;
import hr.fer.zemris.java.hw17.jvdraw.util.Util;

public class LineEditor extends GeometricalObjectEditor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Line line;
	private JTextArea startX;
	private JTextArea startY;
	private JTextArea endX;
	private JTextArea endY;
	private JTextArea fgColor;
	private Point validStart;
	private Point validEnd;
	private String validColor;

	public LineEditor(Line line) {
		this.line = line;
		preparePanel();
	}

	private void preparePanel() {
		setLayout(new GridLayout(0, 2));

		Point start = line.getStartPoint();
		startX = new JTextArea("" + start.x);
		startY = new JTextArea("" + start.y);

		add(new JLabel("Start - x axis:"));
		add(startX);
		add(new JLabel("Start - y axis:"));
		add(startY);

		Point end = line.getEndPoint();
		endX = new JTextArea("" + end.x);
		endY = new JTextArea("" + end.y);

		add(new JLabel("End - x axis:"));
		add(endX);
		add(new JLabel("End - y axis:"));
		add(endY);

		Color color = line.getFgColor();
		fgColor = new JTextArea(Util.colorToHexidecimal(color));

		add(new JLabel("Color:"));
		add(fgColor);
	}

	@Override
	public void checkEditing() throws Exception {
		validStart = validateCoordinates(startX.getText(), startY.getText());
		validEnd = validateCoordinates(endX.getText(), endY.getText());
		validColor = validateColor(fgColor.getText());
	}

	@Override
	public void acceptEditing() {
		line.setStartPoint(validStart);
		line.setEndPoint(validEnd);
		line.setFgColor(Color.decode(validColor));
	}

}
