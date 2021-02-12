package hr.fer.zemris.java.hw15.web.servlets;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw15.dao.DAO;
import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.model.BlogComment;
import hr.fer.zemris.java.hw15.model.BlogEntry;
import hr.fer.zemris.java.hw15.model.BlogUser;
import hr.fer.zemris.java.hw15.model.form.BlogCommentForm;
import hr.fer.zemris.java.hw15.model.form.BlogEntryForm;

/**
 * <p>
 * An implementation of <i><b>web-servlet</b></i> which is used somehow like
 * <i>"filter"</i> for user request with URL pattern "/servleti/author/*" where
 * <b>'*'</b> can lead to author's nick and some extra content which is
 * generated through web.<br>
 * Here are some examples of valid URL-s:
 * 
 * <ol>
 * <li>(webapp's context path)/servleti/author/nick</li>
 * <li>(webapp's context path)/servleti/author/nick/blogId</li>
 * <li>(webapp's context path)/servleti/author/nick/new</li>
 * <li>(webapp's context path)/servleti/author/nick/edit(optional
 * parameters)</li>
 * </ol>
 * 
 * <i><b>nick</b></i> is obviously author's nick, <i><b>blogID</b></i> id of
 * some author's blog, <i><b>new/edit</b></i> pages for creating/editing some
 * blog etc.
 * </p>
 * 
 * <p>
 * Every URL is checked whether provided information could be find in database.
 * If not, an appropriate error is sent to user.<br>
 * Also, URL-s which containts <i>new/edit</i> cannot be called by anyone but
 * the author that have the authorization for it, otherwise an appropriate error
 * is sent to user.
 * </p>
 * 
 * <p>
 * Servlet manipulates with <i><b>blogBaza</b></i> database through {@link DAO}
 * interface i.e. preparing some new database data or editing existing data etc.
 * </p>
 * 
 * @author dbrcina
 * 
 */
@WebServlet("/servleti/author/*")
public class AuthorServlet extends HttpServlet {

	/**
	 * Default serial ID.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		String pathInfo = req.getPathInfo().substring(1);

		// validate provided URL
		if (pathInfo == null) {
			resp.sendError(404, "Pogrešan poziv. Ništa nije direknto mapirano na \"/servleti/author\"");
			return;
		}

		String[] parts = pathInfo.split("/");
		if (parts.length > 3) {
			resp.sendError(414, "Zadan je pogrešan URL. Očekuje se URL tipa \"/servleti/author/nick/*\"");
			return;
		}

		String nick = parts[0];
		BlogUser author = DAOProvider.getDAO().getUser(nick);
		if (author == null) {
			resp.sendError(412, "Autor pod nadimkom \"" + nick + "\" ne postoji");
			return;
		}

		if (parts.length == 1) {
			List<BlogEntry> blogs = DAOProvider.getDAO().getBlogEntries(author);
			req.setAttribute("author", author);
			req.setAttribute("blogs", blogs);
			req.getRequestDispatcher("/WEB-INF/pages/author.jsp").forward(req, resp);
			return;
		}

		// check for additional URL pattern
		if (parts[1].equals("edit")) {
			editBlogGet(req, resp, author);
		} else if (parts[1].equals("new")) {
			newBlogGet(req, resp, author);
		} else if (parts[1].matches("\\d+")) {
			showBlogGet(req, resp, author, Long.valueOf(parts[1]));
		} else {
			resp.sendError(404, "Nepoznat URL");
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");

		String pathInfo = req.getPathInfo().substring(1);
		String[] parts = pathInfo.split("/");
		BlogUser author = DAOProvider.getDAO().getUser(parts[0]);

		if (parts[1].equals("edit")) {
			editBlogPost(req, resp, author);
		} else if (parts[1].equals("new")) {
			newBlogPost(req, resp, author);
		} else if (parts[1].matches("\\d+")) {
			showBlogPost(req, resp, author, Long.valueOf(parts[1]));
		}
	}

	/**
	 * Helper method which is used to perform <i><b>edit</b></i> method through
	 * <i>GET</i> call.
	 * 
	 * <p>
	 * Firstly, it checks the authorization. If authorization is invalid, an
	 * appropriate error message is sent to user, otherwise a {@link BlogEntryForm}
	 * is prepared and it is filled with existing content table
	 * <i>blog_entries</i>.<br>
	 * An id of certain blog entry is provided in <i>jsp</i> file under key
	 * <i>"id"</i>.
	 * </p>
	 * 
	 * @param req    request.
	 * @param resp   response
	 * @param author author.
	 * @throws IOException      if error occurs with streams.
	 * @throws ServletException if error occurs while trying to forward request and
	 *                          response to some <i>.jsp</i> file.
	 */
	private void editBlogGet(HttpServletRequest req, HttpServletResponse resp, BlogUser author)
			throws IOException, ServletException {

		String currentNick = (String) req.getSession().getAttribute("current.user.nick");
		if (currentNick == null || !currentNick.equals(author.getNick())) {
			resp.sendError(404, "Nemate ovlasti");
			return;
		}

		BlogEntryForm bf = new BlogEntryForm();
		bf.fillFromRecord(DAOProvider.getDAO().getBlogEntry(Long.valueOf(req.getParameter("id"))));
		req.setAttribute("form", bf);
		req.setAttribute("action",
				req.getContextPath() + "/servleti/author/" + author.getNick() + "/edit?id=" + req.getParameter("id"));
		req.getRequestDispatcher("/WEB-INF/pages/form.jsp").forward(req, resp);
	}

