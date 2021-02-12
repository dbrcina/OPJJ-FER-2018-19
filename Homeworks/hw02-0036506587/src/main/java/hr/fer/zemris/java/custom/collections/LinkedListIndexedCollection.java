package hr.fer.zemris.java.custom.collections;

/**
 * An implementation of linked list-backed collection of objects denoted as
 * LinkedListIndexedCollection which extends class Collection.General contract
 * of this collection is: duplicate elements are allowed (each of those element
 * will be held in different list node); storage of null references is not
 * allowed.
 * 
 * @author Darijo Brčina
 * @version 1.0
 *
 */
public class LinkedListIndexedCollection extends Collection {

	/**
	 * Current size of collection (number of elements actually stored; number of
	 * nodes in list),
	 */
	private int size;
	/**
	 * Reference to the first node of the linked list.
	 */
	private ListNode first;
	/**
	 * Reference to the last node of the linked list.
	 */
	private ListNode last;

	/**
	 * The default constructor that creates an empty collection with {@link #first}
	 * = {@link #last} = null.
	 */
	public LinkedListIndexedCollection() {
		first = last = null;
		size = 0;
	}

	/**
	 * A second constructor which copies elements from given collection into this
	 * newly constructed collection only if the given collection is a non-null
	 * reference or else throws appropriate exception (NullPointerException).
	 * 
	 * @param other collection from where are elements copied to this collection
	 * @throws NullPointerException if the given collection is a null reference.
	 */
	public LinkedListIndexedCollection(Collection other) {
		if (other == null) {
			throw new NullPointerException("Kolekcija je null!");
		}
		addAll(other);
	}

	/**
	 * 
	 * In this implementation method size returns number of nodes stored in this
	 * collection.
	 */
	@Override
	public int size() {
		return size;
	}

	/**
	 * A method that checks whether the given value exist in the collection, as
	 * determined by equals method. In this implementation the method returns
	 * representative value, not only false.
	 */
	@Override
	public boolean contains(Object value) {
		ListNode temp = first;
		while (temp != null) {
			if (temp.value.equals(value)) {
				return true;
			}
			temp = temp.next;
		}
		return false;
	}

	/**
	 * Adds the given object into this collection at the end of collection; newly
	 * added element becomes the element at the biggest index. The method should
	 * refuse to add null as element by throwing the appropriate exception
	 * (NullPointerException). The average complexity of this method is O(1).
	 * 
	 * @throws NullPointerException if the given {@link #value} is null reference.
	 */
	@Override
	public void add(Object value) {
		if (value == null) {
			throw new NullPointerException("Vrijednost ne smije biti null!");
		}
		// here we create a new ListNode and set it's value to the given value and both
		// references to previous and next ListNode are set to null by default.
		ListNode newNode = new ListNode();
		newNode.value = value;

		if (first == null) {
			first = last = newNode;
		} else if (last.previous == null) {
			first.next = newNode;
			newNode.previous = first;
		} else {
			newNode.previous = last;
			// newNode.next is always null because the element is added as last one
		}
		last.next = newNode;
		last = newNode;
		size++;
	}

	/**
	 * Returns the object value that is stored in linked list at position {@link #index}.
	 * Valid indexes are 0 to size-1. If {@link #index} is invalid, the
	 * implementation should throw the appropriate exception
	 * (IndexOutOfBoundsException). The average complexity of this method is lower
	 * than n/2 + 1.
	 * 
	 * @param index position from where an element should be returned.
	 * @return the object that is stored in linked list at position {@link #index}.
	 * @throws IndexOutOfBoundsException if the index is invalid.
	 */
	public Object get(int index) {
		ListNode node = getNode(index);
		return node.value;
	}

	/**
	 * Returns the node that is stored in linked list at position {@link #index}.
	 * Valid indexes are 0 to size-1. If {@link #index} is invalid, the
	 * implementation should throw the appropriate exception
	 * (IndexOutOfBoundsException). The average complexity of this method is lower
	 * than n/2 + 1.
	 * 
	 * @param index position from where a node should be returned.
	 * @return the node that is stored in linked list at position {@link #index}.
	 * @throws IndexOutOfBoundsException if the index is invalid.
	 */
	private ListNode getNode(int index) {
		if (index < 0 || index > size - 1) {
			throw new IndexOutOfBoundsException("Invalidan index!");
		}
		ListNode node = null;
		if (index > size / 2) {
			ListNode temp = last;
			for (int i = size - 1; i >= index; i--) {
				if (i == index) {
					node = temp;
				}
				temp = temp.previous;
			}
		} else {
			ListNode temp = first;
			for (int i = 0; i <= index; i++) {
				if (i == index) {
					node = temp;
				}
				temp = temp.next;
			}
		}
		return node;
	}

	/**
	 * {@inheritDoc} Collection "forgets" about current linked list.
	 */
	@Override
	public void clear() {
		first = last = null;
		size = 0;
	}

