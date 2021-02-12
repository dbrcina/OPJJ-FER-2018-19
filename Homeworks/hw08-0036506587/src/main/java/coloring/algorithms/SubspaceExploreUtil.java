package coloring.algorithms;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Utility class used for implementations of "bfs", "dfs" and "bfsv" algorithms.
 * 
 * @author dbrcina.
 *
 */
public class SubspaceExploreUtil {

	/**
	 * Method implementing "bfs" algorithm.
	 * 
	 * @param s0         initial state.
	 * @param process    process.
	 * @param succ       successor.
	 * @param acceptable tester.
	 */
	public static <S> void bfs(Supplier<S> s0, Consumer<S> process, Function<S, List<S>> succ,
			Predicate<S> acceptable) {

		List<S> research = new LinkedList<>();
		research.add(s0.get());

		while (!research.isEmpty()) {
			S si = research.remove(0);
			if (!acceptable.test(si)) {
				continue;
			}
			process.accept(si);
			research.addAll(succ.apply(si));
		}
	}

	/**
	 * Method implementing "dfs" algorithm.
	 * 
	 * @param s0         initial state.
	 * @param process    process.
	 * @param succ       successor.
	 * @param acceptable tester.
	 */
	public static <S> void dfs(Supplier<S> s0, Consumer<S> process, Function<S, List<S>> succ,
			Predicate<S> acceptable) {

		List<S> research = new LinkedList<>();
		research.add(s0.get());

		while (!research.isEmpty()) {
			S si = research.remove(0);
			if (!acceptable.test(si)) {
				continue;
			}
			process.accept(si);
			research.addAll(0, succ.apply(si));
		}
	}

	/**
	 * Method implementing "bfsv" algorithm.
	 * 
	 * @param s0         initial state.
	 * @param process    process.
	 * @param succ       successor.
	 * @param acceptable tester.
	 */
	public static <S> void bfsv(Supplier<S> s0, Consumer<S> process, Function<S, List<S>> succ,
			Predicate<S> acceptable) {

		Collection<S> firstState = Arrays.asList(s0.get());
		List<S> research = new LinkedList<>(firstState);
		Set<S> visited = new HashSet<>(firstState);

		while (!research.isEmpty()) {
			S si = research.remove(0);
			if (!acceptable.test(si)) {
				continue;
			}
			process.accept(si);
			List<S> children = succ.apply(si);
			children.removeAll(visited);
			research.addAll(children);
			visited.addAll(children);
		}
	}

}
