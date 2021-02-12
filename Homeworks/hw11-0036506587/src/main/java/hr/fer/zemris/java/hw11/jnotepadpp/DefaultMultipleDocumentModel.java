package hr.fer.zemris.java.hw11.jnotepadpp;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;

import javax.swing.ImageIcon;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import hr.fer.zemris.java.hw11.jnotepadpp.models.MultipleDocumentListener;
import hr.fer.zemris.java.hw11.jnotepadpp.models.MultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.models.SingleDocumentListener;
import hr.fer.zemris.java.hw11.jnotepadpp.models.SingleDocumentModel;

/**
 * An implementation of {@link MultipleDocumentModel} model.
 * 
 * @author dbrcina
 *
 */
public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel {

	/**
	 * Default serial ID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Path to green diskette icon.
	 */
	private static final String GREEN_DISK_PATH = "icons/greenDisk.png";

	/**
	 * Path to red diskette icon.
	 */
	private static final String RED_DISK_PATH = "icons/redDisk.png";

	/**
	 * Path to current dir.
	 */
	private static final Path CURRENT_DIR = Paths.get(".").toAbsolutePath().normalize();

	/**
	 * Unnamed document name.
	 */
	private static final String UNNAMED_DOC = "(unnamed)";

	/**
	 * List of {@link SingleDocumentModel} models.
	 */
	private List<SingleDocumentModel> documents;

	/**
	 * List of {@link MultipleDocumentListener} listeners.
	 */
	private List<MultipleDocumentListener> listeners;

	/**
	 * Single listener.
	 */
	private SingleDocumentListener singleListener;

	/**
	 * Current document.
	 */
	private SingleDocumentModel currentDocument;

	/**
	 * Red diskette.
	 */
	private ImageIcon modifiedIcon;

	/**
	 * Green diskette.
	 */
	private ImageIcon unmodifiedIcon;

	/**
	 * Constructor used for initialization.
	 */
	public DefaultMultipleDocumentModel() {
		setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
		documents = new ArrayList<>();
		listeners = new ArrayList<>();
		modifiedIcon = imageLoader(RED_DISK_PATH);
		unmodifiedIcon = imageLoader(GREEN_DISK_PATH);
		initListener();

		addChangeListener(l -> {
			if (documents.size() != 0) {
				notifyListeners(ml -> ml.currentDocumentChanged(currentDocument, documents.get(getSelectedIndex())));
				currentDocument = documents.get(getSelectedIndex());
			}
		});
	}

	/**
	 * Initialization of {@link #singleListener}.
	 */
	private void initListener() {
		singleListener = new SingleDocumentListener() {
			@Override
			public void documentModifyStatusUpdated(SingleDocumentModel model) {
				setIconAt(getSelectedIndex(), model.isModified() ? modifiedIcon : unmodifiedIcon);
			}

			@Override
			public void documentFilePathUpdated(SingleDocumentModel model) {
				int index = getSelectedIndex();
				Path path = model.getFilePath().normalize();
				setTitleAt(index, path.getFileName().toString());
				setToolTipTextAt(index, path.toString());
			}
		};
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SingleDocumentModel createNewDocument() {
		currentDocument = new DefaultSingleDocumentModel("", null);
		documents.add(currentDocument);

		JScrollPane jp = new JScrollPane(currentDocument.getTextComponent());

		add(UNNAMED_DOC, jp);
		setSelectedComponent(jp);
		setToolTipTextAt(getSelectedIndex(), UNNAMED_DOC);
		setIconAt(getSelectedIndex(), unmodifiedIcon);

		currentDocument.addSingleDocumentListener(singleListener);
		notifyListeners(l -> l.documentAdded(currentDocument));

		return currentDocument;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SingleDocumentModel getCurrentDocument() {
		return currentDocument;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SingleDocumentModel loadDocument(Path path) {
		Objects.requireNonNull(path, "Path cannot be null!");

		if (!Files.exists(path)) {
			throw new IllegalArgumentException(path + " does not exist!");
		}

		SingleDocumentModel model = checkForExisting(path);

		if (model != null) {
			setSelectedIndex(documents.indexOf(model));
			notifyListeners(l -> l.currentDocumentChanged(currentDocument, model));
			currentDocument = model;
			return currentDocument;
		}

		try {
			String fileName = path.getFileName().toString();
			String text = Files.readString(path, StandardCharsets.UTF_8);
			SingleDocumentModel newModel = new DefaultSingleDocumentModel(text, path);
			documents.add(newModel);
			JScrollPane jp = new JScrollPane(newModel.getTextComponent());
			add(fileName, jp);
			setSelectedComponent(jp);
			setToolTipTextAt(getSelectedIndex(), CURRENT_DIR.resolve(path).toString());
			setIconAt(getSelectedIndex(), unmodifiedIcon);
			currentDocument = newModel;
			currentDocument.addSingleDocumentListener(singleListener);
			notifyListeners(l -> l.documentAdded(newModel));
		} catch (IOException e) {
			throw new RuntimeException("Error occured while reading from " + path);
		}
		return currentDocument;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void saveDocument(SingleDocumentModel model, Path newPath) {
		try {
			Files.writeString(newPath == null ? model.getFilePath() : newPath, model.getTextComponent().getText(),
					StandardCharsets.UTF_8);
			currentDocument.setModified(false);
			if (newPath != null) {
				currentDocument.setFilePath(newPath);
			}
			setTitleAt(getSelectedIndex(), currentDocument.getFilePath().getFileName().toString());
			setToolTipTextAt(getSelectedIndex(), currentDocument.getFilePath().toString());
		} catch (IOException e) {
			throw new RuntimeException("Error occured while saving file!");
		}

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void closeDocument(SingleDocumentModel model) {
		documents.remove(model);
		removeTabAt(getSelectedIndex());
		model.removeSingleDocumentListener(singleListener);
		notifyListeners(l -> l.documentRemoved(model));

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.add(l);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void removeMultipleDocumentListener(MultipleDocumentListener l) {
		listeners.remove(l);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getNumberOfDocuments() {
		return documents.size();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public SingleDocumentModel getDocument(int index) {
		if (index < 0 || index > getNumberOfDocuments()) {
			throw new IllegalArgumentException("Index is invalid!");
		}
		return documents.get(index);
	}

	@Override
	public Iterator<SingleDocumentModel> iterator() {
		return new DocumentIterator();
	}

	/**
	 * Private iterator that iterates through {@link SingleDocumentModel} documents.
	 * 
	 * @author dbrcina
	 *
	 */
	private class DocumentIterator implements Iterator<SingleDocumentModel> {

		/**
		 * Current index in iteration.
		 */
		private int index;

		@Override
		public boolean hasNext() {
			return index < documents.size();
		}

		@Override
		public SingleDocumentModel next() {
			if (!hasNext()) {
				throw new NoSuchElementException("There are no more documents!");
			}
			return documents.get(index++);
		}

	}

	/**
	 * Loader method used for loading a picture from <code>path</code> path. If
	 * image on provided <code>path</code> does not exist or if error occures when
	 * reading bytes from picture, a message is printed to {@link System#out}.
	 * 
	 * @param path path.
	 * @return new instance of {@link ImageIcon} as a result.
	 */
	private ImageIcon imageLoader(String path) {
		ImageIcon icon = null;
		try (InputStream is = this.getClass().getResourceAsStream(path)) {
			if (is == null) {
				System.out.println("Could not find path specified!\nTerminating...");
				System.exit(-1);
			}
			byte[] bytes = is.readAllBytes();
			icon = new ImageIcon(bytes);
		} catch (IOException e) {
			System.out.println("Error occured while reading bytes from picture!\nTerminating..");
			System.exit(-1);
		}
		return icon;
	}

	/**
	 * Checks whether document from <code>path</code> exist in this tab model.
	 * 
	 * @param path path.
	 * @return <code>null</code> or instance of existing document.
	 */
	private SingleDocumentModel checkForExisting(Path path) {
		for (SingleDocumentModel documentModel : documents) {
			try {
				if (documentModel.getFilePath() != null) {
					if (Files.isSameFile(path, documentModel.getFilePath())) {
						return documentModel;
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * Notifies listeners about changes as determined by <code>action</code>
	 * 
	 * @param action action.
	 */
	private void notifyListeners(Consumer<MultipleDocumentListener> action) {
		listeners.forEach(l -> action.accept(l));
	}
}
