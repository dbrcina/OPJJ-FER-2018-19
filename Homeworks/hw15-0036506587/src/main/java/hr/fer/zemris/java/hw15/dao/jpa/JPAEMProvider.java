package hr.fer.zemris.java.hw15.dao.jpa;

import javax.persistence.EntityManager;

import hr.fer.zemris.java.hw15.dao.DAOException;

/**
 * Provider for {@link EntityManager} manager.<br>
 * Manager is stored in {@link ThreadLocal} object.
 * 
 * @author dbrcina
 *
 */
public class JPAEMProvider {

	/**
	 * Thread local of entity manager.
	 */
	private static ThreadLocal<EntityManager> locals = new ThreadLocal<>();

	/**
	 * Getter for entity manager.<br>
	 * If instance of {@link EntityManager} doesn't exist, it is craeted.
	 * 
	 * @return entity manager.
	 */
	public static EntityManager getEntityManager() {
		EntityManager em = locals.get();
		if (em == null) {
			em = JPAEMFProvider.getEmf().createEntityManager();
			em.getTransaction().begin();
			locals.set(em);
		}
		return em;
	}

	/**
	 * Closes current entity manager.
	 * 
	 * @throws DAOException if something goes wrong while trying to <i>commit</i>
	 *                      transaction or close entity manager.
	 */
	public static void close() throws DAOException {
		EntityManager em = locals.get();
		if (em == null) {
			return;
		}
		DAOException dex = null;
		try {
			em.getTransaction().commit();
		} catch (Exception e) {
			dex = new DAOException("Unable to commit transaction.", e);
		}
		try {
			em.close();
		} catch (Exception e) {
			dex = new DAOException("Unable to close entity manager.", e);
		}
		locals.remove();
		if (dex != null) {
			throw dex;
		}
	}
}
