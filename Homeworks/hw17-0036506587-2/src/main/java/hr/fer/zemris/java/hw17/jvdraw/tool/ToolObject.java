package hr.fer.zemris.java.hw17.jvdraw.tool;

import hr.fer.zemris.java.hw17.jvdraw.JDrawingCanvas;
import hr.fer.zemris.java.hw17.jvdraw.color.IColorProvider;
import hr.fer.zemris.java.hw17.jvdraw.model.DrawingModel;

/**
 * An implementation of {@link ToolAdapter}.
 * 
 * @author dbrcina
 *
 */
public class ToolObject extends ToolAdapter {

	/**
	 * {@link DrawingModel} model.
	 */
	protected DrawingModel model;
	/**
	 * {@link JDrawingCanvas} canvas.
	 */
	protected JDrawingCanvas canvas;
	/**
	 * {@link IColorProvider} foreground provider.
	 */
	protected IColorProvider fgProvider;
	/**
	 * Boolean flag.
	 */
	protected boolean isFirstClick = true;

	/**
	 * Constructor used for initialization.
	 * 
	 * @param model      model.
	 * @param canvas     canvas
	 * @param fgProvider provider.
	 */
	public ToolObject(DrawingModel model, JDrawingCanvas canvas, IColorProvider fgProvider) {
		this.model = model;
		this.canvas = canvas;
		this.fgProvider = fgProvider;
	}

}
