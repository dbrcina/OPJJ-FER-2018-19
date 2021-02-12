package hr.fer.zemris.java.custom.collections;

import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * An implementation of resizable array-backed collection of objects denoted as
 * ArrayIndexCollection which extends class Collection. General contract of this
 * collection is: duplicate elements are allowed; storage of {@code non-null}
 * references is not allowed.
 * 
 * @author Darijo Brčina
 * @version 1.0
 *
 */
public class ArrayIndexedCollection implements List {

	/**
	 * A constant that represents default capacity of a collection.
	 */
	private static final int DEFAULT_CAPACITY = 16;

	/**
	 * Current size of this collection (number of elements actually stored in
	 * internal storage and not it's capacity).
	 */
	private int size;

	/**
	 * An array of Object references where collection's values are stored.
	 */
	private Object[] elements;

	/**
	 * A variable that increments everytime when this collection is modified
	 * (reallocation, shifting elements etc.).
	 */
	private long modificationCount;

	/**
	 * The default constructor that creates an instance of ArrayIndexCollection with
	 * capacity set to {@code DEFAULT_CAPACITY}.
	 */
	public ArrayIndexedCollection() {
		this(DEFAULT_CAPACITY);
	}

	/**
	 * A constructor that preallocates internal storage(array) with the given
	 * {@code initialCapacity} only if it's value is > 1.
	 * 
	 * @param initialCapacity a variable which represents the new capacity of
	 *                        collection's internal storage.
	 * @throws IllegalArgumentException if the initialCapacity is < 1.
	 */
	public ArrayIndexedCollection(int initialCapacity) {
		if (initialCapacity < 1) {
			throw new IllegalArgumentException("Kapacitet ne može biti manji od 1!");
		}
		elements = new Object[initialCapacity];
	}

	/**
	 * A constructor that copies elements from given collection {@code other} into
	 * this newly constructed collection only if the given collection {@code other}
	 * is a non-null reference.
	 * 
	 * @param other a collection whose elements need to be copied into this newly
	 *              collection.
	 * @throws NullPointerException if the {@link #other} is null reference.
	 */
	public ArrayIndexedCollection(Collection other) {
		this(other, DEFAULT_CAPACITY);
	}

	/**
	 * A constructor that copies elements from given collection {@code other} into
	 * this newly constructed collection only if the given collection {@code other}
	 * is a non-null reference. If the {@code initialCapacity} is smaller than the
	 * size of the given collection {@code other}, the size of the given collection
	 * should be used for internal array preallocation.
	 * 
	 * @param other           a collection whose elements need to be copied into
	 *                        this newly collection.
	 * @param initialCapacity a variable which represents the new capacity of
	 *                        internal array only if it is higher than size of the
	 *                        given collection {@code other}.
	 * @throws NullPointerException     if the {@link #other} is null reference.
	 * @throws IllegalArgumentException if the {@link #initialCapacity} is < 1.
	 */
	public ArrayIndexedCollection(Collection other, int initialCapacity) {
		this(initialCapacity);
		if (other == null) {
			throw new NullPointerException("Kolekcija je null!");
		}
		if (elements.length < other.size()) {
			elements = new Object[other.size()];
		}
		addAll(other);
	}

	@Override
	public int size() {
		return size;
	}

