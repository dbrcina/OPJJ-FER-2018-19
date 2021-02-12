package hr.fer.zemris.java.hw17.jvdraw.geobject.visitor;

import java.awt.Graphics2D;

import hr.fer.zemris.java.hw17.jvdraw.JDrawingCanvas;
import hr.fer.zemris.java.hw17.jvdraw.geobject.Circle;
import hr.fer.zemris.java.hw17.jvdraw.geobject.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.geobject.Line;

/**
 * <i>Visitor</> model from <i>Visitor Design Pattern</i>.
 * 
 * @author dbrcina
 *
 */
public interface GeometricalObjectVisitor {

	/**
	 * Visits {@link Line} object and draws it into instance of {@link Graphics2D}
	 * which will later be rendered on {@link JDrawingCanvas} component.
	 * 
	 * @param line line.
	 */
	void visit(Line line);

	/**
	 * Visits {@link Circle} object and draws it into instance of {@link Graphics2D}
	 * which will later be rendered on {@link JDrawingCanvas} component.
	 * 
	 * @param circle circle.
	 */
	void visit(Circle circle);

	/**
	 * Visits {@link FilledCircle} object and draws it into instance of
	 * {@link Graphics2D} which will later be rendered on {@link JDrawingCanvas}
	 * component.
	 * 
	 * @param filledCircle filled circle.
	 */
	void visit(FilledCircle filledCircle);
}
