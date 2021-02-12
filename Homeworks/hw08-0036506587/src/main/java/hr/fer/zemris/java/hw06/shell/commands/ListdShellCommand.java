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
 * Representation of listd command in shell. It inherits from
 * {@link ShellCommand}.
 * 
 * @author dbrcina
 *
 */
public class ListdShellCommand implements ShellCommand {

	/**
	 * Constant representing command's name.
	 */
	private static final String LISTD_NAME = "listd";

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
		description.add("Command 'listd' takes zero arguments.");
		description.add("It lists all directories from stack starting from last the one that was pushed last.");
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (!arguments.isEmpty()) {
			return status(env, "Command 'listd' takes zero arguments!\n");
		}
		@SuppressWarnings("unchecked")
		Stack<Path> stack = (Stack<Path>) env.getSharedData(CDSTACK);
		if (stack == null) {
			return status(env, "Cannot perform 'listd' command on non existing stack!\n");
		}
		if (stack.isEmpty()) {
			return status(env, "There aren't any directories stored on stack!\n");
		}
		Path[] dirs = new Path[stack.size()];
		stack.copyInto(dirs);
		for (int i = dirs.length - 1; i >= 0; i--) {
			env.writeln(dirs[i].toString());
		}
		return status(env, "");
	}

	@Override
	public String getCommandName() {
		return LISTD_NAME;
	}

	@Override
	public List<String> getCommandDescription() {
		return Collections.unmodifiableList(description);
	}

}
