package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.Images;
import hr.fer.zemris.java.webserver.RequestContext;

public class SumWorker implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) throws Exception {
		int a = 1;
		int b = 2;

		String aText = context.getParameter("a");
		String bText = context.getParameter("b");

		if (aText != null) {
			try {
				a = Integer.parseInt(aText);
				context.setTemporaryParameter("a", Integer.toString(a));
			} catch (NumberFormatException ignored) {
			}
		}
		
		if (bText != null) {
			try {
				b = Integer.parseInt(bText);
				context.setTemporaryParameter("b", Integer.toString(b));
			} catch (NumberFormatException ignored) {
			}
		}
		
		int sum = a + b;
		context.setTemporaryParameter("zbroj", Integer.toString(sum));
		context.setTemporaryParameter("imgName", sum % 2 == 0 ? Images.APPLE : Images.LEMON);
		context.getDispatcher().dispatchRequest("/private/pages/calc.smscr");
	}

}
