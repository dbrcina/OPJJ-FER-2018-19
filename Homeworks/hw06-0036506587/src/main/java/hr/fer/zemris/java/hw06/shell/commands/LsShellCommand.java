package hr.fer.zemris.java.hw06.shell.commands;

import static hr.fer.zemris.java.hw06.shell.ShellUtil.parsePath;
import static hr.fer.zemris.java.hw06.shell.ShellUtil.status;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Representation of ls command in shell. It inherits from {@link ShellCommand}.
 * 
 * @author dbrcina
 *
 */
public class LsShellCommand implements ShellCommand {

	/**
	 * Constant representing command's name.
	 */
	private static final String LS_NAME = "ls";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {

		// if there are no arguments
		if (arguments.isEmpty()) {
			return status(env, "Command 'ls' takes one argument!\n");
		}

		else {
			try {
				String path = parsePath(arguments);
				Path dir = Paths.get(path);

				// check if path file is directory
				if (!Files.isDirectory(dir, LinkOption.NOFOLLOW_LINKS)) {
					return status(env, "'" + arguments + "' is not a directory!\n");
				}

				try (Stream<Path> hasChildren = Files.list(dir); Stream<Path> children = Files.list(dir)) {

					// if there are zero children
					if (hasChildren.count() == 0) {
						return status(env, "Directory '" + dir.getFileName() + "' has zero children.\n");
					}

					children.forEach(child -> generateOutput(child, env));

				} catch (IOException e) {
					throw new IllegalArgumentException(
							"Something went wrong with the opening of '" + dir + "' directory!\n");
				}

			} catch (IllegalArgumentException e) {
				return status(env, e.getMessage());
			}
		}

		return status(env, "");
	}

	/**
	 * Helper method used for decorating output.
	 * 
	 * @param file file.
	 */
	private void generateOutput(Path file, Environment env) {
		String isDirectory = Files.isDirectory(file, LinkOption.NOFOLLOW_LINKS) == true ? "d" : "-";
		String isReadable = Files.isReadable(file) == true ? "r" : "-";
		String isWriteable = Files.isWritable(file) == true ? "w" : "-";
		String isExecuteable = Files.isExecutable(file) == true ? "x" : "-";
		long size = 0;

		try {
			size = Files.size(file);
		} catch (IOException e) {
			throw new IllegalArgumentException("Something went wrong when trying to calculate size of a file!\n");
		}

		String formatedDate = formatDate(file);

		System.out.printf("%s %10d %s %s%n", isDirectory + isReadable + isWriteable + isExecuteable, size, formatedDate,
				file.getFileName());

	}

	/**
	 * Helper method used for formatting date into <code>yyyy-MM-dd HH:mm:ss</code>.
	 * 
	 * @param path path.
	 * @return {@link String} representation of date when file/folder was created.
	 */
	private String formatDate(Path path) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		BasicFileAttributeView faView = Files.getFileAttributeView(path, BasicFileAttributeView.class,
				LinkOption.NOFOLLOW_LINKS);
		BasicFileAttributes attributes = null;
		try {
			attributes = faView.readAttributes();
		} catch (IOException e) {
			throw new IllegalArgumentException("Something went wrong when trying to read file attributes!\n");
		}
		FileTime fileTime = attributes.creationTime();
		return sdf.format(new Date(fileTime.toMillis()));
	}

	@Override
	public String getCommandName() {
		return LS_NAME;
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		description.add("Command 'ls' takes a single argument - directory - and writes a directory listing.");
		description.add("The output consist of four columns.");
		description.add(
				"First column indicates if current object is directory(d), readable(r), writeable(w) and executable(x).");
		description
				.add("Second column contains object size in bytes that is right aligned and occupies 10 characters.");
		description.add("Follows file creation date/time and finally file name.");
		return description;
	}

}