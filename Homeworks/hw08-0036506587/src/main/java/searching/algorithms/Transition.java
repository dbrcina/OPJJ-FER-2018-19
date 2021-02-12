package searching.algorithms;

/**
 * Class that models one transition between two objects. Instances of this class
 * have two fields:
 * <ul>
 * <li>{@link #state}</li>
 * <li>{@link #cost}</li>
 * </ul>
 * 
 * @author drcina
 *
 * @param <S> type of parameter.
 */
public class Transition<S> {

	/**
	 * Objects new state.
	 */
	private S state;

	/**
	 * Cost of this transition.
	 */
	private double cost;

	/**
	 * Constructor.
	 * 
	 * @param state state.
	 * @param cost  cost value.
	 */
	public Transition(S state, double cost) {
		this.state = state;
		this.cost = cost;
	}

	/**
	 * Getter for transition state.
	 * 
	 * @return {@link #state}-
	 */
	public S getState() {
		return state;
	}

	/**
	 * Getter for transition cost.
	 * 
	 * @return {@link #cost}
	 */
	public double getCost() {
		return cost;
	}

}
