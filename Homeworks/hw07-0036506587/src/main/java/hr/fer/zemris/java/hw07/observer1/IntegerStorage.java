package hr.fer.zemris.java.hw07.observer1;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class represents the <i>Subject</i> from <i>Observer design pattern</i>.
 * 
 * @author dbrcina
 *
 */
public class IntegerStorage {

	/**
	 * Variable representing current storage value.
	 */
	private int value;

	/**
	 * Storage where instances of {@link IntegerStorageObserver} are stored.
	 */
	private List<IntegerStorageObserver> observers = new ArrayList<>();

	/**
	 * Constructor which takes only one argument <code>initialValue</code> and
	 * initializes {@link #value}.
	 * 
	 * @param initialValue initial value.
	 */
	public IntegerStorage(int initialValue) {
		this.value = initialValue;
	}

	/**
	 * Adds the given <code>observer</code> into storage only if
	 * <code>observer</code> does not already exist.
	 * 
	 * @param observer observer.
	 * @throws NullPointerException if given {@code observer} is {@code null}.
	 */
	public void addObserver(IntegerStorageObserver observer) {
		if (!observers.contains(Objects.requireNonNull(observer, "Given observer is null!"))) {
			observers.add(observer);
		} else {
			System.out.println("Given observer already exist in storage!");
		}
	}

	/**
	 * Removes the given <code>observer</code> from storage only if
	 * <code>observer</code> exist.
	 * 
	 * @param observer observer.
	 * @throws NullPointerException if given {@code observer} is {@code null}.
	 */
	public void removeObserver(IntegerStorageObserver observer) {
		boolean isRemoved = observers.remove(Objects.requireNonNull(observer, "Given observer is null!"));
		if (!isRemoved) {
			System.out.println("Given observer does not exist in storage!");
		}
	}

	/**
	 * Deletes all observers from storage.
	 */
	public void clearObservers() {
		observers.clear();
	}

	/**
	 * Getter for {@link #value}.
	 * 
	 * @return {@link #value}.
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Setter for {@link #value}. Current value is updated only if provided
	 * <code>value</code> is different than the current value. If update happens,
	 * every registered observer is notified.
	 * 
	 * @param value value.
	 */
	public void setValue(int value) {
		if (this.value != value) {
			this.value = value;
			if (observers != null) {
				IntegerStorageObserver[] obs = observers.toArray(new IntegerStorageObserver[observers.size()]);
				int originalSize = obs.length;
				for (int i = 0; i < originalSize; i++) {
					obs[i].valueChanged(this);
				}
			}
		}
	}
}
