package hr.fer.zemris.java.hw06.shell;

/**
 * Enumeration representing shell status. There are only two states:
 * {@link #CONTINUE} and {@link #TERMINATE}.
 * 
 * @author dbrcina
 *
 */
public enum ShellStatus {

	/**
	 * Status representing that executed command worked properly.
	 */
	CONTINUE,

	/**
	 * Status used for termination of shell program. It is mostly used when an
	 * instance of {@link ShellIOException} is thrown.
	 */
	TERMINATE;
}
