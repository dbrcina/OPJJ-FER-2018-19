package hr.fer.zemris.java.hw17.jvdraw.geobject;

import java.awt.Color;
import java.awt.Point;
import java.util.StringJoiner;

import hr.fer.zemris.java.hw17.jvdraw.geobject.editor.FilledCircleEditor;
import hr.fer.zemris.java.hw17.jvdraw.geobject.editor.GeometricalObjectEditor;
import hr.fer.zemris.java.hw17.jvdraw.geobject.visitor.GeometricalObjectVisitor;

/**
 * Model of {@link GeometricalObject}.
 * 
 * <p>
 * Filled circle is defined with center point, radius, outline color and fill
 * color.<br>
 * All of these properties are available through getters and setters.
 * </p>
 * 
 * @author dbrcina
 *
 */
public class FilledCircle extends GeometricalObject {

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
	 * Fill color.
	 */
	private Color fill;

	/**
	 * Constructor used for initialization.
	 * 
	 * @param center  center point.
	 * @param radius  radius.
	 * @param outline outline color.
	 * @param fill    fill color.
	 */
	public FilledCircle(Point center, int radius, Color outline, Color fill) {
		this.center = center;
		this.radius = radius;
		this.outline = outline;
		this.fill = fill;
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

	/**
	 * Getter for fill color.
	 * 
	 * @return fill folor.
	 */
	public Color getFill() {
		return fill;
	}

	/**
	 * Setter for fill color.
	 * 
	 * @param fill fill color.
	 */
	public void setFill(Color fill) {
		this.fill = fill;
		notifyChange(this);
	}

	@Override
	public GeometricalObject copy() {
		return new FilledCircle(center, radius, outline, fill);
	}
	
	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);
	}

	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new FilledCircleEditor(this);
	}

	@Override
	public String toString() {
		return String.format(" Filled circle (%d,%d) , %s, #%02X%02X%02X", center.x, center.y, radius, fill.getRed(),
				fill.getGreen(), fill.getBlue());
	}

	@Override
	public String fileRepresentation() {
		StringJoiner sj = new StringJoiner(" ");
		sj.add("FCIRCLE");
		sj.add(Integer.toString(center.x));
		sj.add(Integer.toString(center.y));
		sj.add(Integer.toString(radius));
		sj.add(Integer.toString(outline.getRed()));
		sj.add(Integer.toString(outline.getGreen()));
		sj.add(Integer.toString(outline.getBlue()));
		sj.add(Integer.toString(fill.getRed()));
		sj.add(Integer.toString(fill.getGreen()));
		sj.add(Integer.toString(fill.getBlue()));
		return sj.toString();
	}

}