	/**
	 * Helper method which is used to perform <i><b>edit</b></i> method through
	 * <i>POST</i> call.
	 * 
	 * <p>
	 * An instance of {@link BlogEntryForm} is filled with parameters from http
	 * request and validated. If validation failed, the same formular page is
	 * rendered with an appropriate error message for user to fix, otherwise blog
	 * entry is updated through {@link DAO#persistBlog(BlogEntry)} method.
	 * </p>
	 * 
	 * @param req    request.
	 * @param resp   response.
	 * @param author author.
	 * @throws ServletException if error occurs while forwarding request and
	 *                          response to some <i>.jsp</i> file.
	 * @throws IOException      if error occurs with streams.
	 */
	private void editBlogPost(HttpServletRequest req, HttpServletResponse resp, BlogUser author)
			throws ServletException, IOException {

		BlogEntryForm bf = new BlogEntryForm();
		bf.fillFromHttpRequest(req);
		bf.validate();
		if (bf.hasErrors()) {
			req.setAttribute("form", bf);
			req.getRequestDispatcher("/WEB-INF/pages/form.jsp").forward(req, resp);
			return;
		}

		Long id = Long.valueOf(req.getParameter("id"));
		BlogEntry entry = DAOProvider.getDAO().getBlogEntry(id);
		bf.fillRecord(entry);
		entry.setLastModifiedAt(new Date());
		DAOProvider.getDAO().persistBlog(entry);

		resp.sendRedirect(req.getContextPath() + "/servleti/author/" + author.getNick() + "/" + id);
	}

	/**
	 * Helper method which is used to perform <i><b>new</b></i> method through
	 * <i>GET</i> call.
	 * 
	 * <p>
	 * Firstly, it checks the authorization. If authorization is invalid, an
	 * appropriate error message is sent to user, otherwise an empty instance of
	 * {@link BlogEntryForm} is prepared and forwared to some <i>.jsp</i> file.
	 * </p>
	 * 
	 * @param req    request.
	 * @param resp   response.
	 * @param author author.
	 * @throws IOException      if error occurs with streams.
	 * @throws ServletException if error occurs while forwarding request and
	 *                          response to some <i>.jsp</i> file.
	 */
	private void newBlogGet(HttpServletRequest req, HttpServletResponse resp, BlogUser author)
			throws IOException, ServletException {

		String currentNick = (String) req.getSession().getAttribute("current.user.nick");
		if (currentNick == null || !currentNick.equals(author.getNick())) {
			resp.sendError(404, "Nemate ovlasti");
			return;
		}

		BlogEntry entry = new BlogEntry();
		BlogEntryForm bf = new BlogEntryForm();
		bf.fillFromRecord(entry);

		req.setAttribute("form", bf);
		req.setAttribute("action", req.getContextPath() + "/servleti/author/" + author.getNick() + "/new");
		req.getRequestDispatcher("/WEB-INF/pages/form.jsp").forward(req, resp);
	}

