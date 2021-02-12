package hr.fer.zemris.java.hw14.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw14.constants.AttributesConstants;
import hr.fer.zemris.java.hw14.dao.DAOProvider;
import hr.fer.zemris.java.hw14.models.Poll;
import hr.fer.zemris.java.hw14.models.PollOption;

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
 * Finally, at the bottom, there will be links of poll winners.
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
@WebServlet("/servleti/glasanje-rezultati")
public class GlasanjeRezultatiServlet extends HttpServlet {
	
	/**
	 * Default serial ID.
	 */
	private static final long serialVersionUID = 1L;
       
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	long pollID = ((Poll) req.getSession().getAttribute(AttributesConstants.POLL)).getPollID();
    	
    	List<PollOption> results = DAOProvider.getDAO().getPollOptions(pollID, "pollID");
    	results.sort(Comparator.comparing(PollOption::getVotesCount).reversed());
    	
    	long maxVotes = results.get(0).getVotesCount();
    	List<PollOption> winners = new ArrayList<>();
    	results.forEach(p -> {
    		if (p.getVotesCount() == maxVotes) {
    			winners.add(p);
    		}
    	});
    	
    	req.setAttribute(AttributesConstants.RESULTS, results);
    	req.setAttribute(AttributesConstants.WINNERS, winners);
    	req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
    }

}
