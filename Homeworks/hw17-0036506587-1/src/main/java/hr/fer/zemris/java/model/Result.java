package hr.fer.zemris.java.model;

import java.nio.file.Path;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * <p>
 * Model of a result which is generated after successful query command. It
 * contains a path to file which took part in semantic similarity calculation
 * with provided query data and value of that calculation.
 * </p>
 * 
 * <p>
 * Instances of this class are comparable by values of semantic similarity
 * calculations. So, in a sorted collection, results are sorted in descending
 * order by values of calculation.
 * </p>
 * 
 * @author dbrcina
 *
 */
public class Result implements Comparable<Result> {

	/**
	 * Path to file.
	 */
	private Path path;
	/**
	 * Value of semantic similarity calculation.
	 */
	private double semanticSimilarity;
	/**
	 * Instance of decimal formatter.
	 */
	private DecimalFormat formatter;

	/**
	 * Constructor used for initialization.
	 * 
	 * @param path               path
	 * @param semanticSimilarity calculation.
	 */
	public Result(Path path, double semanticSimilarity) {
		this.path = path;
		this.semanticSimilarity = semanticSimilarity;

		DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.getDefault());
		otherSymbols.setDecimalSeparator('.');
		formatter = new DecimalFormat("#0.0000", otherSymbols);
	}

	/**
	 * Getter for file's path.
	 * 
	 * @return path to file.
	 */
	public Path getPath() {
		return path;
	}

	/**
	 * Getter for semantic similarity calculation.
	 * 
	 * @return calculation.
	 */
	public double getSemanticSimilarity() {
		return semanticSimilarity;
	}

	@Override
	public int compareTo(Result other) {
		return Double.compare(other.semanticSimilarity, this.semanticSimilarity);
	}

	@Override
	public String toString() {
		return "(" + formatter.format(semanticSimilarity) + ") " + path;
	}
}
