package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * An implementation of {@link IWebWorker} that changes background color.
 * 
 * @author dbrcina
 *
 */
public class BgColorWorker implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) throws Exception {
		String bgcolor = context.getParameter("bgcolor");
		String msg;
		if (validHexColor(bgcolor)) {
			context.setPersistentParameter("bgcolor", bgcolor);
			msg = "Background color has been updated!";
		} else {
			msg = "Background color has not been changed!";
		}
		
		context.write(
					"<!DOCTYPE html>\r\n"
					+ "<html>\r\n"
					+ "	 <head>\r\n"
					+ "		<title>Feedback</title>\r\n"
					+ "	 </head>\r\n"
					+ "	 <body>\r\n"
					+ "		<h5> " + msg + "<h5>\r\n"
					+ "		<h5>You can return to Home page on this link:</h5>\r\n"
					+ "		<a href=\"index2.html\" target=\"_self\">Home page</a>\r\n"
					+ "	 </body>\r\n"
					+ "</html>");
	}

	private boolean validHexColor(String bgcolor) {
		if (bgcolor.length() != 6) {
			return false;
		}
		for (char c : bgcolor.toCharArray()) {
			c = Character.toUpperCase(c);
			if (!Character.isDigit(c) && !(c >= 'A' || c <= 'F')) {
				return false;
			}
		}
		return true;
	}

}
