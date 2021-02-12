package hr.fer.zemris.java.custom.scripting.lexer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class SmartScriptLexerTest {

	@Test
	public void testNotNull() {
		SmartScriptLexer lexer = new SmartScriptLexer("");

		assertNotNull(lexer.nextToken(), "Token was expected but null was returned.");
	}

	@Test
	public void testNullInput() {
		// must throw!
		assertThrows(NullPointerException.class, () -> new SmartScriptLexer(null));
	}

	@Test
	public void testEmpty() {
		SmartScriptLexer lexer = new SmartScriptLexer("");

		assertEquals(SmartScriptTokenType.EOF, lexer.nextToken().getType(),
				"Empty input must generate only EOF token.");
	}

	@Test
	public void testGetReturnsLastNext() {
		// Calling getToken once or several times after calling nextToken must return
		// each time what nextToken returned...
		SmartScriptLexer lexer = new SmartScriptLexer("");

		SmartScriptToken token = lexer.nextToken();
		assertEquals(token, lexer.getToken(), "getToken returned different token than nextToken.");
		assertEquals(token, lexer.getToken(), "getToken returned different token than nextToken.");
	}

	@Test
	public void testRadAfterEOF() {
		SmartScriptLexer lexer = new SmartScriptLexer("");

		// will obtain EOF
		lexer.nextToken();
		// will throw!
		assertThrows(SmartScriptLexerException.class, () -> lexer.nextToken());
	}

	@Test
	public void testSimpleText() {
		SmartScriptLexer lexer = new SmartScriptLexer("   \r\n\t   Provjera običnog teksta.");

		SmartScriptToken[] correctData = {
				new SmartScriptToken(SmartScriptTokenType.TEXT, "   \r\n\t   Provjera običnog teksta."),
				new SmartScriptToken(SmartScriptTokenType.EOF, null) };

		checkSmartScriptTokenStream(lexer, correctData);
	}

	@Test
	public void testInvalidEscapeEnding() {
		SmartScriptLexer lexer = new SmartScriptLexer("   \\"); // this is three spaces and a single backslash -- 4
																// letters string

		// will throw!
		assertThrows(SmartScriptLexerException.class, () -> lexer.nextToken());
	}

	@Test
	public void testInvalidEscape() {
		SmartScriptLexer lexer = new SmartScriptLexer("   \\a    ");

		// will throw!
		assertThrows(SmartScriptLexerException.class, () -> lexer.nextToken());
	}

	@Test
	public void testCorrectEscape() {
		SmartScriptLexer lexer = new SmartScriptLexer("\\\\\\\\ \\{ \\\\ Luke, I am your father!");

		SmartScriptToken[] correctData = {
				new SmartScriptToken(SmartScriptTokenType.TEXT, "\\\\ { \\ Luke, I am your father!"),
				new SmartScriptToken(SmartScriptTokenType.EOF, null) };

		checkSmartScriptTokenStream(lexer, correctData);
	}

	@Test
	public void testInvalidBeginTag() {
		SmartScriptLexer lexer = new SmartScriptLexer("{   ");

		assertThrows(SmartScriptLexerException.class, () -> lexer.nextToken());
	}

	@Test
	public void testBeginTag() {
		SmartScriptLexer lexer = new SmartScriptLexer("Example \\{$=1$}. Now actually write one {$");

		SmartScriptToken[] correctData = {
				new SmartScriptToken(SmartScriptTokenType.TEXT, "Example {$=1$}. Now actually write one "),
				new SmartScriptToken(SmartScriptTokenType.BEGIN_TAG, "{$"),
				new SmartScriptToken(SmartScriptTokenType.EOF, null) };

		checkSmartScriptTokenStream(lexer, correctData);
	}

	@Test
	public void testNullState() {
		assertThrows(NullPointerException.class, () -> new SmartScriptLexer("").setState(null));
	}

	@Test
	public void testEmptyTag() {
		SmartScriptLexer lexer = new SmartScriptLexer("");
		lexer.setState(SmartScriptLexerState.TAG);

		assertThrows(SmartScriptLexerException.class, () -> lexer.nextToken());
	}

	/**
	 * Calling getToken once or several times after calling nextToken must return
	 * each time what nextToken returned...
	 */
	@Test
	public void testGetReturnsLastNextInTag() {
		SmartScriptLexer lexer = new SmartScriptLexer("FOR i -1 10 1 $}");
		lexer.setState(SmartScriptLexerState.TAG);

		SmartScriptToken token = lexer.nextToken();
		assertEquals(token, lexer.getToken(), "getToken returned different token than nextToken.");
		assertEquals(token, lexer.getToken(), "getToken returned different token than nextToken.");
	}

	@Test
	public void testTagExpression() {
		SmartScriptLexer lexer = new SmartScriptLexer("{$ FOR i -1.35bbb \"1\" $}");
		lexer.setState(SmartScriptLexerState.TEXT);
		
		checkSmartScriptToken(new SmartScriptToken(SmartScriptTokenType.BEGIN_TAG, "{$"), lexer.nextToken());
		
		lexer.setState(SmartScriptLexerState.TAG);
		
		checkSmartScriptToken(new SmartScriptToken(SmartScriptTokenType.VARIABLE, "FOR"), lexer.nextToken());
		checkSmartScriptToken(new SmartScriptToken(SmartScriptTokenType.VARIABLE, "i"), lexer.nextToken());
		checkSmartScriptToken(new SmartScriptToken(SmartScriptTokenType.DOUBLE, -1.35), lexer.nextToken());
		checkSmartScriptToken(new SmartScriptToken(SmartScriptTokenType.VARIABLE, "bbb"), lexer.nextToken());
		checkSmartScriptToken(new SmartScriptToken(SmartScriptTokenType.STRING, "1"), lexer.nextToken());
		checkSmartScriptToken(new SmartScriptToken(SmartScriptTokenType.END_TAG, "$}"), lexer.nextToken());
		
		
	}
	
	@Test
	public void testStrings() {
		SmartScriptLexer lexer = new SmartScriptLexer( "A tag follows {$= \"Joe \\\"Long\\\" Smith\"$}." );
		
		checkSmartScriptToken(new SmartScriptToken(SmartScriptTokenType.TEXT, "A tag follows "), lexer.nextToken());
		checkSmartScriptToken(new SmartScriptToken(SmartScriptTokenType.BEGIN_TAG, "{$"), lexer.nextToken());
		
		lexer.setState(SmartScriptLexerState.TAG);
		
		checkSmartScriptToken(new SmartScriptToken(SmartScriptTokenType.EQUALS_SIGN, "="), lexer.nextToken());
		checkSmartScriptToken(new SmartScriptToken(SmartScriptTokenType.STRING, "Joe \"Long\" Smith"), lexer.nextToken());
		
	}
	/**
	 * When input is only of spaces, tabs, newlines, etc...
	 */
	@Test
	public void testNoActualContentInTag() {
		SmartScriptLexer lexer = new SmartScriptLexer("   \r\n\t    $}");
		lexer.setState(SmartScriptLexerState.TAG);

		assertEquals(SmartScriptTokenType.END_TAG, lexer.nextToken().getType(),
				"Input had no content. Lexer should generated only EOF token.");
	}

	@Test
	public void testMultipartInput() {
		SmartScriptLexer lexer = new SmartScriptLexer("This is sample text.\r\n" + "{$ FOR i 1 10 1 $}"
				+ "\r\n\tThis is" + "{$= i $}" + "-th time this message is generated.\r\n" + "{$END$}" + "\r\n"
				+ "{$FOR i 0 10 2 $}" + "\r\n\tsin(" + "{$=i$}" + "^2) = " + "{$= i i * @sin \"0.000\" @decfmt $}"
				+ "\r\n" + "{$END$}");

		checkSmartScriptToken(new SmartScriptToken(SmartScriptTokenType.TEXT, "This is sample text.\r\n"),
				lexer.nextToken());
		checkSmartScriptToken(new SmartScriptToken(SmartScriptTokenType.BEGIN_TAG, "{$"), lexer.nextToken());

		lexer.setState(SmartScriptLexerState.TAG);

		checkSmartScriptToken(new SmartScriptToken(SmartScriptTokenType.VARIABLE, "FOR"), lexer.nextToken());
		checkSmartScriptToken(new SmartScriptToken(SmartScriptTokenType.VARIABLE, "i"), lexer.nextToken());
		checkSmartScriptToken(new SmartScriptToken(SmartScriptTokenType.INTEGER, 1), lexer.nextToken());
		checkSmartScriptToken(new SmartScriptToken(SmartScriptTokenType.INTEGER, 10), lexer.nextToken());
		checkSmartScriptToken(new SmartScriptToken(SmartScriptTokenType.INTEGER, 1), lexer.nextToken());
		checkSmartScriptToken(new SmartScriptToken(SmartScriptTokenType.END_TAG, "$}"), lexer.nextToken());

		lexer.setState(SmartScriptLexerState.TEXT);

		checkSmartScriptToken(new SmartScriptToken(SmartScriptTokenType.TEXT, "\r\n\tThis is"), lexer.nextToken());
		checkSmartScriptToken(new SmartScriptToken(SmartScriptTokenType.BEGIN_TAG, "{$"), lexer.nextToken());

		lexer.setState(SmartScriptLexerState.TAG);

		checkSmartScriptToken(new SmartScriptToken(SmartScriptTokenType.EQUALS_SIGN, "="), lexer.nextToken());
		checkSmartScriptToken(new SmartScriptToken(SmartScriptTokenType.VARIABLE, "i"), lexer.nextToken());
		checkSmartScriptToken(new SmartScriptToken(SmartScriptTokenType.END_TAG, "$}"), lexer.nextToken());

		lexer.setState(SmartScriptLexerState.TEXT);

		checkSmartScriptToken(
				new SmartScriptToken(SmartScriptTokenType.TEXT, "-th time this message is generated.\r\n"),
				lexer.nextToken());
		checkSmartScriptToken(new SmartScriptToken(SmartScriptTokenType.BEGIN_TAG, "{$"), lexer.nextToken());

		lexer.setState(SmartScriptLexerState.TAG);

		checkSmartScriptToken(new SmartScriptToken(SmartScriptTokenType.VARIABLE, "END"), lexer.nextToken());
		checkSmartScriptToken(new SmartScriptToken(SmartScriptTokenType.END_TAG, "$}"), lexer.nextToken());

		lexer.setState(SmartScriptLexerState.TEXT);

		checkSmartScriptToken(new SmartScriptToken(SmartScriptTokenType.TEXT, "\r\n"), lexer.nextToken());
		checkSmartScriptToken(new SmartScriptToken(SmartScriptTokenType.BEGIN_TAG, "{$"), lexer.nextToken());

		lexer.setState(SmartScriptLexerState.TAG);

		checkSmartScriptToken(new SmartScriptToken(SmartScriptTokenType.VARIABLE, "FOR"), lexer.nextToken());
		checkSmartScriptToken(new SmartScriptToken(SmartScriptTokenType.VARIABLE, "i"), lexer.nextToken());
		checkSmartScriptToken(new SmartScriptToken(SmartScriptTokenType.INTEGER, 0), lexer.nextToken());
		checkSmartScriptToken(new SmartScriptToken(SmartScriptTokenType.INTEGER, 10), lexer.nextToken());
		checkSmartScriptToken(new SmartScriptToken(SmartScriptTokenType.INTEGER, 2), lexer.nextToken());
		checkSmartScriptToken(new SmartScriptToken(SmartScriptTokenType.END_TAG, "$}"), lexer.nextToken());

		lexer.setState(SmartScriptLexerState.TEXT);

		checkSmartScriptToken(new SmartScriptToken(SmartScriptTokenType.TEXT, "\r\n\tsin("), lexer.nextToken());
		checkSmartScriptToken(new SmartScriptToken(SmartScriptTokenType.BEGIN_TAG, "{$"), lexer.nextToken());

		lexer.setState(SmartScriptLexerState.TAG);

		checkSmartScriptToken(new SmartScriptToken(SmartScriptTokenType.EQUALS_SIGN, "="), lexer.nextToken());
		checkSmartScriptToken(new SmartScriptToken(SmartScriptTokenType.VARIABLE, "i"), lexer.nextToken());
		checkSmartScriptToken(new SmartScriptToken(SmartScriptTokenType.END_TAG, "$}"), lexer.nextToken());

		lexer.setState(SmartScriptLexerState.TEXT);

		checkSmartScriptToken(new SmartScriptToken(SmartScriptTokenType.TEXT, "^2) = "), lexer.nextToken());
		checkSmartScriptToken(new SmartScriptToken(SmartScriptTokenType.BEGIN_TAG, "{$"), lexer.nextToken());

		lexer.setState(SmartScriptLexerState.TAG);

		checkSmartScriptToken(new SmartScriptToken(SmartScriptTokenType.EQUALS_SIGN, "="), lexer.nextToken());
		checkSmartScriptToken(new SmartScriptToken(SmartScriptTokenType.VARIABLE, "i"), lexer.nextToken());
		checkSmartScriptToken(new SmartScriptToken(SmartScriptTokenType.VARIABLE, "i"), lexer.nextToken());
		checkSmartScriptToken(new SmartScriptToken(SmartScriptTokenType.OPERATOR, "*"), lexer.nextToken());
		checkSmartScriptToken(new SmartScriptToken(SmartScriptTokenType.FUNCTION, "sin"), lexer.nextToken());
		checkSmartScriptToken(new SmartScriptToken(SmartScriptTokenType.STRING, "0.000"), lexer.nextToken());
		checkSmartScriptToken(new SmartScriptToken(SmartScriptTokenType.FUNCTION, "decfmt"), lexer.nextToken());
		checkSmartScriptToken(new SmartScriptToken(SmartScriptTokenType.END_TAG, "$}"), lexer.nextToken());

		lexer.setState(SmartScriptLexerState.TEXT);

		checkSmartScriptToken(new SmartScriptToken(SmartScriptTokenType.TEXT, "\r\n"), lexer.nextToken());
		checkSmartScriptToken(new SmartScriptToken(SmartScriptTokenType.BEGIN_TAG, "{$"), lexer.nextToken());

		lexer.setState(SmartScriptLexerState.TAG);

		checkSmartScriptToken(new SmartScriptToken(SmartScriptTokenType.VARIABLE, "END"), lexer.nextToken());
		checkSmartScriptToken(new SmartScriptToken(SmartScriptTokenType.END_TAG, "$}"), lexer.nextToken());
	}

	// Helper method for checking if lexer generates the same stream of tokens
	// as the given stream.
	private void checkSmartScriptTokenStream(SmartScriptLexer lexer, SmartScriptToken[] correctData) {
		int counter = 0;
		for (SmartScriptToken expected : correctData) {
			SmartScriptToken actual = lexer.nextToken();
			String msg = "Checking token " + counter + ":";
			assertEquals(expected.getType(), actual.getType(), msg);
			assertEquals(expected.getValue(), actual.getValue(), msg);
			counter++;
		}
	}

	/**
	 * Helper method for checking if lexer generates the same <code>actual</code>
	 * token as the <code>expected</code> one.
	 * 
	 * @param expected The token with right value.
	 * @param actual   A token generated by lexer that is supposed to have the same
	 *                 value and type as {@code expected}.
	 */
	private void checkSmartScriptToken(SmartScriptToken expected, SmartScriptToken actual) {
		assertEquals(expected.getType(), actual.getType());
		assertEquals(expected.getValue(), actual.getValue());
	}
}
