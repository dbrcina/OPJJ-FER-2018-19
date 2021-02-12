package hr.fer.zemris.java.hw03;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import hr.fer.zemris.java.custom.scripting.nodes.*;
import hr.fer.zemris.java.custom.scripting.parser.*;

/**
 * Program that checks whether {@link SmartScriptParser} works correctly.
 * 
 * @author dbrcina
 * @version 1.0
 *
 */
public class SmartScriptTester {

	/**
	 * Main entry for this application.
	 * 
	 * @param args arguments entered through command line.
	 */
	public static void main(String[] args) {
		SmartScriptParser parser = null;
		try {
			String docBody = new String(Files.readAllBytes(Paths.get(args[0])),
					StandardCharsets.UTF_8);
			try {
				parser = new SmartScriptParser(docBody);
			} catch (SmartScriptParserException e) {
				System.out.println(e.getMessage());
				System.exit(-1);
			} catch (Exception e) {
				System.out.println("IF this line ever executes, you have failed this class!");
				System.exit(-1);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		DocumentNode document = parser.getDocumentNode();
		String originalDocumentBody = SmartScriptParser.createOriginalDocumentBody(document);
		System.out.println("Parse no1 :\n" + originalDocumentBody);
		
		System.out.println("------------------------------------------");
		
		SmartScriptParser parser2 = new SmartScriptParser(originalDocumentBody);
		DocumentNode document2 = parser2.getDocumentNode();
		String originalDocumentBody2 = SmartScriptParser.createOriginalDocumentBody(document2);
		System.out.println("Parse no2 :\n" + originalDocumentBody2);
		
		System.out.println("\nAre these 2 documents equal? " + originalDocumentBody.equals(originalDocumentBody2));
		
	}

}
