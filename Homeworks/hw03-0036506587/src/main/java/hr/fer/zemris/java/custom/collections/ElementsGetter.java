package hr.fer.zemris.java.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * Interface that provides a contract for user to fulfill when working with
 * {@link ElementsGetter} type objects. These objects are used to retrieve
 * elements from a collection on user's request using methods:
 * {@link #hasNextElement()} and {@link #getNextElement()}.
 * 
 * @author Darijo Brčina
 * @version 1.0
 *
 */
public interface ElementsGetter {

	/**
	 * Checks whether a collection observerd through {@link ElementsGetter}
	 * interface has next element that needs to be used for something like geting
	 * it's value etc.
	 * 
	 * @return true if a collection has next element, otherwise false.
	 */
	boolean hasNextElement();

	/**
	 * Gets the next element only if there are more elements in the collection
	 * determined by {@link #hasNextElement()} method. If collection was modified
	 * after method {@link Collection#createElementsGetter()} was called, an
	 * appropriate exception is thrown (ConcurrentModificationException). If there
	 * are no more elements, an appropriate exception is thrown
	 * (NoSuchElementException).
	 * 
	 * @return an Object from a collection.
	 * @throws ConcurrentModificationException if collection was modified after
	 *                                         method
	 *                                         {@link Collection#createElementsGetter()}
	 *                                         was called.
	 * @throws NoSuchElementException          if there are no more elements to be
	 *                                         returned.
	 */
	Object getNextElement();

	/**
	 * Default method that for remaining elements in collection calls
	 * {@code p.process()}. If the given {@code p} is {@code null}, an appropriate
	 * exception is thrown (NullPointerException).
	 * @see hr.fer.zemris.java.custom.collections.Processor#process(Object)
	 * @param p Processor type variable
	 * @throws NullPointerException if the {@code p} is {@code null}.
	 */
	default void processRemaining(Processor p) {
		while (hasNextElement()) {
			p.process(getNextElement());
		}
	}
}
