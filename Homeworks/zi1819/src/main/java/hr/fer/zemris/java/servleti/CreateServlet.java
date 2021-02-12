package hr.fer.zemris.java.servleti;

import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.JWindow;

import hr.fer.zemris.java.hw17.jvdraw.JVDraw;

/**
 * Servlet implementation class CreateServlet
 */
@WebServlet("/create")
public class CreateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @throws IOException 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String ime = request.getParameter("ime");
		if (ime == null || !ime.contains(".")) {
			response.sendError(404, "Pogrešno ime");
			return;
		}
		if (!ime.endsWith(".jvd")) {
			response.sendError(404, "Pogrešna extenzija");
			return;
		}
		
		String text = request.getParameter("text");
		List<String> lines = Arrays.asList(text.split("\n"));
		
		JVDraw jvDraw = (JVDraw) request.getServletContext().getAttribute("jvDraw");
		try {
			jvDraw.getOpenAction().parseContent(lines, jvDraw.getModel());
		} catch (Exception e) {
			response.sendError(404, e.getMessage());
			return;
		}
		
		String put = request.getServletContext().getRealPath("/WEB-INF/images/"+ime);
		FileWriter wr = new FileWriter(put);
		for (String line : lines) {
			wr.write(line);
		}
		wr.close();
		
		response.sendRedirect(request.getContextPath() + "/main");
	}
}
