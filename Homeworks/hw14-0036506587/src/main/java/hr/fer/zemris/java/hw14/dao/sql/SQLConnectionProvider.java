package hr.fer.zemris.java.hw14.dao.sql;

import java.sql.Connection;

/**
 * Class which provides storage of connections towards some database.<br>
 * Connections are stored in {@link ThreadLocal} object.
 * 
 * @author dbrcina
 *
 */
public class SQLConnectionProvider {

	/**
	 * Storage of connections.
	 */
	private static ThreadLocal<Connection> connections = new ThreadLocal<>();

	/**
	 * Set connection on <code>con</code> if <code>con</code> is not
	 * <code>null</code>, otherwise remove connection from thread local.
	 * 
	 * @param con connection.
	 */
	public static void setConnection(Connection con) {
		if (con == null) {
			connections.remove();
		} else {
			connections.set(con);
		}
	}

	/**
	 * Getter for connection towards some database.
	 * 
	 * @return connection
	 */
	public static Connection getConnection() {
		return connections.get();
	}
}
