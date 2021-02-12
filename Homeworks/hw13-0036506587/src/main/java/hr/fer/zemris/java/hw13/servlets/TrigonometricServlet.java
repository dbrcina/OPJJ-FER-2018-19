package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw13.javabeans.TrigonometricResults;

/**
 * Servlet implementing simple trigonometric functions, <i>sinus</i> and
 * <i>cosinus</i>.<br>
 * 
 * <p>
 * It gets two arguments(angles in <b>degrees</b>) through <i>URL</i>: initial angle
 * and final angle. If one of the arguments(or both) is not provided, default
 * values are used; for initial angle is used {@link #DEFAULT_A} and for final
 * angle {@link #DEFAULT_B}
 * </p>
 * 
 * <p>
 * If initial angle is greater than final, their values are swaped. Also, if
 * final value is greater than initial angle + {@link #DEFAULT_INCREMENT}, it's
 * value is set to initial angle + {@link #DEFAULT_INCREMENT}.
 * </p>
 * 
 * <p>
 * Once tha data are calculated, it forwards rendering of a resulting page to
 * JSP file.
 * </p>
 * 
 * @author dbrcina
 */
@WebServlet("/trigonometric")
public class TrigonometricServlet extends HttpServlet {

	/**
	 * Default serial ID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Default value for parameter <i>a</i>.
	 */
	private static final Integer DEFAULT_A = 0;
	/**
	 * Default value for parameter <i>b</i>.
	 */
	private static final Integer DEFAULT_B = 360;
	/**
	 * Default value for incrementing parameters.
	 */
	private static final Integer DEFAULT_INCREMENT = 720;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String parA = req.getParameter("a");
		Integer a = parA == null ? DEFAULT_A : Integer.parseInt(parA);
		String parB = req.getParameter("b");
		Integer b = parB == null ? DEFAULT_B : Integer.parseInt(parB);

		// validate parameters.....
		if (a > b) {
			int temp = a;
			a = b;
			b = temp;

		} else if (b > a + DEFAULT_INCREMENT) {
			b = a + DEFAULT_INCREMENT;
		}
		
		List<TrigonometricResults> results = new ArrayList<>();
		for(int i = a; i <= b; i++) {
			results.add(new TrigonometricResults(i));
		}
		
		req.setAttribute("results", results);
		req.getRequestDispatcher("/WEB-INF/pages/trigonometric.jsp").forward(req, resp);
	}
	
}
