package hr.fer.zemris.java.hw15.dao;

import java.util.List;

import hr.fer.zemris.java.hw15.model.BlogComment;
import hr.fer.zemris.java.hw15.model.BlogEntry;
import hr.fer.zemris.java.hw15.model.BlogUser;

/**
 * <i>Data Acces Layer</i> interface used for manupilating with database data
 * <i>(i.e. retrieving/updating etc)</i>.
 * 
 * @author dbrcina
 *
 */
public interface DAO {

	/**
	 * Retrieves all blog entries from <i>bloe_entries</i> table as determined by
	 * <code>user</code>.
	 * 
	 * @param user user.
	 * @return collection of blog entries.
	 */
	List<BlogEntry> getBlogEntries(BlogUser user);

	/**
	 * Retrieves <i>blog-entry</i> as determined by <code>id</code>.<br>
	 * If entry doesn't exist, <code>null</code> is returned.
	 * 
	 * @param id entry key.
	 * @return entry or <code>null</code> if entry doesn't exist.
	 * @throws DAOException if error occurs while retrieving from database.
	 */
	BlogEntry getBlogEntry(Long id) throws DAOException;

	/**
	 * Retrieves a collection of users from <i>blog_users</i> database table.
	 * 
	 * @return collection of users.
	 */
	List<BlogUser> getUsers();

	/**
	 * Retrieves <i>blog-user</i> as determined by <code>nick</code>.<br>
	 * If user doesn't exist, <code>null</code> is returned.
	 * 
	 * @param nick nick.
	 * @return blog user or <code>null</code>.
	 */
	BlogUser getUser(String nick);

	/**
	 * Persists provided <code>user</code> to database.
	 * 
	 * @param user user.
	 */
	void persistUser(BlogUser user);

	/**
	 * Persists provided <code>blogEntry</code> to database.
	 * 
	 * @param blogEntry blog entry.
	 */
	void persistBlog(BlogEntry blogEntry);

	/**
	 * Persists provided <code>comment</code> to database.
	 * 
	 * @param comment blog comment.
	 */
	void persistComment(BlogComment comment);
}
