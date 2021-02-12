package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Model of {@link Node} visitor. It provides four public methods used for
 * traversing through instances of {@link Node}:
 * 
 * <pre>
 * {@link #visitDocumentNode(DocumentNode)}
 * {@link #visitEchoNode(EchoNode)}
 * {@link #visitForLoopNode(ForLoopNode)}
 * {@link #visitTextNode(TextNode)}
 * </pre>
 * 
 * @author dbrcina
 *
 */
public interface INodeVisitor {

	/**
	 * Visits instance of {@link TextNode}.
	 * 
	 * @param node node.
	 */
	void visitTextNode(TextNode node);

	/**
	 * Visits instance of {@link ForLoopNode}.
	 * 
	 * @param node node.
	 */
	void visitForLoopNode(ForLoopNode node);

	/**
	 * Visits instance of {@link EchoNode}.
	 * 
	 * @param node node.
	 */
	void visitEchoNode(EchoNode node);

	void visitNowNode(NowNode visitor);
	/**
	 * Visits instance of {@link DocumentNode}.
	 * 
	 * @param node node.
	 */
	void visitDocumentNode(DocumentNode node);
}
