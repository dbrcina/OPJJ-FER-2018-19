package hr.fer.zemris.java.hw11.jnotepadpp.models;

import java.nio.file.Path;

import javax.swing.JTextArea;

/**
 * <p>
 * Represents a model of single document, having information about file path
 * from which document was loaded (can be <code>null</code> for new document),
 * document modification status and reference to <i>Swing</i> component which is
 * used for editing (each document has its own editor component).
 * </p>
 * 
 * <p>
 * This interface is <i>Subject</i> in <i>Observer design pattern</i>.
 * </p>
 * 
 * @author dbrcina
 *
 */
public interface SingleDocumentModel {

	/**
	 * Getter for document's editor component.
	 * 
	 * @return editor component.
	 */
	JTextArea getTextComponent();

	/**
	 * Getter for document's path.
	 * 
	 * @return documen't path.
	 */
	Path getFilePath();

	/**
	 * Setter for document's path.
	 * 
	 * @param path path.
	 * @throws NullPointerException if <code>path</code> is <code>null</code>.
	 */
	void setFilePath(Path path);

	/**
	 * Checks whether document is modified.
	 * 
	 * @return <code>true</code> if document is modified, otherwise
	 *         <code>false</code>.
	 */
	boolean isModified();

	/**
	 * Setter for document's modification.
	 * 
	 * @param modified boolean flag for modification.
	 * @throws NullPointerException if <code>modified</code> is <code>null</code>.
	 */
	void setModified(boolean modified);

	/**
	 * Adds {@link SingleDocumentListener} <code>l</code> into collection of
	 * listeners.
	 * 
	 * @param l listener.
	 * @throws NullPointerException if <code>l</code> is <code>null</code>.
	 */
	void addSingleDocumentListener(SingleDocumentListener l);

	/**
	 * Removes {@link SingleDocumentListener} <code>l</code> from collection of
	 * listeners.
	 * 
	 * @param l listener.
	 * @throws NullPointerException if <code>l</code> is <code>null</code>.
	 */
	void removeSingleDocumentListener(SingleDocumentListener l);
}
