package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This class implements a simple database which contains student's records.
 * 
 * @author dbrcina
 * @version 1.0
 *
 */
public class StudentDatabase {

	/**
	 * List of records.
	 */
	private List<StudentRecord> recordsList = new ArrayList<>();

	/**
	 * Map of records where key is student's jmbag.
	 */
	private Map<String, StudentRecord> recordsMap = new HashMap<>();

	/**
	 * Constructor which takes list of <code>String</code> objects as an argument
	 * and fills internal storage with records.
	 * 
	 * @param records list of student's records.
	 */
	public StudentDatabase(List<String> records) {
		for (String record : records) {
			String[] parts = record.split("\\t+");

			if (parts.length != 4) {
				throw new IllegalArgumentException("Zapis u datoteci nije dobar!");
			}

			StudentRecord studentRecord = new StudentRecord(parts[0], parts[2], parts[1], parts[3]);
			recordsList.add(studentRecord);
			recordsMap.put(studentRecord.getJmbag(), studentRecord);
		}

		if (hasDuplicate(recordsList)) {
			System.err.println("Baza podataka ima dva studenta sa istim jmbagom!");
			System.exit(-1);
		}
	}

	/**
	 * Fetches student's record as determined by <code>jmbag</code> argument. If the
	 * record does not exist, <code>null</code> value is returned. Complexity of
	 * this method is O(1), because {@link HashMap} is used as storage.
	 * 
	 * @param jmbag jmbag
	 * @return student's record by given {@code jmbag}.
	 * @see HashMap#get(Object)
	 */
	public StudentRecord forJMBAG(String jmbag) {
		return recordsMap.get(jmbag);
	}

	/**
	 * Filters all the records from database as determined by given
	 * <code>filter</code>.
	 * 
	 * @param filter filter used to filter student records.
	 * @return list of filtered student records.
	 */
	public List<StudentRecord> filter(IFilter filter) {
		List<StudentRecord> records = new ArrayList<>();
		for (StudentRecord record : recordsList) {
			if (filter.accepts(record)) {
				records.add(record);
			}
		}
		return records;
	}

	/**
	 * Helper method that checks whether given database has duplicate records as
	 * determined by {@link Set#add(Object)} method.
	 * 
	 * @param all argument for list of records
	 * @return {@code true} if there are duplicates, otherwise {@code false}.
	 */
	private static boolean hasDuplicate(Iterable<StudentRecord> all) {
		Set<StudentRecord> set = new HashSet<>();
		for (StudentRecord record : all) {
			if (!set.add(record)) {
				return true;
			}
		}
		return false;
	}
}
