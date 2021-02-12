package hr.fer.zemris.java.hw13.javabeans;

import java.io.Serializable;
import java.util.Objects;

/**
 * <i>Java bean</i> model of a band.
 * 
 * <p>
 * Each <i>Band</i> model consists of unique ID, band name and a link to song on
 * youtube.<br>
 * Also, it contains property <i>votes</i>, which represents how many votes does
 * the band have.<br>
 * All of these properties are accessible through getters and setters.
 * </p>
 * 
 * <p>
 * Two bands are "equal" if they have same ID.
 * </p>
 * 
 * @author dbrcina
 *
 */
public class Band implements Serializable {

	/**
	 * Default serial ID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Band's ID.
	 */
	private String ID;
	/**
	 * Band's name.
	 */
	private String name;
	/**
	 * Link to youtube song.
	 */
	private String link;
	/**
	 * Band votes.
	 */
	private int votes;

	/**
	 * Default constructor.
	 */
	public Band() {
	}

	/**
	 * Constructor used for initialization.
	 * 
	 * @param ID   id.
	 * @param name name.
	 * @param link link.
	 */
	public Band(String ID, String name, String link) {
		this.ID = ID;
		this.name = name;
		this.link = link;
	}

	/**
	 * Getter for band's ID.
	 * 
	 * @return id.
	 */
	public String getID() {
		return ID;
	}

	/**
	 * Getter for band's name.
	 * 
	 * @return name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Getter for youtube link.
	 * 
	 * @return link.
	 */
	public String getLink() {
		return link;
	}

	/**
	 * Getter for band votes.
	 * 
	 * @return number of votes.
	 */
	public int getVotes() {
		return votes;
	}

	/**
	 * Setter for band ID.
	 * 
	 * @param ID ID.
	 */
	public void setID(String ID) {
		this.ID = ID;
	}

	/**
	 * Setter for band name.
	 * 
	 * @param name name.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Setter for youtube link.
	 * 
	 * @param link link.
	 */
	public void setYoutubeLink(String link) {
		this.link = link;
	}

	/**
	 * Setter for band votes.
	 * 
	 * @param votes number of votes.
	 */
	public void setVotes(int votes) {
		this.votes = votes;
	}

	@Override
	public int hashCode() {
		return Objects.hash(ID);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Band))
			return false;
		Band other = (Band) obj;
		return Objects.equals(ID, other.ID);
	}

	@Override
	public String toString() {
		return ID + "\t" + name + "\t" + link;
	}
}
