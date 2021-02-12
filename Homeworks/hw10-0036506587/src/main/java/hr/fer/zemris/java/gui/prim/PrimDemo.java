package hr.fer.zemris.java.gui.prim;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Program that simulates dynamic generation of prim numbers. Result is shown
 * through <i>GUI</i>.
 * 
 * @author dbrcina
 *
 */
public class PrimDemo extends JFrame {

	/**
	 * Default serial ID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public PrimDemo() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Prim numbers");
		setSize(500, 300);
		setLocation(500, 250);
		initGUI();
	}

	/**
	 * Helper method used for initialization.
	 */
	private void initGUI() {
		PrimListModel model = new PrimListModel();

		JPanel panel = new JPanel(new GridLayout(0, 2));
		panel.add(new JScrollPane(new JList<>(model)));
		panel.add(new JScrollPane(new JList<>(model)));

		JButton button = new JButton("sljedeći");
		button.addActionListener(l -> {
			model.next();
		});

		add(panel);
		add(button, BorderLayout.PAGE_END);
	}

	/**
	 * Main entry of this program.
	 * 
	 * @param args arguments given through command line.
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new PrimDemo().setVisible(true));
	}
}
