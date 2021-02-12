package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.function.Function;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;

import hr.fer.zemris.java.hw11.jnotepadpp.local.FormLocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LJMenu;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAction;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.models.MultipleDocumentListener;
import hr.fer.zemris.java.hw11.jnotepadpp.models.SingleDocumentModel;

public class JNotepadPP extends JFrame {

	/**
	 * Default serial ID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Instance of {@link ILocalizationProvider}
	 */
	private ILocalizationProvider flp = new FormLocalizationProvider(LocalizationProvider.getInstance(), this);

	/**
	 * Instance of {@link MultipleDocumentListener}
	 */
	private MultipleDocumentListener listener = createListener();

	/**
	 * Label length.
	 */
	private JLabel length = new JLabel();

	/**
	 * Info label: ln, col and sel
	 */
	private JLabel info = new JLabel();

	/**
	 * Clock that shows current datetime
	 */
	private Clock clock = new Clock();

	/**
	 * Tab model.
	 */
	private DefaultMultipleDocumentModel tabModel = new DefaultMultipleDocumentModel();

	/**
	 * Constructor used for initialization
	 */
	public JNotepadPP() {
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setSize(1000, 500);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				exitAction();
			}
		});

		flp.addLocalizationListener(() -> updateStatus(tabModel.getCurrentDocument().getTextComponent()));
		tabModel.addMultipleDocumentListener(listener);
		initGUI();
	}

	/**
	 * Creates a new instance of {@link MultipleDocumentListener}.
	 * 
	 * @return listener.
	 */
	private MultipleDocumentListener createListener() {
		return new MultipleDocumentListener() {

			@Override
			public void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel) {
				JTextArea editor = currentModel.getTextComponent();
				Path path = currentModel.getFilePath();
				setTitle((path == null ? "(unnamed)" : path.toString()) + " - JNotepad++");

				editor.addCaretListener(l -> {
					boolean selection = editor.getCaret().getDot() != editor.getCaret().getMark();
					upperAction.setEnabled(selection);
					lowerAction.setEnabled(selection);
					invertAction.setEnabled(selection);
					cutAction.setEnabled(selection);
					copyAction.setEnabled(selection);
					uniqueAction.setEnabled(selection);
					ascAction.setEnabled(selection);
					descAction.setEnabled(selection);
					
					updateStatus(editor);
				});
			}

			@Override
			public void documentRemoved(SingleDocumentModel model) {
			}

			@Override
			public void documentAdded(SingleDocumentModel model) {
			}
		};
	}

	/**
	 * Creates a new document.
	 */
	@SuppressWarnings("serial")
	private final Action newDocAction = new LocalizableAction("new", flp) {
		@Override
		public void actionPerformed(ActionEvent e) {
			tabModel.createNewDocument();
		}
	};

	/**
	 * Opens an existing document.
	 */
	@SuppressWarnings("serial")
	private final Action openAction = new LocalizableAction("open", flp) {
		@Override
		public void actionPerformed(ActionEvent e) {
			openAction();
		}
	};

	/**
	 * Saves current opened document.
	 */
	@SuppressWarnings("serial")
	private final Action saveAction = new LocalizableAction("save", flp) {
		@Override
		public void actionPerformed(ActionEvent e) {
			saveAction();
		}
	};

	/**
	 * Saves opened document as some file.
	 */
	@SuppressWarnings("serial")
	private final Action saveAsAction = new LocalizableAction("save_as", flp) {
		@Override
		public void actionPerformed(ActionEvent e) {
			saveAsAction();
		}
	};

	/**
	 * Provides some statistical information about number of chars, non-blank chars
	 * and lines.
	 */
	@SuppressWarnings("serial")
	private final Action statsAction = new LocalizableAction("stats", flp) {
		@Override
		public void actionPerformed(ActionEvent e) {
			statsAction();
		}
	};

	/**
	 * Closes current document.
	 */
	@SuppressWarnings("serial")
	private final Action closeAction = new LocalizableAction("close", flp) {
		@Override
		public void actionPerformed(ActionEvent e) {
			closeAction();
		}
	};

	/**
	 * Closes application.
	 */
	@SuppressWarnings("serial")
	private final Action exitAction = new LocalizableAction("exit", flp) {
		@Override
		public void actionPerformed(ActionEvent e) {
			exitAction();
		}
	};

	/**
	 * Cuts the selected text.
	 */
	@SuppressWarnings("serial")
	private final Action cutAction = new LocalizableAction("cut", flp) {
		@Override
		public void actionPerformed(ActionEvent e) {
			new DefaultEditorKit.CutAction().actionPerformed(e);
		}
	};

	/**
	 * Copies the selected text.
	 */
	@SuppressWarnings("serial")
	private final Action copyAction = new LocalizableAction("copy", flp) {
		@Override
		public void actionPerformed(ActionEvent e) {
			new DefaultEditorKit.CopyAction().actionPerformed(e);
		}
	};

	/**
	 * Pastes copied text.
	 */
	@SuppressWarnings("serial")
	private final Action pasteAction = new LocalizableAction("paste", flp) {
		@Override
		public void actionPerformed(ActionEvent e) {
			new DefaultEditorKit.PasteAction().actionPerformed(e);
		}
	};

	/**
	 * Changes language to croatian.
	 */
	@SuppressWarnings("serial")
	private final Action hrAction = new LocalizableAction("hr", flp) {
		@Override
		public void actionPerformed(ActionEvent e) {
			LocalizationProvider.getInstance().setLanguage("hr");
		}
	};

	/**
	 * Changes language to english.
	 */
	@SuppressWarnings("serial")
	private final Action enAction = new LocalizableAction("en", flp) {
		@Override
		public void actionPerformed(ActionEvent e) {
			LocalizationProvider.getInstance().setLanguage("en");
		}
	};

	/**
	 * Changes language to german.
	 */
	@SuppressWarnings("serial")
	private final Action deAction = new LocalizableAction("de", flp) {
		@Override
		public void actionPerformed(ActionEvent e) {
			LocalizationProvider.getInstance().setLanguage("de");
		}
	};

	/**
	 * Converts selected text into upper case.
	 */
	@SuppressWarnings("serial")
	private final Action upperAction = new LocalizableAction("to_uppercase", flp) {
		@Override
		public void actionPerformed(ActionEvent e) {
			caseAction(s -> s.toUpperCase());
		}
	};

	/**
	 * Converts selected text into lower case.
	 */
	@SuppressWarnings("serial")
	private final Action lowerAction = new LocalizableAction("to_lowercase", flp) {
		@Override
		public void actionPerformed(ActionEvent e) {
			caseAction(s -> s.toLowerCase());
		}
	};

	/**
	 * Toggles case of selected text.
	 */
	@SuppressWarnings("serial")
	private final Action invertAction = new LocalizableAction("invert_case", flp) {
		@Override
		public void actionPerformed(ActionEvent e) {
			caseAction(s -> toggleCase(s));
		}
	};

	/**
	 * Sorts selected lines in ascending order.
	 */
	@SuppressWarnings("serial")
	private final Action ascAction = new LocalizableAction("asc", flp) {
		@Override
		public void actionPerformed(ActionEvent e) {
			sortAction("ascending");
		}
	};

	/**
	 * Sorts selected lines in descending order.
	 */
	@SuppressWarnings("serial")
	private final Action descAction = new LocalizableAction("desc", flp) {
		@Override
		public void actionPerformed(ActionEvent e) {
			sortAction("descending");
		}
	};

	/**
	 * Removes duplicate lines.
	 */
	@SuppressWarnings("serial")
	private final Action uniqueAction = new LocalizableAction("unique", flp) {
		@Override
		public void actionPerformed(ActionEvent e) {
			uniqueAction();
		}
	};

	/**
	 * Updates the status bar.
	 * 
	 * @param editor editor.
	 */
	protected void updateStatus(JTextArea editor) {
		int len = editor.getText().length();
		length.setText(flp.getString("length") + ": " + len);
		try {
			int pos = editor.getCaretPosition();
			int line = editor.getLineOfOffset(pos);
			int column = pos - editor.getLineStartOffset(line);
			int selected = Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark());
			info.setText(String.format("%s:%d %s:%d %s:%d", flp.getString("ln"), line, flp.getString("col"), column,
					flp.getString("sel"), selected));
		} catch (BadLocationException ignorable) {
		}
	}

	/**
	 * Implementation of case action as determined by <code>action</code>.
	 * 
	 * @param action action: it can be uppercase, lowercase or togglecase.
	 */
	protected void caseAction(Function<String, String> action) {
		JTextComponent tcomp = tabModel.getCurrentDocument().getTextComponent();

		int start = Math.min(tcomp.getCaret().getDot(), tcomp.getCaret().getMark());
		int len = Math.abs(tcomp.getCaret().getDot() - tcomp.getCaret().getMark());

		if (len < 1) {
			return;
		}

		Document doc = tcomp.getDocument();

		try {
			String text = doc.getText(start, len);
			text = action.apply(text);

			doc.remove(start, len);
			doc.insertString(start, text, null);
		} catch (BadLocationException ignored) {
		}
	}

	/**
	 * Toggles <code>s</code> case.
	 * 
	 * @param s text.
	 * @return new {@link String} as a result.
	 */
	protected String toggleCase(String s) {
		char[] chars = s.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			if (Character.isUpperCase(chars[i])) {
				chars[i] = Character.toLowerCase(chars[i]);
			} else if (Character.isLowerCase(chars[i])) {
				chars[i] = Character.toUpperCase(chars[i]);
			}
		}
		return new String(chars);
	}

	/**
	 * Implementation of unique action.
	 */
	protected void uniqueAction() {
		JTextArea editor = tabModel.getCurrentDocument().getTextComponent();
		Document doc = editor.getDocument();

		int start = Math.min(editor.getCaret().getDot(), editor.getCaret().getMark());
		int len = Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark());

		String selected = editor.getSelectedText();

		Set<String> set = new LinkedHashSet<>(Arrays.asList(selected.split("\n")));

		StringBuilder sb = new StringBuilder();
		set.forEach(s -> sb.append(s + "\n"));

		try {
			doc.remove(start, len);
			doc.insertString(start, sb.toString(), null);
		} catch (BadLocationException ignorable) {
		}

	}

	/**
	 * Implementation of sort action as determined by <code>direction</code>.
	 * 
	 * @param direction direction
	 */
	protected void sortAction(String direction) {
		JTextArea editor = tabModel.getCurrentDocument().getTextComponent();
		Document doc = editor.getDocument();

		int start = Math.min(editor.getCaret().getDot(), editor.getCaret().getMark());
		int len = Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark());

		try {
			start = editor.getLineStartOffset(editor.getLineOfOffset(start));
			len = editor.getLineEndOffset(editor.getLineOfOffset(len + start));
			String text = doc.getText(start, len - start);

			List<String> list = new ArrayList<>(Arrays.asList(text.split("\n")));

			if (list.size() == 1) {
				return;
			}

			Locale locale = new Locale(LocalizationProvider.getInstance().getLanguage());
			Collator collator = Collator.getInstance(locale);

			list.sort(direction.equals("ascending") ? collator : collator.reversed());

			StringBuilder sb = new StringBuilder();
			list.forEach(s -> sb.append(s + "\n"));

			doc.remove(start, len);
			doc.insertString(start, sb.toString(), null);
		} catch (BadLocationException ignorable) {
		}
	}

	/**
	 * Implementation of exit action.
	 */
	protected void exitAction() {
		Iterator<SingleDocumentModel> it = tabModel.iterator();
		while (it.hasNext()) {
			SingleDocumentModel doc = it.next();
			if (doc.isModified()) {
				Path path = doc.getFilePath();
				jOptionSave(flp.getString("confirm_question") + (path == null ? "(unnamed)" : path.getFileName()),
						flp.getString("save"), true);
			}
		}
		clock.setStopRequested(true);
		tabModel.removeMultipleDocumentListener(listener);
		dispose();
	}

	/**
	 * Implementation of stats action.
	 */
	protected void statsAction() {
		JTextArea editor = tabModel.getCurrentDocument().getTextComponent();
		int numOfChars = editor.getText().length();
		int numNonBlanks = sumNonBlanks(editor.getText());
		int numOfLines = editor.getLineCount();

		String msg = flp.getString("doc_contains") + numOfChars + " " + flp.getString("characters") + ", "
				+ numNonBlanks + " " + flp.getString("non_blank") + " " + flp.getString("and") + " " + numOfLines + " "
				+ flp.getString("lines");

		JOptionPane.showMessageDialog(this, msg, flp.getString("information"), JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Counts non-blank characters.
	 * 
	 * @param text text.
	 * @return number of non-blank characters.
	 */
	private int sumNonBlanks(String text) {
		char[] chars = text.toCharArray();
		int counter = 0;
		for (int i = 0; i < chars.length; i++) {
			if (!Character.isWhitespace(chars[i])) {
				counter++;
			}
		}
		return counter;
	}

	/**
	 * Implementation of close action.
	 */
	protected void closeAction() {
		SingleDocumentModel model = tabModel.getCurrentDocument();

		if (model.isModified()) {
			Path path = model.getFilePath();
			if (!jOptionSave(flp.getString("confirm_question") + (path == null ? "(unnamed)" : path.getFileName()),
					flp.getString("save"), true)) {
				return;
			}
		}

		tabModel.closeDocument(model);
		if (tabModel.getNumberOfDocuments() == 0) {
			tabModel.createNewDocument();
		}
	}

	/**
	 * Implementation of save as action.
	 */
	protected void saveAsAction() {
		SingleDocumentModel model = tabModel.getCurrentDocument();

		JFileChooser jfc = new JFileChooser();
		jfc.setDialogTitle(flp.getString("save_document"));
		if (jfc.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) {
			JOptionPane.showMessageDialog(this, flp.getString("info_fail"), flp.getString("information"),
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		Path destination = jfc.getSelectedFile().toPath();

		if (Files.exists(destination)) {
			if (!jOptionSave(destination + " " + flp.getString("exist_msg"), flp.getString("save"), false)) {
				return;
			}
		}

		tabModel.saveDocument(model, destination);

		JOptionPane.showMessageDialog(this, flp.getString("info_msg"), flp.getString("information"),
				JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Implementation of save action.
	 */
	protected void saveAction() {
		SingleDocumentModel model = tabModel.getCurrentDocument();

		if (model.getFilePath() == null) {
			saveAsAction();
		} else {
			tabModel.saveDocument(model, null);
		}
	}

	/**
	 * Implementation of open action.
	 */
	protected void openAction() {
		JFileChooser jfc = new JFileChooser();
		jfc.setDialogTitle(flp.getString("open_file"));
		if (jfc.showOpenDialog(JNotepadPP.this) != JFileChooser.APPROVE_OPTION) {
			return;
		}

		Path filePath = jfc.getSelectedFile().toPath();
		if (!Files.isReadable(filePath)) {
			JOptionPane.showMessageDialog(this,
					flp.getString("file") + " " + filePath + " " + flp.getString("not_readable"),
					flp.getString("error"), JOptionPane.ERROR_MESSAGE);
			return;
		}
		tabModel.loadDocument(filePath);
	}

	/**
	 * Helper method used for interaction with user. 
	 * @param question question for user.
	 * @param title pane's title.
	 * @param save boolean flag which tells whether document needs to be saved or not.
	 * @return {@code true} if user wants to save, otherwise {@code false}.
	 */
	private boolean jOptionSave(String question, String title, boolean save) {
		int dialogResult = JOptionPane.showConfirmDialog(this, question, title, JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE);

		if (dialogResult == JOptionPane.CANCEL_OPTION || dialogResult == JOptionPane.CLOSED_OPTION) {
			return false;
		}

		if (dialogResult == JOptionPane.YES_OPTION) {
			if (save) {
				saveAction();
			}
		}

		return true;
	}
	
	/**
	 * Helper method used for initializing GUI.
	 */
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		cp.add(tabModel, BorderLayout.CENTER);
		createActions();
		setJMenuBar(createMenus());
		cp.add(createToolbar(), BorderLayout.PAGE_START);
		cp.add(createStatusBar(), BorderLayout.PAGE_END);

		tabModel.createNewDocument();
		updateStatus(tabModel.getCurrentDocument().getTextComponent());
	}

	/**
	 * Creates a status bar.
	 * @return new status bar.
	 */
	private Component createStatusBar() {
		JPanel statusPanel = new JPanel(new GridLayout(0, 5));

		statusPanel.add(length);

		statusPanel.add(new JSeparator(JSeparator.VERTICAL));

		statusPanel.add(info);

		statusPanel.add(new JSeparator(JSeparator.VERTICAL));

		clock.setHorizontalAlignment(JLabel.RIGHT);
		statusPanel.add(clock);

		statusPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 3));
		return statusPanel;
	}

	/**
	 * Initialization of all actions.
	 */
	private void createActions() {
		createFileActions();
		createEditActions();
		createLanguagesActions();
		createToolsActions();
	}

	/**
	 * Creates all <i>File</i> actions.
	 */
	private void createFileActions() {
		// new document action
		newDocAction.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control N")
		);
		newDocAction.putValue(
				Action.MNEMONIC_KEY, 
				KeyStroke.getKeyStroke(flp.getString("new_mnemonic")).getKeyCode()
		);
		newDocAction.putValue(
				Action.SHORT_DESCRIPTION, 
				flp.getString("new_descr")
		);

		// open file action
		openAction.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control O")
		);
		openAction.putValue(
				Action.MNEMONIC_KEY, 
				KeyStroke.getKeyStroke(flp.getString("open_mnemonic")).getKeyCode()
		);
		openAction.putValue(
				Action.SHORT_DESCRIPTION, 
				flp.getString("open_descr")
		);

		// save file action
		saveAction.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control S")
		);
		saveAction.putValue(
				Action.MNEMONIC_KEY, 
				KeyStroke.getKeyStroke(flp.getString("save_mnemonic")).getKeyCode()
		);
		saveAction.putValue(
				Action.SHORT_DESCRIPTION, 
				flp.getString("save_descr")
		);

		// save file as action
		saveAsAction.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control alt S")
		);
		saveAsAction.putValue(
				Action.MNEMONIC_KEY,
				KeyStroke.getKeyStroke(flp.getString("save_as_mnemonic")).getKeyCode()
		);
		saveAsAction.putValue(
				Action.SHORT_DESCRIPTION, 
				flp.getString("save_as_descr")
		);

		// stats action
		statsAction.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control alt I")
		);
		statsAction.putValue(
				Action.MNEMONIC_KEY, 
				KeyStroke.getKeyStroke(flp.getString("stats_mnemonic")).getKeyCode()
		);
		statsAction.putValue(
				Action.SHORT_DESCRIPTION, 
				flp.getString("stats_descr")
		);

		// close action
		closeAction.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control W")
		);
		closeAction.putValue(
				Action.MNEMONIC_KEY, 
				KeyStroke.getKeyStroke(flp.getString("close_mnemonic")).getKeyCode()
		);
		closeAction.putValue(
				Action.SHORT_DESCRIPTION, 
				flp.getString("close_descr")
		);

		// exit action
		exitAction.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("alt F4")
		);
		exitAction.putValue(
				Action.MNEMONIC_KEY, 
				KeyStroke.getKeyStroke(flp.getString("exit_mnemonic")).getKeyCode()
		);
		exitAction.putValue(
				Action.SHORT_DESCRIPTION, 
				flp.getString("exit_descr")
		);
	}

	/**
	 * Creates all <i>Edit</i> actions.
	 */
	private void createEditActions() {
		// cut action
		cutAction.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control X")
		);
		cutAction.putValue(
				Action.MNEMONIC_KEY, 
				KeyStroke.getKeyStroke(flp.getString("cut_mnemonic")).getKeyCode()
		);
		cutAction.putValue(
				Action.SHORT_DESCRIPTION, 
				flp.getString("cut_descr")
		);

		// copy action
		copyAction.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control C")
		);
		copyAction.putValue(
				Action.MNEMONIC_KEY, 
				KeyStroke.getKeyStroke(flp.getString("copy_mnemonic")).getKeyCode()
		);
		copyAction.putValue(
				Action.SHORT_DESCRIPTION, 
				flp.getString("copy_descr")
		);

		// paste action
		pasteAction.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control V")
				);
		pasteAction.putValue(
				Action.MNEMONIC_KEY, 
				KeyStroke.getKeyStroke(flp.getString("paste_mnemonic")).getKeyCode()
		);
		pasteAction.putValue(
				Action.SHORT_DESCRIPTION, 
				flp.getString("paste_descr")
		);
	}
	
	/**
	 * Creates all <i>Languages</i> actions.
	 */
	private void createLanguagesActions() {
		hrAction.putValue(
				Action.MNEMONIC_KEY, 
				KeyStroke.getKeyStroke(flp.getString("hr_mnemonic")).getKeyCode()
		);
		
		enAction.putValue(
				Action.MNEMONIC_KEY, 
				KeyStroke.getKeyStroke(flp.getString("en_mnemonic")).getKeyCode()
		);
		
		deAction.putValue(
				Action.MNEMONIC_KEY, 
				KeyStroke.getKeyStroke(flp.getString("de_mnemonic")).getKeyCode()
		);
	}
	
	/**
	 * Creates <i>Tools</i> actions.
	 */
	private void createToolsActions() {
		upperAction.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control U")
		);
		upperAction.putValue(
				Action.MNEMONIC_KEY,
				KeyStroke.getKeyStroke(flp.getString("uppercase_mnemonic")).getKeyCode()
		);
		upperAction.putValue(
				Action.SHORT_DESCRIPTION, 
				flp.getString("uppercase_descr")
		);

		lowerAction.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control L")
		);
		lowerAction.putValue(
				Action.MNEMONIC_KEY,
				KeyStroke.getKeyStroke(flp.getString("lowercase_mnemonic")).getKeyCode()
		);
		lowerAction.putValue(
				Action.SHORT_DESCRIPTION, 
				flp.getString("lowercase_descr")
		);

		invertAction.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control I")
		);
		invertAction.putValue(
				Action.MNEMONIC_KEY,
				KeyStroke.getKeyStroke(flp.getString("invert_mnemonic")).getKeyCode()
		);
		invertAction.putValue(
				Action.SHORT_DESCRIPTION, 
				flp.getString("invert_descr")
				);

		ascAction.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control 1")
		);
		ascAction.putValue(
				Action.MNEMONIC_KEY, 
				KeyStroke.getKeyStroke(flp.getString("asc_mnemonic")).getKeyCode()
		);
		ascAction.putValue(
				Action.SHORT_DESCRIPTION, 
				flp.getString("asc_descr")
		);

		descAction.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control 2")
		);
		descAction.putValue(
				Action.MNEMONIC_KEY, 
				KeyStroke.getKeyStroke(flp.getString("desc_mnemonic")).getKeyCode()
		);
		descAction.putValue(
				Action.SHORT_DESCRIPTION, 
				flp.getString("desc_descr")
		);

		uniqueAction.putValue(
				Action.ACCELERATOR_KEY, 
				KeyStroke.getKeyStroke("control 3")
		);
		uniqueAction.putValue(
				Action.MNEMONIC_KEY,
				KeyStroke.getKeyStroke(flp.getString("unique_mnemonic")).getKeyCode()
		);
		uniqueAction.putValue(
				Action.SHORT_DESCRIPTION, 
				flp.getString("unique_descr")
				);
	}
	
	/**
	 * Creates all menus.
	 * 
	 * @return new {@link JMenuBar}.
	 */
	private JMenuBar createMenus() {
		JMenuBar mb = new JMenuBar();

		mb.add(fileMenuBar());
		mb.add(editMenuBar());
		mb.add(languagesMenuBar());
		mb.add(toolsMenuBar());

		return mb;

	}

	/**
	 * Creates <i>File</i> menu.
	 * 
	 * @return new {@link JMenu}.
	 */
	private JMenu fileMenuBar() {
		JMenu file = new LJMenu("file", flp);

		file.add(new JMenuItem(newDocAction));
		file.add(new JMenuItem(openAction));
		file.add(new JMenuItem(saveAction));
		file.add(new JMenuItem(saveAsAction));

		file.addSeparator();

		file.add(new JMenuItem(statsAction));

		file.addSeparator();

		file.add(new JMenuItem(closeAction));
		file.add(new JMenuItem(exitAction));
		return file;
	}

	/**
	 * Creates <i>Edit</i> menu.
	 * 
	 * @return new {@link JMenu}.
	 */
	private JMenu editMenuBar() {
		JMenu edit = new LJMenu("edit", flp);
		edit.add(new JMenuItem(cutAction));
		edit.add(new JMenuItem(copyAction));
		edit.add(new JMenuItem(pasteAction));
		return edit;
	}

	/**
	 * Creates <i>Languages</i> menu.
	 * 
	 * @return new {@link JMenu}.
	 */
	private JMenu languagesMenuBar() {
		JMenu languages = new LJMenu("languages", flp);
		languages.add(new JMenuItem(hrAction));
		languages.add(new JMenuItem(enAction));
		languages.add(new JMenuItem(deAction));
		return languages;
	}

	/**
	 * Creates <i>Tools</i> menu.
	 * 
	 * @return new {@link JMenu}.
	 */
	private JMenu toolsMenuBar() {
		JMenu tools = new LJMenu("tools", flp);

		JMenu changeCase = new LJMenu("change_case", flp);
		changeCase.add(new JMenuItem(upperAction));
		changeCase.add(new JMenuItem(lowerAction));
		changeCase.add(new JMenuItem(invertAction));

		JMenu sort = new LJMenu("sort", flp);
		sort.add(ascAction);
		sort.add(descAction);

		tools.add(changeCase);
		tools.add(sort);
		tools.add(new JMenuItem(uniqueAction));
		return tools;
	}

	/**
	 * Helper method used for creating {@link JToolBar}.
	 * 
	 * @return new {@link JToolBar}.
	 */
	private Component createToolbar() {
		JToolBar tb = new JToolBar();
		tb.setFloatable(true);

		tb.add(new JButton(newDocAction));
		tb.add(new JButton(openAction));
		tb.add(new JButton(saveAction));
		tb.add(new JButton(saveAsAction));

		tb.addSeparator();

		tb.add(new JButton(statsAction));

		tb.addSeparator();

		tb.add(new JButton(cutAction));
		tb.add(new JButton(copyAction));
		tb.add(new JButton(pasteAction));

		tb.addSeparator();

		tb.add(new JButton(closeAction));
		tb.add(new JButton(exitAction));

		return tb;
	}

	/**
	 * Main entry of this application.
	 * 
	 * @param args args.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new JNotepadPP().setVisible(true);
		});
	}
}
