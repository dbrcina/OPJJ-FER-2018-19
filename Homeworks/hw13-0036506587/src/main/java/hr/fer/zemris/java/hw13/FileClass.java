package hr.fer.zemris.java.hw13;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Random;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

import hr.fer.zemris.java.hw13.javabeans.Band;

/**
 * Utility class used for loading content from some file or creating a new file.
 * 
 * @author dbrcina
 *
 */
public class FileClass {

	/**
	 * Multiplier used for generating random numbers from <i>[0,
	 * RANDOM_MULTIPLIER]</i>.
	 */
	private static final int RANDOM_MULTIPLIER = 100;

	/**
	 * <p>
	 * Loads band specification from file whose path is specified in parameter
	 * <code>fileName</code> and stores its content into {@link SortedMap} where key
	 * values are band's ID and values are instances of {@link Band}.
	 * </p>
	 * 
	 * <p>
	 * Map is sorted by band ID's.
	 * </p>
	 * 
	 * @param req servlet request.
	 * @return new instance of {@link SortedMap}
	 * @throws IOException if something goes wrong while reading from a file.
	 */
	public static SortedMap<String, Band> loadBands(String fileName) throws IOException {
		SortedMap<String, Band> bands = new TreeMap<>();
		try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
			for (String line; (line = br.readLine()) != null;) {
				String[] parts = line.split("\\t+");
				Band band = new Band(parts[0], parts[1], parts[2]);
				bands.put(band.getID(), band);
			}
		}
		return bands;
	}

	/**
	 * Creates new file as determined by <code>fileName</code>. File content is
	 * filled with band IDs and initial number of votes for each band.<br>
	 * Initial votes are generated randomly.
	 * 
	 * @param fileName file name.
	 * @param bands    collection of bands.
	 * @throws IOException if something goes wrong when creating/writing to new
	 *                     file.
	 */
	public static void createResultsFile(String fileName, SortedMap<String, Band> bands) throws IOException {
		Random random = new Random();
		try (PrintWriter pwr = new PrintWriter(fileName, StandardCharsets.UTF_8)) {
			for (Map.Entry<String, Band> entry : bands.entrySet()) {
				Band band = entry.getValue();
				band.setVotes(random.nextInt(RANDOM_MULTIPLIER));
				pwr.println(entry.getKey() + "\t" + band.getVotes());
			}
			pwr.flush();
		} catch (Exception ignorable) {
		}
	}
	
	/**
	 * This method reads results from <code>path</code> file and stores them in
	 * {@link Map} where keys are band name and value is number of votes.<br>
	 * Also, it sorts results by number of votes in <b>descending</b> order and
	 * updates number of votes for <code>bands</code>.
	 * 
	 * @param path  path to results file.
	 * @param bands sorted map of bands.
	 * @return map sorted by votes in descending order.
	 * @throws IOException if something happens while reading from a file.
	 */
	public static Map<String, Integer> validateResults(Path path, SortedMap<String, Band> bands) throws IOException{
		// read all lines and sort them by number of votes
		Map<String, Integer> results = Files.readAllLines(path, StandardCharsets.UTF_8)
				.stream()
				.collect(Collectors.toMap(
						s -> bands.get(s.split("\t")[0]).getName(), 
						s -> Integer.parseInt(s.split("\t")[1])
				)
		);
		// update votes	
		for (Map.Entry<String, Integer> resultEntry : results.entrySet()) {
			for (Band bandValue : bands.values()) {
				if (resultEntry.getKey().equals(bandValue.getName())) {
					bandValue.setVotes(resultEntry.getValue());
				}
			}
		}

		return MapUtil.sortByValueDesc(results);
	}
}
