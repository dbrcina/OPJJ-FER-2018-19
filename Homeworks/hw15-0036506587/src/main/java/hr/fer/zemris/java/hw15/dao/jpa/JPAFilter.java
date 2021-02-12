package hr.fer.zemris.java.hw15.dao.jpa;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/**
 * An implemetation of {@link Filter} interface which is called on
 * <i>/servleti/*</i> URL.<br>
 * It ensures a closure of {@link EntityManager} instance through
 * {@link JPAEMProvider#close()} method.
 * 
 * @author dbrcina
 */
@WebFilter("/servleti/*")
public class JPAFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		try {
			chain.doFilter(request, response);
		} finally {
			JPAEMProvider.close();
		}

	}

	@Override
	public void destroy() {
	}

}
