package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

public class Home implements IWebWorker {

	private static final String DEFAULT_COLOR = "#7F7F7F";
	@Override
	public void processRequest(RequestContext context) throws Exception {
		String bgcolor = context.getPersistentParameter("bgcolor");
		context.setTemporaryParameter(
				"background", 
				bgcolor == null ? DEFAULT_COLOR : "#" + bgcolor);
		context.getDispatcher().dispatchRequest("/private/pages/home.smscr");
	}

}
