package hr.fer.zemris.java.custom.collections;

import java.util.Objects;

/**
 * A general agreement between every class that extends/implements this contract
 * about some generic methods that are used to manipulate with some crucial
 * information about the given collection.
 * 
 * @author Darijo Brčina
 * @version 1.0
 *
 */
public interface Collection {

	/**
	 * A method that checks whether there are any objects in the collection.
	 * 
	 * @return true if collection contains no objects and false otherwise.
	 */
	default boolean isEmpty() {
		return size() == 0;
	}

	/**
	 * A method that returns the size of a collection.
	 * 
	 * @return the number of currently stored objects in this collection.
	 */
	int size();

	/**
	 * A method that adds the given object into this collection.
	 * 
	 * @param value an Object type value to be added into this collection.
	 */
	void add(Object value);

	/**
	 * A method that checks whether the given {@code value} exist in the collection,
	 * as determined by {@link Object#equals(Object)} method.
	 * 
	 * @param value an Object value to be checked, {@code value} can be
	 *              {@code null}.
	 * @return true only if the collection contains given {@code value}.
	 */
	boolean contains(Object value);

	/**
	 * A method that removes the given {@code value} from the collection only if the
	 * {@code value} exists.
	 * 
	 * @param value an Object value to be removed.
	 * @return true only if the collection contains given value as determined by
	 *         method {@link #equals(Object)} and removes one occurrence of it.
	 */
	boolean remove(Object value);

	/**
	 * A method that allocates new array with size equals to the size of this
	 * collections and fills it with collection content. This method never returns
	 * {@code null}.
	 * 
	 * @return new array filled with collection content.
	 */
	Object[] toArray();

	/**
	 * A method that calls processor.process(value) for each element of this
	 * collection. {@code processor} cannot be {@code null}.
	 * 
	 * @see hr.fer.zemris.java.custom.collections.Processor#process(Object)
	 * @param processor a Processor type variable which is used to perform process()
	 *                  method.
	 * @throws NullPointerException if the {@code processor} is {@code null}.
	 */
	default void forEach(Processor processor) {
		Objects.requireNonNull(processor);
		ElementsGetter getter = createElementsGetter();
		while (getter.hasNextElement()) {
			processor.process(getter.getNextElement());
		}
	}

	/**
	 * A method that adds into the current collection all elements from the given
	 * collection {@code other}. This {@code other} collection remains unchanged!
	 * 
	 * @param other an collection from where elemenets are copied.
	 * @throws NullPointerException if {@code other} collection is {@code null}.
	 */
	default void addAll(Collection other) {
		Objects.requireNonNull(other);
		other.forEach(value -> add(value));
	}

	/**
	 * A method that removes all elements from this collection.
	 */
	void clear();

	/**
	 * Creates an object which is observed through {@link ElementsGetter} interface
	 * and it is used for going through collection using contract methods
	 * {@link ElementsGetter#hasNextElement()} and
	 * {@link ElementsGetter#getNextElement()}.
	 * 
	 * @see ElementsGetter
	 * @return an object which is observed through {@link ElementsGetter} interface.
	 */
	ElementsGetter createElementsGetter();

	/**
	 * Adds every element from collection {@code col} to current collection at the
	 * end only if the element is accepted by the {@code tester}.
	 * 
	 * @param col    a collection from where the elements are copied.
	 * @param tester a variable for using {@link Tester#test(Object)} method.
	 * @throws NullPointerException if one of the arguments are {@code null}.
	 */
	default void addAllSatisfying(Collection col, Tester tester) {
		Objects.requireNonNull(col);
		Objects.requireNonNull(tester);
		ElementsGetter getter = col.createElementsGetter();
		while (getter.hasNextElement()) {
			Object value = getter.getNextElement();
			if (tester.test(value)) {
				add(value);
			}
		}
	}
}
