package hr.fer.zemris.java.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * This class represents an implementation of storing entries (key,value) inside
 * internal storage as determined by {@link #hashCode()} method. Entries are
 * stored with the help of linked-list. That way overflow between entries is
 * handled. This implementation does not store keys that are
 * <code>null-references</code>, but values can be <code>null-references</code>.
 * 
 * @author dbrcina
 * @version 1.0
 *
 * @param <K> the type of key value.
 * @param <V> the type of entry value.
 */
public class SimpleHashtable<K, V> implements Iterable<SimpleHashtable.TableEntry<K, V>> {

	/**
	 * Structure that represents connection between one key and one value. Key
	 * cannot be <code>null</code>, but value can.
	 * 
	 * @author dbrcina
	 * @version 1.0
	 *
	 * @param <K> the type that represents key
	 * @param <V> the type that represents value
	 */
	public static class TableEntry<K, V> {

		/**
		 * Entry's key value.
		 */
		private K key;
		/**
		 * Entry's value.
		 */
		private V value;

		/**
		 * Reference to next instance of {@link TableEntry}.
		 */
		private TableEntry<K, V> next;

		/**
		 * A constructor for one entry.
		 * 
		 * @param key   input argument for key value.
		 * @param value input argument for entry value.
		 * @throws NullPointerException if the given {@code key} is {@code null}.
		 */
		public TableEntry(K key, V value, TableEntry<K, V> next) {
			this.key = Objects.requireNonNull(key, "Ključ ne može biti null!");
			this.value = value;
			this.next = next;
		}

		/**
		 * Getter for key value.
		 * 
		 * @return key value.
		 */
		public K getKey() {
			return key;
		}

		/**
		 * Getter for entry value.
		 * 
		 * @return entry's value.
		 */
		public V getValue() {
			return value;
		}

		/**
		 * Setter for entry value.
		 * 
		 * @param value input argument for entry value.
		 */
		public void setValue(V value) {
			this.value = value;
		}

		@Override
		public int hashCode() {
			return Objects.hash(key);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (!(obj instanceof TableEntry))
				return false;
			@SuppressWarnings("unchecked")
			TableEntry<K, V> other = (TableEntry<K, V>) obj;
			return Objects.equals(key, other.key);
		}

		@Override
		public String toString() {
			return key + "=" + value;
		}
	}

	/**
	 * Constant used for resizing table.
	 */
	private static final double AVAILABILITY = 0.75;

	/**
	 * Default capacity of {@link SimpleHashTable}'s internal storage.
	 */
	private static final int DEFAULT_CAPACITY = 16;

	/**
	 * {@link SimpleHashTable}'s internal storage.
	 */
	private TableEntry<K, V>[] table;

	/**
	 * Number of actually stored entries.
	 */
	private int size;

	/**
	 * Keeps track on every change that has happened with collection.
	 */
	private long modificationCount;

	/**
	 * Default constructor that creates table with {@link #DEFAULT_CAPACITY}.
	 */
	public SimpleHashtable() {
		this(DEFAULT_CAPACITY);
	}

	/**
	 * Constructor that creates table with given <code>initialCapacity</code>. If
	 * <code>initialCapacity</code> is not power of 2 then the capacity is set to
	 * the first power of 2 as determined by {@link #getRigthCapacity(int)} method.
	 * 
	 * @param initialCapacity the input argument for table capacity.
	 * @throws IllegalArgumentException if {@code initialCapacity < 1}.
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashtable(int initialCapacity) {
		if (initialCapacity < 1) {
			throw new IllegalArgumentException("Kapacitet ne može biti manji od 1!");
		}
		initialCapacity = getRigthCapacity(initialCapacity);
		table = (TableEntry<K, V>[]) new TableEntry[initialCapacity];
	}

	/**
	 * Adds new {@link Entry} with key <code>key</code> and value <code>value</code>
	 * into collection. If the given <code>key</code> already exist then the given
	 * <code>value</code> will overwrite the current value. If the given
	 * <code>key</code> is <code>null</code>, an appropriate exception is thrown.
	 * 
	 * @param key   input argument for key value.
	 * @param value input argument for entry value.
	 * @throws NullPointerException if the given {@code key} is {@code null}.
	 */
	public void put(K key, V value) {
		Objects.requireNonNull(key, "Ključ ne smije biti null!");

		putInTable(key, value, table);

		if (checkForSize()) {
			resizeTable();
		}
	}

	/**
	 * Fetches value from this collection as determined by given <code>key</code>.
	 * If the given <code>key</code> does not exists, <code>null</code> is returned,
	 * also works the same when given <code>key</code> exists but value is
	 * <code>null</code>.
	 * 
	 * @param key input argument for key value.
	 * @return value as determined by key value.
	 */
	public V get(Object key) {
		if (key == null) {
			return null;
		}
		@SuppressWarnings("unchecked")
		TableEntry<K, V> current = (TableEntry<K, V>) getRigthEntry(entry -> entry.key.equals(key));
		return current == null ? null : current.value;
	}

	/**
	 * @return number of actually stored entries in table.
	 */
	public int size() {
		return size;
	}

	/**
	 * Checks whether the given <code>key</code> exist in table.
	 * 
	 * @param key the input argument for key value.
	 * @return {@code true} if {@code key} exist, otherwise {@code false}.
	 */
	public boolean containsKey(Object key) {
		if (key == null) {
			return false;
		}
		return contains(entry -> entry.key.equals(key));
	}

	/**
	 * Checks whether the given <code>value</code> exist in table.
	 * 
	 * @param key the input argument for entry value.
	 * @return {@code true} if {@code value} exist, otherwise {@code false}.
	 */
	public boolean containsValue(Object value) {
		return contains(entry -> entry.value.equals(value));
	}

	/**
	 * Removes entry with the given <code>key</code> from table. If <code>key</code>
	 * is <code>null</code> or if it does not exist in table, method does nothing.
	 * 
	 * @param key the input argument for key value.
	 */
	public void remove(Object key) {
		if (key == null) {
			return;
		}

		int slot = slot(key, table);
		TableEntry<K, V> current = table[slot];

		if (current == null) {
			return;
		}

		if (table[slot].key.equals(key)) {
			table[slot] = table[slot].next;
			size--;
			modificationCount++;
			return;
		}

		while (current.next != null) {
			if (current.next.key.equals(key)) {
				current.next = current.next.next;
				size--;
				modificationCount++;
				break;
			}
			current = current.next;
		}

	}

	/**
	 * Checks whether table is empty.
	 * 
	 * @return {@code true} if table is empty, otherwise {@code false}.
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * Clears entries from table but keeps table's capacity.
	 */
	@SuppressWarnings("unchecked")
	public void clear() {
		table = (TableEntry<K, V>[]) new TableEntry[table.length];
		size = 0;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		int counter = 0;
		sb.append("[");
		for (TableEntry<K, V> entry : table) {
			while (entry != null) {
				sb.append(entry.toString());
				counter++;
				if (counter < size) {
					sb.append(", ");
				}
				entry = entry.next;
			}
		}
		sb.append("]");
		return sb.toString();
	}

	@Override
	public Iterator<TableEntry<K, V>> iterator() {
		return new IteratorImpl();
	}

	/**
	 * Helper method which helps with puting an entry into table.
	 * 
	 * @see {@link #put(Object, Object)} for documentation.
	 * @param key
	 * @param value
	 * @param table a reference to a table where entries will be stored.
	 */
	private void putInTable(K key, V value, TableEntry<K, V>[] table) {
		int slot = slot(key, table);
		if (table[slot] == null) {
			table[slot] = new TableEntry<>(key, value, null);
			modificationCount++;
			size++;
		} else {
			TableEntry<K, V> current = table[slot];
			while (current.next != null && !current.key.equals(key)) {
				current = current.next;
			}
			if (current.key.equals(key)) {
				current.setValue(value);
				return;
			}
			
			current.next = new TableEntry<>(key, value, null);
			modificationCount++;
			size++;
		}
	}

	/**
	 * Helper method that determines whether given <code>action</code> does true
	 * test or false test. Here <code>action</code> can test if method should check
	 * key values or entry values as determined by user. It is used for delegation
	 * in methods {@link #containsKey(Object)} and {@link #containsValue(Object)}.
	 * Method internally uses method {@link #getRigthEntry(Tester)}.
	 * 
	 * @see {@link Tester#test(Object)}.
	 * @see {@link #getRigthEntry(Tester)}.
	 * @param action a {@link Tester} type variable which represents the right test
	 *               that needs to be done.
	 * @return {@code true} if {@code action.test()} is {@code true}, otherwise
	 *         {@code false}.
	 */
	private boolean contains(Tester<TableEntry<K, V>> action) {
		@SuppressWarnings("unchecked")
		TableEntry<K, V> current = (TableEntry<K, V>) getRigthEntry(action);
		return current == null ? false : true;
	}

	/**
	 * Helper method that returns entry which satisfies <code>action.test()</code>
	 * method. Here <code>action</code> can test if method should check key values
	 * or entry values as determined by user.
	 * 
	 * @see {@link Tester#test(Object)}.
	 * @param action a {@link Tester} type variable which represents the right test
	 *               that needs to be done.
	 * @return entry that satisfies given {@code action}, otherwise {@code null} is
	 *         returned.
	 */
	private Object getRigthEntry(Tester<TableEntry<K, V>> action) {
		for (TableEntry<K, V> entry : table) {
			while (entry != null) {
				if (action.test(entry)) {
					return entry;
				}
				entry = entry.next;
			}
		}
		return null;
	}

	/**
	 * Checks whether number of stored elements are using around <code>75%</code> of
	 * internal's table capacity.
	 * 
	 * @return {@code true} if it does, otherwise {@code false}.
	 */
	private boolean checkForSize() {
		return size >= table.length * AVAILABILITY;
	}

	/**
	 * Doubles table's capacity.
	 */
	private void resizeTable() {
		@SuppressWarnings("unchecked")
		TableEntry<K, V>[] newTable = new TableEntry[table.length * 2];
		size = 0;
		for (TableEntry<K, V> entry : table) {
			while (entry != null) {
				putInTable(entry.key, entry.value, newTable);
				entry = entry.next;
			}
		}
		table = newTable;
	}

	/**
	 * Calculates where entry with given <code>key</code> should be stored.
	 * 
	 * @param key the input argument for key value.
	 * @return slot where entry should be stored.
	 */
	private int slot(Object key, TableEntry<K, V>[] table) {
		Objects.requireNonNull(key);
		return Math.abs(key.hashCode() % table.length);
	}

	/**
	 * Calculates the right power of 2. For example if the given
	 * <code>initialCapacity</code> is <code>30</code> then new capacity should be
	 * the first power of 2 that fits and that would be 32 etc.
	 * 
	 * @param initialCapacity the input argument for table capacity.
	 * @return power of 2 capacity.
	 */
	private int getRigthCapacity(int initialCapacity) {
		int expo = (int) Math.ceil(Math.log10(initialCapacity) / Math.log10(2));
		return (int) Math.pow(2, expo);
	}

	/**
	 * Private class that represents an implementation of {@link Iterator}.
	 * 
	 * @author dbrcina
	 * @version 1.0
	 *
	 */
	private class IteratorImpl implements Iterator<TableEntry<K, V>> {

		/**
		 * Current array index.
		 */
		private int arrayIndex;

		/**
		 * Current index of last element.
		 */
		private int currentIndex;

		/**
		 * Keeps track whether initial collection was changed while iterator was
		 * working.
		 */
		private long savedModificationCount = modificationCount;

		/**
		 * Reference to current entry.
		 */
		private TableEntry<K, V> currentElement;

		private boolean isCurrentElementRemoved;

		/**
		 * {@inheritDoc}
		 * 
		 * @throws ConcurrentModificationException if collection was modified after
		 *                                         methods {@link #next()} or
		 *                                         {@link #remove()} were called.
		 */
		@Override
		public boolean hasNext() {
			checkForModifications();
			return currentIndex < size;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @throws NoSuchElementException          if there are no more elements and
		 *                                         this method was called.
		 * @throws ConcurrentModificationException if collection was modified after
		 *                                         methods {@link #next()} or
		 *                                         {@link #remove()} were called.
		 */
		@Override
		public TableEntry<K, V> next() {
			checkForModifications();

			if (!hasNext()) {
				throw new NoSuchElementException("Nema više elemenata za pročitati!");
			}

			isCurrentElementRemoved = false;

			// check whether slot is empty..
			if (currentElement == null) {
				return findNonEmptySlot();
			}

			// go through linked-list if it exists...
			if (currentElement.next != null) {
				currentElement = currentElement.next;
				currentIndex++;
				return currentElement;
			} else {
				currentElement = table[++arrayIndex];
				return findNonEmptySlot();
			}
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @throws IllegalStateException           if user tries to remove the same
		 *                                         elements more than once.
		 * @throws ConcurrentModificationException if collection was modified after
		 *                                         methods {@link #next()} or
		 *                                         {@link #remove()} were called.
		 */
		@Override
		public void remove() {
			if (isCurrentElementRemoved) {
				throw new IllegalStateException("Uklanjanje istog elementa dva puta!");
			}

			checkForModifications();

			SimpleHashtable.this.remove(currentElement.key);
			savedModificationCount++;
			currentIndex--;
			isCurrentElementRemoved = true;
			currentElement = null;
		}

		/**
		 * Helper method that goes through table and finds the first non empty slot and
		 * returns the head of the linked-list.
		 * 
		 * @return the first entry from found slot.
		 */
		private TableEntry<K, V> findNonEmptySlot() {
			while (currentElement == null) {
				currentElement = table[arrayIndex];
				if (currentElement == null) {
					arrayIndex++;
				}
			}
			// first element is found...
			currentIndex++;
			return currentElement;
		}

		/**
		 * Checks whether given collection had any modifications since calling
		 * {@link #next()} method.
		 * 
		 * @throws ConcurrentModificationException if the modifications happened.
		 */
		private void checkForModifications() {
			if (savedModificationCount != modificationCount) {
				throw new ConcurrentModificationException("Kolekcija je bila promijenja za vrijeme rada s njom!");
			}
		}
	}
}
