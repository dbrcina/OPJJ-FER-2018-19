package hr.fer.zemris.java.hw13.listeners;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * An implementation of <i>Life-cycle</i> listener.<br>
 * It keeps track for how long this web application has been running.<br>
 * Also, it removes some atributtes from application and deletes result files if
 * they were generated.
 * 
 * @author dbrcina
 *
 */
@WebListener
public class AppListener implements ServletContextListener {

	/**
	 * Path to results file.
	 */
	private static final String RESULTS_PATH = "/WEB-INF/glasanje-rezultati.txt";

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		ServletContext context = sce.getServletContext();
		context.removeAttribute("life");
		try {
			Files.deleteIfExists(Paths.get(context.getRealPath(RESULTS_PATH)));
		} catch (IOException ignored) {
		}
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		sce.getServletContext().setAttribute("life", System.currentTimeMillis());
	}

}
