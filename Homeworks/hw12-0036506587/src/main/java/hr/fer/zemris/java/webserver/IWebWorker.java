package hr.fer.zemris.java.webserver;

/**
 * Interface which models web workers. It provides one methode,
 * {@link #processRequest(RequestContext)}
 * 
 * @author dbrcina
 *
 */
public interface IWebWorker {

	/**
	 * Performs operation on given <code>context</code> request.
	 * 
	 * @param context context
	 * @throws Exception
	 */
	void processRequest(RequestContext context) throws Exception;
}
