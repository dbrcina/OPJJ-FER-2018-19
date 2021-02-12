package hr.fer.zemris.java.custom.collections;

import java.util.Arrays;

/**
 * An implementation of resizable array-backed collection of objects denoted as
 * ArrayIndexCollection which extends class Collection. General contract of this
 * collection is: duplicate elements are allowed; storage of null references is
 * not allowed.
 * 
 * @author Darijo Brčina
 * @version 1.0
 *
 */
public class ArrayIndexedCollection extends Collection {

	/**
	 * A constant that represents default capacity of collection.
	 */
	private static final int DEFAULT_CAPACITY = 16;

	/**
	 * A variable which represents max capacity of collection.
	 */
	static int capacity;

	/**
	 * Current size of collection (number of elements actually stored in
	 * {@link #elements} array).
	 */
	private int size;

	/**
	 * An array of object references which lenght is determined by {@link #capacity}
	 * variable.
	 */
	private Object[] elements;

	/**
	 * The default constructor that creates an instance of ArrayIndexCollection with
	 * capacity set to {@link #DEFAULT_CAPACITY}.
	 */
	public ArrayIndexedCollection() {
		this(DEFAULT_CAPACITY);
	}

	/**
	 * A constructor that sets {@link #capacity} to initialCapacity given by the
	 * user and preallocates the {@link #elements} array of that size.
	 * 
	 * @param initialCapacity a variable which represents the new capacity of
	 *                        {@link #elements} array.
	 * @throws IllegalArgumentException if the initialCapacity is < 1.
	 */
	public ArrayIndexedCollection(int initialCapacity) {
		if (initialCapacity < 1) {
			throw new IllegalArgumentException("Kapacitet ne može biti manji od 1!");
		}
		capacity = initialCapacity;
		elements = new Object[capacity];
	}

	/**
	 * A constructor that copies elements from given collection into this newly
	 * constructed collection only if the given collection is a non-null reference.
	 * 
	 * @param other a collection whose elements need to be copied into this newly
	 *              collection.
	 * @throws NullPointerException if the {@link #other} is null reference.
	 */
	public ArrayIndexedCollection(Collection other) {
		this(other, DEFAULT_CAPACITY);
	}

	/**
	 * A constructor that copies elements from given collection into this newly
	 * constructed collection only if the given collection is a non-null reference.
	 * If the initialCapacity is smaller than the size of the given collection, the
	 * size of the given collection should be used for elements array preallocation.
	 * 
	 * @param other           a collection whose elements need to be copied into
	 *                        this newly collection.
	 * @param initialCapacity a variable which represents the new capacity of
	 *                        {@link #elements} array only if it is higher than size
	 *                        of the given collection.
	 * @throws NullPointerException     if the {@link #other} is null reference.
	 * @throws IllegalArgumentException if the {@link #initialCapacity} is < 1.
	 */
	public ArrayIndexedCollection(Collection other, int initialCapacity) {
		this(initialCapacity);
		if (other == null) {
			throw new NullPointerException("Kolekcija je null!");
		}
		if (capacity < other.size()) {
			capacity = other.size();
		}
		elements = new Object[capacity];
		addAll(other);
	}

	/**
	 * In this implementation method size returns the size of internal array.
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * Adds the given object into this collection (reference is added into first
	 * empty place in the {@link #elements} array; if the elements array is full, it
	 * should be reallocated by doubling its size). The method should refuse to add
	 * null as element by throwing the appropriate exception (NullPointerException).
	 * Average complexity of this method is O(1).
	 * 
	 * @throws NullPointerException if the value is null.
	 */
	@Override
	public void add(Object value) {
		if (value == null) {
			throw new NullPointerException("Nemoguće je dodati null!");
		}
		if (size == capacity) {
			resizeCollection();
		}
		elements[size++] = value;
	}

