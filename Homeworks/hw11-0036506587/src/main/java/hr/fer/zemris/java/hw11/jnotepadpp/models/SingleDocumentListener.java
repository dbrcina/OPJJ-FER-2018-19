package hr.fer.zemris.java.hw11.jnotepadpp.models;

import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;

/**
 * This interface represents <i>Observer</i> for modifications of
 * {@link SingleDocumentModel} in <i>Observer desing pattern</i>.
 * 
 * @author dbrcina
 *
 */
public interface SingleDocumentListener {

	/**
	 * Update method that is called when some document is modified.
	 * 
	 * @param model instance of {@link SingleDocumentModel}.
	 */
	void documentModifyStatusUpdated(SingleDocumentModel model);

	/**
	 * Update method that is called when another document is in focus in
	 * {@link JNotepadPP}.
	 * 
	 * @param model instance of {@link SingleDocumentModel}.
	 */
	void documentFilePathUpdated(SingleDocumentModel model);
}
