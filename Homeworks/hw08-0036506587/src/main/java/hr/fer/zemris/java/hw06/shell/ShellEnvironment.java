package hr.fer.zemris.java.hw06.shell;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.function.Predicate;

import hr.fer.zemris.java.hw06.shell.commands.CatShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.CdShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.CharsetsShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.CopyShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.DropdShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.ExitShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.HelpShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.HexdumpShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.ListdShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.LsShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.MassrenameShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.MkdirShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.PopdShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.PushdShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.PwdShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.SymbolShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.TreeShellCommand;

public class ShellEnvironment implements Environment {

	/**
	 * Variable representing current directory path.
	 */
	private Path currentDirectory = Paths.get(".").toAbsolutePath().normalize();

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
	 * Map used for shared data.
	 */
	private HashMap<String, Object> sharedData;

	/**
	 * Scanner used for interaction with {@link System#in}.
	 */
	private Scanner scanner;

	/**
	 * Default constructor that initializes shell environment.
	 */
	public ShellEnvironment(Scanner sc) {
		commands = new TreeMap<>();
		sharedData = new HashMap<>();
		scanner = sc;
		registerCommands();
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
		commands.put("pwd", new PwdShellCommand());
		commands.put("cd", new CdShellCommand());
		commands.put("pushd", new PushdShellCommand());
		commands.put("popd", new PopdShellCommand());
		commands.put("listd", new ListdShellCommand());
		commands.put("dropd", new DropdShellCommand());
		commands.put("massrename", new MassrenameShellCommand());
	}

	@Override
	public String readLine() throws ShellIOException {
		try {
			return scanner.nextLine().trim();
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
		write(String.format("%s%n", text));
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

	@Override
	public Path getCurrentDirectory() {
		return currentDirectory;
	}

	/**
	 * @throws IllegalArgumentException if the {@code path} is invalid.
	 */
	@Override
	public void setCurrentDirectory(Path path) {
		checkPath(path, p -> !Files.exists(p), "System cannot find path specified!\n");
		checkPath(path, p -> !Files.isDirectory(p), "Provided path is not a directory!\n");
		currentDirectory = path;
	}

	@Override
	public Object getSharedData(String key) {
		return sharedData.get(key);
	}

	@Override
	public void setSharedData(String key, Object value) {
		sharedData.put(Objects.requireNonNull(key, "Key cannot be null reference!\n"), value);
	}

	/**
	 * Helper method used for checking whether provided <code>path</code> is valid.
	 * 
	 * @param path   path to be tested.
	 * @param tester predicate tester.
	 * @param msg    output message.
	 */
	private void checkPath(Path path, Predicate<Path> tester, String msg) {
		if (tester.test(path)) {
			throw new IllegalArgumentException(msg);
		}
	}
}