package hr.fer.zemris.java.hw14.listeners;

import java.beans.PropertyVetoException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

import hr.fer.zemris.java.hw14.constants.AttributesConstants;
import hr.fer.zemris.java.hw14.constants.PropertiesConstants;
import hr.fer.zemris.java.hw14.dao.DAOException;
import hr.fer.zemris.java.hw14.dao.DAOProvider;

/**
 * An implementation of {@link ServletContextListener} listener which is used
 * for initialization of <i>connection-pool</i> and its destroying.
 * 
 * @author dbrcina
 *
 */
@WebListener
public class InitializationListener implements ServletContextListener {

	/**
	 * Driver class path.
	 */
	private static final String DRIVER_CLASS = "org.apache.derby.jdbc.ClientDriver";

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext context = sce.getServletContext();
		Path propertiesPath = Paths.get(context.getRealPath(PropertiesConstants.DBSETTINGS_PATH));
		String connectionURL = null;

		try {
			connectionURL = initializeProperties(propertiesPath);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		
		ComboPooledDataSource cpds = new ComboPooledDataSource();
		try {
			cpds.setDriverClass(DRIVER_CLASS);
		} catch (PropertyVetoException e) {
			throw new RuntimeException("Error occured while initializing data source pool!");
		}

		cpds.setJdbcUrl(connectionURL);
		context.setAttribute(AttributesConstants.DSPOOL, cpds);
		
		try {
			DAOProvider.getDAO().validateDBTables(context, cpds);
		} catch (DAOException e) {
			System.out.println(e.getMessage());
			contextDestroyed(sce);
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ComboPooledDataSource cpds = (ComboPooledDataSource) sce
				.getServletContext()
				.getAttribute(AttributesConstants.DSPOOL);
		if (cpds != null) {
			try {
				DataSources.destroy(cpds);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Helper method used for reading from <i>.properties</i> file and initializing
	 * all the values.<br>
	 * If <code>path</code> doesn't exist or if file doesn't contain all fields from
	 * {@link PropertiesConstants}, an appropriate exception is thrown, otherwise,
	 * all properties are loaded and connection URL is created as a result.
	 * 
	 * @param path path to <i>.properties</i> file.
	 * @return connection URL.
	 * @throws Exception is some error occurs.
	 */
	private String initializeProperties(Path path) throws Exception {
		if (!Files.exists(path)) {
			throw new Exception(path.getFileName() + " doesn't exist!");
		}

		Properties properties = new Properties();
		properties.load(Files.newInputStream(path));

		// db host
		String host = properties.getProperty(PropertiesConstants.HOST);
		validateProperty(host, PropertiesConstants.HOST);
		// db port
		String port = properties.getProperty(PropertiesConstants.PORT);
		validateProperty(port, PropertiesConstants.PORT);
		// db name
		String name = properties.getProperty(PropertiesConstants.NAME);
		validateProperty(name, PropertiesConstants.NAME);
		// db user
		String user = properties.getProperty(PropertiesConstants.USER);
		validateProperty(user, PropertiesConstants.USER);
		// db password
		String password = properties.getProperty(PropertiesConstants.PASSWORD);
		validateProperty(password, PropertiesConstants.PASSWORD);

		return PropertiesConstants.DBPROVIDER + "://" 
			+ host + ":" + port 
			+ "/" + name 
			+ ";user=" + user 
			+ ";password=" + password;
	}

	/**
	 * Helper method used for validation of <code>property</code> under the name
	 * <code>name</code>.<br>
	 * If <code>property</code> is <code>null</code>, {@link NullPointerException}
	 * exception is thrown.
	 * 
	 * @param property property value.
	 * @param name     property name.
	 */
	private void validateProperty(String property, String name) {
		Objects.requireNonNull(property, "\"" + name + "\" property doesn't exist!");
	}
}
