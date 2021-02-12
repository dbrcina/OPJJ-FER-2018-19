package hr.fer.zemris.java.hw07.demo2;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * An implementation of {@link Iterable} collection which is used for working
 * with prime numbers.
 * 
 * @author dbrcina
 *
 */
public class PrimesCollection implements Iterable<Integer> {

	/**
	 * Number of primes that needs to be found.
	 */
	private int numberOfPrimes;

	/**
	 * Constructor that takes only one argument, <code>numberOfPrimes</code>.
	 * 
	 * @param numberOfPrimes number of primes.
	 */
	public PrimesCollection(int numberOfPrimes) {
		this.numberOfPrimes = numberOfPrimes;
	}

	@Override
	public Iterator<Integer> iterator() {
		return new PrimesIterator(numberOfPrimes);
	}

	/**
	 * An implementation of {@link Iterator} which iterates for
	 * {@link #numberOfPrimes} times and returns next prime number.
	 * 
	 * @author dbrcina
	 *
	 */
	private class PrimesIterator implements Iterator<Integer> {

		/**
		 * Number of primes that needs to be found.
		 */
		private int numberOfPrimes;

		/**
		 * Variable representing current prime.
		 */
		private int currentPrime = 1;

		/**
		 * Constructor that takes only one argument, <code>numberOfPrimes</code>.
		 * 
		 * @param numberOfPrimes number of primes.
		 */
		public PrimesIterator(int numberOfPrimes) {
			this.numberOfPrimes = numberOfPrimes;
		}

		@Override
		public boolean hasNext() {
			return numberOfPrimes > 0;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @throws NoSuchElementException if there are no more primes to be returned.
		 */
		@Override
		public Integer next() {
			if (!hasNext()) {
				throw new NoSuchElementException("There are no more primes to be read!");
			}

			for (int i = currentPrime + 1;; i++) {
				if (isPrime(i)) {
					currentPrime = i;
					numberOfPrimes--;
					return currentPrime;
				}
			}
		}

		/**
		 * Helper method which checks whether given <code>number</code> is a prime
		 * number.
		 * 
		 * @param number number.
		 * @return {@code true} if {@code number} is prime number, otherwise
		 *         {@code false}.
		 */
		private boolean isPrime(int number) {
			for (int i = 2; i <= number / 2; i++) {
				if (number % i == 0) {
					return false;
				}
			}
			return true;
		}

	}
}
