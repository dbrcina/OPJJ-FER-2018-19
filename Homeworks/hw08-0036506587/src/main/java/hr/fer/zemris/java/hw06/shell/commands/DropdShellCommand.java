package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellEnvironment;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

import static hr.fer.zemris.java.hw06.shell.ShellUtil.*;

/**
 * Representation of dropd command in shell. It inherits from
 * {@link ShellCommand}.
 * 
 * @author dbrcina
 *
 */
public class DropdShellCommand implements ShellCommand {

	/**
	 * Constant representing command's name.
	 */
	private static final String DROPD_NAME = "dropd";

	/**
	 * Constant representing key value of shared data map.
	 * 
	 * @see {@link ShellEnvironment#getSharedData(String)}.
	 */
	private static final String CDSTACK = "cdstack";

	/**
	 * Command's description.
	 */
	private static final List<String> description = new ArrayList<>();

	/**
	 * Static initialization block used for adding description.
	 */
	static {
		description.add("Command 'dropd' takes zero arguments.");
		description.add("It removes the last directory pushed on stack.");
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (!arguments.isEmpty()) {
			return status(env, "Command 'dropd' takes zero arguments!\n");
		}
		@SuppressWarnings("unchecked")
		Stack<Path> stack = (Stack<Path>) env.getSharedData(CDSTACK);
		if (stack == null) {
			return status(env, "Cannot perform 'dropd' command on non existing stack!\n");
		}
		if (stack.isEmpty()) {
			return status(env, "Cannot remove directory from an empty stack!\n");
		}
		return status(env, "Directory " + stack.pop() + " removed from stack.\n");
	}

	@Override
	public String getCommandName() {
		return DROPD_NAME;
	}

	@Override
	public List<String> getCommandDescription() {
		return Collections.unmodifiableList(description);
	}

}
