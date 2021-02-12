package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw13.FileClass;
import hr.fer.zemris.java.hw13.javabeans.Band;

/**
 * Servlet used for registration of bands which are given in some .txt file.
 * 
 * <p>
 * Every line of file consists of band's ID, name and link to youtube and
 * everything is separed with <i>TAB</i>.<br>
 * Once the line is read, a new instance of {@link Band} is put into sorted map
 * of bands which is later forwarded to some rendering .jsp file.
 * </p>
 * 
 * <p>
 * Created collection of bands is put into servlet's context under
 * <i>"bands"</i> key.
 * </p>
 * 
 * @author dbrcina
 */
@WebServlet("/glasanje")
public class GlasanjeServlet extends HttpServlet {

	/**
	 * Default serial ID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Definition file.
	 */
	private static final String DEFINITION_PATH = "/WEB-INF/glasanje-definicija.txt";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String fileName = req.getServletContext().getRealPath(DEFINITION_PATH);

		req.setAttribute("bands", FileClass.loadBands(fileName));
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
	}
}
