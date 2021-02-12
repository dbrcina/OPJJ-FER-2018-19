package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;

/**
 * Base class for all graph nodes.
 * 
 * @author dbrcina
 * @version 1.0
 *
 */
public class Node {

	/**
	 * Internally managed collection of children.
	 */
	private ArrayIndexedCollection storage;

	/**
	 * Ads given <code>child</code> to an internally managed collection of children.
	 * 
	 * @param child A Node that needs to be added in internal storage.
	 */
	public void addChildNode(Node child) {
		if (storage == null) {
			storage = new ArrayIndexedCollection();
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
		return (Node) storage.get(index);
	}

}
