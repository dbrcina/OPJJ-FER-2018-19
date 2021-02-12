package hr.fer.zemris.java.custom.scripting.elems;

/**
 * This class inherits {@link Element} and has single read-only
 * <code>double</code> property: <code>value</code>.
 * 
 * @author dbrcina
 * @version 1.0
 *
 */
public class ElementConstantDouble extends Element {

	/**
	 * Element's <code>double</code> value.
	 */
	private double value;

	/**
	 * A constructor which initializes element's <code>value</code>.
	 * 
	 * @param name A element's {@code value}.
	 */
	public ElementConstantDouble(double value) {
		super();
		this.value = value;
	}

	@Override
	public String asText() {
		return Double.toString(value);
	}

	/**
	 * Getter for {@link ElementConstantDouble}'s <code>value</code>.
	 * @return Element's {@code double} {@code value}.
	 */
	public double getValue() {
		return value;
	}
	
	@Override
	public String toString() {
		return asText() + " ";
	}
}