	/**
	 * Adds the given Object {@code value} into this collection (reference is added
	 * into first empty place in the internal array; if the internal array is full,
	 * it should be reallocated by doubling its size). The method should refuse to
	 * add {@code null} as element by throwing an appropriate exception
	 * (NullPointerException). Average complexity of this method is O(1).
	 * 
	 * @throws NullPointerException if the {@code value} is {@code null}.
	 */
	@Override
	public void add(Object value) {
		insert(value, size);
	}

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
	 * {@code index}. Valid indexes are from {@code 0} to {@code size-1}. If
	 * {@code index} is invalid, an appropriate exception is thrown
	 * (IndexOutOfBoundsException). The average complexity of this method is O(1).
	 * 
	 * @param index a position from where an object should be returned.
	 * @return the object stored in backing array at position {@code index}.
	 * @throws IndexOutOfBoundsException if the {@code index} is invalid.
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
		modificationCount++;
	}

	@Override
	public Object[] toArray() {
		return Arrays.copyOf(elements, size);
	}

	/**
	 * Inserts (does not overwrite) the given {@code value} at the given
	 * {@code position} in internal array (observe that before actual insertion
	 * elements at {@code position} and at greater positions must be shifted one
	 * place toward the end, so that an empty place is created at {@code position}).
	 * If the given {@code value} is a {@code null} reference, then an appropriate
	 * exception should be thrown (NullPointerException). The legal positions are
	 * from {@code 0} to {@code size} (both are included). If {@code position} is
	 * invalid, an appropriate exception is thrown (IndexOutOfBoundsException).
	 * Except the difference in position at witch the given object will be inserted,
	 * everything else should be in conformance with the method
	 * {@link #add(Object)}. The average complexity of this method is O(n).
	 * 
	 * @param value    to be inserted in collection
	 * @param position a position where {@code value} should be inserted.
	 * @throws NullPointerException      if the given {@code value} is a
	 *                                   {@code null} reference.
	 * @throws IndexOutOfBoundsException if the {@code position} is invalid.
	 */
	public void insert(Object value, int position) {
		if (value == null) {
			throw new NullPointerException("Pokušava se dodati null vrijednost!");
		}
		if (position < 0 || position > size) {
			throw new IndexOutOfBoundsException("Invalidna pozicija");
		}
		if (size == elements.length) {
			resizeCollection();
		}
		if (position != size) {
			for (int i = size - 1; i >= position; i--) {
				elements[i + 1] = elements[i];
			}
		}
		elements[position] = value;
		size++;
		modificationCount++;
	}

	/**
	 * Searches the collection and returns the index of the first occurrence of the
	 * given {@code value} or {@code -1} if the {@code value} is not found. Argument
	 * can be {@code null} and the result must be that this element is not found
	 * (since the collection cannot contain {@code null}). The average complexity of
	 * this method is O(n).
	 * 
	 * @param value a value that is searched up in this collection.
	 * @return returns the index of the first occurrence of the given {@code value}
	 *         or {@code -1} if the {@code value} is not found.
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
	 * Removes element at specified {@code index} from collection. Element that was
	 * previously at location {@code index+1} after this operation is on location
	 * {@code index}, etc. Legal indexes are from 0 to {@code size-1}. In case of
	 * invalid {@code index}, an appropriate exception is thrown
	 * (IndexOutOfBoundsException).
	 * 
	 * @param index position from where the object should be removed.
	 * @throws IndexOutOfBoundsException if the {@code index} is invalid.
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
		modificationCount++;
	}

	@Override
	public boolean remove(Object value) {
		int index = indexOf(value);
		if (index != -1) {
			remove(index);
			return true;
		}
		return false;
	}

	/**
	 * A private method that is used when an internal array is full. It doubles it's
	 * capacity.
	 */
	private void resizeCollection() {
		elements = Arrays.copyOf(elements, elements.length * 2);
		modificationCount++;
	}

	@Override
	public ElementsGetter createElementsGetter() {
		return new ArrayListElementsGetter(this, modificationCount);
	}

	/**
	 * An implementation that provides user to go through an instance of
	 * {@link ArrayIndexedCollection} and do methods that grant access to
	 * collection's elements.
	 * 
	 * @author Darijo Brčina
	 * @version 1.0
	 *
	 */
	private static class ArrayListElementsGetter implements ElementsGetter {

		/**
		 * Current index from where an element should be returned.
		 */
		private int index;
		/**
		 * A reference that will point to the collection we want to.
		 */
		private ArrayIndexedCollection col;

		/**
		 * Saves the number of modifications that accured in this collection before
		 * calling {@link ArrayIndexedCollection#createElementsGetter()} method.
		 */
		private long savedModificationCount;

		/**
		 * A constructor used to copy collection {@code col} to inner variable which is
		 * used to access {@code col} private fields and save value of
		 * {@code modificationCount} to keep an eye whether there had been any changes
		 * while working with collection {@code col}.
		 * 
		 * @param col               an {@link ArrayIndexedCollection} type collection.
		 * @param modificationCount number of modifications happend before calling
		 *                          {@link ArrayIndexedCollection#createElementsGetter()}
		 *                          method.
		 */
		public ArrayListElementsGetter(ArrayIndexedCollection col, long modificationCount) {
			this.col = col;
			this.savedModificationCount = modificationCount;
		}

		@Override
		public boolean hasNextElement() {
			checkForModifications();
			return index != col.size;
		}

		@Override
		public Object getNextElement() {
			checkForModifications();
			if (!hasNextElement()) {
				throw new NoSuchElementException("Nema više elemenata za dohvatiti!");
			}
			return col.elements[index++];
		}

		/**
		 * Checks whether given collection had any modifications since calling
		 * {@link ArrayIndexedCollection#createElementsGetter()} method.
		 * 
		 * @throws ConcurrentModificationException if the modifications happened.
		 */
		private void checkForModifications() {
			if (savedModificationCount != col.modificationCount) {
				throw new ConcurrentModificationException("Kolekcija je bila promijenja za vrijeme rada s njom!");
			}
		}

	}
}