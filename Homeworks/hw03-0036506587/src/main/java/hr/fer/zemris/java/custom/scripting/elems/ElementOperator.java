package hr.fer.zemris.java.custom.scripting.elems;

/**
 * This class inherits {@link Element} and has single read-only
 * <code>String</code> property: <code>symbol</code>.
 * 
 * @author dbrcina
 * @version 1.0
 *
 */
public class ElementOperator extends Element {

	/**
	 * Operator's symbol.
	 */
	private String symbol;

	/**
	 * A constructor which initializes operator's <code>symbol</code>.
	 * 
	 * @param symbol A operator's {@code symbol}.
	 */
	public ElementOperator(String symbol) {
		super();
		this.symbol = symbol;
	}

	@Override
	public String asText() {
		return symbol;
	}

	/**
	 * Getter for {@link ElementOperator}'s <code>symbol</code>.
	 * 
	 * @return Element's {@code String} {@code symbol}.
	 */
	public String getSymbol() {
		return symbol;
	}

	@Override
	public String toString() {
		return asText();
	}
}
