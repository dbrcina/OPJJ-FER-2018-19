package hr.fer.zemris.java.hw11.jnotepadpp.local;

import javax.swing.AbstractAction;
import javax.swing.Action;

/**
 * An abstract localizable action derived from {@link AbstractAction}.
 * 
 * <p>
 * It is used for dynamical change of action name as determined by key language
 * which is given through constructor. Constructor takes two arguments; one
 * mentioned before and second one is an instance of
 * {@link ILocalizationProvider} used for translation.
 * </p>
 * 
 * @author dbrcina
 *
 */
public abstract class LocalizableAction extends AbstractAction {

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
	 * @see {@link LocalizableAction} for explanation.
	 */
	public LocalizableAction(String key, ILocalizationProvider provider) {
		this.key = key;

		String translator = provider.getString(key);
		putValue(Action.NAME, translator);

		provider.addLocalizationListener(() -> {
			String translate = provider.getString(key);
			putValue(Action.NAME, translate);
		});
	}

}
