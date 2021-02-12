package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;

/**
 * <p>
 * Class derived from {@link LocalizationProviderBridge}.
 * </p>
 * 
 * <p>
 * In its constructor it registeres itself as a {@link WindowListener} to its
 * {@link JFrame}. When frame is opened, it calls {@link #connect()} method and
 * when frame is closed, it calls {@link #disconnect()} method.
 * </p>
 * 
 * <p>
 * When frame closes, instance of this class will de-register itself from the
 * decorated localization provider (i.e {@link LocalizationProviderBridge})
 * automatically so that it won't hold any reference to it, and the garbage
 * collector will be able to free frame and all of its resources.
 * </p>
 * 
 * @author dbrcina
 *
 */
public class FormLocalizationProvider extends LocalizationProviderBridge {

	/**
	 * Constructor used for initialization.
	 * 
	 * @param provider provider.
	 * @param frame    frame.
	 */
	public FormLocalizationProvider(ILocalizationProvider provider, JFrame frame) {
		super(provider);

		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				disconnect();
			}

			@Override
			public void windowOpened(WindowEvent e) {
				connect();
			}
		});
	}

}
