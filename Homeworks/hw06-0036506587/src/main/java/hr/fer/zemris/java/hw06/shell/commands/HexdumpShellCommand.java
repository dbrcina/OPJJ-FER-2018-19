package hr.fer.zemris.java.hw06.shell.commands;

import static hr.fer.zemris.java.hw06.shell.ShellUtil.parsePath;
import static hr.fer.zemris.java.hw06.shell.ShellUtil.status;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Representation of hexdump command in shell. It inherits from
 * {@link ShellCommand}.
 * 
 * @author dbrcina
 *
 */
public class HexdumpShellCommand implements ShellCommand {

	/**
	 * Constant representing command's name.
	 */
	private static final String HEXDUMP_NAME = "hexdump";

	/**
	 * Constant representing max bytes in a byte array.
	 */
	private static final byte BYTES_STORAGE = 16;

	/**
	 * Constant representing minimum byte value that can be stored in an array.
	 */
	private static final byte MIN_BYTE_VALUE = 32;

	/**
	 * Constant representing maximum byte value that can be stored in an array.
	 */
	private static final byte MAX_BYTE_VALUE = 127;

	/**
	 * Constant representin byte value of sign '.'.
	 */
	private static final byte DOT_BYTE_VALUE = 46;

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {

		// check if arguments are valid
		if (arguments.isEmpty()) {
			return status(env, "Command 'hexdump' takes only one argument!\n");
		}

		try {
			Path file = Paths.get(parsePath(arguments));

			if (Files.isDirectory(file, LinkOption.NOFOLLOW_LINKS)) {
				return status(env, "Hexdump cannot be performed on directories!\n");
			}

			hexdump(file, env);

		} catch (IllegalArgumentException e) {
			return status(env, e.getMessage());
		}

		return status(env, "");
	}

	/**
	 * Helper method used for getting hexidecimal record of provided
	 * <code>file</code>. Result is written to console.
	 * 
	 * @param file file.
	 * @param env  environment.
	 */
	private void hexdump(Path file, Environment env) {
		try (InputStream is = Files.newInputStream(file, StandardOpenOption.READ)) {
			byte[] buff = new byte[BYTES_STORAGE];
			int offset = 0x0;

			while (true) {
				int read = is.read(buff);
				if (read < 1) {
					break;
				}

				StringBuilder sb = new StringBuilder();

				sb.append(String.format("%08X: ", offset));

				for (int i = 0; i < BYTES_STORAGE; i++) {
					if (i >= read) {
						sb.append("   ");
					} else {
						sb.append(String.format("%02X ", buff[i]));
					}

					if (i == 7) {
						sb.replace(sb.length() - 1, sb.length(), "|");
					}
				}

				sb.append("| ");

				// remove unspecified byte values
				for (int i = 0; i < read; i++) {
					if (buff[i] < MIN_BYTE_VALUE || buff[i] > MAX_BYTE_VALUE) {
						buff[i] = DOT_BYTE_VALUE;
					}
				}

				// create new String as determined by byte array
				sb.append(new String(buff, 0, read));

				offset += 0x10;
				env.writeln(sb.toString());
			}
		} catch (IOException e) {
			throw new IllegalArgumentException("Something went wrong with the opening of a new file!\n");
		}
	}

	@Override
	public String getCommandName() {
		return HEXDUMP_NAME;
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> description = new ArrayList<>();
		description.add("Command 'hexdump' expects a single argument: file name, and produces hex-output.");
		description.add("All bytes whose value is less than 32 or greater than 127 are replaced with '.'");
		return description;
	}

}
