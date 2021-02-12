package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class DictionaryTest {

	@Test
	public void testIsEmpty() {
		Dictionary<String, Integer> dict = new Dictionary<>();
		assertTrue(dict.isEmpty());
	}

	@Test
	public void testSize() {
		Dictionary<String, Integer> dict = new Dictionary<>();
		assertEquals(0, dict.size());

		dict.put("Štefica", 10);
		dict.put("Jasna", 15);

		assertEquals(2, dict.size());
	}

	@Test
	public void testClear() {
		Dictionary<Integer, String> dict = new Dictionary<>();
		dict.put(1, "a");
		dict.put(2, "b");
		dict.put(1, "c"); // it will overwrite existing value "a"

		assertEquals(2, dict.size());

		dict.clear();

		assertTrue(dict.isEmpty());
	}

	@Test
	public void testPutKeyNull() {
		Dictionary<Integer, String> dict = new Dictionary<>();

		// it throws
		assertThrows(NullPointerException.class, () -> dict.put(null, "a"));
	}

	@Test
	public void testPutValueNull() {
		Dictionary<String, Integer> dict = new Dictionary<>();

		dict.put("Štefica", null);
		assertEquals(null, dict.get("Štefica"));

		dict.put("Janko", null);
		assertEquals(null, dict.get("Janko"));
	}

	@Test
	public void testPut() {
		Dictionary<String, Integer> dict = new Dictionary<>();

		dict.put("a", 1);
		dict.put("b", 2);
		dict.put("c", null);

		assertEquals(2, dict.get("b"));
		assertEquals(null, dict.get("c"));

		dict.put("a", 10);
		dict.put("c", -10);

		assertEquals(10, dict.get("a"));
		assertEquals(-10, dict.get("c"));
	}
	
	@Test
	public void testGetKeyNull() {
		Dictionary<String, Integer> dict = new Dictionary<>();
		
		assertEquals(null, dict.get(null));
	}
	
	@Test
	public void testGet() {
		Dictionary<String, Integer> dict = new Dictionary<>();
		
		assertEquals(null, dict.get("Štefica")); // here is null cuz key "Štefica" does not exist in collection
		
		dict.put("a", null);
		dict.put("b", 10);
		dict.put("blabla", -40);
		
		assertEquals(null, dict.get("a"));
		assertEquals(10, dict.get("b"));
		assertEquals(-40, dict.get("blabla"));
		
		dict.put("a", 100);
		
		assertEquals(100, dict.get("a"));
	}
}