	/**
	 * Inserts (does not overwrite) the given {@code value} at the given
	 * {@code position} in linked-list. Elements starting from this position are
	 * shifted one position. The legal positions are 0 to size. If position is
	 * invalid, an appropriate exception should be thrown. Except the difference in
	 * position at witch the given object will be inserted, everything else should
	 * be in conformance with the method add. The average complexity of this method
	 * is O(n/2 + 1);
	 * 
	 * @param value    an Object value that needs to be added in linked-list.
	 * @param position a position where the given {@code value} needs to be added.
	 * @throws IndexOutOfBoundsException if the {@codeposition} is invalid.
	 */
	public void insert(Object value, int position) {
		if (position < 0 || position > size) {
			throw new IndexOutOfBoundsException("Invalidna pozicija");
		}
		ListNode newNode = new ListNode();
		newNode.value = value; // references previous and next are by default null.

		if (position == 0) {
			atFirst(newNode);
		} else if (position == size) {
			atLast(newNode);
		} else {
			ListNode temp = position > size / 2 ? last : first;
			if (temp == first) {
				fromBegining(newNode, position);
			} else {
				fromEnd(newNode, position);
			}
		}
		size++;

	}

	/**
	 * A private method that inserts the given element at the begining.
	 * 
	 * @param newNode a new node that needs to be inserted.
	 */
	private void atFirst(ListNode newNode) {
		newNode.next = first;
		first.previous = newNode;
		first = newNode;
	}

	/**
	 * A private method that inserts the given element at the end.
	 * 
	 * @param newNode a new node that needs to be inserted.
	 */
	private void atLast(ListNode newNode) {
		last.next = newNode;
		newNode.previous = last;
		last = newNode;
	}

	/**
	 * Go from the begining of the linked-list and insert the {@code newNode},
	 * because {@code position} is < {@link #size} / 2.
	 * 
	 * @param newNode  a node that needs to be inserted in this collection.
	 * @param position a position where the given node needs to be inserted.
	 */
	private void fromBegining(ListNode newNode, int position) {
		ListNode temp = first.next;
		for (int i = 1; i <= position; i++) {
			if (i == position) {
				changeReferences(newNode, temp);
				break;
			}
			temp = temp.next;
		}
	}

	/**
	 * Go from the end of the linked-list and insert the {@code newNode}, because
	 * {@code position} is > {@link #size} / 2.
	 * 
	 * @param newNode  a node that needs to be inserted in this collection.
	 * @param position a position where the given node needs to be inserted.
	 */
	private void fromEnd(ListNode newNode, int position) {
		ListNode temp = last.previous;
		for (int i = size - 1; i >= position; i--) {
			if (i == position) {
				changeReferences(newNode, temp);
				break;
			}
			temp = temp.previous;
		}
	}

	/**
	 * A method which changes internally reference when new node is added.
	 * 
	 * @param newNode a node that needs to be inserted in this collection.
	 * @param temp    a temporary node that needs to be shifted.
	 */
	private void changeReferences(ListNode newNode, ListNode temp) {
		newNode.next = temp;
		newNode.previous = temp.previous;
		temp.previous.next = newNode;
		temp.previous = newNode;
	}

	/**
	 * A method that searches the collection and returns the {@code index} of the
	 * first occurrence of the given {@code value} or -1 if the value is not found.
	 * null is valid argument. The equality should be determined using the equals
	 * method. The average complexity of this method is O(n).
	 * 
	 * @param value an element whose index should be returned.
	 * @return returns the {@code index} of the first occurrence of the given
	 *         {@code value} or -1 if the value is not found.
	 */
	public int indexOf(Object value) {
		ListNode temp = first;
		for (int i = 0; i < size; i++) {
			if (temp.value.equals(value)) {
				return i;
			}
			temp = temp.next;
		}
		return -1;
	}

	/**
	 * Removes element at specified {@code index} from collection. Element that was
	 * previously at location index+1 after this operation is on location index,
	 * etc. Legal indexes are 0 to size-1. In case of invalid index, appropriate
	 * exception is thrown.
	 * 
	 * @param index a location from where an node should be removed.
	 * @throws IndexOutOfBoundsException if the {@code index} is invalid.
	 */
	public void remove(int index) {
		if (index < 0 || index > size - 1) {
			throw new IndexOutOfBoundsException("Invalidan index");
		}
		ListNode node = getNode(index);
		if (index == 0) {
			first = first.next;
			first.previous = null;
		} else if (index == size - 1) {
			last = last.previous;
			last.next = null;
		} else {
			node.next.previous = node.previous;
			node.previous.next = node.next;
		}
		node = null;
		size--;
	}

	/**
	 * A method that removes the given {@code value} from the collection only if it
	 * exists.
	 */
	@Override
	public boolean remove(Object value) {
		int index = indexOf(value);
		if (index > -1) {
			remove(index);
			return true;
		}
		return false;
	}

	@Override
	public void forEach(Processor processor) {
		ListNode temp = first;
		while (temp != null) {
			processor.proces(temp.value);
			temp = temp.next;
		}
	}

	/**
	 * A method that allocates new array with size equals to the size of this
	 * collections and fills it with collection content. This method never returns null.
	 */
	@Override
	public Object[] toArray() {
		Object[] elements = new Object[size];
		for (int i = 0; i < size; i++) {
			elements[i] = get(i);
		}
		return elements;
	}

	/**
	 * A structure that represents node in this collection. It has two references:
	 * one for next node and one for previous node, also has one reference for an
	 * Object value.
	 * 
	 * @author Darijo Brčina
	 * @version 1.0
	 *
	 */
	private static class ListNode {
		/**
		 * A reference to the previous node of this collection.
		 */
		private ListNode previous;
		/**
		 * A reference to the next node of this collection.
		 */
		private ListNode next;
		/**
		 * A reference to node's value.
		 */
		private Object value;
	}
}
