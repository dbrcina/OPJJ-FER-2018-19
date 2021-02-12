package hr.fer.zemris.java.hw17.jvdraw.geobject.visitor;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;

import hr.fer.zemris.java.hw17.jvdraw.geobject.Circle;
import hr.fer.zemris.java.hw17.jvdraw.geobject.FTriangle;
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

	@Override
	public void visit(FTriangle triangle) {
		Point p1 = triangle.getP1();
		Point p2 = triangle.getP2();
		Point p3 = triangle.getP3();

		int x1 = p1.x;
		int x2 = p2.x;
		int x3 = p3.x;
		int y1 = p1.y;
		int y2 = p2.y;
		int y3 = p3.y;

		int xmax = Math.max(x1, Math.max(x2, x3));
		int ymax = Math.max(y1, Math.max(y2, y3));
		int xmin = Math.min(x1, Math.min(x2, x3));
		int ymin = Math.min(y1, Math.min(y2, y3));

		updateBoundingBox(new Rectangle(xmin, ymin, xmax - xmin, ymax - ymin));
	}
}
