package hr.fer.zemris.java.hw17.jvdraw;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JComponent;

import hr.fer.zemris.java.hw17.jvdraw.geobject.visitor.GeometricalObjectPainter;
import hr.fer.zemris.java.hw17.jvdraw.model.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.model.DrawingModelListener;

/**
 * This {@link JComponent} represents a <i>"page"</i> which is used for
 * painting.
 * 
 * <p>
 * It is an implementation of {@link DrawingModelListener} which means that the
 * content is rendered dynamically.
 * </p>
 * 
 * @author dbrcina
 *
 */
public class JDrawingCanvas extends JComponent implements DrawingModelListener {

	/**
	 * Default serial ID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Defualt canvas color;
	 */
	private static final Color CANVAS_COLOR = Color.WHITE;
	/**
	 * Reference to {@link JVDraw} frame.
	 */
	private JVDraw jvDraw;

	/**
	 * Constructor used for initialization.
	 * 
	 * @param jvDraw paint frame.
	 */
	public JDrawingCanvas(JVDraw jvDraw) {
		this.jvDraw = jvDraw;
		jvDraw.getModel().addDrawingModelListener(this);

		setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.BLACK));
		mouseListeners();
	}

	/**
	 * Helper method which sets up {@link MouseListener}'s which are listening to
	 * <b>this</b> component.
	 */
	private void mouseListeners() {
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				jvDraw.getCurrentTool().mouseClicked(e);
			}

		});
		addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				jvDraw.getCurrentTool().mouseMoved(e);
			}
		});
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(CANVAS_COLOR);
		g2d.fillRect(0, 0, getWidth(), getHeight());
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setStroke(new BasicStroke(2));

		DrawingModel drawingModel = jvDraw.getModel();
		GeometricalObjectPainter painter = new GeometricalObjectPainter(g2d);
		for (int i = 0; i < drawingModel.getSize(); i++) {
			drawingModel.getObject(i).accept(painter);
		}

		jvDraw.getCurrentTool().paint(g2d);
	}

	@Override
	public void objectsAdded(DrawingModel source, int index0, int index1) {
		repaint();
	}

	@Override
	public void objectsRemoved(DrawingModel source, int index0, int index1) {
		repaint();
	}

	@Override
	public void objectsChanged(DrawingModel source, int index0, int index1) {
		repaint();
	}

}
