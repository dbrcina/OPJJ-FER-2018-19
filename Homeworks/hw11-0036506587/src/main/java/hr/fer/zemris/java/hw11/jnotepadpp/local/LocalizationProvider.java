package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * <i>Singleton</i> class used for translation. It derives from
 * {@link AbstractLocalizationProvider}. This class uses <i>lazy
 * initialization</i> which means that instance of this class is instantiated
 * only when user calls {@link #getInstance()} method.
 * 
 * <p>
 * <i>Singleton pattern</i> restricts the instantiation of a class to one
 * "single" instance.
 * </p>
 * 
 * @author dbrcina
 *
 */
public class LocalizationProvider extends AbstractLocalizationProvider {

	/**
	 * Constant representing default language.
	 */
	private static final String DEFAULT_LANGUAGE = "en";

	/**
	 * Constant representing bundle path.
	 */
	private static final String BUNDLE_PATH = "hr.fer.zemris.java.hw11.jnotepadpp.local.prijevodi";

	/**
	 * Single instance of this class.
	 */
	private static LocalizationProvider instance;

	/**
	 * Current language.
	 */
	private String language = DEFAULT_LANGUAGE;

	/**
	 * Bundle used for translation resources.
	 */
	private ResourceBundle bundle;

	/**
	 * Private constructor.
	 */
	private LocalizationProvider() {
		setBundle();
	}

	/**
	 * Getter for an instance of this class.
	 * 
	 * @return instance of {@link LocalizationProvider}
	 */
	public static LocalizationProvider getInstance() {
		if (instance == null) {
			instance = new LocalizationProvider();
		}
		return instance;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getString(String key) {
		return bundle.getString(key);
	}

	/**
	 * Getter for language.
	 * 
	 * @return language.
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * Setter for language.
	 * 
	 * @param language language;
	 */
	public void setLanguage(String language) {
		this.language = language;
		setBundle();

		fire();
	}

	private void setBundle() {
		Locale locale = Locale.forLanguageTag(language);
		bundle = ResourceBundle.getBundle(BUNDLE_PATH, locale);
	}
}
