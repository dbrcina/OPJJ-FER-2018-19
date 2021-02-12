package hr.fer.zemris.java.hw06.shell;

import java.util.Collections;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.zemris.java.hw06.shell.commands.CatShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.CharsetsShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.CopyShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.ExitShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.HelpShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.HexdumpShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.LsShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.MkdirShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.SymbolShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.TreeShellCommand;

public class ShellEnvironment implements Environment {

	/**
	 * Variable representing current prompt symbol.
	 */
	private Character promptSymbol = '>';

	/**
	 * Variable representing current multiline symbol.
	 */
	private Character multilineSymbol = '|';

	/**
	 * Variable representing current morelines symbol.
	 */
	private Character morelinesSymbol = '\\';

	/**
	 * Sorted map with all valid shell commands.
	 */
	private SortedMap<String, ShellCommand> commands;

	/**
	 * Scanner used for interaction with {@link System#in}.
	 */
	private Scanner sc = new Scanner(System.in);

	/**
	 * Default constructor that initializes shell environment.
	 */
	public ShellEnvironment() {
		commands = new TreeMap<>();
		registerCommands();
		write("Welcome to MyShell v 1.0\n> ");

	}

	/**
	 * Method used to register all commands.
	 */
	private void registerCommands() {
		commands.put("charsets", new CharsetsShellCommand());
		commands.put("cat", new CatShellCommand());
		commands.put("exit", new ExitShellCommand());
		commands.put("help", new HelpShellCommand());
		commands.put("copy", new CopyShellCommand());
		commands.put("ls", new LsShellCommand());
		commands.put("tree", new TreeShellCommand());
		commands.put("mkdir", new MkdirShellCommand());
		commands.put("hexdump", new HexdumpShellCommand());
		commands.put("symbol", new SymbolShellCommand());
	}

	@Override
	public String readLine() throws ShellIOException {
		try {
			return sc.nextLine().trim();
		} catch (Exception e) {
			throw new ShellIOException("Error occured when trying to read from console!");
		}
	}

	@Override
	public void write(String text) throws ShellIOException {
		try {
			System.out.printf("%s", text);
		} catch (Exception e) {
			throw new ShellIOException("Error occured while writing to console!");
		}
	}

	@Override
	public void writeln(String text) throws ShellIOException {
		write(text + "\n");
	}

	@Override
	public SortedMap<String, ShellCommand> commands() {
		return Collections.unmodifiableSortedMap(commands);
	}

	@Override
	public Character getMultilineSymbol() {
		return multilineSymbol;
	}

	@Override
	public void setMultilineSymbol(Character symbol) {
		multilineSymbol = symbol;
	}

	@Override
	public Character getPromptSymbol() {
		return promptSymbol;
	}

	@Override
	public void setPromptSymbol(Character symbol) {
		promptSymbol = symbol;
	}

	@Override
	public Character getMorelinesSymbol() {
		return morelinesSymbol;
	}

	@Override
	public void setMorelinesSymbol(Character symbol) {
		morelinesSymbol = symbol;
	}

}