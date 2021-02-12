package hr.fer.zemris.java.hw17.jvdraw.tool;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw17.jvdraw.JDrawingCanvas;
import hr.fer.zemris.java.hw17.jvdraw.color.IColorProvider;
import hr.fer.zemris.java.hw17.jvdraw.geobject.Line;
import hr.fer.zemris.java.hw17.jvdraw.geobject.visitor.GeometricalObjectPainter;
import hr.fer.zemris.java.hw17.jvdraw.model.DrawingModel;

/**
 * An implementation of {@link ToolObject}.<br>
 * It represents a {@link Tool} object for drawing a {@link Line}.
 * 
 * @author dbrcina
 *
 */
public class LineTool extends ToolObject {

	/**
	 * Current line.
	 */
	private Line currentLine;
	/**
	 * Final line.
	 */
	private Line finalLine;

	/**
	 * Constructor used for initialization.
	 * 
	 * @param model      model.
	 * @param canvas     canvas.
	 * @param fgProvider provider.
	 */
	public LineTool(DrawingModel model, JDrawingCanvas canvas, IColorProvider fgProvider) {
		super(model, canvas, fgProvider);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (isFirstClick) {
			currentLine = new Line(e.getPoint(), new Point(), fgProvider.getCurrentColor());
			isFirstClick = false;
		} else {
			finalLine = (Line) currentLine.copy();
			model.add(finalLine);
			currentLine = null;
			finalLine = null;
			isFirstClick = true;
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (!isFirstClick) {
			currentLine.setEndPoint(e.getPoint());
			canvas.repaint();
		}
	}

	@Override
	public void paint(Graphics2D g2d) {
		if (currentLine == null && finalLine == null) {
			return;
		}
		GeometricalObjectPainter painter = new GeometricalObjectPainter(g2d);
		if (currentLine != null) {
			painter.visit(currentLine);
		} else {
			painter.visit(finalLine);
		}
	}
}
