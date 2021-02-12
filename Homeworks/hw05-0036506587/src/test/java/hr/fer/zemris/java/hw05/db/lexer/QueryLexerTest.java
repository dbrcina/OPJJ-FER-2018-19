package hr.fer.zemris.java.hw05.db.lexer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class QueryLexerTest {

	@Test
	public void testText1() {
		QueryLexer lexer = new QueryLexer("query jmbag=\"00000003\"");

		QueryToken[] correctData = { 
				new QueryToken(QueryTokenType.FIELD, "jmbag"),
				new QueryToken(QueryTokenType.OPERATOR, "="), 
				new QueryToken(QueryTokenType.STRING, "00000003"),
				new QueryToken(QueryTokenType.EOF, null) 
		};

		checkSmartScriptTokenStream(lexer, correctData);
		
		// it throws
		assertThrows(QueryLexerException.class, () -> lexer.nextToken());
	}

	@Test
	public void testText2() {
		QueryLexer lexer = new QueryLexer("query   lastName  =    \"Blažić\"");

		QueryToken[] correctData = { 
				new QueryToken(QueryTokenType.FIELD, "lastName"),
				new QueryToken(QueryTokenType.OPERATOR, "="), 
				new QueryToken(QueryTokenType.STRING, "Blažić"),
				new QueryToken(QueryTokenType.EOF, null) 
		};

		checkSmartScriptTokenStream(lexer, correctData);
		
		// it throws
		assertThrows(QueryLexerException.class, () -> lexer.nextToken());
	}

	@Test
	public void testText3() {
		QueryLexer lexer = new QueryLexer("query firstName>\"A\" and lastName LIKE \"B*ć\"");

		QueryToken[] correctData = { 
				new QueryToken(QueryTokenType.FIELD, "firstName"),
				new QueryToken(QueryTokenType.OPERATOR, ">"), 
				new QueryToken(QueryTokenType.STRING, "A"),
				new QueryToken(QueryTokenType.AND, "AND"),
				new QueryToken(QueryTokenType.FIELD, "lastName"),
				new QueryToken(QueryTokenType.OPERATOR, "LIKE"),
				new QueryToken(QueryTokenType.STRING, "B*ć"),
				new QueryToken(QueryTokenType.EOF, null) 
		};

		checkSmartScriptTokenStream(lexer, correctData);
		
		// it throws
		assertThrows(QueryLexerException.class, () -> lexer.nextToken());
	}

	@Test
	public void testText4() {
		QueryLexer lexer = new QueryLexer("query firstName>\"A\" and firstName<=\"C\" and lastName LIKE \"B*ć\" and jmbag>\"0000000002\"");
		
		QueryToken[] correctData = { 
				new QueryToken(QueryTokenType.FIELD, "firstName"),
				new QueryToken(QueryTokenType.OPERATOR, ">"), 
				new QueryToken(QueryTokenType.STRING, "A"),
				new QueryToken(QueryTokenType.AND, "AND"),
				new QueryToken(QueryTokenType.FIELD, "firstName"),
				new QueryToken(QueryTokenType.OPERATOR, "<="),
				new QueryToken(QueryTokenType.STRING, "C"),
				new QueryToken(QueryTokenType.AND, "AND"),
				new QueryToken(QueryTokenType.FIELD, "lastName"),
				new QueryToken(QueryTokenType.OPERATOR, "LIKE"),
				new QueryToken(QueryTokenType.STRING, "B*ć"),
				new QueryToken(QueryTokenType.AND, "AND"),
				new QueryToken(QueryTokenType.FIELD, "jmbag"),
				new QueryToken(QueryTokenType.OPERATOR, ">"),
				new QueryToken(QueryTokenType.STRING, "0000000002"),
				new QueryToken(QueryTokenType.EOF, null) 
		};

		checkSmartScriptTokenStream(lexer, correctData);
		
		// it throws
		assertThrows(QueryLexerException.class, () -> lexer.nextToken());
	}
	
	// Helper method for checking if lexer generates the same stream of tokens
	// as the given stream.
	private void checkSmartScriptTokenStream(QueryLexer lexer, QueryToken[] correctData) {
		int counter = 0;
		for (QueryToken expected : correctData) {
			QueryToken actual = lexer.nextToken();
			String msg = "Checking token " + counter + ":";
			assertEquals(expected.getType(), actual.getType(), msg);
			assertEquals(expected.getValue(), actual.getValue(), msg);
			counter++;
		}
	}
}
