package hr.fer.zemris.java.hw06.shell.commands.massrename;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.commands.massrename.lexer.*;

/**
 * An implementation of a simple parser used for parsing substitute expression
 * provided as final argument for massrename command.
 * 
 * @author dbrcina
 *
 */
public class NameBuilderParser {

	/**
	 * Reference to {@link Lexer}.
	 */
	private Lexer lexer;

	/**
	 * List of all parsed {@link NameBuilder}.
	 */
	private List<NameBuilder> builders;

	/**
	 * Constructor.
	 * 
	 * @param expression substitute expression.
	 */
	public NameBuilderParser(String expression) {
		lexer = new Lexer(expression);
		builders = new ArrayList<>();
		try {
			parse();
		} catch (Exception e) {
			throw new NameBuilderParserException(e.getMessage());
		}
	}

	/**
	 * Getter for final {@link NameBuilder} that is refering to {@link #builders}.
	 * Its {@link NameBuilder#execute(FilterResult, StringBuilder)} method calls
	 * upon every other execute method from all parsed name builders.
	 * 
	 * @return final {@link NameBuilder} that is refering to {@link #builders}.
	 * @throws NameBuilderParserException if something happens when executing
	 *                                    NumberBuilders.
	 */
	public NameBuilder getNameBuilder() throws NameBuilderParserException {
		return (result, sb) -> {
			try {
				for (NameBuilder nb : builders) {
					nb.execute(result, sb);
				}
			} catch (IndexOutOfBoundsException ie) {
				generateException("File name doesn't containt that many groups!\n");
			}
		};
	}

	/**
	 * Helper method used for parsing substitute expression.
	 * 
	 * @throws NameBuilderParserException if something is wrong with parsing.
	 */
	private void parse() throws NameBuilderParserException {
		while (true) {
			Token currentToken = lexer.nextToken();

			// check for EOF
			if (currentToken.getType() == TokenType.EOF) {
				break;
			}

			// text outside of expression
			if (currentToken.getType() == TokenType.TEXT) {
				builders.add(text(currentToken.getValue()));
				continue;
			}

			// text inside of expression
			if (currentToken.getType() == TokenType.START_SUBSTITUTION) {
				lexer.setState(LexerState.INSIDE_SUBS_EXPRESSION);
				parseSubs(currentToken);
				lexer.setState(LexerState.OUTSIDE_SUBS_EXPRESSION);
			}
		}
	}

	/**
	 * Helper method used for parsing content inside of a substitute expression.
	 * 
	 * @param currentToken current token.
	 * @throws NameBuilderParserException if expression is invalid.
	 */
	private void parseSubs(Token currentToken) {
		List<Token> args = new ArrayList<>();
		currentToken = lexer.nextToken();
		while (currentToken.getType() != TokenType.END_SUBSTITUTION) {
			args.add(currentToken);
			currentToken = lexer.nextToken();
		}

		if (args.size() != 1 && args.size() != 3) {
			generateException("Invalid number of arguments inside expression!\n");
		}

		// only group number is provided
		if (args.size() == 1) {
			try {
				builders.add(group(Integer.parseInt(args.get(0).getValue())));
			} catch (NumberFormatException e) {
				generateException("Arguments inside expression must be integers!\n");
			}
		}

		// otherwise..
		else {

			// comma is expected as split argument
			if (args.get(1).getType() != TokenType.COMMA) {
				generateException("Invalid split sign in expression. Comma is expected!\n");
			}

			// parse content inside
			try {
				int index = Integer.parseInt(args.get(0).getValue());
				char padding = args.get(2).getValue().charAt(0);
				int minWidth = Integer.parseInt(args.get(2).getValue().substring(1));
				builders.add(group(index, padding, minWidth));
			} catch (NumberFormatException e) {
				generateException("One of the argument is not valid integer!\n");
			}
		}
	}

	/**
	 * Helper method used for generating {@link NameBuilderParserException}.
	 * 
	 * @param msg msg cause.
	 */
	private void generateException(String msg) {
		throw new NameBuilderParserException(msg);
	}

	/**
	 * Helper static method used for creating a new instance of {@link NameBuilder}
	 * whose {@link NameBuilder#execute(FilterResult, StringBuilder)} method appends
	 * provided <code>t</code> string to {@link StringBuilder}.
	 * 
	 * @param t string text.
	 * @return new instance of {@link NameBuilder}.
	 */
	private static NameBuilder text(String t) {
		return (result, sb) -> sb.append(t);
	}

	/**
	 * Helper static method used for creating a new instance of {@link NameBuilder}
	 * whose {@link NameBuilder#execute(FilterResult, StringBuilder)} method appends
	 * {@link FilterResult} group name from file name at <code>index</code>.
	 * 
	 * @param index index.
	 * @return new instance of {@link NameBuilder}.
	 * @see FilterResult#group(int)
	 */
	private static NameBuilder group(int index) {
		return (result, sb) -> sb.append(result.group(index));

	}

	/**
	 * Helper static method used for creating a new instance of {@link NameBuilder}
	 * whose {@link NameBuilder#execute(FilterResult, StringBuilder)} method appends
	 * {@link FilterResult} group name from file name at <code>index</code>. If
	 * groups length is greater than <code>minWidth</code>, whole group name is
	 * taken without <code>padding</code>, othwerwise group name is filled with
	 * <code>padding</code> to fit <code>minWidth</code>.
	 * 
	 * @param index    index.
	 * @param padding  char used for padding if necessary.
	 * @param minWidth minimum width.
	 * @return new instance of {@link NameBuilder}.
	 */
	private static NameBuilder group(int index, char padding, int minWidth) {
		return (result, sb) -> {
			if (minWidth != 0) {
				String group = result.group(index);
				if (group.length() >= minWidth) {
					sb.append(group);
				} else {
					String padd = String.valueOf(padding).repeat(minWidth - group.length());
					sb.append(padd).append(group);
				}
			}
		};
	}

}
