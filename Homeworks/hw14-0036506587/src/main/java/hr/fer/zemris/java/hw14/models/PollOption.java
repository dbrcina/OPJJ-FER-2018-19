package hr.fer.zemris.java.hw14.models;

/**
 * <i>Java-bean</i> model which models options for each defined poll.<br>
 * 
 * It consists of unique ID, option's title, option's link, concrete poll ID and
 * it stores number of votes for particural poll option.<br>
 * Each of these properties are accessible through <i>getters</i> and
 * <i>setters</i>.
 * 
 * @author dbrcina
 *
 */
public class PollOption {

	/**
	 * Option's ID.
	 */
	private long optionID;
	/**
	 * Option's title.
	 */
	private String optionTitle;
	/**
	 * Option's link.
	 */
	private String optionLink;
	/**
	 * Poll's ID.
	 */
	private long pollID;
	/**
	 * Number of votes for this option.
	 */
	private long votesCount;

	/**
	 * Default constructor.
	 */
	public PollOption() {
	}

	/**
	 * Getter for option's ID.
	 * 
	 * @return option's ID.
	 */
	public long getOptionID() {
		return optionID;
	}

	/**
	 * Setter for option's ID.
	 * 
	 * @param ID option's ID.
	 */
	public void setOptionID(long optionID) {
		this.optionID = optionID;
	}

	/**
	 * Getter for option's title.
	 * 
	 * @return option's title.
	 */
	public String getOptionTitle() {
		return optionTitle;
	}

	/**
	 * Setter for option's title.
	 * 
	 * @param optionTitle option's title.
	 */
	public void setOptionTitle(String optionTitle) {
		this.optionTitle = optionTitle;
	}

	/**
	 * Getter for option's link.
	 * 
	 * @return option's link.
	 */
	public String getOptionLink() {
		return optionLink;
	}

	/**
	 * Setter for option's link.
	 * 
	 * @param optionLink option's link.
	 */
	public void setOptionLink(String optionLink) {
		this.optionLink = optionLink;
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
	 * @param pollID poll's ID.
	 */
	public void setPollID(long pollID) {
		this.pollID = pollID;
	}

	/**
	 * Getter for number of votes.
	 * 
	 * @return number of votes.
	 */
	public long getVotesCount() {
		return votesCount;
	}

	/**
	 * Setter for number of votes.
	 * 
	 * @param votesCount number of votes.
	 */
	public void setVotesCount(long votesCount) {
		this.votesCount = votesCount;
	}

}
