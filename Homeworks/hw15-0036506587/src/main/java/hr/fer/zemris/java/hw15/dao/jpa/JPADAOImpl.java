package hr.fer.zemris.java.hw15.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;

import hr.fer.zemris.java.hw15.dao.DAO;
import hr.fer.zemris.java.hw15.dao.DAOException;
import hr.fer.zemris.java.hw15.model.BlogComment;
import hr.fer.zemris.java.hw15.model.BlogEntry;
import hr.fer.zemris.java.hw15.model.BlogUser;

/**
 * An implementation of {@link DAO} interface.
 * 
 * @author dbrcina
 *
 */
public class JPADAOImpl implements DAO {

	@Override
	public List<BlogEntry> getBlogEntries(BlogUser user) {
		EntityManager em = JPAEMProvider.getEntityManager();
		return em.createNamedQuery("GetBlogs.query", BlogEntry.class)
				.setParameter("user", user)
				.getResultList();
	}
	
	@Override
	public BlogEntry getBlogEntry(Long id) throws DAOException {
		BlogEntry blogEntry = JPAEMProvider.getEntityManager().find(BlogEntry.class, id);
		return blogEntry;
	}

	@Override
	public List<BlogUser> getUsers() {
		EntityManager em = JPAEMProvider.getEntityManager();
		List<BlogUser> users = em.createNamedQuery("GetUsers.query", BlogUser.class).getResultList();
		return users;
	}

	@Override
	public BlogUser getUser(String nick) {
		EntityManager em = JPAEMProvider.getEntityManager();
		List<BlogUser> users = em.createNamedQuery("CheckNick.query", BlogUser.class)
				.setParameter("nick", nick)
				.getResultList();
		return users.isEmpty() ? null : users.get(0);
	}
	
	@Override
	public void persistUser(BlogUser user) {
		EntityManager em = JPAEMProvider.getEntityManager();
		em.persist(user);
	}
	
	@Override
	public void persistBlog(BlogEntry blogEntry) {
		EntityManager em = JPAEMProvider.getEntityManager();
		em.persist(blogEntry);
	}
	
	@Override
	public void persistComment(BlogComment comment) {
		EntityManager em = JPAEMProvider.getEntityManager();
		em.persist(comment);
	}
	
}
