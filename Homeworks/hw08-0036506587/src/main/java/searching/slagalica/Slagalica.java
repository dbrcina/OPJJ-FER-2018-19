package searching.slagalica;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import searching.algorithms.Transition;

/**
 * An implementation of puzzle abstraction.
 * 
 * @author dbrcina
 *
 */
public class Slagalica implements Supplier<KonfiguracijaSlagalice>,
		Function<KonfiguracijaSlagalice, List<Transition<KonfiguracijaSlagalice>>>, Predicate<KonfiguracijaSlagalice> {

	/**
	 * Constant representing minimum index.
	 */
	private static final int MIN_INDEX = 0;

	/**
	 * Constant representing maximum index.
	 */
	private static final int MAX_INDEX = 8;

	/**
	 * Constant representing final state.
	 */
	private static final int[] FINAL_STATE = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 0 };

	/**
	 * Reference to configuration state.
	 */
	private KonfiguracijaSlagalice state;

	/**
	 * Constructor.
	 * 
	 * @param state configuration state.
	 */
	public Slagalica(KonfiguracijaSlagalice state) {
		this.state = state;
	}

	@Override
	public boolean test(KonfiguracijaSlagalice state) {
		return Arrays.equals(state.getPolje(), FINAL_STATE);
	}

	@Override
	public List<Transition<KonfiguracijaSlagalice>> apply(KonfiguracijaSlagalice state) {
		List<Transition<KonfiguracijaSlagalice>> transitions = new ArrayList<>();
		int indexOfSpace = state.indexOfSpace();

		// keep in mind that this only works with 9 numbers from 0-8!
		
		// first column
		if (indexOfSpace % 3 == 0) {
			if (indexOfSpace - 3 > MIN_INDEX) {
				// add top one
				transitions.add(createTransition(state.getPolje(), indexOfSpace, indexOfSpace - 3, 1));
			}
			// add right one
			transitions.add(createTransition(state.getPolje(), indexOfSpace, indexOfSpace + 1, 1));
			if (indexOfSpace + 3 < MAX_INDEX) {
				// add bottom one
				transitions.add(createTransition(state.getPolje(), indexOfSpace, indexOfSpace + 3, 1));
			}
			return transitions;
		}

		// second column
		if (indexOfSpace % 3 == 1) {
			// add left one
			transitions.add(createTransition(state.getPolje(), indexOfSpace, indexOfSpace - 1, 1));
			if (indexOfSpace - 3 > MIN_INDEX) {
				// add top one
				transitions.add(createTransition(state.getPolje(), indexOfSpace, indexOfSpace - 3, 1));
			}
			// add right one
			transitions.add(createTransition(state.getPolje(), indexOfSpace, indexOfSpace + 1, 1));
			if (indexOfSpace + 3 < MAX_INDEX) {
				// add bottom one
				transitions.add(createTransition(state.getPolje(), indexOfSpace, indexOfSpace + 3, 1));
			}
			return transitions;
		}

		// third columnd
		else {
			// add left one
			transitions.add(createTransition(state.getPolje(), indexOfSpace, indexOfSpace - 1, 1));
			if (indexOfSpace - 3 > MIN_INDEX) {
				// add top one
				transitions.add(createTransition(state.getPolje(), indexOfSpace, indexOfSpace - 3, 1));
			}
			if (indexOfSpace + 3 < MAX_INDEX) {
				// add bottom one
				transitions.add(createTransition(state.getPolje(), indexOfSpace, indexOfSpace + 3, 1));
			}
			return transitions;
		}

	}

	/**
	 * Method used for creating new instance of {@link Transition}.
	 * 
	 * @param configuration configuration.
	 * @param indexOfSpace  index of space value.
	 * @param index         index of an element that needs to be changed with space
	 *                      value.
	 * @param cost          transition cost.
	 * @return new instance of {@link Transition}.
	 * @see #changeState(int[], int, int)
	 */
	private Transition<KonfiguracijaSlagalice> createTransition(int[] configuration, int indexOfSpace, int index,
			double cost) {
		return new Transition<>(new KonfiguracijaSlagalice(changeState(configuration, indexOfSpace, index)), cost);
	}

	/**
	 * Method used for changing positions of index of space with value at
	 * <code>index</code> from <code>states</code> array.
	 * 
	 * @param states       int array.
	 * @param indexOfSpace index of space.
	 * @param index        index.
	 * @return changed array.
	 */
	private int[] changeState(int[] states, int indexOfSpace, int index) {
		states[indexOfSpace] = states[index];
		states[index] = 0;
		return states;
	}

	@Override
	public KonfiguracijaSlagalice get() {
		return state;
	}

}
