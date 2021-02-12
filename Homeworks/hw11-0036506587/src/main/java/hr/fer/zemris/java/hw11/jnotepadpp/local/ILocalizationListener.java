package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * Functional interface which provides method {@link #localizationChanged()}
 * used when listener is informed about some change.
 * 
 * <p>
 * It represents <i>Observer</i> in <i>Observer design pattern</i>.
 * </p>
 * 
 * @author dbrcina
 *
 */
@FunctionalInterface
public interface ILocalizationListener {

	/**
	 * Informs listener about some change.
	 */
	void localizationChanged();
}
