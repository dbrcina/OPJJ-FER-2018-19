package hr.fer.zemris.java.hw13.servlets;

import java.awt.BasicStroke;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.SortedMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import hr.fer.zemris.java.hw13.FileClass;
import hr.fer.zemris.java.hw13.javabeans.Band;

/**
 * Servlet implementating a simple <i>pie-chart</i> creator with the help of
 * {@link JFreeChart} class and its package.
 * 
 * @author dbrcina
 */
@WebServlet("/glasanje-grafika")
public class GlasanjeGrafikaServlet extends HttpServlet {

	/**
	 * Default serial ID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Chart's width.
	 */
	private static final int WIDTH = 500;
	/**
	 * Chart's height.
	 */
	private static final int HEIGHT = 350;
	/**
	 * Definition file.
	 */
	private static final String DEFINITION_PATH = "/WEB-INF/glasanje-definicija.txt";
	/**
	 * Results file.
	 */
	private static final String RESULTS_PATH = "/WEB-INF/glasanje-rezultati.txt";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("image/png");

		// results path
		String resultsName = req.getServletContext().getRealPath(RESULTS_PATH);
		Path resultsPath = Paths.get(resultsName);
		// load bands
		SortedMap<String, Band> bands = FileClass.loadBands(req.getServletContext().getRealPath(DEFINITION_PATH));

		// create results file if it does not exist
		if (!Files.exists(resultsPath)) {
			FileClass.createResultsFile(resultsName, bands);
		}

		// read all lines and sort them by number of votes
		Map<String, Integer> results = FileClass.validateResults(resultsPath, bands);

		// create chart and write it to the stream
		JFreeChart chart = createChart(results);
		ChartUtilities.writeChartAsPNG(resp.getOutputStream(), chart, WIDTH, HEIGHT);
	}

	/**
	 * Factory method used for creating an instance of {@link JFreeChart} from
	 * <code>results</code>..
	 * 
	 * @param results results.
	 * @return new instance of {@link JFreeChart}.
	 */
	private JFreeChart createChart(Map<String, Integer> results) {
		DefaultPieDataset dataset = new DefaultPieDataset();
		for (Map.Entry<String, Integer> result : results.entrySet()) {
			dataset.setValue(result.getKey(), result.getValue());
		}

		JFreeChart chart = ChartFactory.createPieChart("Voting results", dataset, true, false, false);
		chart.setBorderStroke(new BasicStroke(5.0f));
		chart.setBorderVisible(true);

		return chart;
	}

}
