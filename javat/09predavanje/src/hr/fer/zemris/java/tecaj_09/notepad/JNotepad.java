package hr.fer.zemris.java.tecaj_09.notepad;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

public class JNotepad extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextArea editor;
	private Path openedFilePath;

	public JNotepad() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setLocation(10, 10);
		setSize(500, 500);

		initGUI();
	}

	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());

		editor = new JTextArea();
		cp.add(new JScrollPane(editor), BorderLayout.CENTER);

		editor.getCaret().addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				boolean imaSelekecije = editor.getCaret().getDot() != editor.getCaret().getMark();
				toggleSelectedPart.setEnabled(imaSelekecije);
				deleteSelectedPart.setEnabled(imaSelekecije);
			}
		});
		
		createActions();
		createMenus();
		cp.add(createToolbar(),BorderLayout.PAGE_START);
	}

	// ResourcesBundle
	
	// prijevodi_hr.properties
	// main_open = Otvori
	// main_save = Spremi
	// main_save_as = Spremi kao ...
	
	// prijevodi_en.properties
	// main_open = Open
	// main_save = Save
	// main_save_as = Save As ...
	
	//Translator tr = new Translator("hr");
	
	private void createActions() {
		openDocument.putValue(Action.NAME, "Open");
		openDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
		openDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
		openDocument.putValue(Action.SHORT_DESCRIPTION, "Open document from disk.");
		
		saveDocument.putValue(Action.NAME, "Save");
		saveDocument.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
		saveDocument.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
		saveDocument.putValue(Action.SHORT_DESCRIPTION, "Save document from disk.");
		
		deleteSelectedPart.putValue(Action.NAME, "Delete selected part");
		deleteSelectedPart.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("F2"));
		deleteSelectedPart.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_D);
		deleteSelectedPart.putValue(Action.SHORT_DESCRIPTION, "Delete selection from document if selection exists.");
		deleteSelectedPart.setEnabled(false);
		
		toggleSelectedPart.putValue(Action.NAME, "Toggle case in selection");
		toggleSelectedPart.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control F3"));
		toggleSelectedPart.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_T);
		toggleSelectedPart.putValue(Action.SHORT_DESCRIPTION, "Toggles character casing in selection if selection exists.");
		toggleSelectedPart.setEnabled(false);
		
		exitAction.putValue(Action.NAME, "Exit");
		exitAction.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control Q"));
		exitAction.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);
		exitAction.putValue(Action.SHORT_DESCRIPTION, "Exists editor");
	}

	private void createMenus() {
		JMenuBar mb = new JMenuBar();
		
		JMenu file = new JMenu("File");
		file.add(new JMenuItem(openDocument));
		file.add(new JMenuItem(saveDocument));
		file.addSeparator();
		file.add(new JMenuItem(exitAction));
		
		JMenu edit = new JMenu("Edit");
		edit.add(new JMenuItem(deleteSelectedPart));
		edit.add(new JMenuItem(toggleSelectedPart));
		
		mb.add(file);
		mb.add(edit);
		
		setJMenuBar(mb);
	}

	private JToolBar createToolbar() {
		JToolBar tb = new JToolBar();
		tb.setFloatable(true);
		
		tb.add(new JButton(openDocument));
		tb.add(new JButton(saveDocument));
		tb.add(new JButton(exitAction));
		tb.add(new JButton(toggleSelectedPart));
		
		return tb;
	}

	private final Action openDocument = new AbstractAction() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser jfc = new JFileChooser();
			jfc.setDialogTitle("Open file");
			if (jfc.showOpenDialog(JNotepad.this) != JFileChooser.APPROVE_OPTION) {
				return;
			}
			
			Path filePath = jfc.getSelectedFile().toPath();
			if (!Files.isReadable(filePath)) {
				JOptionPane.showMessageDialog(
						JNotepad.this, 
						"Datoteku " + filePath + " nije moguće čitati", 
						"Pogreška",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			String text = null;
			try {
				text = Files.readString(filePath);
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(
						JNotepad.this, 
						"Došlo je do pogreške pri čitanju datoteke " + filePath, 
						"Pogreška",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			openedFilePath = filePath;
			editor.setText(text);
//			int len = editor.getDocument().getLength();
//			editor.getDocument().remove(0, len);
//			editor.getDocument().insertString(0, text, null);
		}
	};

	private final Action saveDocument = new AbstractAction() {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			if (openedFilePath == null) {
				JFileChooser jfc = new JFileChooser();
				jfc.setDialogTitle("Save document");
				if (jfc.showSaveDialog(JNotepad.this) != JFileChooser.APPROVE_OPTION) {
					JOptionPane.showMessageDialog(
							JNotepad.this, 
							"Ništa nije spremljeno.", 
							"Informacija", 
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
				openedFilePath = jfc.getSelectedFile().toPath();
			}
			
			try {
				Files.writeString(openedFilePath, editor.getText());
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(
						JNotepad.this, 
						"Dogodila se greška pri spremanju!", 
						"Pogreška", 
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			JOptionPane.showMessageDialog(
					JNotepad.this, 
					"Uspješno se savealo.", 
					"Informacija", 
					JOptionPane.INFORMATION_MESSAGE);
			//editor.getDocument().getText(0, editor.getDocument().getLength());
		}
	};
	
	private final Action deleteSelectedPart = new AbstractAction() {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				editor.getDocument().remove(
						Math.min(editor.getCaret().getDot(), editor.getCaret().getMark()), 
						Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark()));
			} catch (BadLocationException ignorable) {
			}
		}
	};
	
	private final Action toggleSelectedPart = new AbstractAction() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			int start = Math.min(editor.getCaret().getDot(), editor.getCaret().getMark());
			int len = Math.abs(editor.getCaret().getDot() - editor.getCaret().getMark());
			
			if (len < 1) {
				return;
			}
			
			Document doc = editor.getDocument();
			
			try {
			String text = doc.getText(start, len);
			text = toggleCase(text);
			
			doc.remove(start, len);
			doc.insertString(start, text, null);}
			catch (BadLocationException ignorable) {
			}
		}

		private String toggleCase(String text) {
			char[] chars = text.toCharArray();
			for (int i = 0; i < chars.length; i++) {
				if (Character.isUpperCase(chars[i])) {
					chars[i] = Character.toLowerCase(chars[i]);
				} else if (Character.isLowerCase(chars[i])) {
					chars[i] = Character.toUpperCase(chars[i]);
				}
			}
			return new String(chars);
		}
	};
	
	private final Action exitAction = new AbstractAction() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			dispose();
		}
	};
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new JNotepad().setVisible(true));
	}
}
