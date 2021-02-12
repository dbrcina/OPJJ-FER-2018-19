package hr.fer.zemris.java.hw06.shell.commands;

import static hr.fer.zemris.java.hw06.shell.ShellUtil.parsePath;
import static hr.fer.zemris.java.hw06.shell.ShellUtil.status;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Representation of cat command in shell. It inherits from
 * {@link ShellCommand}.
 * 
 * @author dbrcina
 *
 */
public class CatShellCommand implements ShellCommand {

	/**
	 * Constant representing command's name.
	 */
	private static final String CAT_NAME = "cat";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {

		// if arguments are invalid...
		if (arguments.isEmpty()) {
			return status(env, "Command 'cat' expects one or two arguments!\n");
		}

		String[] parts = null;

		// if there are is quotation
		if (arguments.contains("\"")) {
			int lastIndexOfQuote = arguments.lastIndexOf("\"");
			lastIndexOfQuote++;

			// concatenate everything after " if there is something and it' not a space.
			while (lastIndexOfQuote < arguments.length()
					&& !Character.isSpaceChar(arguments.charAt(lastIndexOfQuote))) {
				lastIndexOfQuote++;
			}

			// if charset is not provided
			if (lastIndexOfQuote == arguments.length()) {
				parts = new String[] { arguments };
			}

			// otherwise..
			else {
				parts = new String[] { arguments.substring(0, lastIndexOfQuote),
						arguments.substring(lastIndexOfQuote + 1).trim() };
			}

		} else {
			parts = arguments.split("\\s+");
		}

		// this part checks whether path and charset are concatenated
		// if they are, split them by regex..
		// I am providing user to write charsets with quotes like "utf-16",
		// so this is necessary...
		if (parts.length == 1) {
			if (parts[0].contains("\" ")) {
				parts = parts[0].split("\" ");
				parts[0] += "\"";
			} else if (parts[0].contains(" \"")) {
				parts = parts[0].split(" \"");
				parts[1] = "\"" + parts[1];
			}
		}

		// check if arguments are valid
		else if (parts.length > 2) {
			return status(env, "There are more than two arguments!\n");
		}

		try {

			// first check if path is valid
			String pathString = parsePath(parts[0]);
			Path file = env.getCurrentDirectory().resolve(Paths.get(pathString)).normalize();

			// check if file on path exists
			if (!Files.exists(file, LinkOption.NOFOLLOW_LINKS)) {
				throw new IllegalArgumentException("File '" + file + "' does not exist!\n");
			}

			// check if file is directory
			if (Files.isDirectory(file, LinkOption.NOFOLLOW_LINKS)) {
				throw new IllegalArgumentException("'" + file + "' is a directory!\n");
			}

			Charset charset = Charset.defaultCharset();

			// check if charset is provided
			if (parts.length == 2) {

				// replacing quotes if charset is given like "utf-16" for example
				// this method does nothing if there are not eny quotes
				String charName = parts[1].replace("\"", "");

				if (Charset.isSupported(charName)) {
					charset = Charset.forName(charName);
				} else {
					throw new IllegalArgumentException("Given charset '" + parts[1] + "' is invalid!\n");
				}
			}
			writeToConsole(file, charset, env);

		} catch (IllegalArgumentException e) {
			return status(env, e.getMessage());
		}

		return status(env, "");
	}

	/**
	 * Helper method used for writing to console from <code>file</code>.
	 * 
	 * @param file    file written to console.
	 * @param charset charset.
	 * @param env     environment.
	 */
	private static void writeToConsole(Path file, Charset charset, Environment env) {
		try (BufferedReader br = new BufferedReader(
				new InputStreamReader(Files.newInputStream(file, StandardOpenOption.READ), charset))) {

			StringBuilder sb = new StringBuilder();

			try (Stream<String> stream = br.lines()) {
				stream.forEach(s -> sb.append(s + "\n"));
			}

			env.write(sb.toString());

		} catch (IOException e) {
			env.writeln(e.getMessage());
		}
	}

	@Override
	public String getCommandName() {
		return CAT_NAME;
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		description.add("Command 'cat' takes one or two arguments.");
		description.add("The first argument is path to some file(not directory!) and is mandatory.");
		description.add("The second argument is charset name that should be used to interpret chars from bytes.");
		description.add("If not provided, a default platform charset should be used.");
		description.add("This command opens given file and writes its content to console.");
		return description;
	}

}
