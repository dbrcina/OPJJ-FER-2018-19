package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.file.Path;
import java.nio.file.Paths;
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
 * Representation of pushd command in shell. It inherits from
 * {@link ShellCommand}.
 * 
 * @author dbrcina
 *
 */
public class PushdShellCommand implements ShellCommand {

	/**
	 * Constant representing command's name.
	 */
	private static final String PUSHD_NAME = "pushd";

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
		description.add("Command 'pushd' takes only one argument, path to some directory.");
		description.add("It pushes current directory to stack and sets provided directory as current directory.");
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (arguments.isEmpty()) {
			return status(env, "Command 'pushd' takes only one argument!\n");
		}
		try {
			Path currentDir = env.getCurrentDirectory();
			Path newDir = currentDir.resolve(Paths.get(parsePath(arguments))).normalize();
			env.setCurrentDirectory(newDir);
			pushToStack(env, currentDir);
			env.writeln("Directory " + currentDir + " pushed on stack.");
			env.writeln("Directory " + newDir + " is set as current directory.");
		} catch (IllegalArgumentException e) {
			return status(env, e.getMessage());
		}

		return status(env, "");
	}

	/**
	 * Helper method used for pushing provided <code>currentDir</code> on stack if
	 * that stack exists in map as determined by {@link #CDSTACK}, otherwise new
	 * stack is created.
	 * 
	 * @param env        environment.
	 * @param currentDir directory that needs to pushed on stack.
	 * @see Stack#push(Object)
	 */
	private void pushToStack(Environment env, Path currentDir) {
		@SuppressWarnings("unchecked")
		Stack<Path> stack = (Stack<Path>) env.getSharedData(CDSTACK);
		if (stack == null) {
			stack = new Stack<>();
			env.setSharedData(CDSTACK, stack);
		}
		stack.push(currentDir);
	}

	@Override
	public String getCommandName() {
		return PUSHD_NAME;
	}

	@Override
	public List<String> getCommandDescription() {
		return Collections.unmodifiableList(description);
	}

}
