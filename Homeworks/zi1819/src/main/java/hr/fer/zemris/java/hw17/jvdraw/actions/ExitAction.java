package hr.fer.zemris.java.hw17.jvdraw.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw17.jvdraw.JVDraw;
import hr.fer.zemris.java.hw17.jvdraw.model.DrawingModel;

/**
 * An implementation of <b><i>EXIT</i></b> action from programs menu.
 * 
 * <p>
 * When this action is invoked, current opened file is checked for
 * modifications. If there were some modifications, user will be asked about
 * saving changes before exiting the program.
 * </p>
 * 
 * @author dbrcina
 *
 */
public class ExitAction extends AbstractAction {

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
	public ExitAction(JVDraw jvDraw) {
		this.jvDraw = jvDraw;
		initAction();
	}

	/**
	 * Helper method used for initialization <b>this</b> actions <i>Action map</i>.
	 */
	private void initAction() {
		putValue(Action.NAME, "Exit");
		putValue(Action.SHORT_DESCRIPTION, "Exits the program");
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control W"));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		DrawingModel model = jvDraw.getModel();
		if (!model.isModified()) {
			jvDraw.dispose();
			return;
		}

		int answer = JOptionPane.showConfirmDialog(
				jvDraw,
				"Current file has been modified.\nDo you want to save it before closing it?",
				"Save",
				JOptionPane.YES_NO_CANCEL_OPTION, 
				JOptionPane.QUESTION_MESSAGE
		);
		if (answer == JOptionPane.CANCEL_OPTION) {
			return;
		}
		if (answer == JOptionPane.YES_OPTION) {
			jvDraw.getSaveAction().actionPerformed(e);
		}

		jvDraw.dispose();
	}

}
