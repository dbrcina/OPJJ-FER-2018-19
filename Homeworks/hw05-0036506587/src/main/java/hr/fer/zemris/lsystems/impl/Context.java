package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * An implementation that provides user to use stack to manipulate with turtle's
 * current state when painting fractals.
 * 
 * @author dbrcina
 * @version 1.0
 * 
 */
public class Context {

	/**
	 * Stack used for states.
	 */
	private ObjectStack<TurtleState> stack;

	/**
	 * Defalut constructor.
	 */
	public Context() {
		stack = new ObjectStack<>();
	}

	/**
	 * @return current state stored on stack, but does not remove it.
	 * @see {@link ObjectStack#peek()}.
	 */
	public TurtleState getCurrentState() {
		return stack.peek();
	}

	/**
	 * Pushes <code>state</code> on stack.
	 * 
	 * @param state the input argument for state.
	 * @see {@link ObjectStack#push(Object)}.
	 */
	public void pushState(TurtleState state) {
		stack.push(state);
	}

	/**
	 * Pops current state off the stack.
	 * 
	 * @see {@link ObjectStack#pop}.
	 */
	public void popState() {
		stack.pop();
	}
}
