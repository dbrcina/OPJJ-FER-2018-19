package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import hr.fer.zemris.java.hw11.jnotepadpp.models.SingleDocumentListener;
import hr.fer.zemris.java.hw11.jnotepadpp.models.SingleDocumentModel;

/**
 * An implementation of {@link SingleDocumentModel} interface.
 * 
 * @author dbrcina
 *
 */
public class DefaultSingleDocumentModel implements SingleDocumentModel {

	/**
	 * Document's editor.
	 */
	private JTextArea editor;

	/**
	 * Document's path.
	 */
	private Path path;

	/**
	 * Boolean flag which tells whether current document has been modified.
	 */
	private boolean modified;

	/**
	 * List of {@link SingleDocumentListener} listeners.
	 */
	private List<SingleDocumentListener> listeners;

	/**
	 * Constructor used for initialization of single document.
	 * 
	 * @param text input text.
	 * @param path document's path.
	 * @throws NullPointerException if one of the arguments is <code>null</code>.
	 */
	public DefaultSingleDocumentModel(String text, Path path) {
		this.editor = new JTextArea(text);
		this.path = path;
		this.modified = false;
		this.listeners = new ArrayList<>();

		editor.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				setModified(true);
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				setModified(true);
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				setModified(true);
			}
		});
	}

	/**
	 * {@inheritDoc}
	 */
	public JTextArea getTextComponent() {
		return editor;
	}

	/**
	 * {@inheritDoc}
	 */
	public Path getFilePath() {
		return path;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setFilePath(Path path) {
		this.path = Objects.requireNonNull(path, "Path cannot be null!");
		notifyListeners(l -> l.documentFilePathUpdated(this));
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isModified() {
		return modified;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setModified(boolean modified) {
		this.modified = Objects.requireNonNull(modified, "Modified cannot be null!");
		notifyListeners(l -> l.documentModifyStatusUpdated(this));
	}

	/**
	 * {@inheritDoc}
	 */
	public void addSingleDocumentListener(SingleDocumentListener l) {
		listeners.add(l);
	}

	/**
	 * {@inheritDoc}
	 */
	public void removeSingleDocumentListener(SingleDocumentListener l) {
		listeners.remove(l);
	}

	/**
	 * Method used to inform every listener about the change.
	 * 
	 * @param action action.
	 */
	private void notifyListeners(Consumer<SingleDocumentListener> action) {
		listeners.forEach(l -> action.accept(l));
	}
}
