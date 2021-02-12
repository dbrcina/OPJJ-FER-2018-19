package hr.fer.zemris.java.hw14.dao;

import java.util.List;

import javax.servlet.ServletContext;
import javax.sql.DataSource;

import hr.fer.zemris.java.hw14.models.Poll;
import hr.fer.zemris.java.hw14.models.PollOption;

/**
 * Interface towards data subsystem for retrieving/updating table values.
 * 
 * @author dbrcina
 *
 */
public interface DAO {

	/**
	 * Checks whether <i>Polls</i> and <i>PollOptions</i> tables exist in
	 * database.<br>
	 * If one of them (or both) doesn't exist, they are created and filled with some
	 * appropriate data (<i>votesCount</i> are randomly generated).<br>
	 * Also, if one of them is empty, it is also filled with some appropriate
	 * data.<br>
	 * Connection is established by {@link DataSource#getConnection()}.
	 * 
	 * @param context application context.
	 * @param cpds    pool connection.
	 * @throws DAOException if something goes wrong while creating/updating tables.
	 */
	void validateDBTables(ServletContext context, DataSource cpds) throws DAOException;

	/**
	 * Retrieves all {@link Poll} polls from database.
	 * 
	 * @return list of polls.
	 * @throws DAOException if something goes wrong while retrieving data.
	 */
	List<Poll> getPolls() throws DAOException;

	/**
	 * Getter for {@link Poll} poll from database as determined by
	 * <code>pollID</code>.<br>
	 * If poll doesn't exist, <code>null</code> is returned.
	 * 
	 * @param pollID poll ID.
	 * @return instance of {@link Poll} or <code>null</code>.
	 * @throws DAOException if something goes wrong while retrieving data.
	 */
	Poll getPoll(long pollID) throws DAOException;

	/**
	 * Getter for {@link PollOption} poll options from database as determined by
	 * <code>ID</code>.<br>
	 * If poll option doesn't exist, <code>null</code> is returned.<br>
	 * Parameter <code>field</code> determines whether poll options need to be
	 * selected by <i>"pollID"</i> or <i>"id"</i>. Every other field input is
	 * invalid.
	 * 
	 * @param ID    ID.
	 * @param field field.
	 * @return list of poll options or <code>null</code>.
	 * @throws DAOException if something goes wrong while retrieving data.
	 */
	List<PollOption> getPollOptions(long ID, String field) throws DAOException;

	/**
	 * Updates <code>pollOption's</code> current number of votes by
	 * <code>votes</code>.
	 * 
	 * @param pollOption poll option.
	 * @param votes      number of votes.
	 * @throws DAOException if something goes wrong while updating data.
	 */
	void addVotes(PollOption pollOption, long votes) throws DAOException;
}
