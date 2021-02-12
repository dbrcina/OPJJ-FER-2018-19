package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * Interface which provides three methods. Two of them are used for
 * registration/de-registration and one is used for translation of provided key.
 * 
 * <p>
 * It represents <i>Subject</i> in <i>Observer design pattern</i> which will
 * notify all registered listeners when a selected language has changed.
 * </p>
 * 
 * @author dbrcina
 *
 */
public interface ILocalizationProvider {

	/**
	 * Register's listener <code>l</code>.
	 * 
	 * @param l listener.
	 */
	void addLocalizationListener(ILocalizationListener l);

	/**
	 * De-register's listener <code>l</code>.
	 * 
	 * @param l listener.
	 */
	void removeLocalizationListener(ILocalizationListener l);

	/**
	 * It takes a <code>key</code> and gives back the localization.
	 * 
	 * @param key key.
	 * @return localization determined by <code>key</code>.
	 */
	String getString(String key);
}
