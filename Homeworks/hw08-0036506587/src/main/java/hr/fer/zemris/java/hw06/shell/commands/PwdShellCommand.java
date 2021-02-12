package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

import static hr.fer.zemris.java.hw06.shell.ShellUtil.*;

/**
 * Representation of pwd command in shell. It inherits from
 * {@link ShellCommand}.
 * 
 * @author dbrcina
 *
 */
public class PwdShellCommand implements ShellCommand {

	/**
	 * Constant representing command's name.
	 */
	private static final String PWD_NAME = "pwd";

	/**
	 * Command's description.
	 */
	private static final List<String> description = new ArrayList<>();

	/**
	 * Static initialization block used for adding description.
	 */
	static {
		description.add("Command 'pwd' takes zero arguments.");
		description.add("It prints path of current directory.");
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		if (!arguments.isEmpty()) {
			return status(env, "Command 'pwd' takes zero arguments!\n");
		}
		env.writeln(env.getCurrentDirectory().toString());
		return status(env, "");
	}

	@Override
	public String getCommandName() {
		return PWD_NAME;
	}

	@Override
	public List<String> getCommandDescription() {
		return Collections.unmodifiableList(description);
	}

}
