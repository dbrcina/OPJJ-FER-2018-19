package hr.fer.zemris.java.custom.scripting.parser;

import static hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser.createOriginalDocumentBody;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;

public class SmartScriptParserTest {

	@Test
	public void testNullDocuments() {
		assertThrows(NullPointerException.class, () -> new SmartScriptParser(null));
	}

	@Test
	public void testDoc1() {
		String doc1 = loader("doc1.txt");
		assertTrue(checkWhetherContextIsTheSame(doc1));
	}

	@Test
	public void testDoc2() {
		String doc2 = loader("doc2.txt");
		assertTrue(checkWhetherContextIsTheSame(doc2));
	}

	@Test
	public void testDoc3() {
		String doc3 = loader("doc3.txt");
		assertTrue(checkWhetherContextIsTheSame(doc3));
	}

	@Test
	public void testDoc4() {
		String doc4 = loader("doc4.txt");
		assertTrue(checkWhetherContextIsTheSame(doc4));
	}

	@Test
	public void testincorrectVarInFor() {
		String doc = loader("incorrectVarInFor.txt");
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(doc));
	}

	@Test
	public void testDoc5() {
		String doc5 = loader("doc5.txt");
		assertTrue(checkWhetherContextIsTheSame(doc5));
	}

	@Test
	public void testTooManyEndTags() {
		String doc = loader("tooManyEndTags.txt");
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(doc));
	}

	@Test
	public void testTooManyArgsInFor() {
		String doc = loader("tooManyArgsInFor.txt");
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(doc));
	}

	@Test
	public void testWithSpacingAndCasing() {
		String doc = loader("withSpacingAndCasing.txt");
		assertTrue(checkWhetherContextIsTheSame(doc));
	}

	private boolean checkWhetherContextIsTheSame(String documentBody) {
		SmartScriptParser parser = new SmartScriptParser(documentBody);

		DocumentNode doc1 = parser.getDocumentNode();
		String originalDocumentBody = createOriginalDocumentBody(doc1);

		SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
		DocumentNode doc2 = parser2.getDocumentNode();
		String originalDocumentBody2 = createOriginalDocumentBody(doc2);

		return originalDocumentBody.equals(originalDocumentBody2);
	}

	private String loader(String filename) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try (InputStream is = this.getClass().getClassLoader().getResourceAsStream(filename)) {
			byte[] buffer = new byte[1024];
			while (true) {
				int read = is.read(buffer);
				if (read < 1)
					break;
				bos.write(buffer, 0, read);
			}
			return new String(bos.toByteArray(), StandardCharsets.UTF_8);
		} catch (IOException ex) {
			return null;
		}
	}

}
