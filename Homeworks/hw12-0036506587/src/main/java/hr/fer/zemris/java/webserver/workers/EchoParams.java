package hr.fer.zemris.java.webserver.workers;

import java.util.Map.Entry;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * An implementation of {@link IWebWorker} interface.<br>
 * It generates <i>HTML table</i> which consists of all client's parameters
 * given through <i>url</i>.
 * 
 * @author dbrcina
 *
 */
public class EchoParams implements IWebWorker {

	@Override
	public void processRequest(RequestContext context) throws Exception {
		context.setMimeType("text/html");
		context.write("<!DOCTYPE html>\n"
				+ "<html>\n"
				+ "<head>\n"
				+ "<style>\n"
				+ "table, th, td {\n"
				+ "	border: 1px solid black;\n"
				+ "	border-collapse: collapse;\n"
				+ "}\n"
				+ "th, td {\n"
				+ "	padding: 5px;\n"
				+ "	text-allign: left;\n"
				+ "}\n"
				+ "</style>\n"
				+ "</head>\n"
				+ "<body>\n"
				+"	<table style =\"width:100%\">\n"
				+"		<tr>\n"
				+ "			<th>Argument name</th>\n"
				+ "			<th>Argument value</th>\n"
				+ "		</tr>\n"
		);
		for (Entry<String, String> entry : context.getParameters().entrySet()) {
			context.write("	<tr>\n"
					+ "		<td>"+entry.getKey()+"</td>\n"
					+ "	 	<td>"+entry.getValue()+"</td>\n"
					+ "	</tr>\n"
			);
		}
		context.write("</table>\n"
				+ "</body>\n"
				+ "</html>");
	}

}
