package hr.fer.zemris.java.hw16.servlets;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * An implementation of <i>web-servlet</i> which is used for retrieving a
 * picture from some folder as determined by URL parameters.<br>
 * Through URL are provided two parameters: picture name(<b>name</b>) and
 * boolean flag(<b>real</b>) which tells whether an image needs to be read in
 * original size or shrinked size.
 * 
 * @author dbrcina
 */
@WebServlet("/image")
public class ImageServlet extends HttpServlet {
	
	/**
	 * Default serial ID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Relative path to <i>thumbnails</i> folder used for cacheing shrinked photos.
	 */
	private static final String THUMBNAILS_FOLDER = "/WEB-INF/thumbnails";
	/**
	 * Relative path to <i>slike</i> folder where all pictures are stored.
	 */
	private static final String PICTURES_FOLDER = "/WEB-INF/slike";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String name = req.getParameter("name").replaceAll("'", ""); // because of JSON
		String real = req.getParameter("real");
		
		String imagePath = req.getServletContext()
				.getRealPath((real.equals("false") ? THUMBNAILS_FOLDER : PICTURES_FOLDER) + "/" + name);
		
		BufferedImage bim = ImageIO.read(Files.newInputStream(Paths.get(imagePath)));
		ImageIO.write(bim, "jpg", resp.getOutputStream());
	}

}
