package hr.fer.zemris.java.hw17.jvdraw.geobject;

import java.awt.Color;
import java.awt.Point;
import java.util.StringJoiner;

import hr.fer.zemris.java.hw17.jvdraw.geobject.editor.GeometricalObjectEditor;
import hr.fer.zemris.java.hw17.jvdraw.geobject.editor.LineEditor;
import hr.fer.zemris.java.hw17.jvdraw.geobject.visitor.GeometricalObjectVisitor;

/**
 * Model of {@link GeometricalObject}.
 * 
 * <p>
 * Line is defined with start point, end point and a color.<br>
 * All of these properties are available through getters and setters.
 * </p>
 * 
 * @author dbrcina
 *
 */
public class Line extends GeometricalObject {

	/**
	 * Starting point.
	 */
	private Point start;
	/**
	 * End point.
	 */
	private Point end;
	/**
	 * Foreground color.
	 */
	private Color fgColor;

	/**
	 * Constructor used for initialization.
	 * 
	 * @param start   starting point.
	 * @param end     end point.
	 * @param fgColor foreground color.
	 */
	public Line(Point start, Point end, Color fgColor) {
		this.start = start;
		this.end = end;
		this.fgColor = fgColor;
	}

	/**
	 * Getter for starting point.
	 * 
	 * @return starting point.
	 */
	public Point getStartPoint() {
		return start;
	}

	/**
	 * Setter for starting point.
	 * 
	 * @param start starting point.
	 */
	public void setStartPoint(Point start) {
		this.start = start;
		notifyChange(this);
	}

	/**
	 * Getter for end point.
	 * 
	 * @return end point.
	 */
	public Point getEndPoint() {
		return end;
	}

	/**
	 * Setter for end point.
	 * 
	 * @param end end point.
	 */
	public void setEndPoint(Point end) {
		this.end = end;
		notifyChange(this);
	}

	/**
	 * Getter for foreground color.
	 * 
	 * @return foreground color.
	 */
	public Color getFgColor() {
		return fgColor;
	}

	/**
	 * Setter for foreground color.
	 * 
	 * @param fgColor foreground color.
	 */
	public void setFgColor(Color fgColor) {
		this.fgColor = fgColor;
		notifyChange(this);
	}

	@Override
	public GeometricalObject copy() {
		return new Line(start, end, fgColor);
	}
	
	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);
	}

	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new LineEditor(this);
	}

	@Override
	public String toString() {
		return String.format(" Line (%d,%d)-(%d,%d)", start.x, start.y, end.x, end.y);
	}

	@Override
	public String fileRepresentation() {
		StringJoiner sj = new StringJoiner(" ");
		sj.add("LINE");
		sj.add(Integer.toString(start.x));
		sj.add(Integer.toString(start.y));
		sj.add(Integer.toString(end.x));
		sj.add(Integer.toString(end.y));
		sj.add(Integer.toString(fgColor.getRed()));
		sj.add(Integer.toString(fgColor.getGreen()));
		sj.add(Integer.toString(fgColor.getBlue()));
		return sj.toString();
	}

}
