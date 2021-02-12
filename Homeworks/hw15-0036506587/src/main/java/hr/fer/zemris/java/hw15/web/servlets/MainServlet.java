package hr.fer.zemris.java.hw15.web.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.model.BlogUser;
import hr.fer.zemris.java.hw15.model.form.LoginForm;

/**
 * An implementation of <i>main-page</i> servlet which provides some basic
 * functionality like user registration, login etc.
 * 
 * @author dbrcina
 * 
 */
@WebServlet("/servleti/main")
public class MainServlet extends HttpServlet {
	
	/**
	 * Default serial ID.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<BlogUser> users = DAOProvider.getDAO().getUsers();
		req.setAttribute("users", users);
		req.getRequestDispatcher("/WEB-INF/pages/main.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		
		String method = req.getParameter("method");
		if (method != null && method.equals("logout")) {
			req.getSession().invalidate();
			doGet(req, resp);
			return;
		}
		
		LoginForm lf = new LoginForm();
		lf.fillFromHttpRequest(req);
		lf.validate();
		if (lf.hasErrors()) {
			lf.setPasswordHash("");
			req.setAttribute("record", lf);
			doGet(req, resp);
			return;
		}
		
		String nick = lf.getNick();
		BlogUser user = DAOProvider.getDAO().getUser(nick);

		if (user == null) {
			lf.setError("nick", "Netočan login");
			lf.setPasswordHash("");
			req.setAttribute("record", lf);
//			req.getRequestDispatcher("/WEB-INF/pages/main.jsp").forward(req, resp);
			doGet(req, resp);
			return;
		}

		String passwordHash = lf.getPasswordHash();
		if (!passwordHash.equals(user.getPasswordHash())) {
			lf.setError("pwd", "Netočan login");
			lf.setPasswordHash("");
			req.setAttribute("record", lf);
//			req.getRequestDispatcher("/WEB-INF/pages/main.jsp").forward(req, resp);
			doGet(req, resp);
			return;
		}

		HttpSession session = req.getSession();
		session.setAttribute("current.user.id", user.getId());
		session.setAttribute("current.user.fn", user.getFirstName());
		session.setAttribute("current.user.ln", user.getLastName());
		session.setAttribute("current.user.nick", user.getNick());
		session.setAttribute("current.user.email", user.getEMail());

		doGet(req, resp);
	}
}
