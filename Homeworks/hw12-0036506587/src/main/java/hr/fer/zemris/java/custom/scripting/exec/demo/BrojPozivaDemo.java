package hr.fer.zemris.java.custom.scripting.exec.demo;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

public class BrojPozivaDemo {

	public static void main(String[] args) throws UnsupportedEncodingException, IOException {
		String documentBody = new String(Files.readAllBytes(Paths.get("webroot/scripts/brojPoziva.smscr")), "UTF-8");
		Map<String, String> parameters = new HashMap<>();
		Map<String, String> persistentParameters = new HashMap<>();
		List<RCCookie> cookies = new ArrayList<>();
		persistentParameters.put("brojPoziva", "3");
		RequestContext rc = new RequestContext(System.out,parameters, persistentParameters, cookies);
		new SmartScriptEngine(
				new SmartScriptParser(documentBody).getDocumentNode(),
				rc
		).execute();
		System.out.println("Vrijednost u mapi: " + rc.getPersistentParameter("brojPoziva"));
	}
}
