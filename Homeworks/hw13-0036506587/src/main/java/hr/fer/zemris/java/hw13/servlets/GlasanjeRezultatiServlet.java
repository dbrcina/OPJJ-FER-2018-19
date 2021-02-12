package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw13.FileClass;
import hr.fer.zemris.java.hw13.javabeans.Band;

/**
 * <p>
 * Servlet which validates voting results.<br>
 * It processes results in <i>backend</i> and delegates <i>request</i> to some
 * <i>.jsp</i> file which then renders application view (i.e. <i>HTML</i> page).
 * </p>
 * 
 * <p>
 * On that page there will be a simple table which will represent results sorted
 * by number of votes.<br>
 * Also, results will be displayed in a <i>pie-chart</i>(created by
 * {@link GlasanjeGrafikaServlet}) and there will be a downloadable <i>XLS</i>
 * document filled with results(created by {@link GlasanjeXLSServlet}.<br>
 * Finally, at the bottom ,there will be links of winner bands songs.
 * </p>
 * 
 * <p>
 * Collection of results and winners are put into servlet's context under
 * <i>"results"</i> and <i>"winners"</i> keys.
 * </p>
 * 
 * @see {@link GlasanjeGrafikaServlet} for further documentation on creating a
 *      simple <i>pie-chart</i>.
 * @see {@link GlasanjeXLSServlet} for further documentation on creating a
 *      simple <i>XLS</i> document.
 * 
 * @author dbrcina
 */
@WebServlet("/glasanje-rezultati")
public class GlasanjeRezultatiServlet extends HttpServlet {

	/**
	 * Default serial ID.
	 */
	private static final long serialVersionUID = 1L;
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

		// fill winners collection
		List<Band> winners = new ArrayList<>();
		int maxVotes = (new ArrayList<>(results.values())).get(0);
		bands.values().forEach(b -> {
			if (b.getVotes() == maxVotes) {
				winners.add(b);
			}
		});

		req.setAttribute("results", results);
		req.setAttribute("winners", winners);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
	}
}
