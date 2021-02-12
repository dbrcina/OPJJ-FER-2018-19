package hr.fer.zemris.java.hw14.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw14.constants.AttributesConstants;
import hr.fer.zemris.java.hw14.dao.DAO;
import hr.fer.zemris.java.hw14.dao.DAOProvider;
import hr.fer.zemris.java.hw14.models.PollOption;

/**
 * Servlet used for incrementing poll option's votes by one.<br>
 * Poll option ID is provided as an <i>URL</i> parameter.
 * 
 * @author dbrcina
 */
@WebServlet("/servleti/glasanje-glasaj")
public class GlasanjeGlasajServlet extends HttpServlet {
	
	/**
	 * Default serial ID.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		long optionID = Long.valueOf(req.getParameter(AttributesConstants.OPTIONID));

		DAO dao = DAOProvider.getDAO();
		PollOption pollOption = dao.getPollOptions(optionID, "id").get(0);
		dao.addVotes(pollOption, 1L);

		resp.sendRedirect(req.getContextPath() + "/servleti/glasanje-rezultati");
	}

}
