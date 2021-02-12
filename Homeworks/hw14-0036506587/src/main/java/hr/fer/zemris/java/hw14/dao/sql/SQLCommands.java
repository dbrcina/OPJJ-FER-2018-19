package hr.fer.zemris.java.hw14.dao.sql;

/**
 * Utility class which provides some generic <i>SQL</i> query commands.
 * 
 * @author dbrcina
 *
 */
public class SQLCommands {

	/**
	 * <i>SQL</i> command for creating <i>Polls</i> table.
	 */
	public static final String CREATE_POLLS_TABLE = 
			"CREATE TABLE Polls ("
			+"	id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,"
			+"	title VARCHAR(150) NOT NULL,"
			+"	message CLOB(2048) NOT NULL"
			+")";
	
	/**
	 * <i>SQL</i> command for creating <i>PollOptions</i> table.
	 */
	public static final String CREATE_POLLOPTIONS_TABLE =
			"CREATE TABLE PollOptions ("
			+"	id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,"
			+"	optionTitle VARCHAR(100) NOT NULL,"
			+"	optionLink VARCHAR(150) NOT NULL,"
			+"	pollID BIGINT,"
			+"	votesCount BIGINT,"
			+"	FOREIGN KEY (pollID) REFERENCES Polls(id)"
			+")";
	
	/**
	 * <i>SQL</i> command for inserting values into <i>Polls</i> table.
	 */
	public static final String INSERT_INTO_POLLS =
			"INSERT INTO Polls (title, message) VALUES (?, ?)";
	
	/**
	 * <i>SQL</i> command for inserting values into <i>PollOptions</i> table.
	 */
	public static final String INSERT_INTO_POLLOPTIONS =
			"INSERT INTO PollOptions (optionTitle, optionLink, pollID, votesCount) VALUES (?, ?, ?, ?)";
	
	/**
	 * <i>SQL</i> command for retrieving content from <i>Polls</i> table.
	 */
	public static final String SELECT_ALL_FROM_POLLS =
			"SELECT * FROM Polls";
	
	/**
	 * <i>SQL</i> command for counting rows in <i>Polls</i> table.
	 */
	public static final String COUNT_ROWS_IN_POLLS_TABLE =
			"SELECT COUNT(*) FROM Polls";
}
