package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

import static hr.fer.zemris.java.hw06.shell.ShellUtil.status;

/**
 * Representation of charsets command in shell. It inherits from
 * {@link ShellCommand}.
 * 
 * @author dbrcina
 *
 */
public class CharsetsShellCommand implements ShellCommand {

	/**
	 * Constant representing command's name.
	 */
	private static final String CHARSETS_NAME = "charsets";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {

		// if there is an argument..
		if (!arguments.isEmpty()) {
			return status(env, "Command 'charsets' takes no arguments!\n");
		}

		// otherwise, list all supported charsets
		else {
			SortedMap<String, Charset> charsets = Charset.availableCharsets();
			env.writeln("List of supported charsets on this platform:");
			charsets.entrySet().forEach(entry -> env.writeln(entry.getKey()));
		}

		return status(env, "");
	}

	@Override
	public String getCommandName() {
		return CHARSETS_NAME;
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		description.add("Command 'charsets' takes zero arguments!");
		description.add("It lists all supported charsets on this platform.");
		return description;
	}

}
