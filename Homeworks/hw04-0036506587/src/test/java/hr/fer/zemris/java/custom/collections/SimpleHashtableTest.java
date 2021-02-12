package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class SimpleHashtableTest {

	@Test
	public void putTest() {
		SimpleHashtable<String, String> dictonary = new SimpleHashtable<>();
		dictonary.put("key", "YEAH");
		assertEquals("YEAH", dictonary.get("key"));
		
		dictonary.put("key", "BOOIII");
		assertEquals("BOOIII", dictonary.get("key"));
		
		assertEquals(1, dictonary.size());
	}
	
	@Test
	public void IteratorConcurentNOTException() {
		SimpleHashtable<Integer, Integer> dictonary = new SimpleHashtable<>(20);
		dictonary.put(1, 10);
		dictonary.put(123,0);
		
		var iterator=dictonary.iterator();
		dictonary.put(123, 2);
		iterator.next();
	}

	@Test
	void toStringTest() {
		SimpleHashtable<String,Integer> dictonary = new SimpleHashtable<>(2);
		dictonary.put("Ivana", 1);
		dictonary.put("Ante", 2);
		dictonary.put("Jasna", 3);
		dictonary.put("Kristina", 4);
		assertEquals("[Ante=2, Ivana=1, Jasna=3, Kristina=4]", dictonary.toString().trim());
	}
}
