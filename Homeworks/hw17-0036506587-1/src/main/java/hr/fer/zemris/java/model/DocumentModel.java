package hr.fer.zemris.java.model;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import hr.fer.zemris.java.math.NDimensionalVector;
import hr.fer.zemris.java.search_engine.SearchEngine;

/**
 * <p>
 * This class represents document model which contains information about where
 * certain document is located.
 * </p>
 * 
 * <p>
 * It also registers all words and their frequencies from document into
 * underlaying map. Based on that information and global vocabulary, a
 * <b><i>term frequency</i></b> vector is created.<br>
 * 
 * <p>
 * Alongside <b><i>term frequency</i></b>, <b><i>inverse document
 * frequency</i></b> and finally <b><i>term frequency - inverse document
 * frequency</i></b> vectors are also created.<br>
 * </p>
 * 
 * <p>
 * <b><i>TF(t,d) = Term Frequency(t,d):</i></b> is the number of times that term
 * t occurs in document d.
 * </p>
 * 
 * <p>
 * <b><i>IDF(t,D) = Inverse Document Frequency(t,D):</i></b> measures the
 * importance of term t in all documents (D), we obtain this measure by dividing
 * the total number of documents by the number of documents containing the term,
 * and then taking the logarithm of that quotient.
 * </p>
 * 
 * <p>
 * Finally, the <b><i>Term Frequency - Inverse Document Frequency</i></b> is
 * obtained by multiplying the two measures:<br>
 * <b><i>TF-IDF(t,d) = TF(t,d) * IDF(t,D)</i></b>
 * </p>
 * 
 * @author dbrcina
 *
 */
public class DocumentModel {

	/**
	 * Location of this document.
	 */
	private Path path;
	/**
	 * Map of words where key values are words and values are frequencies of certain
	 * word.
	 */
	private Map<String, Integer> words = new HashMap<>();

	/**
	 * <i>tfidf-vector</i> representation of this document.
	 */
	private NDimensionalVector tfidfVector;

	/**
	 * Constructor used for initializing.
	 * 
	 * @param path path.
	 */
	public DocumentModel(Path path) {
		this.path = path;
	}

	/**
	 * Getter for <i>tfidf-vector</i> representation of this model.
	 * 
	 * @return tfidf vector.
	 */
	public NDimensionalVector getTFIDFVector() {
		return tfidfVector;
	}

	/**
	 * Getter for document's location.
	 * 
	 * @return document's location.
	 */
	public Path getPath() {
		return path;
	}

	/**
	 * Getter for {@link #words} map.
	 * 
	 * @return map of words.
	 */
	public Map<String, Integer> getWords() {
		return words;
	}

	/**
	 * Registers provided <i>word</i> into underlaying map of words alongside with
	 * its frequency.
	 * 
	 * @param word word.
	 */
	public void registerWord(String word) {
		words.merge(word, 1, (oldV, newV) -> oldV + newV);
	}

	public void vectorize() {
		var vocabulary = SearchEngine.getVocabulary();
		tfidfVector = new NDimensionalVector(vocabulary.size());
		var idfVector = SearchEngine.getIDFVector();
		for (var entry : words.entrySet()) {
			Double wordIDF = idfVector.get(entry.getKey());
			if (wordIDF == null) {
				wordIDF = calculateIDF(entry.getKey());
				idfVector.put(entry.getKey(), wordIDF);
			}
			double wordTFIDF = words.get(entry.getKey()) * wordIDF;
			tfidfVector.registerValue(wordTFIDF, vocabulary.get(entry.getKey()));
		}
	}

	/**
	 * Helper method used for calculation of <i>inverse-document-frequency</i> for
	 * provided <i>word</i>.
	 * 
	 * @param word word.
	 * @return idf value.
	 */
	private double calculateIDF(String word) {
		double counter = 0;
		var documents = SearchEngine.getDocuments();
		for (var document : documents) {
			if (document.words.containsKey(word)) {
				counter++;
			}
		}
		return Math.log10(documents.size() / counter);
	}
}
