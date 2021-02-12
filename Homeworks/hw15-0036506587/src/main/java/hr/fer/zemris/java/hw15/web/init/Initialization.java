package hr.fer.zemris.java.hw15.web.init;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import hr.fer.zemris.java.hw15.dao.jpa.JPAEMFProvider;

/**
 * An implementation of {@link ServletContextListener} which is used for
 * initialization of this <i>webapp</i>.<br>
 * It creates appropriate {@link EntityManagerFactory} on its initialization and
 * saves it through {@link JPAEMFProvider#setEmf(EntityManagerFactory)}
 * method.<br>
 * On apps closing, this listener closes {@link EntityManagerFactory} which was
 * previously stored in {@link ServletContext}.
 * 
 * @author dbrcina
 *
 */
@WebListener
public class Initialization implements ServletContextListener {

	private static final String EMF_ATT_NAME = "my.application.emf";

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("baza.podataka.za.blog");
		sce.getServletContext().setAttribute(EMF_ATT_NAME, emf);
		JPAEMFProvider.setEmf(emf);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		JPAEMFProvider.setEmf(null);
		EntityManagerFactory emf = (EntityManagerFactory) sce.getServletContext().getAttribute(EMF_ATT_NAME);
		if (emf != null) {
			emf.close();
		}
	}

}
