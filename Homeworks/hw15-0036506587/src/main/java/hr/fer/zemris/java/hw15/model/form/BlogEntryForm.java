package hr.fer.zemris.java.hw15.model.form;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import hr.fer.zemris.java.hw15.model.BlogEntry;

public class BlogEntryForm {

	private String title;
	private String text;
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

	public void setError(String name, String value) {
		errors.put(name, value);
	}

	public void fillFromHttpRequest(HttpServletRequest req) {
		this.title = prepare(req.getParameter("title"));
		this.text = prepare(req.getParameter("text"));
	}

	public void fillFromRecord(BlogEntry blogEntry) {
		this.text = blogEntry.getText();
		this.title = blogEntry.getTitle();
	}

	public void fillRecord(BlogEntry blogEntry) {
		blogEntry.setText(text);
		blogEntry.setTitle(title);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Map<String, String> getErrors() {
		return errors;
	}

	public void setErrors(Map<String, String> errors) {
		this.errors = errors;
	}

	public void validate() {
		errors.clear();
		if (title.isEmpty()) {
			errors.put("title", "Naslov je obavezan");
		}
		if (title.length() > 200) {
			errors.put("title", "Naslov je predugačak. Podržava maksimalno 200 znakova");
		}
		if (text.isEmpty()) {
			errors.put("text", "Blog ne može biti prazan");
		}
		if (text.length() > 4096) {
			errors.put("text", "Količina teksta je prevelika. Očekuje se maksimalno 4KB");
		}
	}

	private String prepare(String s) {
		if (s == null) {
			return "";
		}
		return s.trim();
	}
}
