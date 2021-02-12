package hr.fer.zemris.java.hw06.shell;

import java.util.List;

/**
 * Interface that provides three regular methods for every command used in
 * shell: method for executing command, getter for command name and getter for
 * command description.
 * 
 * @author dbrcina
 * @see #executeCommand(Environment, String)
 * @see #getCommandName()
 * @see #getCommandDescription()
 *
 */
public interface ShellCommand {

	/**
	 * Method used to execute current command.
	 * 
	 * @param env       instance of {@link Environment}.
	 * @param arguments text entered in console by user after command name.
	 * @return status type of {@link ShellStatus} that is propagated to main shell
	 *         program which determines whether program should continue or not.
	 */
	ShellStatus executeCommand(Environment env, String arguments);

	/**
	 * Getter for current's command name.
	 * 
	 * @return command's name.
	 */
	String getCommandName();

	/**
	 * Getter for current's command description.
	 * 
	 * @return command's description as list of {@link String} values.
	 */
	List<String> getCommandDescription();
}
