package hr.fer.zemris.java.hw11.jnotepadpp.models;

/**
 * This interface represents <i>Observer</i> for modifications of
 * {@link MultipleDocumentModel} in <i>Observer desing pattern</i>.
 * 
 * @author dbrcina
 *
 */
public interface MultipleDocumentListener {

	/**
	 * Update method that is called when current document has changed.
	 * 
	 * @param previousModel previous document.
	 * @param currentModel  new document.
	 * @throws NullPointerException if one of the arguments is <code>null</code>.
	 */
	void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel);

	/**
	 * Update method that is called when new document is added.
	 * 
	 * @param model new document.
	 * @throws NullPointerException if <code>model</code> is <code>null</code>.
	 */
	void documentAdded(SingleDocumentModel model);

	/**
	 * Update method that is called when some document is removed.
	 * 
	 * @param model some document.
	 * @throws NullPointerException if <code>model</code> is <code>null</code>.
	 */
	void documentRemoved(SingleDocumentModel model);
}
