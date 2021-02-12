package hr.fer.zemris.java.hw06.shell.commands.massrename;

/**
 * Functional interface used for working with instances of {@link NameBuilder}.
 * It provides {@link #execute(FilterResult, StringBuilder)} method.
 * 
 * @author dbrcina
 *
 */
public interface NameBuilder {

	/**
	 * Method used for generating <code>result</code> name and puting it into
	 * provided <code>sb</code>.
	 * 
	 * @param result {@link FilterResult} result.
	 * @param sb     {@link StringBuilder}.
	 */
	void execute(FilterResult result, StringBuilder sb);

}
