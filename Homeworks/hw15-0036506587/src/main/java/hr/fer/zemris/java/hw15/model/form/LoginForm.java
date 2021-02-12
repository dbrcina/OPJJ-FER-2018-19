package hr.fer.zemris.java.hw15.model.form;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import hr.fer.zemris.java.hw15.crypto.Util;

public class LoginForm {

	private String nick;
	private String passwordHash;
	private Map<String, String> errors = new HashMap<>();

	public boolean hasErrors() {
		return !errors.isEmpty();
	}

	public boolean hasError(String name) {
		return errors.containsKey(name);
	}

	public String getError(String name) {
		return errors.get(name);
	}

	public void fillFromHttpRequest(HttpServletRequest req) {
		this.nick = prepare(req.getParameter("nick"));
		this.passwordHash = prepare(req.getParameter("pwd"));
		if (!passwordHash.isEmpty()) {
			passwordHash = Util.encrypt(passwordHash);
		}
	}

	public void validate() {
		errors.clear();
		if (nick.isEmpty()) {
			errors.put("nick", "Nadimak je obavezan");
		}
		if (passwordHash.isEmpty()) {
			errors.put("pwd", "Lozinka je obavezna");
		}
	}

	public void setError(String name, String value) {
		errors.put(name, value);
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public Map<String, String> getErrors() {
		return errors;
	}

	public void setErrors(Map<String, String> errors) {
		this.errors = errors;
	}

	private String prepare(String s) {
		if (s == null) {
			return "";
		}
		return s.trim();
	}

}
