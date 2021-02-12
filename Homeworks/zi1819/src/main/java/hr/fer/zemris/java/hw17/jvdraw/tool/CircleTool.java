package hr.fer.zemris.java.hw17.jvdraw.tool;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw17.jvdraw.JDrawingCanvas;
import hr.fer.zemris.java.hw17.jvdraw.color.IColorProvider;
import hr.fer.zemris.java.hw17.jvdraw.geobject.Circle;
import hr.fer.zemris.java.hw17.jvdraw.geobject.visitor.GeometricalObjectPainter;
import hr.fer.zemris.java.hw17.jvdraw.model.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.util.Util;

/**
 * An implementation of {@link ToolObject}.<br>
 * It represents {a @link Tool} object for drawing a {@link Circle}.
 * 
 * @author dbrcina
 *
 */
public class CircleTool extends ToolObject {

	/**
	 * Current circle.
	 */
	private Circle currentCircle;
	/**
	 * Final circle.
	 */
	private Circle finalCircle;

	/**
	 * Constructor used for initialization.
	 * 
	 * @param model      model.
	 * @param canvas     canvas.
	 * @param fgProvider provider.
	 */
	public CircleTool(DrawingModel model, JDrawingCanvas canvas, IColorProvider fgProvider) {
		super(model, canvas, fgProvider);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (isFirstClick) {
			currentCircle = new Circle(e.getPoint(), 0, fgProvider.getCurrentColor());
			isFirstClick = false;
		} else {
			finalCircle = (Circle) currentCircle.copy();
			model.add(finalCircle);
			currentCircle = null;
			finalCircle = null;
			isFirstClick = true;
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (!isFirstClick) {
			int radius = Util.calculateDistance(currentCircle.getCenter(), e.getPoint());
			currentCircle.setRadius(radius);
			canvas.repaint();
		}
	}

	@Override
	public void paint(Graphics2D g2d) {
		if (currentCircle == null && finalCircle == null) {
			return;
		}
		GeometricalObjectPainter painter = new GeometricalObjectPainter(g2d);
		if (currentCircle != null) {
			painter.visit(currentCircle);
		} else {
			painter.visit(finalCircle);
		}
	}
}
