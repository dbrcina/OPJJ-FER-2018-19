package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * A node representing an entire document. It inherits from {@link Node} class.
 * 
 * @author dbrcina
 * @version 1.0
 *
 */
public class DocumentNode extends Node {

	/**
	 * {@inheritDoc}
	 */
	public void accept(INodeVisitor visitor) {
		visitor.visitDocumentNode(this);
	}
}
