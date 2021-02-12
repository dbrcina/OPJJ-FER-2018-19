package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;

/**
 * A node representing a command which generates some textual output
 * dynamically. It inherits from {@link Node} class.
 * 
 * @author dbrcina
 * @version 1.0
 *
 */
public class EchoNode extends Node {

	/**
	 * Internal storage of {@link Element} instances.
	 */
	private Element[] elements;

	/**
	 * A constructor.
	 * 
	 * @param elements
	 */
	public EchoNode(Element[] elements) {
		super();
		this.elements = elements;
	}

	/**
	 * Getter for {@link EchoNode}'s elements.
	 * 
	 * @return {@code EchoNode's} elements.
	 */
	public Element[] getElements() {
		return elements;
	}

	@Override
	public String toString() {
		String text = "{$= ";

		for (Element e : elements) {
			text += e + " ";
		}

		text += "$}";
		return text;
	}

	/**
	 * {@inheritDoc}
	 */
	public void accept(INodeVisitor visitor) {
		visitor.visitEchoNode(this);
	}
}
