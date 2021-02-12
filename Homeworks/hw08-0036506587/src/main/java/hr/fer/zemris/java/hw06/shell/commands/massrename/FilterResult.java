package hr.fer.zemris.java.hw06.shell.commands.massrename;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class representing object whose name matches some provided pattern. It has
 * three methods:
 * <ul>
 * <li>{@link #toString()}</li>
 * <li>{@link #numberOfGroups()}</li>
 * <li>{@link #group(int)}</li>
 * </ul>
 * 
 * @author dbrcina
 * @see Pattern
 * @see Matcher
 *
 */
public class FilterResult {

	/**
	 * Result's name.
	 */
	private String fileName;

	/**
	 * Reference to {@link Matcher}.
	 */
	private Matcher matcher;

	/**
	 * Constructor.
	 * 
	 * @param fileName filtered file name.
	 * @param matcher  matcher.
	 */
	public FilterResult(String fileName, Matcher matcher) {
		this.fileName = Objects.requireNonNull(fileName, "File name cannot be null!\n");
		this.matcher = Objects.requireNonNull(matcher, "Matcher cannot be null reference!\n");
	}

	@Override
	public String toString() {
		return fileName;
	}

	/**
	 * Calculates number of groups from {@link #fileName} as determined by
	 * {@link Matcher#groupCount()} method.
	 * 
	 * @return number of groups.
	 */
	public int numberOfGroups() {
		return matcher.groupCount();
	}

	/**
	 * Fetches group name from {@link #fileName} at <code>index</code> as determined
	 * by {@link Matcher#group(int)} method.
	 * 
	 * @param index index.
	 * @return group name from {@link #fileName} at {@code index}.
	 * @throws IndexOutOfBoundsException if {@code index} is {@code <1} or
	 *                                   {@code >number of groups}.
	 */
	public String group(int index) {
		if (index < 0 || index > numberOfGroups()) {
			throw new IndexOutOfBoundsException("Group index is invalid!\n");
		}
		return matcher.group(index);
	}
}
