package hr.fer.zemris.java.hw16.model;

import java.util.Arrays;
import java.util.List;

/**
 * <i><b>Javabean</b></i> model of a picture.<br>
 * It consist of three properties:
 * <ol>
 * <li>picture's name</li>
 * <li>picture's description</li>
 * <li>list of picture tags</li>
 * </ol>
 * 
 * Every property is accessible through getters.
 * 
 * @author dbrcina
 *
 */
public class Picture {

	/**
	 * Picture's name.
	 */
	private String name;
	/**
	 * Picture's description.
	 */
	private String description;
	/**
	 * A list of picture tags.
	 */
	private List<String> tags;

	/**
	 * Constructor used for initialization.
	 * 
	 * @param name        picture name.
	 * @param description picture description.
	 * @param tags        an array of picture tags.
	 */
	public Picture(String name, String description, String... tags) {
		this.name = name;
		this.description = description;
		this.tags = Arrays.asList(tags);
	}

	/**
	 * Getter for picture's name.
	 * 
	 * @return picture's name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Getter for picture's description.
	 * 
	 * @return picture's description.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Getter for list of picture tags.
	 * 
	 * @return a list of tags.
	 */
	public List<String> getTags() {
		return tags;
	}

}
