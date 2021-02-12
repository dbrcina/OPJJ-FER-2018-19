package hr.fer.zemris.java.hw16.rest;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.google.gson.Gson;

import hr.fer.zemris.java.hw16.model.Picture;
import hr.fer.zemris.java.hw16.model.PictureDB;

/**
 * Class which formats data as into <i>JSON</i> format and works with
 * {@link Gson} library.
 * 
 * @author dbrcina
 *
 */
@Path("/galerija")
public class PictureJSON {

	/**
	 * Relative path to <i>thumbnails</i> folder used for cacheing shrinked photos.
	 */
	private static final String THUMBNAILS_FOLDER = "/WEB-INF/thumbnails";
	/**
	 * Relative path to <i>slike</i> folder where all pictures are stored.
	 */
	private static final String PICTURES_FOLDER = "/WEB-INF/slike";
	/**
	 * Default thumbnail width.
	 */
	private static final int THUMBNAIL_WIDTH = 150;
	/**
	 * Default thumbnail height.
	 */
	private static final int THUMBNAIL_HEIGHT = 150;

	/**
	 * This method is initially invoked upon opening <i>index.html</i> page under
	 * <i>http://localhost:8080/jsaplikacija/rest/galerija</i> URL.<br>
	 * It retrieves all picture tags from picture database.
	 * 
	 * @return response to web browser with results in <i>JSON</i> format.
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getTagsList() {
		Set<String> tags = PictureDB.getTags();
		Gson gson = new Gson();
		String jsonText = gson.toJson(tags);
		return Response.status(Status.OK).entity(jsonText).build();
	}

	/**
	 * This method is invoked when any of the tags is clicked.<br>
	 * It retrieves all thumbnail pictures as determined by clicked tag. If a
	 * pictures doesn't exist in thumbnails folder, original one is then shrinked
	 * and put into that folder.
	 * 
	 * @param tag     picture tag.
	 * @param context servlet context.
	 * @return response to web browser with results in <i>JSON</i> format.
	 */
	@Path("{tag}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getThumbnailsForTag(@PathParam("tag") String tag, @Context ServletContext context) {
		java.nio.file.Path thumbnailsPath = Paths.get(context.getRealPath(THUMBNAILS_FOLDER));
		java.nio.file.Path picturesPath = Paths.get(context.getRealPath(PICTURES_FOLDER));

		// search for WEB-INF/thumbnails directory
		if (!Files.exists(thumbnailsPath)) {
			try {
				Files.createDirectory(thumbnailsPath);
			} catch (IOException e) {
				System.out.println("Error occured while creating a new directory..");
				System.exit(-1);
			}
		}

		// validate thumbnails, i.e. create it if it doesn't exist
		List<Picture> thumbnails = PictureDB.forTag(tag);
		for (Picture picture : thumbnails) {
			java.nio.file.Path thumbnailPath = thumbnailsPath.resolve(picture.getName());
			java.nio.file.Path realPath = picturesPath.resolve(picture.getName());
			if (!Files.exists(thumbnailPath)) {
				try {
					createThumbnail(thumbnailPath, realPath);
				} catch (IOException e) {
					System.out.println("Error occured while shrinking the picture...");
					System.exit(-1);
				}
			}
		}

		Gson gson = new Gson();
		String jsonText = gson.toJson(thumbnails);
		return Response.status(Status.OK).entity(jsonText).build();

	}

	/**
	 * Helper method used for creating thumbnail from original picture size (under
	 * <i>realPath</i>) to shrinked size (under <i>thumbnailPath</i>).
	 * 
	 * @param thumbnailPath path to shrinked picture.
	 * @param realPath      path to real size picture.
	 * @throws IOException if something goes wrong with streams.
	 */
	private void createThumbnail(java.nio.file.Path thumbnailPath, java.nio.file.Path realPath) throws IOException {
		BufferedImage originalImage = ImageIO.read(Files.newInputStream(realPath));
		BufferedImage resizedImage = new BufferedImage(THUMBNAIL_WIDTH, THUMBNAIL_HEIGHT, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage.getScaledInstance(THUMBNAIL_WIDTH, THUMBNAIL_HEIGHT, Image.SCALE_FAST), 0, 0, null);
		ImageIO.write(resizedImage, "jpg", thumbnailPath.toFile());
	}

	/**
	 * This method is invoked when some thumbnail picture is clicked.<br>
	 * It retrieves original size picture from database alongside picture
	 * description and picture tagsas determined by <i>name</i> parameter from
	 * database.
	 * 
	 * @param name    picture name.
	 * @param context servlet context.
	 * @return response to web browser with results in <i>JSON</i> format.
	 */
	@Path("slika/{name}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getPictureForName(@PathParam("name") String name, @Context ServletContext context) {
		Picture picture = PictureDB.forName(name);
		Gson gson = new Gson();
		String jsonText = gson.toJson(picture);
		return Response.status(Status.OK).entity(jsonText).build();
	}
}
