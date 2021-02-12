package hr.fer.zemris.java.custom.collections;

import java.util.Objects;

/**
 * A simple implementation of dictionary-like collection where elements are
 * stored by unique key. In that case, key CANNOT BE <code>null</code> and
 * element's value can.
 * 
 * @author dbrcina
 * @version 1.0
 *
 * @param <K> the type that represents key
 * @param <V> the type that represents value
 */
public class Dictionary<K, V> {

	/**
	 * Private structure that represents connection between one key and one value.
	 * Key cannot be <code>null</code>.
	 * 
	 * @author dbrcina
	 * @version 1.0
	 *
	 * @param <K> the type that represents key
	 * @param <V> the type that represents value
	 */
	private static class Entry<K, V> {

		/**
		 * Entry's key value.
		 */
		private K key;
		/**
		 * Entry's value.
		 */
		private V value;

		/**
		 * A constructor for one entry.
		 * 
		 * @param key   input argument for key value.
		 * @param value input argument for entry value.
		 * @throws NullPointerException if the given {@code key} is {@code null}.
		 */
		public Entry(K key, V value) {
			this.key = Objects.requireNonNull(key, "Ključ ne smije biti null!");
			this.value = value;
		}

		/**
		 * Getter for key value.
		 * 
		 * @return key value.
		 */
		@SuppressWarnings("unused")
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
			if (!(obj instanceof Entry))
				return false;
			@SuppressWarnings("unchecked")
			Entry<K, V> other = (Entry<K, V>) obj;
			return Objects.equals(key, other.key);
		}

	}

	/**
	 * Dictionary's internal array storage.
	 */
	private ArrayIndexedCollection<Entry<K, V>> elements;
	{
		elements = new ArrayIndexedCollection<>(); // initialization block
	}

	/**
	 * Checks whether dictionary-like collection is empty.
	 * 
	 * @return {@code true} if is empty, otherwise {@code false}.
	 */
	public boolean isEmpty() {
		return elements.isEmpty();
	}

	/**
	 * @return the number of {@link Entry}'s stored {@link Entry}'s in this
	 *         colllection.
	 */
	public int size() {
		return elements.size();
	}

	/**
	 * Clears all elements in this collection.
	 */
	public void clear() {
		elements.clear();
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
		Entry<K, V> entry = new Entry<>(key, value);
		int index = elements.indexOf(entry);
		if (index == -1) {
			elements.add(entry);
		} else {
			elements.get(index).setValue(value);
		}
	}

	/**
	 * Fetches value from this collection as determined by given <code>key</code>.
	 * If the given <code>key</code> does not exists, <code>null</code> is returned,
	 * also works when given <code>key</code> exists but value is <code>null</code>.
	 * 
	 * @param key input argument for key value.
	 * @return value as determined by key value
	 */
	public V get(Object key) {
		if (key == null) {
			return null;
		}
		int index = elements.indexOf(new Entry<>(key, null));
		return index == -1 ? null : elements.get(index).getValue();
	}
}