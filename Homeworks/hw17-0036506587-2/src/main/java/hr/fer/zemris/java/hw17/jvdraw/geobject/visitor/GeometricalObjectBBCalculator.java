package hr.fer.zemris.java.hw17.jvdraw.geobject.visitor;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

import hr.fer.zemris.java.hw17.jvdraw.geobject.Circle;
import hr.fer.zemris.java.hw17.jvdraw.geobject.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.geobject.Line;

/**
 * An implementation of {@link GeometricalObjectVisitor} which is used for
 * calculating bounding box of all elements.
 * 
 * @author dbrcina
 *
 */
public class GeometricalObjectBBCalculator implements GeometricalObjectVisitor {

	/**
	 * Bounding box.
	 */
	private Rectangle boundingBox;

	/**
	 * Getter for bounding box.
	 * 
	 * @return bounding box.
	 */
	public Rectangle getBoundingBox() {
		return boundingBox;
	}

	@Override
	public void visit(Line line) {
		Point start = line.getStartPoint();
		Point end = line.getEndPoint();

		int topLeftX = Math.min(start.x, end.x);
		int topLeftY = Math.min(start.y, end.y);
		int width = Math.abs(start.x - end.x);
		int height = Math.abs(start.y - end.y);

		updateBoundingBox(new Rectangle(new Point(topLeftX, topLeftY), new Dimension(width, height)));
	}

	@Override
	public void visit(Circle circle) {
		calculateCircle(circle.getCenter(), circle.getRadius());
	}

	@Override
	public void visit(FilledCircle filledCircle) {
		calculateCircle(filledCircle.getCenter(), filledCircle.getRadius());
	}

	/**
	 * Calculate bounding box for circle.
	 * 
	 * @param center center.
	 * @param radius radius.
	 */
	private void calculateCircle(Point center, int radius) {
		int topLeftX = center.x - radius;
		int topLeftY = center.y - radius;
		int diametar = radius * 2;
		updateBoundingBox(new Rectangle(topLeftX, topLeftY, diametar, diametar));
	}

	/**
	 * Update bounding box by <i>rectangle</i>.
	 * 
	 * @param rectangle
	 */
	private void updateBoundingBox(Rectangle rectangle) {
		boundingBox = boundingBox == null ? rectangle : boundingBox.union(rectangle);
	}
}
