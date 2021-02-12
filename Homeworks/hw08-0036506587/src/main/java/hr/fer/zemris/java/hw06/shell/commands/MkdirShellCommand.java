package hr.fer.zemris.java.hw06.shell.commands;

import static hr.fer.zemris.java.hw06.shell.ShellUtil.parsePath;
import static hr.fer.zemris.java.hw06.shell.ShellUtil.status;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Representation of mkdir command in shell. It inherits from
 * {@link ShellCommand}.
 * 
 * @author dbrcina
 *
 */
public class MkdirShellCommand implements ShellCommand {

	/**
	 * Constant representing command's name.
	 */
	private static final String MKDIR_NAME = "mkdir";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {

		// if there are no arguments
		if (arguments.isEmpty()) {
			return status(env, "Command 'mkdir' takes one argument!\n");
		}

		try {
			Path path = env.getCurrentDirectory().resolve(Paths.get(parsePath(arguments))).normalize();

			if (Files.exists(path, LinkOption.NOFOLLOW_LINKS)) {
				return status(env, "Directory '" + path + "' already exist!\n");
			}

			Files.createDirectories(path);
			env.writeln("Directory " + path + " is created.");
		} catch (AccessDeniedException e) {
			return status(env, "Acces denied!\n");
		} catch (IllegalArgumentException | IOException e) {
			return status(env, e.getMessage() + "\n");
		}

		return status(env, "");
	}

	@Override
	public String getCommandName() {
		return MKDIR_NAME;
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		description.add("Command 'mkdir' takes only one argument.");
		description.add("It creates a new directory with name given as an argument.");
		return description;
	}

}
