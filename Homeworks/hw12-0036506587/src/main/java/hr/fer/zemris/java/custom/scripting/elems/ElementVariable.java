package hr.fer.zemris.java.custom.scripting.elems;

/**
 * This class inherits {@link Element} and has a single read-only
 * <code>String</code> property <code>name</code>.
 * 
 * @author dbrcina
 * @version 1.0
 *
 */
public class ElementVariable extends Element {

	/**
	 * Variable's name.
	 */
	private String name;

	/**
	 * A constructor which initializes variable's <code>name</code>.
	 * @param name A variable's {@code name}.
	 */
	public ElementVariable(String name) {
		this.name = name;
	}

	@Override
	public String asText() {
		return name;
	}
	
	/**
	 * Getter for {@link ElementVariable}'s <code>name</code>.
	 * @return Element's {@code name}.
	 */
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return asText();
	}
}
