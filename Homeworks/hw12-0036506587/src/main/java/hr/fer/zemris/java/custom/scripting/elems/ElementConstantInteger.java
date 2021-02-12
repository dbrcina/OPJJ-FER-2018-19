package hr.fer.zemris.java.custom.scripting.elems;

/**
 * This class inherits {@link Element} and has single read-only <code>int</code>
 * property: <code>value</code>.
 * 
 * @author dbrcina
 * @version 1.0
 *
 */
public class ElementConstantInteger extends Element {

	/**
	 * Element's <code>int</code> value.
	 */
	private int value;

	/**
	 * A constructor which initializes element's <code>value</code>.
	 * 
	 * @param name A element's {@code value}.
	 */
	public ElementConstantInteger(int value) {
		this.value = value;
	}

	@Override
	public String asText() {
		return Integer.toString(value);
	}
	
	/**
	 * Getter for {@link ElementConstantInteger}'s <code>value</code>.
	 * @return Element's {@code int} {@code value}.
	 */
	public int getValue() {
		return value;
	}
	
	@Override
	public String toString() {
		return asText() + " ";
	}
}
