package hr.fer.zemris.java.hw13.servlets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementing background color changer.
 * 
 * <p>
 * Color name is given as an <i>URL</i> parameter. If color is not specified,
 * <i>white</i> is used as a default.
 * </p>
 * 
 * @author dbrcina
 */
@WebServlet("/setcolor")
public class SetColorServlet extends HttpServlet {

	/**
	 * Default serial ID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Hexdecimal white color.
	 */
	private static final String WHITE = "#FFFFFF";
	/**
	 * Color mappings.
	 */
	private static Map<String, String> colors = new HashMap<>();
	/**
	 * Static initialization block.
	 */
	static {
		colors.put("white", "FFFFFF");
		colors.put("red", "FF0000");
		colors.put("green", "008000");
		colors.put("cyan", "00FFFF");
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String colorName = req.getParameter("color");
		if (colorName == null) {
			colorName = "white";
		}
		String color = colors.get(colorName);
		color = color == null ? WHITE : color;

		req.getSession().setAttribute("pickedBgCol", color);
		resp.getWriter().print(
				"<title>Set Color servlet</title>\n"
			   +"<body bgcolor=\""+color+"\" style=\"font-size: large;\"\n"
			   +"	<p>New background color is set to <i>"+colorName.toUpperCase()+"</i>.</p>\n"
			   +"	<p>You can return to the home page "
			   +"		<a href=\""+req.getContextPath()+"/index.jsp\" title=\"Returns to the home page\">here</a>\n"
			   +"	</p>\n"
			   + "</body>");
	}
}
