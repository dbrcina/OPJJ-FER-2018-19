package hr.fer.zemris.java.hw14.models;

import java.io.Serializable;

/**
 * <i>Java-bean</i> model which models concrete poll.<br>
 * 
 * Each poll have its own poll ID, title and message.<br>
 * All of these properties are accesible through <i>getters</i> and
 * <i>setters</i>.
 * 
 * @author dbrcina
 *
 */
public class Poll implements Serializable {

	/**
	 * Serial ID.
	 */
	private static final long serialVersionUID = 4352545571240150432L;
	/**
	 * Poll's ID.
	 */
	private long pollID;
	/**
	 * Poll's title.
	 */
	private String title;
	/**
	 * Poll's message.
	 */
	private String message;

	/**
	 * Default constructor.
	 */
	public Poll() {
	}

	/**
	 * Getter for poll's ID.
	 * 
	 * @return poll's ID.
	 */
	public long getPollID() {
		return pollID;
	}

	/**
	 * Setter for poll's ID.
	 * 
	 * @param pollID poll ID.
	 */
	public void setPollID(long pollID) {
		this.pollID = pollID;
	}

	/**
	 * Getter for poll's title.
	 * 
	 * @return poll's title.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Setter for poll's title.
	 * 
	 * @param title title.
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Getter for poll's message.
	 * 
	 * @return poll's message.
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Setter for poll's message.
	 * 
	 * @param message message.
	 */
	public void setMessage(String message) {
		this.message = message;
	}

}
