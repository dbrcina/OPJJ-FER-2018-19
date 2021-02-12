package hr.fer.zemris.java.hw11.jnotepadpp.models;

import java.nio.file.Path;

/**
 * <p>
 * Represents a model capable of holding zero, one or more documents, where each
 * document is having a concept of current document - the one which is shown to
 * the user and on which user works.
 * </p>
 * 
 * <p>
 * This interface is <i>Subject</i> in <i>Observer design pattern</i>.
 * </p>
 * 
 * @author dbrcina
 *
 */
public interface MultipleDocumentModel extends Iterable<SingleDocumentModel> {

	/**
	 * Creates a new document.
	 * 
	 * @return instance of {@link SingleDocumentModel} as a result.
	 */
	SingleDocumentModel createNewDocument();

	/**
	 * Getter for current focused document.
	 * 
	 * @return current document.
	 */
	SingleDocumentModel getCurrentDocument();

	/**
	 * Loads a document from given <code>path</code>.
	 * 
	 * @param path path.
	 * @return new instance of {@link SingleDocumentModel} as a result.
	 * @throws NullPointerException if <code>path</code> is <code>null</code>.
	 */
	SingleDocumentModel loadDocument(Path path);

	/**
	 * Saves document - <code>model</code> to <code>newPath</code>.
	 * <code>newPath</code> can be <code>null</code> and in that case, document is
	 * saved to it's original path, otherwise document is saved to
	 * <code>newPath</code>.
	 * 
	 * @param model   document.
	 * @param newPath new path.
	 * @throws NullPointerException if <code>model</code> is <code>null</code>.
	 */
	void saveDocument(SingleDocumentModel model, Path newPath);

	/**
	 * Closes document - <code>model</code> forcefully.
	 * 
	 * @param model document.
	 */
	void closeDocument(SingleDocumentModel model);

	/**
	 * Adds {@link MultipleDocumentListener} <code>l</code> into collection of
	 * listeners.
	 * 
	 * @param l listener.
	 * @throws NullPointerException if <code>l</code> is <code>null</code>.
	 */
	void addMultipleDocumentListener(MultipleDocumentListener l);

	/**
	 * Removes {@link MultipleDocumentListener} <code>l</code> from collection of
	 * listeners.
	 * 
	 * @param l listener.
	 * @throws NullPointerException if <code>l</code> is <code>null</code>.
	 */
	void removeMultipleDocumentListener(MultipleDocumentListener l);

	/**
	 * Getter for number of documents in this model.
	 * 
	 * @return number of documents.
	 */
	int getNumberOfDocuments();

	/**
	 * Getter for document at <code>index</code>. Valid <code>index</code> is from
	 * <code>[0,numberOfDocuments]</code> as determined by
	 * {@link #getNumberOfDocuments()} method.
	 * 
	 * @param index index.
	 * @return instance of {@link SingleDocumentModel}.
	 * @throws IllegalArgumentException if <code>index</code> is invalid.
	 */
	SingleDocumentModel getDocument(int index);
}
