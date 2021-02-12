package hr.fer.zemris.java.hw15.web.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.model.BlogUser;
import hr.fer.zemris.java.hw15.model.form.RegistrationForm;

/**
 * An implementation of user registration servlet.
 * 
 * @author dbrcina
 */
@WebServlet("/servleti/register")
public class RegisterServlet extends HttpServlet {

	/**
	 * Default serial ID.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BlogUser bu = new BlogUser();
		RegistrationForm rf = new RegistrationForm();
		rf.fillFromRecord(bu);

		req.setAttribute("record", rf);
		req.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");

		RegistrationForm rf = new RegistrationForm();
		rf.fillFromHttpRequest(request);
		rf.validate();
		if (rf.hasErrors()) {
			rf.setPasswordHash("");
			request.setAttribute("record", rf);
			request.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(request, response);
			return;
		}

		BlogUser user = DAOProvider.getDAO().getUser(rf.getNick());
		if (user != null) {
			rf.setError("nick", "Željeni nick već postoji u bazi");
			rf.setPasswordHash("");
			request.setAttribute("record", rf);
			request.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(request, response);
			return;
		}
		
		user = new BlogUser();
		rf.fillRecord(user);
		DAOProvider.getDAO().persistUser(user);
		response.sendRedirect(request.getContextPath());
	}

}