	/**
	 * In this implementation the method returns representative value, not only
	 * false.
	 */
	@Override
	public boolean contains(Object value) {
		for (int i = 0; i < size; i++) {
			if (elements[i].equals(value)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * A method that returns the object stored in backing array at position
	 * {@link #index}. Valid indexes are 0 to size-1. If index is invalid, the
	 * implementation should throw the appropriate exception
	 * (IndexOutOfBoundsException). The average complexity of this method is O(1).
	 * 
	 * @param index a position from where an object should be returned.
	 * @return the object stored in backing array at position {@link #index}.
	 * @throws IndexOutOfBoundsException if the {@link #index} is invalid.
	 */
	public Object get(int index) {
		if (index < 0 || index > size - 1) {
			throw new IndexOutOfBoundsException("Invalidan index!");
		}
		return elements[index];
	}

	@Override
	public void clear() {
		for (int i = 0; i < size; i++) {
			elements[i] = null;
		}
		size = 0;
	}

	/**
	 * A method that returns this collection as an array. It cannot return null
	 * value.
	 * 
	 * @throws UnsupportedOperationException if the return value is null reference.
	 */
	@Override
	public Object[] toArray() {
		if (elements == null) {
			throw new UnsupportedOperationException("Kolekcija je null!");
		}
		return Arrays.copyOfRange(elements, 0, size);
	}

	@Override
	public void forEach(Processor processor) {
		for (int i = 0; i < size; i++) {
			processor.proces(elements[i]);
		}
	}

	/**
	 * Inserts (does not overwrite) the given value at the given position in array
	 * (observe that before actual insertion elements at position and at greater
	 * positions must be shifted one place toward the end, so that an empty place is
	 * created at position) . The legal positions are 0 to size (both are included).
	 * If position is invalid, an appropriate exception should be thrown
	 * (IndexOutOfBoundsException). Except the difference in position at witch the
	 * given object will be inserted, everything else should be in conformance with
	 * the method {@link #add(Object)}. The average complexity of this method is
	 * O(n).
	 * 
	 * @param value    to be inserted in collection
	 * @param position a position where {@link #value} should be inserted.
	 * @throws IndexOutOfBoundsException if the position is invalid.
	 */
	public void insert(Object value, int position) {
		if (position < 0 || position > size) {
			throw new IndexOutOfBoundsException("Invalidna pozicija");
		}
		if (size == capacity) {
			resizeCollection();
		}
		if (position != size) {
			for (int i = size - 1; i >= position; i--) {
				elements[i + 1] = elements[i];
			}
		}
		elements[position] = value;
		size++;
	}

	/**
	 * Searches the collection and returns the index of the first occurrence of the
	 * given value or -1 if the value is not found. Argument can be null and the
	 * result must be that this element is not found (since the collection can not
	 * contain null). The average complexity of this method is O(n).
	 * 
	 * @param value a value that is searched up in this collection.
	 * @return returns the index of the first occurrence of the given {@link #value}
	 *         or -1 if the value is not found.
	 */
	public int indexOf(Object value) {
		for (int i = 0; i < size; i++) {
			if (elements[i].equals(value)) {
				return i;
			}
		}
		return -1;
	}

	/**
	 * Removes element at specified {@link #index} from collection. Element that was
	 * previously at location index+1 after this operation is on location index,
	 * etc. Legal indexes are 0 to size-1. In case of invalid index throw an
	 * appropriate exception (IndexOutOfBoundsException).
	 * 
	 * @param index position from where the object should be removed.
	 * @throws IndexOutOfBoundsException if the index is invalid.
	 */
	public void remove(int index) {
		if (index < 0 || index > size - 1) {
			throw new IndexOutOfBoundsException("Invalidan index!");
		}
		for (int i = index; i < size - 1; i++) {
			elements[i] = elements[i + 1];
		}
		elements[size - 1] = null;
		size--;
	}

	/**
	 * A method that removes the given {@link #value} from the collection only if
	 * the value exists. In this implementation return value is representative. It
	 * depends whether collection contains given value or don't.
	 */
	@Override
	public boolean remove(Object value) {
		if (contains(value)) {
			remove(indexOf(value));
			return true;
		}
		return false;
	}

	/**
	 * A private method that is used when an internal array is full. It doubles
	 * {@link #elements} capacity.
	 */
	private void resizeCollection() {
		capacity *= 2;
		elements = Arrays.copyOf(elements, capacity);
	}
}