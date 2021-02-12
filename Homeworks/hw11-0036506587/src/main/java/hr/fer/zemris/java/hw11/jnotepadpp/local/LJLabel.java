package hr.fer.zemris.java.hw11.jnotepadpp.local;

import javax.swing.JLabel;

public class LJLabel extends JLabel {

	/**
	 * Default serial ID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Language key
	 */
	String key;

	public LJLabel(String key, ILocalizationProvider provider) {
		this.key = key;

		String translator = provider.getString(key);
		setText(translator + ":");

		provider.addLocalizationListener(() -> {
			String translate = provider.getString(key);
			setText(translate + ":");
		});
	}
}
