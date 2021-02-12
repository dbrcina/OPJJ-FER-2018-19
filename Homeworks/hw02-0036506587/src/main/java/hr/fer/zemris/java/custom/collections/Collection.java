package hr.fer.zemris.java.custom.collections;

/**
 * A general agreement between every class that extends/implements this contract
 * about some generic methods that are used to manipulate with some crucial
 * information about the given collection.
 * 
 * @author Darijo Brčina
 * @version 1.0
 *
 */
public class Collection {

	/**
	 * A default collection constructor.
	 */
	protected Collection() {
	}

	/**
	 * A method that checks whether there are any objects in the collection.
	 * 
	 * @return true if collection contains no objects and false otherwise.
	 */
	public boolean isEmpty() {
		return size() == 0;
	}

	/**
	 * A method that calculates the size of the collection. In this implementation
	 * size is returned as 0.
	 * 
	 * @return the number of currently stored objects in this collection.
	 */
	public int size() {
		return 0;
	}

	/**
	 * A method that adds the given object into this collection.
	 * 
	 * @param value an Object type value to be added into this collection.
	 */
	public void add(Object value) {
	}

	/**
	 * A method that checks whether the given value exist in the collection, as
	 * determined by equals method. In this implementation false is always returned.
	 * 
	 * @param value an Object value to be checked, value can be null.
	 * @return true only if the collection contains given value.
	 */
	public boolean contains(Object value) {
		return false;
	}

	/**
	 * A method that removes the given value from the collection only if the value
	 * exists. In this implementation false is always returned.
	 * 
	 * @param value an Object value to be removed.
	 * @return true only if the collection contains given value as determined by
	 *         method {@link #equals(Object)} and removes one occurrence of it.
	 */
	public boolean remove(Object value) {
		return false;
	}

	/**
	 * A method that allocates new array with size equals to the size of this
	 * collections and fills it with collection content. This method never returns
	 * null. In this implementation it throws {@link UnsupportedOperationException}.
	 * 
	 * @return new array filled with collection content.
	 * @throws UnsupportedOperationException
	 */
	public Object[] toArray() {
		throw new UnsupportedOperationException();
	}

	/**
	 * A method that calls processor.process(value) for each element of this
	 * collection.
	 * 
	 * @see Processor#process()
	 * @param processor a Processor type variable which is used to perform process()
	 *                  method.
	 */
	public void forEach(Processor processor) {
	}

	/**
	 * A method that adds into the current collection all elements from the given
	 * collection. This other collection remains unchanged!
	 * 
	 * @param other an collection from where elemenets are copied.
	 */
	public void addAll(Collection other) {
		class Proc extends Processor {
			@Override
			public void proces(Object value) {
				add(value);
			}
		}
		other.forEach(new Proc());
	}

	/**
	 * A method that removes all elements from this collection.
	 */
	public void clear() {
	}

}
