package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ArrayIndexedCollectionTest {

	@Test
	public void defaultConstructor() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		assertEquals(16, ArrayIndexedCollection.capacity);
		assertFalse(ArrayIndexedCollection.capacity == 0);
		assertEquals(0, col.size());
	}

	@Test
	public void constructorWithInitialCapacity() {
		// check for capacity < 1
		assertThrows(IllegalArgumentException.class, () -> new ArrayIndexedCollection(0));

		// check for good value
		ArrayIndexedCollection col = new ArrayIndexedCollection(10);
		assertEquals(10, ArrayIndexedCollection.capacity);
		assertFalse(ArrayIndexedCollection.capacity == 16);
		assertEquals(0, col.size());
	}

	@Test
	public void constructorWithAnotherCollection() {
		ArrayIndexedCollection col1 = new ArrayIndexedCollection();
		col1.add("Štefica");
		col1.add(Integer.valueOf(25));
		col1.add(3.14);

		// check for collection that is null
		assertThrows(NullPointerException.class, () -> new ArrayIndexedCollection(null));

		// check for other legit values
		ArrayIndexedCollection col2 = new ArrayIndexedCollection(col1);
		assertTrue(col2.size() == 3);
		assertEquals(25, col2.get(1));
		assertEquals("Štefica", col2.get(0));
		assertEquals(16, ArrayIndexedCollection.capacity);
	}

	@Test
	public void constructorWithAnotherCollAndInitCap() {
		ArrayIndexedCollection col1 = new ArrayIndexedCollection();
		col1.add("Štefica");
		col1.add(Integer.valueOf(25));
		col1.add(3.14);

		// check for capacity < 1
		assertThrows(IllegalArgumentException.class, () -> new ArrayIndexedCollection(-1));
		// check for collection that is null
		assertThrows(NullPointerException.class, () -> new ArrayIndexedCollection(null, 4));

		ArrayIndexedCollection col2 = new ArrayIndexedCollection(col1, 5);
		assertTrue(col2.size() == 3);
		assertEquals(5, ArrayIndexedCollection.capacity);
		col2 = new ArrayIndexedCollection(col1, 2);
		assertEquals(3, ArrayIndexedCollection.capacity);
	}

	@Test
	public void testForIsEmpy() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		assertTrue(col.isEmpty());
		col.add(2);
		col.add("Štefica");
		assertFalse(col.isEmpty());
		col = new ArrayIndexedCollection(col, 4);
		assertFalse(col.isEmpty());
	}

	@Test
	public void testForSize() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		assertEquals(0, col.size());
		col.add("Štefica");
		col.add(30);
		assertEquals(2, col.size());
	}

	@Test
	public void testForAdd() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();

		// check for adding null value
		assertThrows(NullPointerException.class, () -> col.add(null));

		// check for other legit values
		col.add("Štefica");
		col.add(Integer.valueOf(25));
		col.add(3.14);
		assertFalse(col.isEmpty());
		assertTrue(col.size() == 3);
		assertEquals(25, col.get(1));
		assertTrue(ArrayIndexedCollection.capacity == 16);
	}

	@Test
	public void testForContains() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		col.add("Štefica");
		col.add(Integer.valueOf(25));
		col.add(3.14);
		assertTrue(col.contains("Štefica"));
		assertFalse(col.contains(-3));
		col = new ArrayIndexedCollection(col, 3);
		col.remove(1);
		assertFalse(col.contains(25));
		assertTrue(col.contains(3.14));
	}

	@Test
	public void testForRemoveAtIndex() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		col.add("Štefica");
		col.add(Integer.valueOf(25));
		col.add(3.14);

		// check for invalid index
		assertThrows(IndexOutOfBoundsException.class, () -> col.remove(-1));
		assertThrows(IndexOutOfBoundsException.class, () -> col.remove(3));

		// check for valid index
		col.remove(1); // removed 25 and everything is shifted
		assertEquals(3.14, col.get(1));
		col.add("Avion");
		col.remove(0); // removed Štefica
		assertEquals(3.14, col.get(0));
	}

	@Test
	public void testForRemoveValue() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		col.add("Štefica");
		col.add(Integer.valueOf(25));
		col.add(3.14);

		assertFalse(col.remove("Zagreb")); // false, because Zagreb does not exist in this collection.
		assertTrue(col.remove("Štefica")); // true, everything is shifted
		assertEquals(2, col.size());
		assertEquals(25, col.get(0));
		
		assertThrows(IndexOutOfBoundsException.class, () -> col.get(2));
	}

	@Test
	public void testForToArray() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		col.add("Štefica");
		col.add(Integer.valueOf(25));
		col.add(3.14);
		Object[] newArray = col.toArray();
		assertTrue(newArray.length == 3);
		assertEquals(25, newArray[1]);
	}

	@Test
	public void testForEach() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		col.add("Štefica");
		col.add(Integer.valueOf(25));
		col.add(3.14);
		System.out.println("Test for forEach:");
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
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		col.add("Štefica");
		col.add(Integer.valueOf(25));
		col.add(3.14);

		ArrayIndexedCollection col1 = new ArrayIndexedCollection(1);
		col1.addAll(col);
		assertEquals(3, col1.size());
		System.out.println("Test for addAll:");
		col.forEach(new Processor() {
			@Override
			public void proces(Object value) {
				System.out.println(value);
			}
		});
	}

	@Test
	public void testForClear() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		col.add("Štefica");
		col.add(Integer.valueOf(25));
		col.add(3.14);
		col.clear();
		assertEquals(0, col.size());
	}

	@Test
	public void testForGet() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		col.add("Štefica");
		col.add(Integer.valueOf(25));
		col.add(3.14);

		// check for invalid index
		assertThrows(IndexOutOfBoundsException.class, () -> col.get(3));
		assertThrows(IndexOutOfBoundsException.class, () -> col.get(-1));

		// check for valid index
		assertTrue(col.get(0).equals("Štefica"));
		col.remove(1);
		assertTrue(col.get(1).equals(3.14));
	}

	@Test
	public void testForInsert() {
		ArrayIndexedCollection col = new ArrayIndexedCollection(3);
		col.add("Štefica");
		col.add(Integer.valueOf(25));
		col.add(3.14);

		// check for invalid position
		assertThrows(IndexOutOfBoundsException.class, () -> col.insert("blabla", -1));
		assertThrows(IndexOutOfBoundsException.class, () -> col.insert("blabla", 4));

		// check for valid position
		col.insert("blabla", 3); // before inserting, capacity of the collection should have been doubled!
		assertEquals(6, ArrayIndexedCollection.capacity);
		assertTrue(col.get(3).equals("blabla"));
		col.insert("Zagreb", 1);
		assertTrue(col.get(0).equals("Štefica"));
		assertTrue(col.get(1).equals("Zagreb"));
		assertTrue(col.get(2).equals(25));
		assertTrue(col.get(3).equals(3.14));
		assertTrue(col.get(4).equals("blabla"));
	}

	@Test
	public void testForIndexOf() {
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		col.add("Štefica");
		col.add(Integer.valueOf(25));
		col.add(3.14);
		assertEquals(-1, col.indexOf(100));
		assertEquals(2, col.indexOf(3.14));
	}

}
