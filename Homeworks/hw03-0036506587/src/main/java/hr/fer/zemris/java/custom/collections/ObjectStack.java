package hr.fer.zemris.java.custom.collections;

/**
 * An implementation of regural stack-like collection
 * 
 * @author Darijo Brčina
 * @version 1.0
 *
 */
public class ObjectStack {

	/**
	 * Internal variable that stores elements pushed to stack.
	 */
	private ArrayIndexedCollection elements;

	/**
	 * A default constructor for stack-like collection. It initializes the internal
	 * structure where elements are stored.
	 */
	public ObjectStack() {
		elements = new ArrayIndexedCollection();
	}

	/**
	 * A simple method that checks whether stack is empty or full determined by the
	 * size of it.
	 * 
	 * @return true if the stack is empty or false if it has atleast one element.
	 */
	public boolean isEmpty() {
		return elements.size() == 0;
	}

	/**
	 * A method which return how many elements are stored on the stack.
	 * 
	 * @return number of stored elements.
	 */
	public int size() {
		return elements.size();
	}

	/**
	 * Pushes given {@code value} on the stack. null value must not be allowed to be
	 * placed on stack. The average complexity of this method is O(1).
	 * 
	 * @param value that needs to be pushed on the stack.
	 * @throws NullPointerException if the given value is null reference.
	 */
	public void push(Object value) {
		elements.add(value);
	}

	/**
	 * Removes last value pushed on stack from stack and returns it. If the stack is
	 * empty when method pop is called, the method should throw EmptyStackException.
	 * The average complexity of this method is O(1).
	 * 
	 * @return the last object that was pushed on stack.
	 * @throws EmptyStackException when someone tries to take an element from an
	 *                             emty stack.
	 */
	public Object pop() {
		Object value = peek();
		elements.remove(value);
		return value;
	}

	/**
	 * Similar as {@link pop}; returns last element placed on stack but does not
	 * delete it from stack. If the stack is empty when method pop is called, the
	 * method should throw EmptyStackException. The average complexity of this
	 * method is O(1).
	 * 
	 * @return the last object that was pushed on stack.
	 * @throws EmptyStackException if the stack is empty when {@link peek} is
	 *                             called.
	 */
	public Object peek() {
		if (isEmpty()) {
			throw new EmptyStackException("Stog je prazan!");
		}
		return elements.get(elements.size() - 1);
	}

	/**
	 * Removes all elements from stack.
	 */
	public void clear() {
		elements.clear();
	}
}
