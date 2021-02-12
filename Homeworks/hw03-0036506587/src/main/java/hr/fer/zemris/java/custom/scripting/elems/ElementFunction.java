package hr.fer.zemris.java.custom.scripting.elems;

/**
 * This class inherits {@link Element} and has single read-only
 * <code>String</code> property: <code>name</code>.
 * 
 * @author dbrcina
 * @version 1.0
 *
 */
public class ElementFunction extends Element {

	/**
	 * Function's name.
	 */
	private String name;

	/**
	 * A constructor which initializes function's <code>name</code>.
	 * 
	 * @param name A function's {@code name}.
	 */
	public ElementFunction(String name) {
		super();
		this.name = name;
	}

	@Override
	public String asText() {
		return name;
	}
	
	/**
	 * Getter for {@link ElementFunction}'s <code>name</code>.
	 * @return Element's {@code name}.
	 */
	public String getName() {
		return name;
	}
	
	@Override
	public String toString() {
		return "@" + name;
	}
}
