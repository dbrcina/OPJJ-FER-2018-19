package hr.fer.zemris.java.hw17.jvdraw.geobject.visitor;

import java.awt.Graphics2D;
import java.awt.Point;

import hr.fer.zemris.java.hw17.jvdraw.JDrawingCanvas;
import hr.fer.zemris.java.hw17.jvdraw.geobject.Circle;
import hr.fer.zemris.java.hw17.jvdraw.geobject.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.geobject.Line;

/**
 * An implementation of {@link GeometricalObjectVisitor} interface.
 * 
 * <p>
 * It is used for drawing objects into instance of {@link Graphics2D} object
 * which will later be rendered on {@link JDrawingCanvas} component.
 * </p>
 * 
 * @author dbrcina
 *
 */
public class GeometricalObjectPainter implements GeometricalObjectVisitor {

	/**
	 * Instance of {@link Graphics2D}.
	 */
	private Graphics2D g2d;

	/**
	 * Constructor used for initialization.
	 * 
	 * @param g2d graphics 2d.
	 */
	public GeometricalObjectPainter(Graphics2D g2d) {
		this.g2d = g2d;
	}
	
	@Override
	public void visit(Line line) {
		Point start = line.getStartPoint();
		Point end = line.getEndPoint();

		g2d.setColor(line.getFgColor());
		g2d.drawLine(start.x, start.y, end.x, end.y);
	}

	@Override
	public void visit(Circle circle) {
		Point center = circle.getCenter();
		int radius = circle.getRadius();

		g2d.setColor(circle.getOutline());
		g2d.drawOval(center.x - radius, center.y - radius, radius * 2, radius * 2);

	}

	@Override
	public void visit(FilledCircle filledCircle) {
		Point center = filledCircle.getCenter();
		int radius = filledCircle.getRadius();

		g2d.setColor(filledCircle.getOutline());
		g2d.drawOval(center.x - radius, center.y - radius, radius * 2, radius * 2);
		g2d.setColor(filledCircle.getFill());
		g2d.fillOval(center.x - radius, center.y - radius, radius * 2, radius * 2);
	}

}
