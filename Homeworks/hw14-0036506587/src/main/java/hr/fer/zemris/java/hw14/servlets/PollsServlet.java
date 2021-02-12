package hr.fer.zemris.java.hw14.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw14.constants.AttributesConstants;
import hr.fer.zemris.java.hw14.dao.DAOProvider;
import hr.fer.zemris.java.hw14.models.Poll;

/**
 * Servlet which provides all polls from database and its rendering forwards to
 * <i>polls.jsp</i> file.
 * 
 * @author dbrcina
 */
@WebServlet("/servleti/index.html")
public class PollsServlet extends HttpServlet {
	
	/**
	 * Default serial ID.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<Poll> polls = DAOProvider.getDAO().getPolls();
		req.setAttribute(AttributesConstants.POLLS, polls);
		req.getRequestDispatcher("/WEB-INF/pages/polls.jsp").forward(req, resp);
	}

}
