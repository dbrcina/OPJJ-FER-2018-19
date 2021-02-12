package hr.fer.zemris.java.hw11.jnotepadpp.local;

import javax.swing.JMenu;

/**
 * An implementation of localized {@link JMenu}.
 * 
 * @author dbrcina
 *
 */
public class LJMenu extends JMenu {

	/**
	 * Default serial ID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Language key.
	 */
	String key;

	/**
	 * Constructor used for initialization.
	 * 
	 * @param key      language key.
	 * @param provider provider.
	 */
	public LJMenu(String key, ILocalizationProvider provider) {
		this.key = key;

		String translation = provider.getString(key);
		setText(translation);

		provider.addLocalizationListener(() -> {
			String translate = provider.getString(key);
			setText(translate);
		});
	}
}
