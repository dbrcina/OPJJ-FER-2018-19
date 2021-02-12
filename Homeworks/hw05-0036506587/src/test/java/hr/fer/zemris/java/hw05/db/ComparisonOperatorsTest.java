package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ComparisonOperatorsTest {
	
	@Test
	public void testLess() {
		IComparisonOperator oper = ComparisonOperators.LESS;
		assertTrue(oper.satisfied("Ana", "Jasna"));
	}

	@Test
	public void testLessOrEquals() {
		IComparisonOperator oper = ComparisonOperators.LESS_OR_EQUALS;
		assertTrue(oper.satisfied("Ana", "Jasna"));
		assertTrue(oper.satisfied("Ana", "Ana"));
	}
	
	@Test
	public void testGreater() {
		IComparisonOperator oper = ComparisonOperators.GREATER;
		assertFalse(oper.satisfied("Ana", "Jasna"));
		assertTrue(oper.satisfied("Jasna", "Ana"));
	}
	
	@Test
	public void testGreaterOrEquals() {
		IComparisonOperator oper = ComparisonOperators.GREATER_OR_EQUALS;
		assertTrue(oper.satisfied("Jasna", "Jasna"));
		assertTrue(oper.satisfied("Jasna", "Ana"));
	}
	
	@Test
	public void testEquals() {
		IComparisonOperator oper = ComparisonOperators.EQUALS;
		assertTrue(oper.satisfied("blabla", "blabla"));
		assertFalse(oper.satisfied("blabla", "BLABLA"));
	}
	
	@Test
	public void testNotEquals() {
		IComparisonOperator oper = ComparisonOperators.NOT_EQUALS;
		assertFalse(oper.satisfied("blabla", "blabla"));
		assertTrue(oper.satisfied("blabla", "BLABLA"));
	}
	
	@Test
	public void testLikeWithoutWildcard() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		assertTrue(oper.satisfied("Jasna", "Jasna"));
		assertFalse(oper.satisfied("Jasna", "Jas"));
	}
	
	@Test
	public void testLikeWithMoreThanOneWildcard() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		
		// it throws..
		assertThrows(IllegalArgumentException.class, () -> oper.satisfied("Ana", "A**"));
		assertThrows(IllegalArgumentException.class, () -> oper.satisfied("Ana", "*n**"));
	}
	
	@Test
	public void testLikeWithMoreSign() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		assertFalse(oper.satisfied("Jasna", "Ja*snaa"));
		assertFalse(oper.satisfied("Ana", "*Anaa"));
	}
	
	@Test
	public void testLikeWithOnlyWildcard() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		assertTrue(oper.satisfied("Jasna", "*"));
	}
	
	@Test
	public void testLikeWithWildcardAtTheBegining() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		assertTrue(oper.satisfied("Jasna", "*na"));
		assertTrue(oper.satisfied("blabla", "*bla"));
	}
	
	@Test
	public void testLikeWithWildcardAtTheEnd() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		assertFalse(oper.satisfied("Zagreb", "Aba*"));
		assertTrue(oper.satisfied("Zagreb", "Zag*"));
		assertTrue(oper.satisfied("Bosnić", "B*"));
		assertTrue(oper.satisfied("Brezović", "B*"));
	}
	
	@Test
	public void testLikeWithWildcardInTheMiddle() {
		IComparisonOperator oper = ComparisonOperators.LIKE;
		assertFalse(oper.satisfied("AAA", "AA*AA"));
		assertTrue(oper.satisfied("AAAA", "AA*AA"));
		assertTrue(oper.satisfied("Jakov", "Ja*kov"));
		assertTrue(oper.satisfied("Bosnić", "B*ć"));
		assertTrue(oper.satisfied("Irena", "Ir*a"));
		assertTrue(oper.satisfied("Barišić", "Bar*ć"));
		
	}
}
