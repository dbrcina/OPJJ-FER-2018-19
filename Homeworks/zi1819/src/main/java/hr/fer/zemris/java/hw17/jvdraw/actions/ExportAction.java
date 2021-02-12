package hr.fer.zemris.java.hw17.jvdraw.actions;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import hr.fer.zemris.java.hw17.jvdraw.JVDraw;
import hr.fer.zemris.java.hw17.jvdraw.geobject.visitor.GeometricalObjectBBCalculator;
import hr.fer.zemris.java.hw17.jvdraw.geobject.visitor.GeometricalObjectPainter;
import hr.fer.zemris.java.hw17.jvdraw.geobject.visitor.GeometricalObjectVisitor;
import hr.fer.zemris.java.hw17.jvdraw.model.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.util.Util;

/**
 * An implementation of <b><i>EXPORT</i></b> action from programs menu.
 * 
 * <p>
 * This action exports current paint state into some <i>Image file</i> -
 * <b><i>jpg, png, gif</i></b> extensions only.<br>
 * Image's dimensions are calculated through instance of
 * {@link GeometricalObjectBBCalculator} and results are retrieved through
 * {@link GeometricalObjectBBCalculator#getBoundingBox()} method.
 * </p>
 * 
 * <p>
 * If the file extension is invalid, an appropriate message is sent to the
 * user.<br>
 * Also, action will have some feedback whether transaction was successful or
 * some error occured.
 * </p>
 * 
 * @author dbrcina
 *
 */
public class ExportAction extends AbstractAction {

	/**
	 * Default serial ID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * List of valid extensions.
	 */
	private static final List<String> VALID_EXTENSIONS = Arrays.asList("jpg", "png", "gif");
	/**
	 * Reference to main frame.
	 */
	private JVDraw jvDraw;

	/**
	 * Constructor used for initialization.
	 * 
	 * @param jvDraw main frame.
	 */
	public ExportAction(JVDraw jvDraw) {
		this.jvDraw = jvDraw;
		initAction();
	}

	/**
	 * Helper method used for initialization <b>this</b> actions <i>Action map</i>.
	 */
	private void initAction() {
		putValue(Action.NAME, "Export..");
		putValue(Action.SHORT_DESCRIPTION, "Exports current file in specified format");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_E);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control E"));
		setEnabled(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Path destination = preparePath();
		if (destination == null) {
			return;
		}

		File file = destination.toFile();
		String extension = getExtension(file);
		if (!validateExtension(extension)) {
			JOptionPane.showMessageDialog(
					jvDraw, 
					"Invalid image extension", 
					"Error", 
					JOptionPane.ERROR_MESSAGE
			);
			return;
		}
		
		DrawingModel model = jvDraw.getModel();
		GeometricalObjectBBCalculator bbcalc = new GeometricalObjectBBCalculator();
		for (int i = 0; i < model.getSize(); i++) {
			model.getObject(i).accept(bbcalc);
		}

		try {
			BufferedImage image = createExportImage(model, bbcalc.getBoundingBox());
			ImageIO.write(image, extension, file);
			JOptionPane.showMessageDialog(
					jvDraw, 
					"Image was successfully exported.",
					"Information",
					JOptionPane.INFORMATION_MESSAGE);
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(jvDraw, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}

	}

	/**
	 * Prepares a path into where an export result needs to be written.
	 * 
	 * @return result of {@link Util#getSavePath(JVDraw)} method.
	 */
	private Path preparePath() {
		FileFilter filter = new FileNameExtensionFilter("Image files(jpg, png, gif)", "jpg", "png", "gif");
		Util.getChooser().setFileFilter(filter);
		return Util.getSavePath(jvDraw);
	}

	/**
	 * Checks whether provided <i>extension</i> is valid.
	 * 
	 * @param extension extension.
	 * @return <code>true</code> if extension is valid, otherwise
	 *         <code>false</code>.
	 */
	private boolean validateExtension(String extension) {
		return VALID_EXTENSIONS.contains(extension);
	}

	/**
	 * Retrieves extension from <i>file</i> name.
	 * 
	 * @param file file.
	 * @return file extension.
	 */
	private String getExtension(File file) {
		String fileName = file.getName();
		return fileName.substring(fileName.indexOf(".") + 1);
	}

	/**
	 * Helper method used for creation of export image which is in form of
	 * {@link BufferedImage}
	 * 
	 * @param model model.
	 * @param box   bounding box.
	 * @return new instance of {@link BufferedImage} as a result.
	 */
	public BufferedImage createExportImage(DrawingModel model, Rectangle box) {
		BufferedImage image = new BufferedImage(box.width, box.height, BufferedImage.TYPE_3BYTE_BGR);

		Graphics2D g2d = image.createGraphics();
		g2d.setColor(Color.WHITE);
		g2d.fillRect(0, 0, box.width, box.height);
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setStroke(new BasicStroke(2));
		g2d.translate(-box.x, -box.y);

		GeometricalObjectVisitor painter = new GeometricalObjectPainter(g2d);
		for (int i = 0; i < model.getSize(); i++) {
			model.getObject(i).accept(painter);
		}
		g2d.dispose();

		return image;
	}

}
