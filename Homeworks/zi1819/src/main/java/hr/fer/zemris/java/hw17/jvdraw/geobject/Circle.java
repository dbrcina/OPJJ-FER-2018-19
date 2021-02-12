package hr.fer.zemris.java.hw17.jvdraw.geobject;

import java.awt.Color;
import java.awt.Point;
import java.util.StringJoiner;

import hr.fer.zemris.java.hw17.jvdraw.geobject.editor.CircleEditor;
import hr.fer.zemris.java.hw17.jvdraw.geobject.editor.GeometricalObjectEditor;
import hr.fer.zemris.java.hw17.jvdraw.geobject.visitor.GeometricalObjectVisitor;

/**
 * Model of {@link GeometricalObject}.
 * 
 * <p>
 * Circle is defined with center point, radius and outline color.<br>
 * All of these properties are available through getters and setters.
 * </p>
 * 
 * @author dbrcina
 *
 */
public class Circle extends GeometricalObject {

	/**
	 * Center point.
	 */
	private Point center;
	/**
	 * Circle radius.
	 */
	private int radius;
	/**
	 * Outline color.
	 */
	private Color outline;

	/**
	 * Constructor used for initialization.
	 * 
	 * @param center  center point.
	 * @param radius  radius.
	 * @param outline outline color.
	 */
	public Circle(Point center, int radius, Color outline) {
		this.center = center;
		this.radius = radius;
		this.outline = outline;
	}

	/**
	 * Getter for center point.
	 * 
	 * @return center point.
	 */
	public Point getCenter() {
		return center;
	}

	/**
	 * Setter for center point.
	 * 
	 * @param center center point.
	 */
	public void setCenter(Point center) {
		this.center = center;
		notifyChange(this);
	}

	/**
	 * Getter for radius.
	 * 
	 * @return radius.
	 */
	public int getRadius() {
		return radius;
	}

	/**
	 * Setter for radius.
	 * 
	 * @param radius radius.
	 */
	public void setRadius(int radius) {
		this.radius = radius;
		notifyChange(this);
	}

	/**
	 * Getter for outline color.
	 * 
	 * @return outline color.
	 */
	public Color getOutline() {
		return outline;
	}

	/**
	 * Setter for outline color.
	 * 
	 * @param outline outline color.
	 */
	public void setOutline(Color outline) {
		this.outline = outline;
		notifyChange(this);
	}

	@Override
	public GeometricalObject copy() {
		return new Circle(center, radius, outline);
	}
	
	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);
	}

	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new CircleEditor(this);
	}

	@Override
	public String toString() {
		return String.format(" Circle (%d,%d), %s", center.x, center.y, radius);
	}

	@Override
	public String fileRepresentation() {
		StringJoiner sj = new StringJoiner(" ");
		sj.add("CIRCLE");
		sj.add(Integer.toString(center.x));
		sj.add(Integer.toString(center.y));
		sj.add(Integer.toString(radius));
		sj.add(Integer.toString(outline.getRed()));
		sj.add(Integer.toString(outline.getGreen()));
		sj.add(Integer.toString(outline.getBlue()));
		return sj.toString();
	}

}
