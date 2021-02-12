package hr.fer.zemris.java.hw01;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class FactorialTest {

	@Test
	public void testForNegativeNumber() {
		assertThrows(IllegalArgumentException.class, () -> Factorial.calculateFactorial(-2));
	}
	
	// interval for the function calculateFactorial is [0,20]
	@Test
	public void testForNumberOutOfInterval() {
		assertThrows(IllegalArgumentException.class, () -> Factorial.calculateFactorial(21));
	}
	
	@Test
	public void testForNumber4() {
		long factorial = Factorial.calculateFactorial(4);
		assertEquals(24, factorial);
	}
	
	@Test
	public void testForNumber10() {
		long factorial = Factorial.calculateFactorial(10);
		assertEquals(3628800, factorial);
	}
	
	@Test
	public void testForNumber20() {
		long factorial = Factorial.calculateFactorial(20);
		assertEquals(2432902008176640000L, factorial);
	}
}
