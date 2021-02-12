package searching.algorithms;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Utility class that provides implementation of "bfs" and "bfsv" algorithms for
 * search.
 * 
 * @author dbrcina
 *
 */
public class SearchUtil {

	/**
	 * Method implementing "bfs" algorithm.
	 * 
	 * @param s0   initial state.
	 * @param succ successor.
	 * @param goal goal that needs to be achieved.
	 * @return instance of {@link Node} whose state satisfies current {@code goal}, otherwise {@code null}.
	 */
	public static <S> Node<S> bfs(Supplier<S> s0, Function<S, List<Transition<S>>> succ, Predicate<S> goal) {
		List<Node<S>> research = new LinkedList<>(Arrays.asList(new Node<>(null, s0.get(), 0)));

		while (!research.isEmpty()) {
			Node<S> ni = research.remove(0);
			if (goal.test(ni.getState())) {
				return ni;
			}
			for (Transition<S> t : succ.apply(ni.getState())) {
				research.add(new Node<S>(ni, t.getState(), ni.getCost() + t.getCost()));
			}
		}

		return null;
	}

	/**
	 * Method implementing "bfsv" algorithm.
	 * 
	 * @param s0   initial state.
	 * @param succ successor.
	 * @param goal goal that needs to be achieved.
	 * @return instance of {@link Node} whose state satisfies current {@code goal}, otherwise {@code null}.
	 */
	public static <S> Node<S> bfsv(Supplier<S> s0, Function<S, List<Transition<S>>> succ, Predicate<S> goal) {
		Collection<Node<S>> initialState = Arrays.asList(new Node<>(null, s0.get(), 0));
		List<Node<S>> research = new LinkedList<>(initialState);
		Set<Node<S>> visited = new HashSet<>(initialState);

		while (!research.isEmpty()) {
			Node<S> ni = research.remove(0);
			if (goal.test(ni.getState())) {
				return ni;
			}
			List<Node<S>> children = new LinkedList<>();
			for (Transition<S> t : succ.apply(ni.getState())) {
				children.add(new Node<S>(ni, t.getState(), ni.getCost() + t.getCost()));
			}
			children.removeAll(visited);
			research.addAll(children);
			visited.addAll(children);
		}

		return null;
	}
}
