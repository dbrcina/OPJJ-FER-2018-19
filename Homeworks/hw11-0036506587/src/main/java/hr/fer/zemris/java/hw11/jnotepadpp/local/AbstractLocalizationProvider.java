package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class implementing functional interface
 * {@link ILocalizationProvider}. It has methods that register/de-register
 * {@link ILocalizationListener} and one method which is used to inform other
 * listeners about changes.
 * 
 * @author dbrcina
 *
 */
public abstract class AbstractLocalizationProvider implements ILocalizationProvider {

	/**
	 * List of {@link ILocalizationListener} listeners.
	 */
	private List<ILocalizationListener> listeners;

	/**
	 * Default constructor used to initialize list of listeners.
	 */
	public AbstractLocalizationProvider() {
		listeners = new ArrayList<>();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addLocalizationListener(ILocalizationListener l) {
		listeners.add(l);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeLocalizationListener(ILocalizationListener l) {
		listeners.remove(l);
	}

	/**
	 * Inform method. Used to inform listeners about changes.
	 */
	public void fire() {
		listeners.forEach(l -> l.localizationChanged());
	}
}
