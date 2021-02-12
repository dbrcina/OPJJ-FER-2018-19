package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class LinkedListIndexedCollectionTest {

	@Test
	public void defaultConstructor() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		assertEquals(0, col.size());
		assertTrue(col.isEmpty());
	}

	@Test
	public void constructorWithOtherColl() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		col.add("Štefica");
		col.add(Integer.valueOf(30));
		col.add(3.14);

		LinkedListIndexedCollection newCol = new LinkedListIndexedCollection(col);
		assertEquals(3, newCol.size());
		assertEquals(30, newCol.get(1));
	}

	@Test
	public void testIsEmpy() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		assertTrue(col.isEmpty());
		col.add("Štefica");
		assertFalse(col.isEmpty());
	}

	@Test
	public void testSize() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		assertEquals(0, col.size());
		col.add("Štefica");
		col.add(Integer.valueOf(30));
		col.add(3.14);
		assertEquals(3, col.size());
	}
	
	@Test
	public void testForAdd() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		col.add("Štefica");
		col.add(Integer.valueOf(30));
		col.add(3.14);
		assertEquals("Štefica", col.get(0));
		assertEquals(3, col.size());
	}

	@Test
	public void testToArray() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		col.add("Štefica");
		col.add(Integer.valueOf(30));
		col.add(3.14);
		Object[] array = col.toArray();
		assertEquals(3, array.length);
		assertEquals(3.14, array[2]);
		System.out.println("Test for toArray():");
		for (Object o : array) {
			System.out.println(o);
		}
		System.out.println("----------------");
	}
	
	@Test
	public void testForEach() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		col.add("Štefica");
		col.add(Integer.valueOf(30));
		col.add(3.14);
		System.out.println("Test for forEach():");
		col.forEach(new Processor() {
			@Override
			public void proces(Object value) {
				System.out.println(value);
			}
		});
		System.out.println("----------------");
	}
	
	@Test
	public void testAddAll() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		col.add("Štefica");
		col.add(Integer.valueOf(30));
		col.add(3.14);
		col.add("Zagreb");
		LinkedListIndexedCollection newCol = new LinkedListIndexedCollection();
		newCol.addAll(col);
		assertEquals(4, newCol.size());
		assertEquals(3.14, newCol.get(2));
		System.out.println("Test for addAll():");
		newCol.forEach(new Processor() {
			@Override
			public void proces(Object value) {
				System.out.println(value);
			}
		});
	}
	
	@Test
	public void testClear() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		col.add("Štefica");
		col.add(Integer.valueOf(30));
		col.add(3.14);
		col.clear();
		assertEquals(0, col.size());
	}
	
	@Test
	public void testForGet() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		col.add("Štefica");
		col.add(Integer.valueOf(30));
		col.add(3.14);

		// check for invalid index
		assertThrows(IndexOutOfBoundsException.class, () -> col.get(-1));
		assertThrows(IndexOutOfBoundsException.class, () -> col.get(3));

		// check for valid index
		assertEquals("Štefica", col.get(0));
		assertEquals(30, col.get(1));
		assertEquals(3.14, col.get(2));
	}

	@Test
	public void testForInsert() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		col.add("Štefica");
		col.add(Integer.valueOf(30));
		col.add(3.14);

		// check for invalid index
		assertThrows(IndexOutOfBoundsException.class, () -> col.insert("bla", -1));
		assertThrows(IndexOutOfBoundsException.class, () -> col.insert("bla", 4));

		// check for valid index
		col.insert("bla", 0); // insert at the begining.
		col.insert("Zagreb", 4); // insert at the end.
		col.insert(Integer.valueOf(100), 1);
		assertEquals("Zagreb", col.get(5));
	}

	@Test
	public void testForIndex() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		col.add("Štefica");
		col.add(Integer.valueOf(30));
		col.add(3.14);
		col.insert("bla", 0); // insert at the begining.
		assertEquals(-1, col.indexOf("Zagreb")); // returns -1 because Zagreb does not exist in collection.
		assertEquals(1, col.indexOf("Štefica"));
	}

	@Test
	public void testRemoveAtIndex() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		col.add("Štefica");
		col.add(Integer.valueOf(30));
		col.add(3.14);

		// check for invalid index
		assertThrows(IndexOutOfBoundsException.class, () -> col.remove(-1));
		assertThrows(IndexOutOfBoundsException.class, () -> col.remove(3));

		// check for valid index
		col.remove(1);
		assertEquals(3.14, col.get(1));
	}

	@Test
	public void testRemoveValue() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		col.add("Štefica");
		col.add(Integer.valueOf(30));
		col.add(3.14);
		assertFalse(col.remove("Zagreb")); // returns false because Zagreb does not exist in collection.
		assertTrue(col.remove("Štefica")); // returns true and shifts everything.
		assertEquals(2, col.size());
		assertEquals(3.14, col.get(1));
	}
}
