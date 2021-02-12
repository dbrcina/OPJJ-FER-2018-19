package hr.fer.zemris.java.gui.charts;

import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Demo program that illustrates work of {@link BarChartComponent}. Result is
 * shown through <i>GUI</i>.
 * <p>
 * Program expects only one argument through command line, a path to some txt
 * file.
 * </p>
 * 
 * @author dbrcina
 *
 */
public class BarChartDemo extends JFrame {

	/**
	 * Default serial ID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constant representing rows from file.
	 */
	private static final int NUMBER_OF_ROWS = 6;

	/**
	 * Reference of {@link BarChart}.
	 */
	private BarChart barChart;

	/**
	 * Reference of file.
	 */
	private Path file;

	/**
	 * Constructor used for initialization.
	 * 
	 * @param barChart bar chart.
	 * @param file     file.
	 */
	public BarChartDemo(BarChart barChart, Path file) {
		this.barChart = barChart;
		this.file = file;
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Bar chart");
		setSize(1000, 700);
		initGUI();
	}

	/**
	 * Helper method used for initialization.
	 */
	private void initGUI() {
		add(new JLabel(file.toString(), SwingConstants.CENTER), BorderLayout.PAGE_START);
		add(new BarChartComponent(barChart));
	}

	/**
	 * Main entry of this program.
	 * 
	 * @param args arguments given through command line.
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Program expects only one arguments, path to some file.\nExiting...");
			System.exit(-1);
		}

		try {
			Path currentDir = Paths.get(".").toAbsolutePath().normalize();
			Path file = currentDir.resolve(Paths.get(args[0])).normalize();

			if (!Files.exists(file)) {
				System.out.println("Provided file" + file + " does not exist!");
				System.exit(-1);
			}

			BarChart barChart = parse(readFromFile(file));
			SwingUtilities.invokeLater(() -> new BarChartDemo(barChart, file).setVisible(true));

		} catch (InvalidPathException e) {
			System.out.println("Cannot find path specified!");
			System.exit(-1);
		} catch (IOException e) {
			System.out.println("Error occured while reading from a file!");
			System.exit(-1);
		}
	}

	/**
	 * Method used for reading from a <code>file</code>.
	 * 
	 * @param file file.
	 * @return list of rows.
	 * @throws IOException if something goes wrong while reading from a file.
	 */
	private static List<String> readFromFile(Path file) throws IOException {
		try (BufferedReader br = new BufferedReader(new FileReader(file.toFile()))) {
			int counter = NUMBER_OF_ROWS;
			List<String> lines = new ArrayList<>();
			String line;
			while ((line = br.readLine()) != null && counter > 0) {
				lines.add(line);
				counter--;
			}
			if (counter != 0) {
				System.out.println("Invalid number of rows in file!");
			}
			return lines;
		}
	}

	/*
	 * ------------Utility methods used for parsing text from a file----------------
	 */

	/**
	 * <i>Parser-method</i> used for parsing {@link String} arguments into new
	 * instance of {@link BarChart}.
	 * 
	 * @param lines list of txt lines.
	 * @return new instance of {@link BarChart} as a result.
	 */
	private static BarChart parse(List<String> lines) {
		String xDescription = lines.get(0);
		String yDescription = lines.get(1);
		List<XYValue> xyValues = parseValues(lines.get(2));
		int yMin = 0;
		int yMax = 0;
		int yDifference = 0;
		try {
			yMin = Integer.parseInt(lines.get(3));
			yMax = Integer.parseInt(lines.get(4));
			yDifference = Integer.parseInt(lines.get(5));
		} catch (NumberFormatException e) {
			System.out.println("Cannot parse String into Integer!");
			System.exit(-1);
		}
		return new BarChart(xyValues, xDescription, yDescription, yMin, yMax, yDifference);
	}

	/**
	 * <i>Parser-method</i> used for parsing {@link String} line into new instance
	 * of {@link XYValue}.
	 * 
	 * @param line line.
	 * @return new instance of {@link XYValue} as a result.
	 */
	private static List<XYValue> parseValues(String line) {
		List<XYValue> xyValues = new ArrayList<>();
		String[] parts = line.split("\\s+");
		for (String part : parts) {
			String[] coordinates = part.split(",");
			try {
				xyValues.add(new XYValue(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1])));
			} catch (NumberFormatException e) {
				System.out.println("Cannot parse String coordinate into Integer!");
				System.exit(-1);
			}
		}
		return xyValues;
	}

}
