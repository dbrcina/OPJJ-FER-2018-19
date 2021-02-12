package hr.fer.zemris.java.hw17.jvdraw.actions;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;

import hr.fer.zemris.java.hw17.jvdraw.JVDraw;
import hr.fer.zemris.java.hw17.jvdraw.geobject.Circle;
import hr.fer.zemris.java.hw17.jvdraw.geobject.FTriangle;
import hr.fer.zemris.java.hw17.jvdraw.geobject.FilledCircle;
import hr.fer.zemris.java.hw17.jvdraw.geobject.Line;
import hr.fer.zemris.java.hw17.jvdraw.model.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.util.Util;

/**
 * An implementation of <b><i>OPEN</i></b> action from programs menu.
 * 
 * <p>
 * This action only opens <i>JVDraw files - jvd</i>, otherwise an appropriate
 * message is sent to the user.<br>
 * These files should have defined structure. If anything is invalid, an
 * appropriate message is sent to the user.
 * </p>
 * 
 * @author dbrcina
 *
 */
public class OpenAction extends AbstractAction {

	/**
	 * Default serial ID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Reference to main frame.
	 */
	private JVDraw jvDraw;

	/**
	 * Constructor used for initialization.
	 * 
	 * @param jvDraw main frame.
	 */
	public OpenAction(JVDraw jvDraw) {
		this.jvDraw = jvDraw;
		initAction();
	}

	/**
	 * Helper method used for initialization <b>this</b> actions <i>Action map</i>.
	 */
	private void initAction() {
		putValue(Action.NAME, "Open");
		putValue(Action.SHORT_DESCRIPTION, "Opens a .jvd file");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		DrawingModel model = jvDraw.getModel();
		checkModifications(e, model);
		
		model.clear();
		List<String> lines = null;
		try {
			Util.getChooser().setFileFilter(new FileNameExtensionFilter("JVDraw files(jvd)", "*.jvd"));
			lines = Util.readFromFile(jvDraw);
		} catch (IOException ignorable) {
		}
		
		if (lines == null) {
			return;
		}

		try {
			parseContent(lines, model);
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(jvDraw, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}

		model.clearModifiedFlag();
		jvDraw.getSaveAction().setEnabled(false);
	}

	/**
	 * Checks whether there are any modifications of <i>model</i>.<br>
	 * If modifications are found, user is asked whether he wants to save currently
	 * opened file before opening a new one.
	 * 
	 * @param e     action event.
	 * @param model model.
	 */
	private void checkModifications(ActionEvent e, DrawingModel model) {
		if (!model.isModified()) {
			return;
		}
		int answer = JOptionPane.showConfirmDialog(
				jvDraw,
				"Current file has been modified.\nDo you want to save it before opening a new one?",
				"Save",
				JOptionPane.YES_NO_CANCEL_OPTION, 
				JOptionPane.QUESTION_MESSAGE);
		if (answer != JOptionPane.YES_OPTION) {
			return;
		}
		jvDraw.getSaveAction().actionPerformed(e);
	}

	/**
	 * Parses read text from file.
	 * 
	 * @param lines lines.
	 * @param model model.
	 * @throws Exception if content is invalid.
	 */
	public void parseContent(List<String> lines, DrawingModel model) throws Exception {
		for (String line : lines) {
			String[] parts = line.split("\\s+");
			switch (parts[0].toUpperCase()) {
			case "LINE":
				parseLine(parts, model);
				break;
			case "CIRCLE":
				parseCircle(parts, model);
				break;
			case "FCIRCLE":
				parseFCircle(parts, model);
				break;
			case "FTRIANGLE":
				parseFTriangle(parts, model);
				break;
			default:
				throw new IllegalArgumentException("Incorrect line declaration was provided");
			}
		}
	}

	private void parseFTriangle(String[] parts, DrawingModel model) {
		if (parts.length != 13) {
			throw new IllegalArgumentException("Incorrect definition for FTRIANGLE was provided");
		}
		Point p1 = new Point(
				Util.validateNumber(parts[1], false),
				Util.validateNumber(parts[2], false)
		);
		Point p2 = new Point(
				Util.validateNumber(parts[3], false),
				Util.validateNumber(parts[4], false)
		);
		Point p3 = new Point(
				Util.validateNumber(parts[5], false),
				Util.validateNumber(parts[6], false)
		);
		Color fg = new Color(
				Util.validateNumber(parts[7], true),
				Util.validateNumber(parts[8], true),
				Util.validateNumber(parts[9], true)
		);
		Color bg = new Color(
				Util.validateNumber(parts[10], true),
				Util.validateNumber(parts[11], true),
				Util.validateNumber(parts[12], true)
		);
		model.add(new FTriangle(p1, p2, p3, fg, bg));
	}

	/**
	 * Parses <i>LINE</i> type object.
	 * 
	 * @param parts parts.
	 * @param model model.
	 * @throws Exception if line object is invalid.
	 */
	private void parseLine(String[] parts, DrawingModel model) throws Exception {
		if (parts.length != 8) {
			throw new IllegalArgumentException("Incorrect definition for LINE was provided");
		}
		Point start = new Point(
				Util.validateNumber(parts[1], false), 
				Util.validateNumber(parts[2], false)
		);
		Point end = new Point(
				Util.validateNumber(parts[3], false),
				Util.validateNumber(parts[4], false)
		);
		Color color = new Color(
				Util.validateNumber(parts[5], true), 
				Util.validateNumber(parts[6], true),
				Util.validateNumber(parts[7], true));
		model.add(new Line(start, end, color));
	}

	/**
	 * Parses <i>CIRCLE</i> type object.
	 * 
	 * @param parts parts.
	 * @param model model.
	 * @throws Exception if circle object is invalid.
	 */
	private void parseCircle(String[] parts, DrawingModel model) {
		if (parts.length != 7) {
			throw new IllegalArgumentException("Incorrect definition for CIRCLE was provided");
		}
		Point center = new Point(
				Util.validateNumber(parts[1], false),
				Util.validateNumber(parts[2], false)
		);
		int radius = Util.validateNumber(parts[3], false);
		Color outline = new Color(
				Util.validateNumber(parts[4], true), 
				Util.validateNumber(parts[5], true),
				Util.validateNumber(parts[6], true)
		);
		model.add(new Circle(center, radius, outline));
	}

	/**
	 * Parses <i>FCIRCLE</i> type object.
	 * 
	 * @param parts parts.
	 * @param model model.
	 * @throws Exception if fcircle object is invalid.
	 */
	private void parseFCircle(String[] parts, DrawingModel model) {
		if (parts.length != 10) {
			throw new IllegalArgumentException("Incorrect definition for FCIRCLE was provided");
		}
		Point center = new Point(
				Util.validateNumber(parts[1], false), 
				Util.validateNumber(parts[2], false)
		);
		int radius = Util.validateNumber(parts[3], false);
		Color outline = new Color(
				Util.validateNumber(parts[4], true), 
				Util.validateNumber(parts[5], true),
				Util.validateNumber(parts[6], true));
		Color fill = new Color(
				Util.validateNumber(parts[7], true), 
				Util.validateNumber(parts[8], true),
				Util.validateNumber(parts[9], true));
		model.add(new FilledCircle(center, radius, outline, fill));
	}

}
