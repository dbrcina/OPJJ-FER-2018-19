package hr.fer.zemris.java.hw17.jvdraw.util;

import java.awt.Color;
import java.awt.Point;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.StringJoiner;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileSystemView;

import hr.fer.zemris.java.hw17.jvdraw.JVDraw;
import hr.fer.zemris.java.hw17.jvdraw.geobject.GeometricalObject;
import hr.fer.zemris.java.hw17.jvdraw.model.DrawingModel;

/**
 * Utility class which provides varios methods.
 * 
 * @author dbrcina.
 *
 */
public class Util {

	/**
	 * Instance of {@link JFileChooser}.
	 */
	private static JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

	/**
	 * Getter for {@link JFileChooser}
	 * 
	 * @return
	 */
	public static JFileChooser getChooser() {
		return jfc;
	}

	/**
	 * Calculates distance between two points.
	 * 
	 * @param start start point.
	 * @param end   end point.
	 * @return distance.
	 */
	public static int calculateDistance(Point start, Point end) {
		return (int) Math.sqrt(Math.pow(start.x - end.x, 2) + Math.pow(start.y - end.y, 2));
	}

	/**
	 * Converts <i>color</i> into hexadecimal representation.
	 * 
	 * @param color color.
	 * @return hexadecimal representation.
	 */
	public static String colorToHexidecimal(Color color) {
		return String.format("#%02X%02X%02X", color.getRed(), color.getGreen(), color.getBlue());
	}

	/**
	 * Validates provided <i>s</i> as a regular number or color number as determined
	 * by <i>colorNumber</i>.
	 * 
	 * @param s           string s
	 * @param colorNumber boolean
	 * @return validated number or throws exception if something is invalid.
	 * @throws IllegalArgumentException when something is invalid.
	 */
	public static int validateNumber(String s, boolean colorNumber) throws IllegalArgumentException {
		int number;
		try {
			number = Integer.parseInt(s);
		} catch (Exception e) {
			throw new IllegalArgumentException("Coordinates and color numbers need to integers");
		}
		if (number < 0) {
			throw new IllegalArgumentException("Coordinates and color numbers cannot be negative");
		}
		if (number > 255 && colorNumber) {
			throw new IllegalArgumentException("Color numbers need to be from [0,255].");
		}
		return number;
	}

	/**
	 * Prepares all {@link GeometricalObject} with
	 * {@link GeometricalObject#fileRepresentation()} method.
	 * 
	 * @param model model.
	 * @return string representation of objects splitted with space.
	 */
	public static String prepareObjects(DrawingModel model) {
		StringJoiner sj = new StringJoiner("\n");
		for (int i = 0; i < model.getSize(); i++) {
			sj.add(model.getObject(i).fileRepresentation());
		}
		return sj.toString();
	}

	/**
	 * Reads from file that is provided through {@link #jfc}.
	 * 
	 * @param jvDraw main frame.
	 * @return list of read lines or <code>null</code> if opening a file was
	 *         declined.
	 * @throws IOException if something goes wrong while reading from a file.
	 */
	public static List<String> readFromFile(JVDraw jvDraw) throws IOException {
		jfc.setDialogTitle("Open file");
		if (jfc.showOpenDialog(jvDraw) != JFileChooser.APPROVE_OPTION) {
			return null;
		}
		Path source = jfc.getSelectedFile().toPath();
		jvDraw.setPath(source);
		return Files.readAllLines(source);
	}

	/**
	 * With the help of {@link #jfc}, this method gets the path where some file
	 * needs to be saved.
	 * 
	 * @param jvDraw main frame.
	 * @return instance of {@link Path} or <code>null</code> if saving was declined.
	 */
	public static Path getSavePath(JVDraw jvDraw) {
		jfc.setDialogTitle("Save file");
		if (jfc.showSaveDialog(jvDraw) != JFileChooser.APPROVE_OPTION) {
			JOptionPane.showMessageDialog(
					jvDraw,
					"Nothing was saved",
					"Information",
					JOptionPane.INFORMATION_MESSAGE
			);
			return null;
		}

		Path destination = jfc.getSelectedFile().toPath();

		if (Files.exists(destination)) {
			int answer = JOptionPane.showConfirmDialog(
					jvDraw,
					destination + " already exists.\nDo you want to overwrite it?",
					"Save",
					JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE
			);
			if (answer != JOptionPane.YES_OPTION) {
				JOptionPane.showMessageDialog(
						jvDraw, 
						"Nothing was saved", "Information",
						JOptionPane.INFORMATION_MESSAGE
				);
				return null;
			}
		}

		return destination;
	}
}
