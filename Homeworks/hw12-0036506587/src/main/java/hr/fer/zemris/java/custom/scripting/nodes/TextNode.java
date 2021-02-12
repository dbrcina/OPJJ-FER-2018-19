package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * A node representing a piece of textual data. It inherits from {@link Node}
 * class.
 * 
 * @author dbrcina
 * @version 1.0
 *
 */
public class TextNode extends Node {

	/**
	 * TextNode's text.
	 */
	private String text;

	/**
	 * Public constructor that saves the input <code>text</code>.
	 * 
	 * @param text A {@code String} representation of Node's text.
	 */
	public TextNode(String text) {
		super();
		this.text = text;
	}

	/**
	 * Getter for <code>TextNode's</code> text.
	 * 
	 * @return {@code TextNode's} text.
	 */
	public String getText() {
		return text;
	}

	@Override
	public String toString() {
		// replace sign \ with \\
		// replace sign { with \{
		return text.replaceAll("\\\\", "\\\\\\\\").replaceAll("[{]", "\\\\{");
	}

	/**
	 * {@inheritDoc}
	 */
	public void accept(INodeVisitor visitor) {
		visitor.visitTextNode(this);
	}
}
