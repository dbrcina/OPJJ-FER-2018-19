package hr.fer.zemris.java.hw06.shell;

import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.SortedMap;
import java.util.Stack;

/**
 * Interface representing shell environment. In other words, it is used as a
 * communication with user and it provides a numerous methods which are used to
 * support working with {@link ShellCommand} commands that are given by user
 * through console.
 * 
 * @author dbrcina
 *
 */
public interface Environment {

	/**
	 * Method used for reading current text which user inputs in console.
	 * 
	 * @return {@link String} representation of input text.
	 * @throws ShellIOException if something happens with reading from console.
	 */
	String readLine() throws ShellIOException;

	/**
	 * Method used for writing one word to console.
	 * 
	 * @param text a word that needs to be written.
	 * @throws ShellIOException if something happens with writing to console.
	 */
	void write(String text) throws ShellIOException;

	/**
	 * Method used for writing one whole line to console.
	 * 
	 * @param text text that needs to be written.
	 * @throws ShellIOException if something happens with writing to console.
	 */
	void writeln(String text) throws ShellIOException;

	/**
	 * Getter for all {@link ShellCommand} commands. Commands are stored in an
	 * instance of {@link SortedMap} where key is name of command and value is
	 * instance of {@link ShellCommand}. Internally, map is unmodifiable, so that
	 * user can not delete any of the commands.
	 * 
	 * @return an unmodifiable {@link SortedMap} of all commands.
	 * @see Collections#unmodifiableSortedMap(SortedMap)
	 */
	SortedMap<String, ShellCommand> commands();

	/**
	 * Getter for multiline symbol.
	 * 
	 * @return multiline symbol.
	 */
	Character getMultilineSymbol();

	/**
	 * Setter for multiline symbol.
	 * 
	 * @param symbol symbol.
	 */
	void setMultilineSymbol(Character symbol);

	/**
	 * Getter for prompt symbol.
	 * 
	 * @return prompt symbol.
	 */
	Character getPromptSymbol();

	/**
	 * Setter for prompt symbol.
	 * 
	 * @param symbol symbol.
	 */
	void setPromptSymbol(Character symbol);

	/**
	 * Getter for morelines symbol.
	 * 
	 * @return morelines symbol.
	 */
	Character getMorelinesSymbol();

	/**
	 * Setter for morelines symbol.
	 * 
	 * @param symbol symbol.
	 */
	void setMorelinesSymbol(Character symbol);

	/**
	 * Getter for absolute path of current directory.
	 * 
	 * @return path of current directory.
	 */
	Path getCurrentDirectory();

	/**
	 * Setter for path of current directory.
	 * 
	 * @param path directory path.
	 */
	void setCurrentDirectory(Path path);

	/**
	 * Getter for shared data. Internally, shared data is stored in an instance of
	 * {@link HashMap} where key values are {@link String} references and map values
	 * are instances of {@link Stack}.
	 * 
	 * @param key key.
	 * @return shared data.
	 */
	Object getSharedData(String key);

	/**
	 * Setter for shared data. Internally, shared data is stored in an instance of
	 * {@link HashMap} where key values are {@link String} references and map values
	 * are instances of {@link Stack}.
	 * 
	 * @param key   key.
	 * @param value value.
	 */
	void setSharedData(String key, Object value);
}