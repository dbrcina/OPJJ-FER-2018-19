package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellEnvironment;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

import static hr.fer.zemris.java.hw06.shell.ShellUtil.*;

/**
 * Representation of popd command in shell. It inherits from
 * {@link ShellCommand}.
 * 
 * @author dbrcina
 *
 */
public class PopdShellCommand implements ShellCommand {

	/**
	 * Constant representing command's name.
	 */
	private static final String POPD_NAME = "popd";

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
		description.add("Command 'popd' takes zero arguments.");
		description.add("It removes last directory path from stack and sets it as a current directory.");
		description.add("If removed directory had been previously deleted, "
				+ "method does nothing but only removes it from stack.");
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (!arguments.isEmpty()) {
			return status(env, "Command 'popd' takes zero arguments!\n");
		}
		try {
			@SuppressWarnings("unchecked")
			Stack<Path> stack = (Stack<Path>) env.getSharedData(CDSTACK);
			if (stack == null) {
				return status(env, "Cannot perform 'popd' command on non existing stack!\n");
			}
			Path stackDir = stack.pop();
			env.writeln("Directory " + stackDir + " removed from stack.");
			env.setCurrentDirectory(stackDir);
			env.writeln("Current directory changed to " + env.getCurrentDirectory());
		} catch (EmptyStackException e) {
			env.writeln("Performing 'popd' command on an emty stack!");
		} catch (IllegalArgumentException illArgE) {
			env.writeln("Previously removed directory has been deleted by user!");
			env.writeln("Current directory stayed the same.");
		}
		return status(env, "");
	}

	@Override
	public String getCommandName() {
		return POPD_NAME;
	}

	@Override
	public List<String> getCommandDescription() {
		return Collections.unmodifiableList(description);
	}

}
