package hr.fer.zemris.java.hw17.jvdraw.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;

import hr.fer.zemris.java.hw17.jvdraw.JVDraw;
import hr.fer.zemris.java.hw17.jvdraw.model.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.util.Util;

/**
 * An implementation of <b><i>SAVEAS</i></b> action from programs menu.
 * 
 * @author dbrcina
 *
 */
public class SaveAsAction extends AbstractAction {

	/**
	 * Default serial ID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Reference to main frame.
	 */
	private JVDraw jvDraw;

	/**
	 * Constructor used for initialization.
	 * 
	 * @param jvDraw main frame.
	 */
	public SaveAsAction(JVDraw jvDraw) {
		this.jvDraw = jvDraw;
		initAction();

	}

	/**
	 * Helper method used for initialization <b>this</b> actions <i>Action map</i>.
	 */
	private void initAction() {
		putValue(Action.NAME, "Save As...");
		putValue(Action.SHORT_DESCRIPTION, "Saves current file under specified location and name");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control alt S"));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		DrawingModel model = jvDraw.getModel();
		String objects = Util.prepareObjects(model);

		Util.getChooser().setFileFilter(new FileNameExtensionFilter("JVDraw files(jvd)", "jvd"));
		Path destination = Util.getSavePath(jvDraw);
		jvDraw.setPath(destination);

		try {
			Files.writeString(destination, objects);
		} catch (IOException ignorable) {
		}

		model.clearModifiedFlag();
		jvDraw.getSaveAction().setEnabled(false);
	}

}
