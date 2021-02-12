package hr.fer.zemris.java.custom.scripting.exec;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ObjectMultistackTest {

	@Test
	public void testPush() {
		ObjectMultistack stack = new ObjectMultistack();
		stack.push("a", new ValueWrapper("-3"));
		stack.push("a", new ValueWrapper(Boolean.valueOf(true)));
		stack.push("ojoj", new ValueWrapper("bla"));
		assertEquals(true, stack.peek("a").getValue());
		assertEquals("bla", stack.peek("ojoj").getValue());
	}

	@Test
	public void testPeek() {
		ObjectMultistack stack = new ObjectMultistack();
		stack.push("1", new ValueWrapper(20));
		stack.push("2", new ValueWrapper("-32"));
		stack.push("2", new ValueWrapper(null));
		assertEquals(20, stack.peek("1").getValue());
		assertEquals(null, stack.peek("2").getValue());
	}

	@Test
	public void testPop() {
		ObjectMultistack stack = new ObjectMultistack();
		stack.push("1", new ValueWrapper(20));
		stack.push("2", new ValueWrapper("-32"));
		stack.push("2", new ValueWrapper(null));
		assertEquals(null, stack.pop("2").getValue());
		assertEquals("-32", stack.peek("2").getValue());
		stack.pop("2");
		assertTrue(stack.isEmpty("2"));
		assertThrows(EmptyStackException.class, () -> stack.pop("2"));
		assertThrows(EmptyStackException.class, () -> stack.pop("1212"));
	}
	
	@Test
	public void testIsEmpty() {
		ObjectMultistack stack = new ObjectMultistack();
		assertTrue(stack.isEmpty("bla"));
		stack.push("bla", new ValueWrapper(12));
		assertFalse(stack.isEmpty("bla"));
		stack.push("danijel", new ValueWrapper(null));
		assertFalse(stack.isEmpty("danijel"));
	}
}
