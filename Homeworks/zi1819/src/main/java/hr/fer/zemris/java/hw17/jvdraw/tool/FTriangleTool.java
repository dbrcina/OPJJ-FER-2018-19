package hr.fer.zemris.java.hw17.jvdraw.tool;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;

import hr.fer.zemris.java.hw17.jvdraw.JDrawingCanvas;
import hr.fer.zemris.java.hw17.jvdraw.color.IColorProvider;
import hr.fer.zemris.java.hw17.jvdraw.geobject.FTriangle;
import hr.fer.zemris.java.hw17.jvdraw.geobject.visitor.GeometricalObjectPainter;
import hr.fer.zemris.java.hw17.jvdraw.model.DrawingModel;

public class FTriangleTool extends ToolObject {

	private IColorProvider bgProvider;
	private int numOfClicks;
	private FTriangle currentTriangle;
	private FTriangle finalTriangle;

	public FTriangleTool(DrawingModel model, JDrawingCanvas canvas, IColorProvider fgProvider,
			IColorProvider bgProvider) {
		super(model, canvas, fgProvider);
		this.bgProvider = bgProvider;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		numOfClicks++;
		if (numOfClicks == 1) {
			currentTriangle = new FTriangle(e.getPoint(), e.getPoint(), e.getPoint(), fgProvider.getCurrentColor(),
					bgProvider.getCurrentColor());
		} else if (numOfClicks == 3) {
			finalTriangle = (FTriangle) currentTriangle.copy();
			model.add(finalTriangle);
			finalTriangle = null;
			currentTriangle = null;
			numOfClicks = 0;
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (numOfClicks == 1) {
			currentTriangle.setP2(e.getPoint());
			canvas.repaint();
		} else if (numOfClicks == 2) {
			currentTriangle.setP3(e.getPoint());
			canvas.repaint();
		}
	}

	@Override
	public void paint(Graphics2D g2d) {
		if (currentTriangle == null && finalTriangle == null) {
			return;
		}
		GeometricalObjectPainter painter = new GeometricalObjectPainter(g2d);
		if (currentTriangle != null) {
			painter.visit(currentTriangle);
		} else {
			painter.visit(finalTriangle);
		}
	}
}
