package hr.fer.zemris.java.servleti;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw17.jvdraw.JVDraw;
import hr.fer.zemris.java.hw17.jvdraw.geobject.visitor.GeometricalObjectBBCalculator;
import hr.fer.zemris.java.hw17.jvdraw.model.DrawingModel;

/**
 * Servlet implementation class PictureServlet
 */
@WebServlet("/picture")
public class PictureServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<String> lines = (List<String>) request.getSession().getAttribute("content");
		JVDraw jvDraw = (JVDraw) request.getServletContext().getAttribute("jvDraw");
		DrawingModel model = jvDraw.getModel();
		model.clear();
		try {
			jvDraw.getOpenAction().parseContent(lines, model);
		} catch (Exception e) {
			response.sendError(404, e.getMessage());
			return;
		}
		GeometricalObjectBBCalculator bbcalc = new GeometricalObjectBBCalculator();
		for (int i = 0; i < model.getSize(); i++) {
			model.getObject(i).accept(bbcalc);
		}

		BufferedImage image = jvDraw.getExportAction().createExportImage(model, bbcalc.getBoundingBox());
		ImageIO.write(image, "png", response.getOutputStream());
	}

}
