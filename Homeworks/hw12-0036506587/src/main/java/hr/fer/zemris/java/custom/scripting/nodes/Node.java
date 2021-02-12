package hr.fer.zemris.java.custom.scripting.nodes;

import java.util.ArrayList;
import java.util.List;

/**
 * Base class for all graph nodes.
 * 
 * @author dbrcina
 * @version 1.0
 *
 */
public abstract class Node {

	/**
	 * Internally managed collection of children.
	 */
	private List<Node> storage;

	/**
	 * Ads given <code>child</code> to an internally managed collection of children.
	 * 
	 * @param child A Node that needs to be added in internal storage.
	 */
	public void addChildNode(Node child) {
		if (storage == null) {
			storage = new ArrayList<>();
		}
		storage.add(child);
	}

	/**
	 * Method which returns number of children stored in internal storage.
	 * 
	 * @return Number of children stored in internal storage.
	 */
	public int numberOfChildren() {
		return storage.size();
	}

	/**
	 * Returns child at the given <code>index</code> or throws an appropriate
	 * exception if the <code>index</code> is invalid.
	 * 
	 * @param index A position from where a child should be returned.
	 * @return A Node child.
	 * @throws IndexOutOfBoundsException if {@code index} is invalid.
	 */
	public Node getChild(int index) {
		return storage.get(index);
	}

	/**
	 * Performs visit operation by <code>visitor</code> on current model .
	 * 
	 * @param visitor visitor.
	 */
	public abstract void accept(INodeVisitor visitor);
}
