package hr.fer.zemris.java.hw05.db;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

public class QueryParserTest {

	@Test
	public void testDirectQuery() {
		QueryParser qp = new QueryParser(" jmbag       =\"0123456789\"    ");
		assertTrue(qp.isDirectQuery());
		assertEquals("0123456789", qp.getQueriedJmbag());
		assertEquals(1, qp.getQuery().size());
	}

	@Test
	public void testNonDirectQuery() {
		QueryParser qp = new QueryParser("jmbag=\"0123456789\" and lastName>\"J\"");
		assertFalse(qp.isDirectQuery());

		// it throws
		assertThrows(IllegalStateException.class, () -> qp.getQueriedJmbag());

		assertEquals(2, qp.getQuery().size());
	}

	@Test
	public void testMultipleConditions() {
		QueryParser qp = new QueryParser("  firstName > \"A\" and jmbag >= \"0000000023\" and lastName LIKE \"B*c\"");
		List<ConditionalExpression> expressions = qp.getQuery();
		assertEquals(3, expressions.size());
		assertEquals(ComparisonOperators.GREATER, expressions.get(0).getOperator());
	}
}
