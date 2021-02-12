package hr.fer.zemris.tecaj.swing.p03;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.ListDataListener;

public class Prozor2 extends JFrame {

	private static final long serialVersionUID = 1L;

	public Prozor2() {
		super();
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Prozor1");
		setLocation(20, 20);
		setSize(500, 200);
		initGUI();
	}

	private void initGUI() {
		getContentPane().setLayout(new GridLayout(1,2));
		MojModelKvadrataBrojeva model = new MojModelKvadrataBrojeva();
		JList<Integer> prikazListe = new JList<>(model);
		JList<Integer> prikazListe2 = new JList<>(model);

		getContentPane().add(new JScrollPane(prikazListe));
		getContentPane().add(new JScrollPane(prikazListe2));
	}

	static class MojModelKvadrataBrojeva implements ListModel<Integer> {
		@Override
		public int getSize() {
			return 100;
		}

		@Override
		public Integer getElementAt(int index) {
			return (index + 1) * (index + 1);
		}

		@Override
		public void addListDataListener(ListDataListener l) {
			System.out.println("Netko se registrirao.");
		}

		@Override
		public void removeListDataListener(ListDataListener l) {
			// TODO Auto-generated method stub

		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new Prozor2().setVisible(true);
		});
//		
//		SwingUtilities.invokeLater(new Runnable() {
//			@Override
//			public void run() {
//				Prozor1 prozor = new Prozor1();
//				prozor.setVisible(true);
//			}
//		});
	}
}
