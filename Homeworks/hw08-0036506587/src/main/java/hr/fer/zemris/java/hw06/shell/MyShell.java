package hr.fer.zemris.java.hw06.shell;

import java.util.Scanner;

/**
 * Program that simulates simple interaction between user and shell. Shell
 * provides some generic commands like ls, copy, cat, mkdir, help, exit, etc.
 * When program starts, type command help if u wish for further information. In
 * src/main/resources folder you can find some files for testing.
 * 
 * @author dbrcina
 *
 */
public class MyShell {

	/**
	 * Main entry of this program.
	 * 
	 * @param args arguments given through command line.
	 */
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		Environment environment = new ShellEnvironment(sc);
		ShellStatus status = ShellStatus.CONTINUE;
		environment.write("Welcome to MyShell v 1.0\n> ");
		do {

			try {
				String line = environment.readLine();
				if (line.isEmpty()) {
					environment.write(environment.getPromptSymbol() + " ");
					continue;
				}
				
				// check if read line contains morelines symbol
				StringBuilder sb = new StringBuilder();
				while (line.endsWith(environment.getMorelinesSymbol().toString())) {
					environment.write(environment.getMultilineSymbol() + " ");
					sb.append(line, 0, line.length() - 1);
					line = environment.readLine();
				}
				sb.append(line);
				line = sb.toString();

				// parse commands
				String commandName = ShellUtil.parseName(line);
				String arguments = line.substring(commandName.length()).trim();
				ShellCommand command = environment.commands().get(commandName);

				if (command == null) {
					environment.writeln("'" + commandName + "' is not recognized as a valid command!\n");
					environment.write(environment.getPromptSymbol() + " ");
				} else {
					status = command.executeCommand(environment, arguments);
				}
			}

			catch (ShellIOException e) {
				environment.writeln(e.getMessage());
				environment.write("Exiting shell..");
				status = ShellStatus.TERMINATE;
				sc.close();
			}

		} while (status != ShellStatus.TERMINATE);
	}

}
