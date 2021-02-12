package hr.fer.zemris.java.custom.collections;

/**
 * An ordered collection that inherits {@link Collection} interface.
 * 
 * @author dbrcina
 * @version 1.0
 *
 */
public interface List extends Collection {

	/**
	 * Returns the element at the specified position in this list.
	 *
	 * @param index index of the element to return
	 * @return the element at the specified position in this list
	 * @throws IndexOutOfBoundsException if the index is out of range
	 *                                   ({@code index < 0 || index >= size()})
	 */
	Object get(int index);

	/**
	 * Inserts the specified value at the specified position in this list. Shifts
	 * the element currently at that position (if any) and any subsequent elements
	 * to the right.
	 *
	 * @param index index at which the specified element is to be inserted
	 * @param value value to be inserted
	 * @throws NullPointerException      if the specified value is null and this
	 *                                   list does not permit null elements
	 * @throws IndexOutOfBoundsException if the index is out of range
	 *                                   ({@code index < 0 || index > size()})
	 */
	void insert(Object value, int position);

	/**
	 * Returns the index of the first occurrence of the specified element in this
	 * list, or -1 if this list does not contain the element.
	 *
	 * @param value value to search for
	 * @return the index of the first occurrence of the specified value in this
	 *         list, or -1 if this list does not contain the value
	 * @throws NullPointerException if the specified element is null
	 */
	int indexOf(Object value);

	/**
	 * Removes the element at the specified position in this list. Shifts any
	 * subsequent elements to the left.
	 *
	 * @param index the index of the element to be removed
	 * @throws IndexOutOfBoundsException if the index is out of range
	 *                                   ({@code index < 0 || index >= size()})
	 */
	void remove(int index);
}
