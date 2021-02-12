package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * <i>Decorator</i> for some other instance of {@link ILocalizationProvider}. It
 * offers two additional methods: {@link #connect()} and {@link #disconnect()},
 * and it manages a connection status (so that you can not connect if you are
 * already connected).
 * 
 * <p>
 * When an instance of this class receives the notification, it will notify all
 * listeners that are registered as its listeners.
 * </p>
 * 
 * <p>
 * When asked to resolve a key, it delegates this request to <i>wrapped</i>
 * (decorated) {@link ILocalizationProvider} object.
 * </p>
 * 
 * @author dbrcina
 *
 */
public class LocalizationProviderBridge extends AbstractLocalizationProvider {

	/**
	 * Boolean flag which tells whether this provider is connected.
	 */
	private boolean connected;

	/**
	 * Instance of {@link ILocalizationProvider}.
	 */

	/**
	 * Instance of {@link ILocalizationListener}.
	 */
	private ILocalizationProvider provider;
	private ILocalizationListener listener = () -> fire();

	/**
	 * Constructor used for initialization.
	 * 
	 * @param provider provider.
	 */
	public LocalizationProviderBridge(ILocalizationProvider provider) {
		this.provider = provider;
	}

	/**
	 * When user calls this method, <i>this</i> object will be deregistered from
	 * decorated object.
	 */
	public void disconnect() {
		if (!connected) {
			return;
		}
		provider.removeLocalizationListener(listener);
		connected = false;
	}

	/**
	 * When user calls this method, it will register an instance of anonimous
	 * {@link ILocalizationListener} on the decorated object.
	 */
	public void connect() {
		if (connected) {
			return;
		}
		provider.addLocalizationListener(listener);
		connected = true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getString(String key) {
		return provider.getString(key);
	}
}
