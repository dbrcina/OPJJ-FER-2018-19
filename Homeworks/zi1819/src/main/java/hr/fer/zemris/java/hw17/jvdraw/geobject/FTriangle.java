package hr.fer.zemris.java.hw17.jvdraw.geobject;

import java.awt.Color;
import java.awt.Point;
import java.util.StringJoiner;

import hr.fer.zemris.java.hw17.jvdraw.color.IColorProvider;
import hr.fer.zemris.java.hw17.jvdraw.geobject.editor.FTriangleEditor;
import hr.fer.zemris.java.hw17.jvdraw.geobject.editor.GeometricalObjectEditor;
import hr.fer.zemris.java.hw17.jvdraw.geobject.visitor.GeometricalObjectVisitor;

public class FTriangle extends GeometricalObject {

	private Point p1;
	private Point p2;
	private Point p3;
	private Color fg;
	private Color bg;

	public Point getP1() {
		return p1;
	}

	public void setP1(Point p1) {
		this.p1 = p1;
		notifyChange(this);
	}

	public Point getP2() {
		return p2;
	}

	public void setP2(Point p2) {
		this.p2 = p2;
		notifyChange(this);
	}

	public Point getP3() {
		return p3;
	}

	public void setP3(Point p3) {
		this.p3 = p3;
		notifyChange(this);
	}

	public Color getFg() {
		return fg;
	}

	public void setFg(Color fg) {
		this.fg = fg;
		notifyChange(this);
	}

	public Color getBg() {
		return bg;
	}

	public void setBg(Color bg) {
		this.bg = bg;
		notifyChange(this);
	}

	public FTriangle(Point p1, Point p2, Point p3, Color fg, Color bg) {
		this.p1 = p1;
		this.p2 = p2;
		this.p3 = p3;
		this.fg = fg;
		this.bg = bg;
	}

	@Override
	public void accept(GeometricalObjectVisitor v) {
		v.visit(this);
	}

	@Override
	public GeometricalObjectEditor createGeometricalObjectEditor() {
		return new FTriangleEditor(this);
	}

	@Override
	public String fileRepresentation() {
		StringJoiner sj = new StringJoiner(" ");
		sj.add("FTRIANGLE");
		sj.add(Integer.toString(p1.x));
		sj.add(Integer.toString(p1.y));
		sj.add(Integer.toString(p2.x));
		sj.add(Integer.toString(p2.y));
		sj.add(Integer.toString(p3.x));
		sj.add(Integer.toString(p3.y));
		sj.add(Integer.toString(fg.getRed()));
		sj.add(Integer.toString(fg.getGreen()));
		sj.add(Integer.toString(fg.getBlue()));
		sj.add(Integer.toString(bg.getRed()));
		sj.add(Integer.toString(bg.getGreen()));
		sj.add(Integer.toString(bg.getBlue()));
		return sj.toString();
	}

	@Override
	public String toString() {
		return String.format(" FTRIANGLE #%02X%02X%02X, #%02X%02X%02X", fg.getRed(), fg.getGreen(), fg.getBlue(),
				bg.getRed(), bg.getGreen(), bg.getBlue());
	}

	@Override
	public GeometricalObject copy() {
		return new FTriangle(p1, p2, p3, fg, bg);
	}

}
