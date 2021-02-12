package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw13.FileClass;
import hr.fer.zemris.java.hw13.javabeans.Band;

/**
 * Servlet used for updating number of votes for provided band.
 * 
 * <p>
 * Band ID is provided through <i>URL</i> and it determines which band gets one
 * more vote from some random user.
 * </p>
 * 
 * Results are generated into some results.txt file.<br>
 * If file does not exist, it is created and filled with some default number of
 * votes.
 * 
 * @see FileClass#createResultsFile(String, SortedMap)
 * 
 * @author dbrcina
 */
@WebServlet("/glasanje-glasaj")
public class GlasanjeGlasajServlet extends HttpServlet {

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

		// proceed into saving generated vote...
		String id = req.getParameter("id");
		List<String> results = new ArrayList<>();

		Files.lines(resultsPath, StandardCharsets.UTF_8)
			.forEach(line -> results.add(updateLine(id, line)));
		Files.write(resultsPath, results, StandardCharsets.UTF_8);

		resp.sendRedirect(req.getContextPath() + "/glasanje-rezultati");
	}

	/**
	 * Helper method which updates number of votes for band with provided
	 * <code>ID</code>.<br>
	 * It increments a vote for one.
	 * 
	 * @param ID   band ID.
	 * @param line line in results file.
	 * @return updated line.
	 */
	private String updateLine(String ID, String line) {
		if (!line.startsWith(ID)) {
			return line;
		}
		String[] parts = line.split("\t");
		return ID + "\t" + (Integer.parseInt(parts[1]) + 1);
	}

}
