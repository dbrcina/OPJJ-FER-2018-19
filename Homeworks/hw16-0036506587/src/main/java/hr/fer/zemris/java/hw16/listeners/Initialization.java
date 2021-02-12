package hr.fer.zemris.java.hw16.listeners;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import hr.fer.zemris.java.hw16.model.Picture;
import hr.fer.zemris.java.hw16.model.PictureDB;

/**
 * An implementation of {@link ServletContextListener} which is used for
 * initialization of this webapp.<br>
 * It prepares the picture database from provided description file.
 * 
 * @author dbrcina
 *
 */
@WebListener
public class Initialization implements ServletContextListener {

	/**
	 * Path where database description is stored.
	 */
	private static final String DESCRIPTION_PATH = "/WEB-INF/opisnik.txt";

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		try {
			List<Picture> pictures = initializePictureDB(sce.getServletContext().getRealPath(DESCRIPTION_PATH));
			PictureDB.setPictures(pictures);
		} catch (IOException e) {
			System.out.println("Error occured while reading from file...");
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Helper method used for initialization of picture database.
	 * 
	 * @param path path to description file.
	 * @throws IOException if something goes wrong while reading from a file.
	 */
	private List<Picture> initializePictureDB(String path) throws IOException {
		List<String> lines = Files.readAllLines(Paths.get(path), StandardCharsets.UTF_8);
		List<Picture> pictures = new ArrayList<>();
		for (int i = 0; i < lines.size(); i += 3) {
			String name = lines.get(i);
			String description = lines.get(i + 1);
			String[] tags = lines.get(i + 2).split(",");
			pictures.add(new Picture(name, description, trim(tags)));
		}
		return pictures;
	}

	/**
	 * Trims spaces around every element of an array.
	 * 
	 * @param array array.
	 * @return trimed array as a result.s
	 */
	private String[] trim(String[] array) {
		for (int i = 0; i < array.length; i++) {
			array[i] = array[i].trim();
		}
		return array;
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}

}
