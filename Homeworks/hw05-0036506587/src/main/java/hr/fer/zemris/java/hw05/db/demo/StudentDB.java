package hr.fer.zemris.java.hw05.db.demo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import hr.fer.zemris.java.hw05.db.QueryFilter;
import hr.fer.zemris.java.hw05.db.QueryParser;
import hr.fer.zemris.java.hw05.db.QueryParserException;
import hr.fer.zemris.java.hw05.db.StudentDatabase;
import hr.fer.zemris.java.hw05.db.StudentRecord;

/**
 * Simple application that illustrates working with query command and filtering
 * some database files.
 * 
 * @author dbrcina
 * @version 1.0
 *
 */
public class StudentDB {

	/**
	 * Constant representing jmbag length.
	 */
	private static final int JMBAG_LENGTH = 10;

	/**
	 * Constant representing final grade length.
	 */
	private static final int FINAL_GRADE_LENGTH = 1;

	/**
	 * Main entry of this program.
	 * 
	 * @param args arguments given through command line.
	 */
	public static void main(String[] args) {

		// read from a .txt file
		List<String> lines = new ArrayList<>();
		try {
			lines = Files.readAllLines(Paths.get("database.txt"), StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		simulateQueryCommand(new StudentDatabase(lines));

	}

	/**
	 * Method used for simulating query command on a simple {@link StudentDatabase}
	 * <code>db</code>. Results are printed on {@link System#out}.
	 * 
	 * @param db simple database.
	 */
	private static void simulateQueryCommand(StudentDatabase db) {
		try (Scanner sc = new Scanner(System.in)) {
			while (true) {
				System.out.print("> ");
				String line = sc.nextLine().trim();

				if (line.equals("exit")) {
					System.out.println("Goodbye");
					break;
				}

				if (!line.startsWith("query")) {
					System.out.println("Pogrešan command!");
					continue;
				}

				// skip query command
				line = line.substring(5);

				QueryParser parser;

				// try to parse input text
				try {
					parser = new QueryParser(line);
				} catch (QueryParserException e) {
					System.out.println(e.getMessage());
					continue;
				}

				List<StudentRecord> records = getFilteredRecords(parser, db);

				generateOutput(records);
			}
		}
	}

	/**
	 * Getter method for filtered records as determined by parser and it's method
	 * {@link QueryParser#isDirectQuery()}.
	 * 
	 * @param parser {@link QueryParser} parser.
	 * @param db     {@link StudentDatabase} db.
	 * @return list of filtered {@link StudentRecord}.
	 */
	private static List<StudentRecord> getFilteredRecords(QueryParser parser, StudentDatabase db) {
		List<StudentRecord> records;

		// check whether query is direct
		if (parser.isDirectQuery()) {
			records = new ArrayList<>();
			StudentRecord record = db.forJMBAG(parser.getQueriedJmbag());
			if (record != null) {
				records.add(record);
			}
			System.out.println("Using index for record retrieval.");
		}

		// else do the query filtering
		else {
			records = db.filter(new QueryFilter(parser.getQuery()));
		}
		return records;
	}

	/**
	 * Method used for printing a result of a query command.
	 * 
	 * @param records list of records.
	 */
	private static void generateOutput(List<StudentRecord> records) {
		List<String> output;

		if (records.size() > 0) {
			output = formatRecords(records);
			output.forEach(System.out::println);
		}

		System.out.println("Records selected : " + records.size() + "\n");
	}

	/**
	 * Helper method used for formating database records in an output table
	 * 
	 * @param records list of records.
	 * @return list of {@link String} types where one element represents one row of
	 *         a table.
	 */
	private static List<String> formatRecords(List<StudentRecord> records) {
		List<String> output = new ArrayList<>();
		int nameLength = 0;
		int lastNameLength = 0;

		// find max to determine how to create a table
		for (StudentRecord record : records) {
			nameLength = Math.max(nameLength, record.getFirstName().length());
			lastNameLength = Math.max(lastNameLength, record.getLastName().length());
		}

		output.add(firstAndLastRow(JMBAG_LENGTH + 2, lastNameLength + 2, nameLength + 2, FINAL_GRADE_LENGTH + 2));

		for (StudentRecord record : records) {
			output.add(makeRow(record, JMBAG_LENGTH, lastNameLength, nameLength, FINAL_GRADE_LENGTH));
		}

		output.add(firstAndLastRow(JMBAG_LENGTH + 2, lastNameLength + 2, nameLength + 2, FINAL_GRADE_LENGTH + 2));
		return output;
	}

	/**
	 * Helper method used for creating one row of a table.
	 * 
	 * @param record           {@link StudentRecord} type used for fetching right
	 *                         data.
	 * @param jmbagLength      jmbag length.
	 * @param lastNameLength   last name length.
	 * @param nameLength       first name length.
	 * @param finalGradeLength final grade length.
	 * @return String representation of one {@link StudentRecord} in one row.
	 */
	private static String makeRow(StudentRecord record, int jmbagLength, int lastNameLength, int nameLength,
			int finalGradeLength) {
		StringBuilder sb = new StringBuilder();

		sb.append("| ");
		sb.append(record.getJmbag());
		for (int i = 0; i < jmbagLength - record.getJmbag().length(); i++) {
			sb.append(" ");
		}

		sb.append(" | ");
		sb.append(record.getLastName());
		for (int i = 0; i < lastNameLength - record.getLastName().length(); i++) {
			sb.append(" ");
		}

		sb.append(" | ");
		sb.append(record.getFirstName());
		for (int i = 0; i < nameLength - record.getFirstName().length(); i++) {
			sb.append(" ");
		}

		sb.append(" | ");
		sb.append(record.getFinalGrade());
		for (int i = 0; i < finalGradeLength - record.getFinalGrade().length(); i++) {
			sb.append(" ");
		}

		sb.append(" |");

		return sb.toString();
	}

	/**
	 * Helper method that creates first and last row of the output table.
	 * 
	 * @param jmbagLength      jmbag length.
	 * @param lastNameLength   last name length.
	 * @param nameLength       name length.
	 * @param finalGradeLength final grade length.
	 * @return String representation of first and last row.
	 */
	private static String firstAndLastRow(int jmbagLength, int lastNameLength, int nameLength, int finalGradeLength) {
		StringBuilder sb = new StringBuilder();
		sb.append("+");
		for (int i = 0; i < jmbagLength; i++) {
			sb.append("=");
		}
		sb.append("+");
		for (int i = 0; i < lastNameLength; i++) {
			sb.append("=");
		}
		sb.append("+");
		for (int i = 0; i < nameLength; i++) {
			sb.append("=");
		}
		sb.append("+");
		for (int i = 0; i < finalGradeLength; i++) {
			sb.append("=");
		}
		sb.append("+");
		return sb.toString();
	}
	
}