package hr.fer.zemris.java.hw06.shell.commands;

import static hr.fer.zemris.java.hw06.shell.ShellUtil.parsePath;
import static hr.fer.zemris.java.hw06.shell.ShellUtil.status;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Representation of tree command in shell. It inherits from
 * {@link ShellCommand}.
 * 
 * @author dbrcina
 *
 */
public class TreeShellCommand implements ShellCommand {

	/**
	 * Constant representing command's name.
	 */
	private static final String TREE_NAME = "tree";

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {

		// if there are no arguments
		if (arguments.isEmpty()) {
			return status(env, "Command 'tree' takes only one argument!\n");
		}

		try {
			Path path = Paths.get(parsePath(arguments));

			if (!Files.exists(path, LinkOption.NOFOLLOW_LINKS)) {
				return status(env, "Directory '" + path + "' does not exist!\n");
			}

			File[] children = path.toFile().listFiles();
			if (children.length == 0) {
				return status(env, "Direcory '" + path + "' does not have any subtrees!\n");
			}

			Files.walkFileTree(path, new PrintTree());
		} catch (IllegalArgumentException | IOException e) {
			return status(env, e.getMessage());
		}

		return status(env, "");
	}

	/**
	 * Private structure representing one instance of {@link FileVisitor}. It is
	 * used for printing all subtrees from current folder.
	 * 
	 * @author dbrcina
	 *
	 */
	private static class PrintTree implements FileVisitor<Path> {

		/**
		 * Variable representing current subtree level.
		 */
		private int level;

		@Override
		public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
			System.out.println(" ".repeat(level * 2) + (level == 0 ? dir.toAbsolutePath() : dir.getFileName()));
			level++;
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
			level--;
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
			System.out.println(" ".repeat(level * 2) + file.getFileName());
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
			return FileVisitResult.CONTINUE;
		}
	}

	@Override
	public String getCommandName() {
		return TREE_NAME;
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		description.add("Command 'tree' takes only one argument, directory name.");
		description.add("It lists all files and folders from provided directory.");
		return description;
	}

}
