package hr.fer.zemris.java.hw06.shell.commands;

import static hr.fer.zemris.java.hw06.shell.ShellUtil.status;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Representation of symbol command in shell. It inherits from
 * {@link ShellCommand}.
 * 
 * @author dbrcina
 *
 */
public class SymbolShellCommand implements ShellCommand {

	/**
	 * Constant representing command's name.
	 */
	private static final String SYMBOL_NAME = "symbol";

	/**
	 * Constant representing valid prompt name.
	 */
	private static final String PROMPT_NAME = "PROMPT";

	/**
	 * Constant representing valid morelines name.
	 */
	private static final String MORELINES_NAME = "MORELINES";

	/**
	 * Constant representing valid multiline name.
	 */
	private static final String MULTILINE_NAME = "MULTILINE";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		String parts[] = arguments.split(" ");

		// if there is invalid number of arguments
		if (arguments.isEmpty()) {
			return status(env, "Command 'symbol' accepts one or two arguments!\n");
		}

		// if there is only one argument
		else if (parts.length == 1) {
			symbolCommand(arguments, env);
		}

		// if there are two arguments
		else {

			// check whether new sign is valid
			if (parts[1].length() != 1) {
				return status(env, "Symbol's new sign must be a single sign!\n");
			}
			
			symbolCommand(parts[0], parts[1].charAt(0), env);
		}

		return status(env, "");
	}

	/**
	 * Helper method used for printing current symbol sign for
	 * <code>symbolName</code> symbol.
	 * 
	 * @param symbolName symbol name.
	 * @param env        environment.
	 */
	private static void symbolCommand(String symbolName, Environment env) {
		switch (symbolName) {
		case PROMPT_NAME:
			env.writeln("Symbol for PROMPT is '" + env.getPromptSymbol() + "'");
			break;
		case MORELINES_NAME:
			env.writeln("Symbol for MORELINES is '" + env.getMorelinesSymbol() + "'");
			break;
		case MULTILINE_NAME:
			env.writeln("Symbol for MULTILINE is '" + env.getMultilineSymbol() + "'");
			break;
		default:
			env.writeln("'" + symbolName + "' is invalid symbol name!");
		}
	}

	/**
	 * Helper method used for changing current symbol sign for
	 * <code>symbolName</code> symbol to <code>newSign</code> sign.
	 * 
	 * @param symbolName symbol name.
	 * @param newSign    new sign entered through console.
	 * @param env        environment.
	 */
	private static void symbolCommand(String symbolName, char newSign, Environment env) {
		switch (symbolName) {
		case PROMPT_NAME:
			changeSymbolSign(PROMPT_NAME, env.getPromptSymbol(), newSign, env, e -> env.setPromptSymbol(newSign));
			break;
		case MORELINES_NAME:
			changeSymbolSign(MORELINES_NAME, env.getMorelinesSymbol(), newSign, env,
					e -> env.setMorelinesSymbol(newSign));
			break;
		case MULTILINE_NAME:
			changeSymbolSign(MULTILINE_NAME, env.getMultilineSymbol(), newSign, env,
					e -> env.setMultilineSymbol(newSign));
			break;
		default:
			env.writeln("'" + symbolName + "' is invalid symbol name!");
			break;
		}
	}

	/**
	 * Method delegated from {@link #symbolCommand(String, char, Environment)}
	 * method.
	 * 
	 * @param symbolName  symbol name.
	 * @param currentSign symbol's current sign.
	 * @param newSign     symbol's new sign.
	 * @param env         environment.
	 * @param action      {@link Consumer} type action which is used for setting
	 *                    symbol's new sign.
	 * @see {@link #symbolCommand(String, char, Environment)} for documentation.
	 * @see Environment#setPromptSymbol(Character)
	 * @see Environment#setMorelinesSymbol(Character)
	 * @see Environment#setMultilineSymbol(Character)
	 */
	private static void changeSymbolSign(String symbolName, char currentSign, char newSign, Environment env,
			Consumer<Character> action) {
		env.writeln("Symbol for " + symbolName + " changed from '" + currentSign + "' to '" + newSign + "'");
		action.accept(newSign);
	}

	@Override
	public String getCommandName() {
		return SYMBOL_NAME;
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		description.add("Command 'symbol' takes one or two arguments.");
		description.add(
				"If used with only one argument, that argument represents symbol name and symbol command prints sign which represents provided symbol name.");
		description.add(
				"If used with two arguments, first one is the same and second represents new symbol that needs to be set for provided symbol name.");
		description.add("Valid symbol names are: PROMPT, MORELINES and MULTILINE.");
		return description;
	}

}