package hr.fer.zemris.java.hw06.shell.commands;

import static hr.fer.zemris.java.hw06.shell.ShellUtil.parsePath;
import static hr.fer.zemris.java.hw06.shell.ShellUtil.status;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Representation of copy command in shell. It inherits from
 * {@link ShellCommand}.
 * 
 * @author dbrcina
 *
 */
public class CopyShellCommand implements ShellCommand {

	/**
	 * Constant representing command's name.
	 */
	private static final String COPY_NAME = "copy";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {

		// check whether arguments are invalid
		if (arguments.isEmpty()) {
			return status(env, "Command 'copy' expects two arguments!\n");
		}

		String[] parts = parseArgument(arguments);

		// check whether arguments are invalid
		if (Arrays.stream(parts).filter(s -> !s.isEmpty()).count() != 2) {
			return status(env, "Command 'copy' expects two arguments!\n");
		}

		try {

			// check src file
			Path src = env.getCurrentDirectory().resolve(Paths.get(parsePath(parts[0]))).normalize();
			if (!Files.exists(src, LinkOption.NOFOLLOW_LINKS)) {
				return status(env, "File '" + src + "' does not exist!\n");
			}

			// check if source is directory
			if (Files.isDirectory(src, LinkOption.NOFOLLOW_LINKS)) {
				return status(env, "Command 'copy' can only copy files, not directories!\n");
			}

			// check destination
			Path destination = env.getCurrentDirectory().resolve(Paths.get(parsePath(parts[1]))).normalize();

			// check if destination is directory
			if (Files.isDirectory(destination, LinkOption.NOFOLLOW_LINKS)) {
				destination = Paths.get(destination + "/" + src.getFileName());

				// check whether source file already exist in directory
				if (Files.exists(destination, LinkOption.NOFOLLOW_LINKS)) {
					env.write(destination + " already exists. Do you want to overwrite it? (Y,N): ");
					boolean overwrite = env.readLine().trim().toUpperCase().equals("Y");
					if (!overwrite) {
						return status(env, "");
					}
				}
			}

			copy(src, destination);

		} catch (IllegalArgumentException e) {
			return status(env, e.getMessage());
		}

		return status(env, "File copied!\n");
	}

	/**
	 * Helper method used for parsing input arguments.
	 * 
	 * @param arguments args
	 * @return an array of arguments.
	 */
	private String[] parseArgument(String arguments) {
		if (arguments.contains("\"")) {
			int indexOfQuote = arguments.indexOf("\"");
			int currentIndex = indexOfQuote + 1;

			// inside quote
			while (currentIndex < arguments.length() && (arguments.charAt(currentIndex) != '\"')) {
				currentIndex++;
			}

			// concatenate if there is more signs that are not space
			while (currentIndex < arguments.length() && !Character.isSpaceChar(arguments.charAt(currentIndex))) {
				currentIndex++;
			}

			if (indexOfQuote == 0) {
				return new String[] { arguments.substring(0, currentIndex), arguments.substring(currentIndex).trim() };
			} else {
				return new String[] { arguments.substring(0, indexOfQuote).trim(),
						arguments.substring(indexOfQuote).trim() };
			}
		} else {
			return arguments.split(" ");
		}
	}

	/**
	 * Method that copies <code>src</code> file to given <code>destination</code>.
	 * 
	 * @param src         source file.
	 * @param destination destination.
	 */
	private void copy(Path src, Path destination) {
		try (BufferedInputStream bis = new BufferedInputStream(Files.newInputStream(src));
				BufferedOutputStream bos = new BufferedOutputStream(Files.newOutputStream(destination))) {
			bis.transferTo(bos);
		} catch (IOException e) {
			throw new IllegalArgumentException("Error ocurred while copying!\n");
		}
	}

	@Override
	public String getCommandName() {
		return COPY_NAME;
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		description.add(
				"Command 'copy' expects two arguments: source file name and destination file name (i.e. paths and names).");
		description.add("If destination file exists, you should ask user is it allowed to overwrite it.");
		description.add("Copy command must work only with files (no directories).");
		description.add(
				"If the second argument is directory, you should assume that user wants to copy the original file into that directory using the original file name.");
		return description;
	}

}
