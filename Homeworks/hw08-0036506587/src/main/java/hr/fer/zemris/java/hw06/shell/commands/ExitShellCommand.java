package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

import static hr.fer.zemris.java.hw06.shell.ShellUtil.status;

/**
 * Representation of exit command in shell. It inherits from
 * {@link ShellCommand}.
 * 
 * @author dbrcina
 *
 */
public class ExitShellCommand implements ShellCommand {

	/**
	 * Constant representing command's name.
	 */
	private static final String EXIT_NAME = "exit";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {

		// if there is an argument..
		if (!arguments.isEmpty()) {
			return status(env, "Command 'exit' takes zero arguments!\n");
		}

		return ShellStatus.TERMINATE;
	}

	@Override
	public String getCommandName() {
		return EXIT_NAME;
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		description.add("Command 'exit' takes zero arguments.");
		description.add("It is used for exiting from shell.");
		return description;
	}

}
