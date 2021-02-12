package hr.fer.zemris.java.webserver;

/**
 * Interface which provides one method; {@link #dispatchRequest(String)}. It is
 * used to model clients.
 * 
 * @author dbrcina
 *
 */
public interface IDispatcher {

	/**
	 * Recieves some request and produces result to client.
	 * 
	 * @param urlPath path.
	 * @throws Exception
	 */
	void dispatchRequest(String urlPath) throws Exception;
}
