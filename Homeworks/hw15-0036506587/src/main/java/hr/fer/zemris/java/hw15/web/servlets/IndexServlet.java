package hr.fer.zemris.java.hw15.web.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * An implemenatation of {@link HttpServlet} which redirects to another web page
 * under <i>/servleti/main</i> URL.<br>
 * It represents welcome page.
 * 
 * @author dbrcina
 */
@WebServlet("/index.jsp")
public class IndexServlet extends HttpServlet {

	/**
	 * Default serial ID.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.sendRedirect(req.getContextPath() + "/servleti/main");
	}

}
