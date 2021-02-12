package hr.fer.zemris.java.hw06.shell.commands;

import static hr.fer.zemris.java.hw06.shell.ShellUtil.parseArguments;
import static hr.fer.zemris.java.hw06.shell.ShellUtil.status;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.commands.massrename.FilterResult;
import hr.fer.zemris.java.hw06.shell.commands.massrename.NameBuilder;
import hr.fer.zemris.java.hw06.shell.commands.massrename.NameBuilderParser;

/**
 * Representation of massrename command in shell. It inherits from
 * {@link ShellCommand}.
 * 
 * @author dbrcina
 *
 */
public class MassrenameShellCommand implements ShellCommand {

	/**
	 * Constant representing command's name.
	 */
	private static final String MASSRENAME_NAME = "massrename";

	/**
	 * Constant representing filter command.
	 */
	private static final String CMD_FILTER = "filter";

	/**
	 * Constant representing groups command.
	 */
	private static final String CMD_GROUPS = "groups";

	/**
	 * Constant representing show command.
	 */
	private static final String CMD_SHOW = "show";

	/**
	 * Constant representing execute command.
	 */
	private static final String CMD_EXECUTE = "execute";

	/**
	 * Command's description.
	 */
	private static final List<String> description = new ArrayList<>();

	/**
	 * Static initialization block used for adding description.
	 */
	static {
		description.add("Command 'massrename' takes four(mandatory) or five arguments.");
		description.add("First argument is a path to DIR1(not file!) whose files are being renamed/removed.");
		description.add("Second arguments is also a path to DIR2 where renamed files will be moved.");
		description.add("Third argument represents a command.");
		description.add("Fourth argument is a regular expression used for filtering file names.");
		description.add("Fifth argument is a substitute expression used for renaming files.");
		description.add("Substitute expression has two forms:");
		description.add("   ${grpNumber}");
		description.add("   ${grpNumber, explanation}");
		description.add("Field explanation consist of digit 0 and any other digit representing minWidth");
		description.add("MinWidth defines on how many signs group name needs to be printed.");
		description.add("If groups length is greater than minWidth, whole group name is printed, "
				+ "otherwise group name is filled with zeroes to fit minWidth.");
		description.add("There are four valid commands:");
		description.add("   filter  - filters all files with name pattern provided as fourth argument from src dir.");
		description.add("   groups  - prints groups defined by fourth argument.");
		description.add("   show    - defines new name for a file as determined by substitute expression.");
		description.add("   execute - does everything from above and moves files from DIR1 to DIR2.");
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		try {
			List<String> parsedArguments = parseArguments(arguments);
			if (parsedArguments.size() != 4 && parsedArguments.size() != 5) {
				return status(env, "Command 'massrename' expects four or five arguments!\n");
			}

			// fetch mandatory arguments
			Path src = parseDir(env.getCurrentDirectory(), parsedArguments.get(0));
			Path destination = parseDir(env.getCurrentDirectory(), parsedArguments.get(1));
			String command = parsedArguments.get(2);
			String pattern = parsedArguments.get(3);

			// execute commands
			if (command.equals(CMD_FILTER) || command.equals(CMD_GROUPS)) {
				filterOrGroupsCommand(env, src, command.equals(CMD_FILTER), pattern);
			} else if (command.equals(CMD_SHOW) || command.equals(CMD_EXECUTE)) {
				showOrExecuteCommand(env, src, destination, command.equals(CMD_SHOW), pattern, parsedArguments.get(4));
			} else {
				throw new IllegalArgumentException("Command '" + command + "' is invalid!\n");
			}

		} catch (IllegalArgumentException | IOException e) {
			return status(env, e.getMessage());
		}
		return status(env, "");
	}

	/**
	 * Helper method which performs show or execute command as determined by
	 * <code>isShow</code> flag. If <code>isShow</code> is <code>true</code>,
	 * {@link #CMD_SHOW} is performed, otherwise {@link #CMD_EXECUTE}.
	 * 
	 * @param env         environment.
	 * @param src         source directory.
	 * @param destination destination directory.
	 * @param isShow      boolean flag.
	 * @param pattern     pattern used for finding matching files.
	 * @param expression  expression that needs to be validated.
	 * @throws IOException if error occurs in {@link #filter(Path, String)} method
	 *                     or with
	 *                     {@link Files#move(Path, Path, java.nio.file.CopyOption...)}
	 *                     method.
	 */
	private void showOrExecuteCommand(Environment env, Path src, Path destination, boolean isShow, String pattern,
			String expression) throws IOException {

		List<FilterResult> results = filter(src, pattern);

		NameBuilderParser parser = null;
		try {
			parser = new NameBuilderParser(expression);
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getMessage());
		}

