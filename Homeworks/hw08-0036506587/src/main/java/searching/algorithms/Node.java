package searching.algorithms;

/**
 * Representation of a node that has a reference to a parrent {@link Node},
 * current state and double cost value. Cost here represents how many steps it
 * needs to be taken to get to current state.
 * 
 * @author dbrcina
 *
 * @param <S> type of parameter.
 */
public class Node<S> {

	/**
	 * Reference to parent node.
	 */
	private Node<S> parent;

	/**
	 * Current state.
	 */
	private S state;

	/**
	 * Cost value.
	 */
	private double cost;

	/**
	 * Constructor.
	 * 
	 * @param parent parent reference.
	 * @param state  state.
	 * @param cost   cost value.
	 */
	public Node(Node<S> parent, S state, double cost) {
		this.parent = parent;
		this.state = state;
		this.cost = cost;
	}

	/**
	 * Getter for parrent reference.
	 * 
	 * @return {@link #getParent()}.
	 */
	public Node<S> getParent() {
		return parent;
	}

	/**
	 * Getter for node's state.
	 * 
	 * @return {@link #state}.
	 */
	public S getState() {
		return state;
	}

	/**
	 * Getter for node's cost.
	 * 
	 * @return {@link #cost}.
	 */
	public double getCost() {
		return cost;
	}

}
