package hr.fer.zemris.java.hw14.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw14.constants.AttributesConstants;
import hr.fer.zemris.java.hw14.dao.DAO;
import hr.fer.zemris.java.hw14.dao.DAOProvider;
import hr.fer.zemris.java.hw14.models.Poll;
import hr.fer.zemris.java.hw14.models.PollOption;

/**
 * Servlet which prepares all poll option data (determined by pollID which ic
 * given through <i>URL</i>) and delegates them to some <i>jsp</i> file which
 * then renders a <i>HTML</i> page.
 * 
 * @author dbrcina
 */
@WebServlet("/servleti/glasanje")
public class GlasanjeServlet extends HttpServlet {
	
	/**
	 * Default serial ID.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		long pollID = Long.valueOf(req.getParameter(AttributesConstants.POLLID));

		DAO dao = DAOProvider.getDAO();
		Poll poll = dao.getPoll(pollID);
		List<PollOption> pollOptions = dao.getPollOptions(pollID, "pollID");

		req.getSession().setAttribute(AttributesConstants.POLL, poll);
		req.setAttribute(AttributesConstants.POLL_OPTIONS, pollOptions);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
	}
}
