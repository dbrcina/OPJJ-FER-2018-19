package hr.fer.zemris.java.hw16.model;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import hr.fer.zemris.java.hw16.listeners.Initialization;

/**
 * A simple model of picture dabase that provides some generic query methods
 * like filtering records by provided tag or name.<br>
 * This database is filled up within {@link Initialization} listener.
 * 
 * @author dbrcina
 *
 */
public class PictureDB {

	/**
	 * List of pictures.
	 */
	private static List<Picture> pictures;

	/**
	 * Setter for list of pictures in database.
	 * 
	 * @param pictures pictures.
	 */
	public static void setPictures(List<Picture> pictures) {
		PictureDB.pictures = pictures;
	}

	/**
	 * Retrieves number of pictures stored in database.
	 * 
	 * @return number of pictures.
	 */
	public int numberOfPictures() {
		return pictures.size();
	}

	/**
	 * Retrieves all picture tags.
	 * 
	 * @return set of picture tags.
	 */
	public static Set<String> getTags() {
		Set<String> tags = new TreeSet<>();
		for (Picture picture : pictures) {
			tags.addAll(picture.getTags());
		}
		return tags;
	}

	/**
	 * Retrieves a list of pictures which contains provided <i>tag</i>.
	 * 
	 * @param tag tag name.
	 * @return list of filtered pictures as determined by <i>tag</i>.
	 */
	public static List<Picture> forTag(String tag) {
		return pictures.stream()
				.filter(p -> p.getTags().contains(tag))
				.collect(Collectors.toList());
	}

	/**
	 * Retrieves a picture from database as determined by provided <i>name</i>.
	 * 
	 * @param name picture name.
	 * @return picture from database.
	 */
	public static Picture forName(String name) {
		return pictures.stream()
				.filter(p -> p.getName().equals(name))
				.findFirst()
				.get();
	}

}
