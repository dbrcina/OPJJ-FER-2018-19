package hr.fer.zemris.java.custom.scripting.elems;

/**
 * This class inherits {@link Element} and has single read-only
 * <code>String</code> property: <code>value</code>.
 * 
 * @author dbrcina
 * @version 1.0
 *
 */
public class ElementString extends Element {

	/**
	 * Element's String value.
	 */
	private String value;

	/**
	 * A constructor which initializes element's <code>value</code>.
	 * 
	 * @param name A element's {@code value}.
	 */
	public ElementString(String value) {
		super();
		this.value = value;
	}

	@Override
	public String asText() {
		return value;
	}

	/**
	 * Getter for {@link ElementString}'s <code>value</code>.
	 * 
	 * @return Element's {@code String} {@code value}.
	 */
	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		// replace sign \ with \\
		// replace sign " with \"
		return "\"" + value.replaceAll("\\\\", "\\\\\\\\").replaceAll("\"", "\\\\\"") + "\"";
	}
}
