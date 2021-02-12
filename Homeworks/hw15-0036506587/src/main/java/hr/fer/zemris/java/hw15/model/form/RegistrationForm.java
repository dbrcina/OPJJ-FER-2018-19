package hr.fer.zemris.java.hw15.model.form;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import hr.fer.zemris.java.hw15.crypto.Util;
import hr.fer.zemris.java.hw15.model.BlogUser;

/**
 * Formular form which mimics all properties from {@link BlogUser} class.<br>
 * It is used for validation of parameters given through web formular.
 * 
 * @author dbrcina
 *
 */
public class RegistrationForm {

	private String firstName;
	private String lastName;
	private String email;
	private String nick;
	private String passwordHash;
	private Map<String, String> errors = new HashMap<>();

	public String getError(String name) {
		return errors.get(name);
	}

	public boolean hasErrors() {
		return !errors.isEmpty();
	}

	public boolean hasError(String name) {
		return errors.containsKey(name);
	}

	public void fillFromHttpRequest(HttpServletRequest request) {
		this.firstName = prepare(request.getParameter("fn"));
		this.lastName = prepare(request.getParameter("ln"));
		this.nick = prepare(request.getParameter("nick"));
		this.email = prepare(request.getParameter("email"));
		this.passwordHash = prepare(request.getParameter("pwd"));
		if (!passwordHash.isEmpty()) {
			passwordHash = Util.encrypt(passwordHash);
		}
	}

	public void fillFromRecord(BlogUser bu) {
		this.firstName = bu.getFirstName();
		this.lastName = bu.getLastName();
		this.nick = bu.getNick();
		this.email = bu.getEMail();
		this.passwordHash = bu.getPasswordHash();
	}

	public void fillRecord(BlogUser bu) {
		bu.setFirstName(firstName);
		bu.setLastName(lastName);
		bu.setNick(nick);
		bu.setEMail(email);
		bu.setPasswordHash(passwordHash);
	}

	private String prepare(String s) {
		if (s == null) {
			return "";
		}
		return s.trim();
	}

	public void validate() {
		errors.clear();

		if (firstName.isEmpty()) {
			errors.put("fn", "Ime je obavezno");
		}
		if (lastName.isEmpty()) {
			errors.put("ln", "Prezime je obavezno");
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
		if (nick.isEmpty()) {
			errors.put("nick", "Nadimak je obavezan");
		}
		if (passwordHash.isEmpty()) {
			errors.put("pwd", "Lozinka je obavezna");
		}
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public void setError(String name, String value) {
		errors.put(name, value);
	}
}
