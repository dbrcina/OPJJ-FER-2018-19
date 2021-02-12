package hr.fer.zemris.java.hw14.servlets;

import java.awt.BasicStroke;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import hr.fer.zemris.java.hw14.constants.AttributesConstants;
import hr.fer.zemris.java.hw14.dao.DAOProvider;
import hr.fer.zemris.java.hw14.models.Poll;
import hr.fer.zemris.java.hw14.models.PollOption;

/**
 * Servlet implementating a simple <i>pie-chart</i> creator with the help of
 * {@link JFreeChart} class and its package.<br>
 * 
 * The chart is showing voting results for certain poll.
 * 
 * @author dbrcina
 */
@WebServlet("/servleti/glasanje-grafika")
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

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("image/png");

		long pollID = ((Poll) req.getSession().getAttribute(AttributesConstants.POLL)).getPollID();

		List<PollOption> results = DAOProvider.getDAO().getPollOptions(pollID, "pollID");
		results.sort(Comparator.comparing(PollOption::getVotesCount).reversed());

		// create chart and write it to the stream
		JFreeChart chart = createChart(results);
		ChartUtilities.writeChartAsPNG(resp.getOutputStream(), chart, WIDTH, HEIGHT);
	}

	/**
	 * Factory method used for creating an instance of {@link JFreeChart} from
	 * <code>results</code>.
	 * 
	 * @param results results.
	 * @return new instance of {@link JFreeChart}.
	 */
	private JFreeChart createChart(List<PollOption> results) {
		DefaultPieDataset dataset = new DefaultPieDataset();
		for (PollOption pollOption : results) {
			dataset.setValue(pollOption.getOptionTitle(), pollOption.getVotesCount());
		}

		JFreeChart chart = ChartFactory.createPieChart("Rezultati glasanja", dataset, true, false, false);
		chart.setBorderStroke(new BasicStroke(5.0f));
		chart.setBorderVisible(true);

		return chart;
	}

}
