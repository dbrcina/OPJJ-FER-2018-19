package hr.fer.zemris.java.pred04;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Kvartet<T> implements Iterable<T> {

	private T[] elems;

	@SuppressWarnings("unchecked")
	public Kvartet(T elem1, T elem2, T elem3, T elem4) {
		elems = (T[]) new Object[4];
		elems[0] = elem1;
		elems[1] = elem2;
		elems[2] = elem3;
		elems[3] = elem4;
	}

	public T getElementAt(int index) {
		return elems[index];
	}

	@Override
	public String toString() {
		return Arrays.toString(elems);
	}

	public Iterator<T> iterator() {
		return new IteratorImpl<>(this);
	}

	public Iterator<T> dajIterator() {
		return new IteratorImpl2();
	}

	private static class IteratorImpl<E> implements Iterator<E> {

		private Kvartet<E> kolekcija;
		private int nextIndex;

		public IteratorImpl(Kvartet<E> kolekcija) {
			super();
			this.kolekcija = kolekcija;
		}

		@Override
		public boolean hasNext() {
			return nextIndex < kolekcija.elems.length;
		}

		@Override
		public E next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			return kolekcija.elems[nextIndex++];
		}

	}

	private class IteratorImpl2 implements Iterator<T> {

		private int nextIndex;

		@Override
		public boolean hasNext() {
			return nextIndex < Kvartet.this.elems.length;
		}

		@Override
		public T next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			return Kvartet.this.elems[nextIndex++];
		}

	}

	private class IteratorImpl3 implements Iterator<T> {

		private int nextIndex;

		@Override
		public boolean hasNext() {
			return nextIndex < elems.length;
		}

		@Override
		public T next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			return elems[nextIndex++];
		}

	}
	
	public class IteratorImpl4 implements Iterator<T> {

		private int nextIndex;

		@Override
		public boolean hasNext() {
			return nextIndex < elems.length;
		}

		@Override
		public T next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			return elems[nextIndex++];
		}

	}
}
