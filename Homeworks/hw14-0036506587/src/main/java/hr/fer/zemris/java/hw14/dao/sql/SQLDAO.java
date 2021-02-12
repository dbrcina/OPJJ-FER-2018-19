package hr.fer.zemris.java.hw14.dao.sql;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.ServletContext;
import javax.sql.DataSource;

import hr.fer.zemris.java.hw14.dao.DAO;
import hr.fer.zemris.java.hw14.dao.DAOException;
import hr.fer.zemris.java.hw14.models.Poll;
import hr.fer.zemris.java.hw14.models.PollOption;

/**
 * An implementation of interface {@link DAO} which is used to manipulate with
 * data between database and server.<br>
 * Connection towards database is provided by {@link SQLConnectionProvider}
 * class.
 * 
 * @author dbrcina.
 *
 */
public class SQLDAO implements DAO {

	/**
	 * Definitions path.<br>
	 * In definitions folder is polls definition along with poll options definition.
	 */
	private static final String DEFINITIONS_PATH = "/WEB-INF/definitions/";

	@Override
	public void validateDBTables(ServletContext context, DataSource cpds) throws DAOException {
		try (
			Connection con = cpds.getConnection();
			ResultSet polls = con.getMetaData().getTables(null, null, "POLLS", null);
			ResultSet pollOptions = con.getMetaData().getTables(null, null, "POLLOPTIONS", null);
		) {
			if (!polls.next()) {
				createTable(con, SQLCommands.CREATE_POLLS_TABLE);
			}
			if (!pollOptions.next()) {
				createTable(con, SQLCommands.CREATE_POLLOPTIONS_TABLE);
			}
			if (pollsTableIsEmpty(con)) {
				fillTheTables(context, con);
			}
		} catch (Exception e) {
			throw new DAOException(e.getMessage());
		}
	}

	@Override
	public List<Poll> getPolls() throws DAOException {
		Connection con = SQLConnectionProvider.getConnection();
		try (
			PreparedStatement pst = con.prepareStatement(SQLCommands.SELECT_ALL_FROM_POLLS);
			ResultSet rs = pst.executeQuery();
		) {
			List<Poll> polls = new ArrayList<>();
			while (rs.next()) {
				Poll poll = new Poll();
				poll.setPollID(rs.getLong(1));
				poll.setTitle(rs.getString(2));
				poll.setMessage(rs.getString(3));
				polls.add(poll);
			}
			return polls;
		} catch (SQLException e) {
			throw new DAOException();
		}
		
	}
	
	@Override
	public Poll getPoll(long pollID) throws DAOException {
		Poll poll = new Poll();
		String sql = "SELECT title,message FROM Polls WHERE id = " + pollID;
		Connection con = SQLConnectionProvider.getConnection();
		try (
			PreparedStatement pst = con.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
		) {
			if (rs.next()) {
				poll.setPollID(pollID);
				poll.setTitle(rs.getString(1));
				poll.setMessage(rs.getString(2));
			}
			return poll;
		} catch (SQLException e) {
			throw new DAOException();
		}
	}
	
	@Override
	public List<PollOption> getPollOptions(long ID, String field) throws DAOException {
		List<PollOption> pollOptions = new ArrayList<>();
		String sql = "SELECT * FROM PollOptions WHERE " + field + " = " + ID;
		Connection con = SQLConnectionProvider.getConnection();
		try (
			PreparedStatement pst = con.prepareStatement(sql);
			ResultSet rs = pst.executeQuery();
		) {
			while (rs.next()) {
				PollOption po = new PollOption();
				po.setOptionID(rs.getLong(1));
				po.setOptionTitle(rs.getString(2));
				po.setOptionLink(rs.getString(3));
				po.setPollID(rs.getLong(4));
				po.setVotesCount(rs.getLong(5));
				pollOptions.add(po);
			}
			return pollOptions;
		} catch (SQLException e) {
			throw new DAOException(e.getMessage());
		}
	}
	
	@Override
	public void addVotes(PollOption pollOption, long votes) throws DAOException {
		long updatedVotes = votes + pollOption.getVotesCount();
		String sql = "UPDATE PollOptions SET votesCount = " + updatedVotes + " WHERE id = " + pollOption.getOptionID();
		Connection con = SQLConnectionProvider.getConnection();
		try (
			PreparedStatement pst = con.prepareStatement(sql);
		) {
			pst.executeUpdate();
		} catch (SQLException e) {
			throw new DAOException();
		}
	}
	
	/**
	 * Factory method used for creating a table in database as determined by
	 * <code>createCommand</code>.
	 * 
	 * @param con           connection.
	 * @param createCommand sql create command.
	 * @throws SQLException if error occurs while creating a table.
	 */
	private void createTable(Connection con, String createCommand) throws SQLException {
		try (PreparedStatement pst = con.prepareStatement(createCommand)) {
			pst.executeUpdate();
		}
	}

	/**
	 * Checks whether <i>Polls</i> table is empty.
	 * 
	 * @param con conenction.
	 * @return <code>true</code> if the table is empty, otherwise
	 *         <code>false</code>.
	 * @throws SQLException if error occurse while executing query.
	 */
	private boolean pollsTableIsEmpty(Connection con) throws SQLException {
		try (
			PreparedStatement pst = con.prepareStatement(SQLCommands.COUNT_ROWS_IN_POLLS_TABLE);
			ResultSet rs = pst.executeQuery();
		) {
			rs.next();
			return rs.getInt(1) == 0;
		}
	}
	
	/**
	 * Fills the <i>Polls</i> and <i>PollOptions</i> table with provided definitions
	 * files from {@value #DEFINITIONS_PATH}.<br>
	 * 
	 * <i>votesCount</i> are generated randomly.
	 * 
	 * @param context application context used for retrieving real path.
	 * @param con     connection.
	 * @throws Exception if something goes wrong.
	 */
	private void fillTheTables(ServletContext context,Connection con) throws Exception {
		try (
			PreparedStatement pstPolls = con.prepareStatement(
					SQLCommands.INSERT_INTO_POLLS,
					Statement.RETURN_GENERATED_KEYS
			);
			PreparedStatement pstPollOptions = con.prepareStatement(
					SQLCommands.INSERT_INTO_POLLOPTIONS
			)
		) {
			Random random = new Random();
			
			// read polls configuration
			List<String> pollsFile = Files.readAllLines(
					Paths.get(context.getRealPath(DEFINITIONS_PATH + "polls.txt")),
					StandardCharsets.UTF_8);
			// insert poll definitions into database
			for (String poll : pollsFile) {
				String[] pollParts = poll.split("\\t+");
				pstPolls.setString(1, pollParts[1]);
				pstPolls.setString(2, pollParts[2]);
				pstPolls.executeUpdate();
				
				ResultSet rs = pstPolls.getGeneratedKeys();
				
				// generate pollID key
				if (rs.next()) {
					long pollID = rs.getLong(1);
					
					// read poll options configuration
					List<String> pollOptionsFile = Files.readAllLines(
							Paths.get(context.getRealPath(DEFINITIONS_PATH + pollParts[0])),
							StandardCharsets.UTF_8);
					// insert poll options into database under generated pollID key
					for (String pollOption : pollOptionsFile) {
						String[] pollOptionParts = pollOption.split("\\t+");
						pstPollOptions.setString(1, pollOptionParts[0]);
						pstPollOptions.setString(2, pollOptionParts[1]);
						pstPollOptions.setLong(3, pollID);
						pstPollOptions.setLong(4, random.nextInt(100));
						pstPollOptions.executeUpdate();
					}
				}
			}
		}
	}
	
}