	/**
	 * Helper method which is used to perform <i><b>new</b></i> method through
	 * <i>POST</i> call.
	 * 
	 * <p>
	 * An instance of {@link BlogEntryForm} is filled with parameters from http
	 * request and validated. If validation failed, the same formular page is
	 * rendered with an appropriate error message for user to fix, otherwise a new
	 * blog entry is created through {@link DAO#persistBlog(BlogEntry)} method.
	 * </p>
	 * 
	 * @param req    request.
	 * @param resp   response.
	 * @param author author.
	 * @throws ServletException if error occurs while forwarding requst and response
	 *                          to some <i>.jsp</i> file.
	 * @throws IOException      if error occurs with streams.
	 */
	private void newBlogPost(HttpServletRequest req, HttpServletResponse resp, BlogUser author)
			throws ServletException, IOException {

		BlogEntryForm bf = new BlogEntryForm();
		bf.fillFromHttpRequest(req);
		bf.validate();
		if (bf.hasErrors()) {
			req.setAttribute("form", bf);
			req.getRequestDispatcher("/WEB-INF/pages/form.jsp").forward(req, resp);
			return;
		}

		BlogEntry entry = new BlogEntry();
		bf.fillRecord(entry);
		entry.setCreatedAt(new Date());
		entry.setCreator(author);
		DAOProvider.getDAO().persistBlog(entry);

		resp.sendRedirect(req.getContextPath() + "/servleti/author/" + author.getNick());
	}

	/**
	 * Helper method which prepares information of some blog by provided <i>id</i>
	 * through URL and <i>GET</i> call.
	 * 
	 * <p>
	 * Firstly, a blog entry is retrieved through {@link DAO#getBlogEntry(Long)}
	 * method and check whether it exist. If not, an appropriate error message is
	 * sent to user, otherwise an instance of {@link BlogCommentForm} is prepared
	 * for some <i>.jsp</i> file alongside with author and blog entry information.
	 * </p>
	 * 
	 * @param req    request.
	 * @param resp   response.
	 * @param author author.
	 * @param id     blog entry id.
	 * @throws IOException      if error occurs with streams.
	 * @throws ServletException if error occurst while forwarding requst and
	 *                          response to some <i>.jsp</i> file.
	 */
	private void showBlogGet(HttpServletRequest req, HttpServletResponse resp, BlogUser author, Long id)
			throws IOException, ServletException {

		BlogEntry entry = DAOProvider.getDAO().getBlogEntry(id);
		if (entry == null) {
			resp.sendError(404, "Ne postoji blog entry pod id-em " + id);
			return;
		}
		List<BlogComment> comments = entry.getComments();
		BlogCommentForm bf = new BlogCommentForm();
		BlogComment comment = new BlogComment();
		bf.fillFromBlogComment(comment);

		req.setAttribute("comments", comments);
		req.setAttribute("author", author);
		req.setAttribute("entry", entry);
		req.setAttribute("form", bf);
		req.getRequestDispatcher("/WEB-INF/pages/entry.jsp").forward(req, resp);
	}

	/**
	 * Helper method which prepares information of some blog by provided <i>id</i>
	 * through URL and <i>POST</i> call.
	 * 
	 * <p>
	 * An instance of {@link BlogCommentForm} is filled with parameters from http
	 * request and validated. If validation failed, the same formular page is
	 * rendered with an appropriate error message for user to fix, otherwise a new
	 * blog comment is created through {@link DAO#persistComment(BlogComment)}
	 * method.
	 * </p>
	 * 
	 * @param req    request.
	 * @param resp   response.
	 * @param author author.
	 * @param id     blog entry id.
	 * @throws IOException      if error occurs with streams.
	 * @throws ServletException if error occurst while forwarding requst and
	 *                          response to some <i>.jsp</i> file.
	 */
	private void showBlogPost(HttpServletRequest req, HttpServletResponse resp, BlogUser author, Long id)
			throws ServletException, IOException {

		BlogEntry entry = DAOProvider.getDAO().getBlogEntry(id);
		BlogCommentForm bf = new BlogCommentForm();
		bf.fillFromHttpRequest(req);
		bf.validate();
		if (bf.hasErrors()) {
			req.setAttribute("form", bf);
			req.setAttribute("comments", entry.getComments());
			req.setAttribute("author", author);
			req.setAttribute("entry", entry);
			req.getRequestDispatcher("/WEB-INF/pages/entry.jsp").forward(req, resp);
			return;
		}

		BlogComment comment = new BlogComment();
		bf.fillBlogComment(comment);
		comment.setPostedOn(new Date());
		comment.setBlogEntry(entry);
		DAOProvider.getDAO().persistComment(comment);

		resp.sendRedirect(req.getContextPath() + "/servleti/author/" + author.getNick() + "/" + id);
	}

}
