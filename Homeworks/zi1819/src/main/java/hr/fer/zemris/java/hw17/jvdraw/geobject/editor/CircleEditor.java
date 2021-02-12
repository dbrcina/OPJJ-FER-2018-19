package hr.fer.zemris.java.hw17.jvdraw.geobject.editor;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.JLabel;
import javax.swing.JTextArea;

import hr.fer.zemris.java.hw17.jvdraw.geobject.Circle;
import hr.fer.zemris.java.hw17.jvdraw.util.Util;

public class CircleEditor extends GeometricalObjectEditor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Circle circle;
	private JTextArea centerX;
	private JTextArea centerY;
	private JTextArea radius;
	private JTextArea outline;
	private Point validCenter;
	private int validRadius;
	private String validOutline;

	public CircleEditor(Circle circle) {
		this.circle = circle;
		prepanePanel();
	}

	private void prepanePanel() {
		setLayout(new GridLayout(0, 2));

		Point center = circle.getCenter();
		centerX = new JTextArea("" + center.x);
		centerY = new JTextArea("" + center.y);

		add(new JLabel("Center - x axis:"));
		add(centerX);
		add(new JLabel("Center - y axis:"));
		add(centerY);

		int r = circle.getRadius();
		radius = new JTextArea("" + r);

		add(new JLabel("Radius:"));
		add(radius);

		Color color = circle.getOutline();
		outline = new JTextArea(Util.colorToHexidecimal(color));

		add(new JLabel("Outline:"));
		add(outline);
	}

	@Override
	public void checkEditing() throws Exception {
		validCenter = validateCoordinates(centerX.getText(), centerY.getText());
		validRadius = validateRadius(radius.getText());
		validOutline = validateColor(outline.getText());
	}

	@Override
	public void acceptEditing() {
		circle.setCenter(validCenter);
		circle.setRadius(validRadius);
		circle.setOutline(Color.decode(validOutline));
	}

}
