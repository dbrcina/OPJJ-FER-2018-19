package hr.fer.zemris.java.custom.scripting.parser;

import java.util.Arrays;
import java.util.Objects;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.collections.ObjectStack;
import hr.fer.zemris.java.custom.scripting.elems.*;
import hr.fer.zemris.java.custom.scripting.lexer.*;
import hr.fer.zemris.java.custom.scripting.nodes.*;;

/**
 * This class is used for parsing script texts. It parses given text into "tree"
 * which elements are build from {@link Node} elements. There is always one
 * root, a {@link DocumentNode} and many leafs(child nodes) consisted of other
 * nodes like {@link EchoNode}, {@link ForLoopNode} and {@link TextNode}. This
 * structure is build with a help of {@link ObjectStack} class. Firstly, a
 * {@link DocumentNode} type node is pushed on the stack. Then, for each empty
 * tag or {@link TextNode}, a node is created and added as a child of
 * {@link Node} that was last pushed on the stack. If a program encounters a
 * non-empty tag(like FOR-tag), {@link ForLoopNode} is created, added as a child
 * of {@link Node} that was last pushed on the stack and than pushed on the
 * stack also. The exception is tag {$END$}; when a program encounters it, one
 * entry is poped from the stack. {$END$} is end tag only for
 * {@link ForLoopNode} nodes. If stack remains empty, there is error in document
 * - it contains more {$END$}-s than opened non-empty tags and an appropriate
 * exception is thrown.
 * 
 * @author dbrcina
 * @version 1.0
 *
 */
public class SmartScriptParser {

	/**
	 * Internal memory used to build a syntax tree.
	 */
	private ObjectStack stack;

	/**
	 * A lexer for geting tokenized tokens.
	 */
	private SmartScriptLexer lexer;

	/**
	 * A main node(root).
	 */
	private DocumentNode document;

	/**
	 * A constructor in which {@link #document} is pushed on a stack, {@link #lexer}
	 * is initialized with <code>documentBody</code> and {@link #parse()} method is
	 * called. If <code>documentBody</code> is <code>null</code>, an appropriate
	 * exception is thrown.
	 * 
	 * @param documentBody An input script that needs to be parsed.
	 * @throws NullPointerException if {@code documentBody} is {@code null}.
	 */
	public SmartScriptParser(String documentBody) {
		Objects.requireNonNull(documentBody);
		stack = new ObjectStack();
		lexer = new SmartScriptLexer(documentBody);
		document = new DocumentNode();
		stack.push(document);

		try {
			parse();
		} catch (Exception e) {
			throw new SmartScriptParserException(e.getMessage());
		}
	}

	/**
	 * A method that parses given script. Internally, for text is used
	 * {@link #parseText()} method and for tags {@link #parseTag()} method.
	 * 
	 * @throws SmartScriptParserException if something goes wrong.
	 */
	private void parse() {

		while (true) {

			if (lexer.getState() == SmartScriptLexerState.TEXT) {
				if (isEOF()) {
					break;
				}
				parseText();
			} else if (lexer.getState() == SmartScriptLexerState.TAG) {
				parseTag();
			} else {
				throw new SmartScriptParserException("Lexers state je nepoznat!");
			}
		}

		if (stack.size() != 1) {
			throw new SmartScriptParserException("Postoje nezatvoreni blokovi!");
		}
	}

	/**
	 * Checks whether next token represents the end of a file.
	 * 
	 * @return {@code true} if the next token represents
	 *         {@link SmartScriptTokenType#EOF}, otherwise {@code false}.
	 */
	private boolean isEOF() {
		return lexer.nextToken().getType() == SmartScriptTokenType.EOF;
	}

	/**
	 * Parses text as determined by {@link SmartScriptParser} and
	 * {@link SmartScriptLexer}
	 * 
	 * @see SmartScriptParser
	 * @see SmartScriptLexer
	 * @throws SmartScriptParserException if something goes wrong.
	 */
	private void parseText() {
		SmartScriptToken nextToken = lexer.getToken();
		if (nextToken.getType() == SmartScriptTokenType.TEXT) {
			TextNode textNode = new TextNode((String) nextToken.getValue());
			((Node) stack.peek()).addChildNode(textNode);
		} else if (nextToken.getType() == SmartScriptTokenType.BEGIN_TAG) {
			lexer.setState(SmartScriptLexerState.TAG);
		} else {
			throw new SmartScriptParserException("Tip tokena nije iz zadane enumeracije!");
		}
	}

	/**
	 * Parses given tag as determined by {@link #parseForTag()} method and
	 * {@link #parseEchoTag()} method. Also, if tag name is END, check for
	 * explanation in description of {@link SmartScripParser} class.
	 * 
	 * @throws SmartScriptParserException if something goes wrong.
	 */
	private void parseTag() {
		SmartScriptToken nextToken = lexer.nextToken();
		SmartScriptTokenType currentType = nextToken.getType();
		String tagName = nextToken.getValue().toString().toUpperCase();

		if (currentType == SmartScriptTokenType.VARIABLE && tagName.equals("FOR")) {
			parseFor();
		} else if (currentType == SmartScriptTokenType.EQUALS_SIGN) {
			parseEcho();
		} else if (currentType == SmartScriptTokenType.VARIABLE && tagName.equals("END")) {
			stack.pop();
			// to skip end tag
			lexer.nextToken();
		} else {
			throw new SmartScriptParserException("Početak taga nije dobar!");
		}

		lexer.setState(SmartScriptLexerState.TEXT);
	}

