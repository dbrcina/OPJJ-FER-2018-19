package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

import static hr.fer.zemris.java.hw06.shell.ShellUtil.*;

/**
 * Representation of help command in shell. It inherits from
 * {@link ShellCommand}.
 * 
 * @author dbrcina
 *
 */
public class HelpShellCommand implements ShellCommand {

	/**
	 * Constant representing command's name.
	 */
	private static final String HELP_NAME = "help";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		SortedMap<String, ShellCommand> commands = env.commands();

		// if there are no arguments
		if (arguments.isEmpty()) {
			env.writeln("For more information on a specific command, type help command-name");
			commands.entrySet().forEach(entry -> env.writeln(entry.getKey()));
		}

		// there is an argument
		else {
			ShellCommand command = commands.get(arguments);
			if (command == null) {
				return status(env, "'" + arguments + "' is not recognized as a command!\n");
			} else {
				env.writeln(command.getCommandName());
				List<String> description = command.getCommandDescription();
				description.forEach(s -> env.writeln(s));
			}
		}

		return status(env, "");
	}
	
	@Override
	public String getCommandName() {
		return HELP_NAME;
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		description.add("Command 'help' takes zero or one argument.");
		description.add("If zero arguments are provided, all supported commands are listed.");
		description
				.add("If one argument is provided, name of given command is printed along with command's description.");
		return description;
	}

}
