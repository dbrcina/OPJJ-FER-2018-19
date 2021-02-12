package hr.fer.zemris.java.visitors;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.Map;

import hr.fer.zemris.java.model.DocumentModel;
import hr.fer.zemris.java.search_engine.SearchEngine;
import hr.fer.zemris.java.util.Util;

/**
 * <p>
 * An implementation of {@link SimpleFileVisitor} model which is used for
 * reading all provided files from some documents folder and preparing global
 * vocabulary and {@link DocumentModel} model representation of every document
 * as a result.
 * </p>
 * 
 * <p>
 * When a document is read, it is registered with
 * {@link SearchEngine#addDocument(DocumentModel)} method as being read.
 * </p>
 * 
 * @author dbrcina
 *
 */
public class DocumentVisitor extends SimpleFileVisitor<Path> {

	/**
	 * Variable which is used for vocabulary map. It is serial type variable which
	 * means it is incremented by default when it is added as a value into map.
	 */
	private static int vectorIndex;
	/**
	 * A reference to global vocabulary which can be accessed through
	 * {@link SearchEngine#getVocabulary()} method.
	 */
	private Map<String, Integer> vocabulary = SearchEngine.getVocabulary();
	/**
	 * A reference to list of stopwords which can be accessed through
	 * {@link SearchEngine#getStopwords()} method.
	 */
	private List<String> stopwords = SearchEngine.getStopwords();

	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
		if (Files.isReadable(file)) {
			DocumentModel document = new DocumentModel(file);
			readFromFile(file, document);
			SearchEngine.addDocument(document);
		}
		return FileVisitResult.CONTINUE;
	}

	/**
	 * Method used for reading content from provided <i>file</i> and preparing
	 * global vocabulary alongside with creating {@link DocumentModel}
	 * representation of current <i>file</i>.
	 * 
	 * @param file     file that needs to be read.
	 * @param document model of current file.
	 * @throws IOException if something goes wrong while reading from a file.
	 */
	private void readFromFile(Path file, DocumentModel document) throws IOException {
		Files.readAllLines(file).forEach(line -> {
			String[] words = line.split("\\s+");
			for (String word : words) {
				if (word.isEmpty()) {
					continue;
				}
				word = word.toLowerCase().trim();
				String[] parsedWord = Util.parseWord(word);
				for (String result : parsedWord) {
					if (result.isEmpty() || stopwords.contains(result)) {
						continue;
					}
					// add to vocabulary if it doesn't exist
					if (!vocabulary.containsKey(result)) {
						vocabulary.put(result, vectorIndex++);
					}
					// register word into document model
					document.registerWord(result);
				}
			}
		});
//		try (BufferedReader br = new BufferedReader(Files.newBufferedReader(file))) {
//			String line = null;
//			while ((line = br.readLine()) != null) {
//				String[] words = line.split("\\s+");
//				for (String word : words) {
//					if (word.isEmpty()) {
//						continue;
//					}
//					word = word.toLowerCase().trim();
//					String[] parsedWord = Util.parseWord(word);
//					for (String result : parsedWord) {
//						if (result.isEmpty() || stopwords.contains(result)) {
//							continue;
//						}
//						if (!vocabulary.containsKey(result)) {
//							vocabulary.put(result, vectorIndex++);
//						}
//						document.registerWord(result);
//					}
//				}
//			}
//		}
	}
}
