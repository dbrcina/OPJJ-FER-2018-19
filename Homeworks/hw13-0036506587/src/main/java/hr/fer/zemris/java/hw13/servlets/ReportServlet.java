package hr.fer.zemris.java.hw13.servlets;

import java.awt.BasicStroke;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

/**
 * Servlet implementing report action on OS usage.
 * 
 * <p>
 * It creates a simple <i>pie-chart</i> which is representing usage of three OS:
 * <li>Windows</li>
 * <li>Linux</li>
 * <li>Mac</li>
 * </p>
 * 
 * @author dbrcina
 */
@WebServlet("/reportImage")
public class ReportServlet extends HttpServlet {

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

		OutputStream outputStream = resp.getOutputStream();

		JFreeChart chart = createChart();
		ChartUtilities.writeChartAsPNG(outputStream, chart, WIDTH, HEIGHT);
	}

	/**
	 * Factory method used to create new chart.
	 * 
	 * @return new instance of {@link JFreeChart}
	 */
	private JFreeChart createChart() {
		DefaultPieDataset dataset = new DefaultPieDataset();
		dataset.setValue("Windows", 60);
		dataset.setValue("Linux", 30);
		dataset.setValue("Mac", 10);

		JFreeChart chart = ChartFactory.createPieChart("OS usage", dataset, true, false, false);
		chart.setBorderStroke(new BasicStroke(5.0f));
		chart.setBorderVisible(true);
		
		return chart;
	}
}
