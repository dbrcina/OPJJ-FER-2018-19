package hr.fer.zemris.java.hw14.filters;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.sql.DataSource;

import hr.fer.zemris.java.hw14.constants.AttributesConstants;
import hr.fer.zemris.java.hw14.dao.sql.SQLConnectionProvider;

/**
 * An implementation of {@link Filter} whose job is to secure connection towards
 * database for servlets.<br>
 * 
 * Filters are called right before servlets and right after servlets finish
 * their job, so when some servlet finishes, this filter removes connection from
 * connection pool.
 * 
 * @author dbrcina
 */
@WebFilter("/servleti/*")
public class ConnectionSetterFilter implements Filter {

	@Override
	public void init(FilterConfig fConfig) throws ServletException {
	}

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		DataSource ds = (DataSource) request.getServletContext().getAttribute(AttributesConstants.DSPOOL);
		Connection con = null;

		try {
			con = ds.getConnection();
		} catch (SQLException e) {
			throw new IOException("Database is not available!");
		}
		
		SQLConnectionProvider.setConnection(con);
		
		try {
			chain.doFilter(request, response);
		} finally {
			SQLConnectionProvider.setConnection(null);
			try {
				con.close();
			} catch (SQLException ignorable) {
			}
		}
	}

}