	/**
	 * Parses content inside for tag:
	 * <ul>
	 * <li>Tag can have three or four parameters(as specified by user): First it
	 * must have one {@link ElementVariable} and then after two or three
	 * {@link Element}s of type variable, number or string.</li>
	 * <li>If user specifies something which does not obeys this rule, an
	 * appropriate exception is thrown.</li>
	 * </ul>
	 * 
	 * @throws SmartScriptParserException if something is wrong.
	 */
	private void parseFor() {
		SmartScriptToken[] forElements = new SmartScriptToken[4];
		int counter = 0;

		for (int i = 0; i < 5; i++) {
			SmartScriptToken nextToken = lexer.nextToken();

			if (i == 0 && nextToken.getType() != SmartScriptTokenType.VARIABLE) {
				throw new SmartScriptParserException("Varijabla nije dobro zadana!");
			}

			if (nextToken.getType() == SmartScriptTokenType.END_TAG) {
				if (i < 2) {
					throw new SmartScriptParserException("Premalo argumenata za for tag!");
				} else {
					break;
				}
			}

			forElements[i] = nextToken;
			counter++;
		}

		ForLoopNode forNode = new ForLoopNode((ElementVariable) parseElement(forElements[0]),
				parseElement(forElements[1]), parseElement(forElements[2]),
				counter == 4 ? parseElement(forElements[3]) : null);

		((Node) stack.peek()).addChildNode(forNode);
		stack.push(forNode);
	}

	/**
	 * Parses content inside echo tag:
	 * <ul>
	 * <li><b>Valid variable name</b> starts by letter and after follows zero or
	 * more letters, digits or underscores.</li>
	 * <li><b>Valid function name</b> starts with @ after which follows a letter and
	 * after than can follow zero or more letters, digits or underscores.</li>
	 * <li><b>Valid operators</b> are '+', '-', '*', '/' and '^'.</li>
	 * <li><b>Valid tag names</b> are "=", or variable name. = is valid tag name(but
	 * not valid variable name).</li>
	 * <ul>
	 * 
	 * @throws SmartScriptParserException if something is invalid.
	 */
	private void parseEcho() {
		ArrayIndexedCollection elements = new ArrayIndexedCollection();
		while (true) {
			SmartScriptToken nextToken = lexer.nextToken();
			if (nextToken.getType() == SmartScriptTokenType.END_TAG) {
				if (elements.size() == 0) {
					throw new SmartScriptParserException("Echo tag je prazan!");
				} else {
					break;
				}
			}
			elements.add(parseElement(nextToken));
		}
		Element[] echoElements = Arrays.copyOf(elements.toArray(), elements.size(), Element[].class);
		EchoNode echoNode = new EchoNode(echoElements);
		((Node) stack.peek()).addChildNode(echoNode);
	}

	/**
	 * Helper method which parses token value into right type.
	 * 
	 * @param token Token which value needs to be parsed.
	 * @return new {@link Element} type variable.
	 * 
	 * @throws SmartScriptParserException if something goes wrong.
	 */
	private Element parseElement(SmartScriptToken token) {
		Object value = token.getValue();
		SmartScriptTokenType type = token.getType();

		switch (type) {
		case INTEGER:
			return new ElementConstantInteger((int) value);

		case DOUBLE:
			return new ElementConstantDouble((double) value);

		case VARIABLE:
			return new ElementVariable((String) value);

		case FUNCTION:
			return new ElementFunction((String) value);

		case OPERATOR:
			return new ElementOperator((String) value);

		case STRING:
			return new ElementString((String) value);

		default:
			throw new SmartScriptParserException("Token type je pogrešan!");
		}
	}

	/**
	 * Getter for document.
	 * 
	 * @return {@link SmartScriptParser#document};
	 */
	public DocumentNode getDocumentNode() {
		return document;
	}

	/**
	 * Creates a document that was originally used for parsing
	 * 
	 * @param document {@link DocumentNode} type that needs to be created as
	 *                 {@link String} like the first input.
	 * @return {@link String} representation of {@code document}}.
	 */
	public static String createOriginalDocumentBody(DocumentNode document) {
		return parseChildren(document);
	}

	private static String parseChildren(Node document) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < document.numberOfChildren(); i++) {
			Node n = document.getChild(i);
			if (n instanceof TextNode) {
				sb.append((TextNode) n);
			} else if (n instanceof ForLoopNode) {
				sb.append((ForLoopNode) n);
				sb.append(parseChildren((ForLoopNode) n));
				sb.append("{$END$}");
			} else if (n instanceof EchoNode) {
				sb.append((EchoNode) n);
			} else {
				throw new SmartScriptParserException("Pogreška u hijerarhiji dokumenta!");
			}
		}
		return sb.toString();
	}
}
