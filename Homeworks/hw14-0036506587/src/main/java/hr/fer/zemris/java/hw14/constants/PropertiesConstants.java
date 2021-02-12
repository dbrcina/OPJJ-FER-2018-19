package hr.fer.zemris.java.hw14.constants;

/**
 * Utility class used for storing constants which represents properties from
 * <i>dbsettings.properties</i> file.
 * 
 * @author dbrcina
 * 
 */
public class PropertiesConstants {

	/**
	 * Constant representing database settings properties file.
	 */
	public static final String DBSETTINGS_PATH = "/WEB-INF/dbsettings.properties";
	/**
	 * <i>"host"</i> property.
	 */
	public static final String HOST = "host";
	/**
	 * <i>"port"</i> property.
	 */
	public static final String PORT = "port";
	/**
	 * <i>"name"</i> property.
	 */
	public static final String NAME = "name";
	/**
	 * <i>"user"</i> property.
	 */
	public static final String USER = "user";
	/**
	 * <i>"password"</i> property.
	 */
	public static final String PASSWORD = "password";
	/**
	 * Database provider.
	 */
	public static final String DBPROVIDER = "jdbc:derby";
}
