package hr.fer.zemris.java.hw15.dao.jpa;

import javax.persistence.EntityManagerFactory;

/**
 * Provider for {@link EntityManagerFactory} factory.
 * 
 * @author dbrcina
 *
 */
public class JPAEMFProvider {

	/**
	 * An instance of {@link EntityManagerFactory}.
	 */
	public static EntityManagerFactory emf;

	/**
	 * Getter for entity manager factory.
	 * 
	 * @return emf.
	 */
	public static EntityManagerFactory getEmf() {
		return emf;
	}

	/**
	 * Setter for entity manager factory.
	 * 
	 * @param emf emf.
	 */
	public static void setEmf(EntityManagerFactory emf) {
		JPAEMFProvider.emf = emf;
	}
}