		NameBuilder nameBuilder = parser.getNameBuilder();
		for (FilterResult file : results) {
			StringBuilder sb = new StringBuilder();

			try {
				nameBuilder.execute(file, sb);
			} catch (Exception e) {
				throw new IllegalArgumentException(e.getMessage());
			}

			if (!isShow) {
				try {
					Files.move(src.resolve(file.toString()), destination.resolve(sb.toString()),
							StandardCopyOption.REPLACE_EXISTING);
				} catch (Exception e) {
					throw new IOException("Error occured while trying to move " + file.toString() + " file!\n");
				}
			}
			
			String arg1 = isShow ? file.toString() : src.resolve(file.toString()).toString();
			String arg2 = isShow ? sb.toString() : destination.resolve(sb.toString()).toString();
			env.writeln(String.format("%s => %s", arg1, arg2));
		}
	}

	/**
	 * Helper method which performs filter or groups command as determined by
	 * <code>isFilter</code> flag. If <code>isFilter</code> is <code>true</code>,
	 * {@link #CMD_FILTER} is performed, otherwise {@link #CMD_GROUPS} is.
	 * 
	 * @param env      environemnt.
	 * @param dir      directory from where files are filtered.
	 * @param isFilter boolean flag.
	 * @param pattern  pattern used for finding matching files.
	 * @throws IOException if error occurs in {@link #filter(Path, String)} method.
	 */
	private void filterOrGroupsCommand(Environment env, Path dir, boolean isFilter, String pattern) throws IOException {
		List<FilterResult> results = filter(dir, pattern);
		if (isFilter) {
			results.stream().map(FilterResult::toString).forEach(s -> env.writeln(s));
		} else {
			for (FilterResult result : results) {
				env.write(result.toString() + " ");
				for (int i = 0; i <= result.numberOfGroups(); i++) {
					env.write(i + ": " + result.group(i) + " ");
				}
				env.writeln("");
			}
		}
	}

	/**
	 * Helper method used for filtering files from <code>dir</code> by given
	 * <code>pattern</code>.
	 * 
	 * @param dir     directory from where files are filtered by {@code pattern}.
	 * @param pattern regular expression.
	 * @return list of {@link FilterResult}'s
	 * @throws IOException              if something happens while listing all
	 *                                  files.
	 * @throws IllegalArgumentException if provided directory has zero files.
	 */
	private static List<FilterResult> filter(Path dir, String pattern) throws IOException {
		try (DirectoryStream<Path> ds = Files.newDirectoryStream(dir)) {
			List<FilterResult> filterResult = new ArrayList<>();
			Pattern p = Pattern.compile(pattern, Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
			Iterator<Path> iterator = ds.iterator();

			while (iterator.hasNext()) {
				String fileName = iterator.next().toFile().getName();
				Matcher matcher = p.matcher(fileName);
				if (matcher.matches()) {
					filterResult.add(new FilterResult(fileName, matcher));
				}
			}

			if (filterResult.isEmpty()) {
				throw new IllegalArgumentException(
						"Directory " + dir + " doesn't contain files with provided pattern name!\n");
			}

			return filterResult;
		} catch (IOException e) {
			throw new IOException("Error occured while working with files!\n");
		}
	}

	/**
	 * Helper method used for checking whether provided <code>stringDir</code> is
	 * valid {@link String} representation of an existing directory.
	 * 
	 * @param currentDirectory current directory used for creating asbolute path
	 *                         using {@link Path#resolve(String)} method.
	 * @param stringDir        {@link String} representation of a directory.
	 * @return path to directory.
	 * @throws IllegalArgumentException if provided directory is invalid.
	 */
	private Path parseDir(Path currentDirectory, String stringDir) {
		Path dir = currentDirectory.resolve(stringDir).normalize();
		if (!Files.exists(dir)) {
			throw new IllegalArgumentException("Directory " + dir + " doesn't exist!\n");
		}
		if (!Files.isDirectory(dir)) {
			throw new IllegalArgumentException(dir + " is not a directory!\n");
		}
		return dir;
	}

	@Override
	public String getCommandName() {
		return MASSRENAME_NAME;
	}

	@Override
	public List<String> getCommandDescription() {
		return Collections.unmodifiableList(description);
	}

}
