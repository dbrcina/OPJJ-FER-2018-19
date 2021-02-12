package hr.fer.zemris.java.hw07.demo4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StudentDemo {

	public static void main(String[] args) {
		List<String> lines = null;
		try {
			lines = Files.readAllLines(Paths.get("./studenti.txt"));
		} catch (IOException e) {
			System.out.println("Error occured while reading from a file!\nExiting....");
			System.exit(-1);
		}
		List<StudentRecord> records = convert(lines);

		// Zadatak 1
		System.out.println("Zadatak 1\n=========\n" + vratiBodovaViseOd25(records));
		
		// Zadatak 2
		System.out.println("Zadatak 2\n=========\n" + vratiBrojOdlikasa(records));

		// Zadatak 3
		List<StudentRecord> odlikasi = vratiListuOdlikasa(records);
		System.out.println("Zadatak 3\n=========");
		odlikasi.forEach(System.out::println);

		// Zadatak 4
		List<StudentRecord> odlikasiSortirano = vratiSortiranuListuOdlikasa(records);
		System.out.println("Zadatak 4\n=========");
		odlikasiSortirano.forEach(System.out::println);

		// Zadatak 5
		List<String> nepolozeniJMBAGovi = vratiPopisNepolozenih(records);
		System.out.println("Zadatak 5\n=========");
		nepolozeniJMBAGovi.forEach(System.out::println);

		// Zadatak 6
		Map<Integer, List<StudentRecord>> mapaPoOcjenama = razvrstajStudentePoOcjenama(records);
		System.out.println("Zadatak 6\n=========");
		mapaPoOcjenama.entrySet().forEach(System.out::println);

		// Zadatak 7
		Map<Integer, Integer> mapaPoOcjenama2 = vratiBrojStudenataPoOcjenama(records);
		System.out.println("Zadatak 7\n=========");
		mapaPoOcjenama2.entrySet().forEach(System.out::println);

		// Zadatak 8
		Map<Boolean, List<StudentRecord>> prolazNeprolaz = razvrstajProlazPad(records);
		System.out.println("Zadatak 8\n=========");
		prolazNeprolaz.entrySet().forEach(System.out::println);
	}

	/**
	 * Helper method that converts databse units from file as an instance of
	 * {@link StudentRecord}.
	 * 
	 * @param lines lines.
	 * @return list of {@link StudentRecord}.
	 */
	private static List<StudentRecord> convert(List<String> lines) {
		List<StudentRecord> records = new ArrayList<>();
		for (String line : lines) {
			String[] parts = line.split("\\t+");
			try {
				records.add(new StudentRecord(parts[0], parts[1], parts[2], Double.parseDouble(parts[3]),
						Double.parseDouble(parts[4]), Double.parseDouble(parts[5]), Integer.parseInt(parts[6])));
			} catch (NumberFormatException e) {
				System.out.println("Error occured while parsing numbers from file!\nExiting....");
				System.exit(-1);
			}
		}
		return records;
	}

	/**
	 * @param records Student records.
	 * @return records whose points from mi, zi and lab together are greater than 25.
	 */
	private static long vratiBodovaViseOd25(List<StudentRecord> records) {
		return records.stream()
				.filter(r -> r.getMiPoints() + r.getZiPoints() + r.getLabPoints() > 25)
				.count();
	}

	/**
	 * @param records Student records.
	 * @return records whose grade is excellent.
	 */
	private static long vratiBrojOdlikasa(List<StudentRecord> records) {
		return records.stream()
				.filter(r -> r.getGrade() == 5)
				.count();
	}

	/**
	 * @param records Student records.
	 * @return list of records whose grade is excellent.
	 */
	private static List<StudentRecord> vratiListuOdlikasa(List<StudentRecord> records) {
		return records.stream()
				.filter(r -> r.getGrade() == 5)
				.collect(Collectors.toList());
	}

	/**
	 * @param records Student records.
	 * @return sorted list by total points of records whose grade is excellent.
	 */
	private static List<StudentRecord> vratiSortiranuListuOdlikasa(List<StudentRecord> records) {
		return records.stream()
				.filter(r -> r.getGrade() == 5)
				.sorted((r2, r1) -> Double.compare(r1.getMiPoints() + r1.getZiPoints() + r1.getLabPoints(),
						r2.getMiPoints() + r2.getZiPoints() + r2.getLabPoints()))
				.collect(Collectors.toList());
	}

	/**
	 * @param records Student records.
	 * @return sorted list by jmbag of records who did not pass.
	 */
	private static List<String> vratiPopisNepolozenih(List<StudentRecord> records) {
		return records.stream()
				.filter(r -> r.getGrade() == 1)
				.map(StudentRecord::getJmbag).sorted()
				.collect(Collectors.toList());
	}

	/**
	 * @param records Student records.
	 * @return map where key is grade value and map value is list of records whose
	 *         grade is determined by key value.
	 */
	private static Map<Integer, List<StudentRecord>> razvrstajStudentePoOcjenama(List<StudentRecord> records) {
		return records.stream()
				.collect(Collectors.groupingBy(StudentRecord::getGrade));
	}

	/**
	 * @param records Student records.
	 * @return map where key is grade value and map value is number of records with
	 *         grade determined by key value.
	 */
	private static Map<Integer, Integer> vratiBrojStudenataPoOcjenama(List<StudentRecord> records) {
		return records.stream()
				.collect(Collectors.toMap(StudentRecord::getGrade, r -> 1, (k, v) -> k + v));
	}

	/**
	 * @param records Student records.
	 * @return map where key is boolean value representing whether record had passed
	 *         or not and map value is list of records .
	 */
	private static Map<Boolean, List<StudentRecord>> razvrstajProlazPad(List<StudentRecord> records) {
		return records.stream()
				.collect(Collectors.partitioningBy(r -> r.getGrade() > 1));
	}
}
