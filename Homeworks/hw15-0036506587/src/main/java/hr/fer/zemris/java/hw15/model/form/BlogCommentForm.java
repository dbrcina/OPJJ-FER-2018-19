package hr.fer.zemris.java.hw15.model.form;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import hr.fer.zemris.java.hw15.model.BlogComment;

/**
 * Formular form which mimics all properties from {@link BlogComment} class.<br>
 * It is used for validation of parameters given through web formular.
 * 
 * @author dbrcina
 *
 */
public class BlogCommentForm {

	private String message;
	private String email;
	Map<String, String> errors = new HashMap<>();

	public String getError(String name) {
		return errors.get(name);
	}

	public boolean hasErrors() {
		return !errors.isEmpty();
	}

	public boolean hasError(String name) {
		return errors.containsKey(name);
	}

	public void fillFromHttpRequest(HttpServletRequest req) {
		this.message = prepare(req.getParameter("message"));
		this.email = prepare(req.getParameter("email"));
	}

	public void fillFromBlogComment(BlogComment bc) {
		this.email = bc.getUsersEMail();
		this.message = bc.getMessage();
	}

	public void fillBlogComment(BlogComment bc) {
		bc.setMessage(this.message);
		bc.setUsersEMail(email);
	}

	public void validate() {
		errors.clear();

		if (message.isEmpty()) {
			errors.put("message", "Komentar je obavezan");
		}
		if (message.length() > 4096) {
			errors.put("message", "Komentar predugačak");
		}
		if (email.isEmpty()) {
			errors.put("email", "Email je obavezan");
		} else {
			int i = email.indexOf("@");
			int l = email.length();
			if (l < 3 || i == -1 || i == 0 || i == l - 1) {
				errors.put("email", "Email je neispravnog formata");
			}
		}
	}

	private String prepare(String s) {
		if (s == null) {
			return "";
		}
		return s.trim();
	}

	public String getMessage() {
		return message;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Map<String, String> getErrors() {
		return errors;
	}

	public void setErrors(Map<String, String> errors) {
		this.errors = errors;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
