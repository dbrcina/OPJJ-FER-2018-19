package hr.fer.zemris.java.custom.scripting.exec;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;
import java.util.Stack;

/**
 * An implementation of stack-like abstraction that internally uses instance of
 * {@link Map} which can store multiple values for same key on contrary real
 * {@link Map} implementation. Keys are instances of {@link String} and values
 * are instances of {@link MultistackEntry}.
 * 
 * @author dbrcina
 *
 */
public class ObjectMultistack {

	/**
	 * Private structure representing stack-like collection with the
	 * implementational detail of {@link LinkedList} where every node consist of
	 * value and reference to next node. Value is an instance of
	 * {@link ValueWrapper} and reference to next node is an instance of
	 * {@link MultistackEntry}.
	 * 
	 * @author dbrcina
	 *
	 */
	private static class MultistackEntry {

		/**
		 * Reference to {@link ValueWrapper} object.
		 */
		private ValueWrapper value;

		/**
		 * Reference to next {@link MultistackEntry} object.
		 */
		private MultistackEntry next;

		/**
		 * Constructor that takes two arguments, <code>code</code> and
		 * <code>next</code>. It initializes {@link #value} and {@link #next}.
		 * 
		 * @param value {@link ValueWrapper} value.
		 * @param next  reference to the next instance of {@link MultistackEntry}.
		 * @throws NullPointerException if {@code value} is {@code null}.
		 */
		public MultistackEntry(ValueWrapper value, MultistackEntry next) {
			this.value = Objects.requireNonNull(value, "Value cannot be null reference!");
			this.next = next;
		}

	}

	/**
	 * Storage of {@link MultistackEntry} elements.
	 */
	private Map<String, MultistackEntry> multistackMap = new HashMap<>();

	/**
	 * Method used for pushing the given <code>valueWrapper</code> on multistack.
	 * 
	 * @param keyName      key name.
	 * @param valueWrapper value.
	 * @throws NullPointerException if one of the given arguments is {@code null}.
	 * @see Stack#push(Object)
	 */
	public void push(String keyName, ValueWrapper valueWrapper) {
		Objects.requireNonNull(valueWrapper, "Value wrapper cannot be null reference!");
		if (!multistackMap.containsKey(Objects.requireNonNull(keyName, "Key cannot be null reference!"))) {
			multistackMap.put(keyName, new MultistackEntry(valueWrapper, null));
			return;
		}
		MultistackEntry entry = multistackMap.get(keyName);
		multistackMap.put(keyName, new MultistackEntry(valueWrapper, entry));
	}

	/**
	 * This method returs the last element from stack but does not remove it.
	 * 
	 * @param keyName key name.
	 * @return {@link ValueWrapper} type value.
	 * @throws NullPointerException if given {@code keyName} is {@code null}.
	 * @throws EmptyStackException  if the stack is empy.
	 */
	public ValueWrapper peek(String keyName) {
		if (isEmpty(keyName)) {
			throw new EmptyStackException("Reading from empty stack!");
		}
		return getEntry(keyName).value;
	}

	/**
	 * This method returns the last element from stack and removes it.
	 * 
	 * @param keyName key name.
	 * @return {@link ValueWrapper} type variable.
	 * @throws EmptyStackException if the stack is empy.
	 */
	public ValueWrapper pop(String keyName) {
		if (isEmpty(keyName)) {
			throw new EmptyStackException("Poping from empty stack!");
		}

		MultistackEntry entry = getEntry(keyName);
		multistackMap.remove(keyName);
		multistackMap.put(keyName, entry.next);
		return entry.value;
	}

	/**
	 * @param keyName key name.
	 * @return {@code true} if stack with key {@code keyName} is empty, otherwise
	 *         {@code false}.
	 * @throws NullPointerException if given {@code keyName} is {@code null}.
	 */
	public boolean isEmpty(String keyName) {
		return getEntry(keyName) == null;
	}

	/**
	 * Helper method used for fetching entry by given <code>keyName</code> key.
	 * 
	 * @param keyName key.
	 * @return entry.
	 */
	private MultistackEntry getEntry(String keyName) {
		return multistackMap.get(keyName);
	}
}
