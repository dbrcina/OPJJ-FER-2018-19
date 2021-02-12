package hr.fer.zemris.java.hw06.shell.commands;

import static hr.fer.zemris.java.hw06.shell.ShellUtil.parsePath;
import static hr.fer.zemris.java.hw06.shell.ShellUtil.status;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Representation of cd command in shell. It inherits from {@link ShellCommand}.
 * 
 * @author dbrcina
 *
 */
public class CdShellCommand implements ShellCommand {

	/**
	 * Constant representing command's name.
	 */
	private static final String CD_NAME = "cd";

	/**
	 * Command's description.
	 */
	private static final List<String> description = new ArrayList<>();

	/**
	 * Static initialization block used for adding description.
	 */
	static {
		description.add("Command 'cd' takes only one argument, a path to new directory.");
		description.add("It changes current directory with provided one.");
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (arguments.isEmpty()) {
			return status(env, "Command 'cd' takes only one argument!\n");
		}
		try {
			String newDirString = parsePath(arguments);
			Path newDir = env.getCurrentDirectory().resolve(newDirString).normalize();
			env.setCurrentDirectory(newDir);
			env.writeln("Current directory changed to " + env.getCurrentDirectory());
		} catch (IllegalArgumentException e) {
			return status(env, e.getMessage());
		}
		
		return status(env, "");
	}

	@Override
	public String getCommandName() {
		return CD_NAME;
	}

	@Override
	public List<String> getCommandDescription() {
		return Collections.unmodifiableList(description);
	}

}
