package hr.fer.zemris.java.hw15.dao;

import hr.fer.zemris.java.hw15.dao.jpa.JPADAOImpl;

/**
 * <i>Singleton</i> class which provides an implementation of {@link DAO}
 * interface through {@link #getDAO()} method.
 * 
 * @author dbrcina
 *
 */
public class DAOProvider {

	/**
	 * {@link DAO} implementation.
	 */
	private static DAO dao = new JPADAOImpl();
	
	/**
	 * Getter for {@link DAO} implementation.
	 * 
	 * @return instance of {@link DAO}.
	 */
	public static DAO getDAO() {
		return dao;
	}
}
