package hr.fer.zemris.java.servleti;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DocServlet
 */
@WebServlet("/doc")
public class DocServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String IMAGES_FOLDER = "/WEB-INF/images";

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String parPath = request.getParameter("path");
		String path = request.getServletContext().getRealPath(IMAGES_FOLDER + "/" + parPath);

		List<String> lines = Files.readAllLines(Paths.get(path));
		int cntLine = 0;
		int cntCircle = 0;
		int cntFCircle = 0;
		int cntFTriangle = 0;

		for (String line : lines) {
			if (line.startsWith("LINE"))
				cntLine++;
			if (line.startsWith("CIRCLE"))
				cntCircle++;
			if (line.startsWith("FCIRCLE"))
				cntFCircle++;
			if (line.startsWith("FTRIANGLE"))
				cntFTriangle++;
		}

		request.setAttribute("ime", parPath);
		request.setAttribute("line", cntLine);
		request.setAttribute("circle", cntCircle);
		request.setAttribute("fcircle", cntFCircle);
		request.setAttribute("ftriangle", cntFTriangle);
		request.getSession().setAttribute("content", lines);
		request.getRequestDispatcher("/WEB-INF/details.jsp").forward(request, response);

	}

}
