package searching.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import searching.algorithms.Node;
import searching.algorithms.SearchUtil;
import searching.slagalica.KonfiguracijaSlagalice;
import searching.slagalica.Slagalica;
import searching.slagalica.gui.SlagalicaViewer;

/**
 * Demo program for example of {@link Slagalica}. Here we use gui illustration
 * of this problem and argument( as valid digits - {@link #VALID_DIGITS} ) are
 * given through command line and they need to be distinct.
 * 
 * @author dbrcina
 *
 */
public class SlagalicaMain {

	/**
	 * Constant representing valid digits. There are 9 valid digits, from 0 to 8.
	 */
	static final int[] VALID_DIGITS = new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8 };

	/**
	 * Main method
	 * 
	 * @param args arguments given through command line.
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("One argument is expected through command line!\nExiting..");
			System.exit(-1);
		}

		Slagalica slagalica = new Slagalica(new KonfiguracijaSlagalice(parseInput(args)));
		Node<KonfiguracijaSlagalice> rješenje = SearchUtil.bfsv(slagalica, slagalica, slagalica);
		if (rješenje == null) {
			System.out.println("Nisam uspio pronaći rješenje.");
		} else {
			System.out.println("Imam rješenje. Broj poteza je: " + rješenje.getCost());
			List<KonfiguracijaSlagalice> lista = new ArrayList<>();
			Node<KonfiguracijaSlagalice> trenutni = rješenje;
			while (trenutni != null) {
				lista.add(trenutni.getState());
				trenutni = trenutni.getParent();
			}
			Collections.reverse(lista);
			lista.stream().forEach(k -> {
				System.out.println(k);
				System.out.println();
			});
			SlagalicaViewer.display(rješenje);
		}
	}

	/**
	 * Parser method used for parsing given <code>args</code> input through command
	 * line.
	 * 
	 * @param args arguments.
	 * @return int array of provided digits.
	 */
	private static int[] parseInput(String[] args) {
		String[] digits = args[0].split("");
		int[] configuration = new int[9];
		try {
			for (int i = 0; i < digits.length; i++) {
				configuration[i] = Integer.parseInt(digits[i]);
			}
		} catch (NumberFormatException e) {
			System.out.println("Cannot parse given input into numbers!");
			System.exit(-1);
		}

		int[] helper = Arrays.copyOf(configuration, configuration.length);
		Arrays.sort(helper);
		if (!Arrays.equals(VALID_DIGITS, helper)) {
			System.out.println("Invalid digits! Expecting distinct numbers from: " + Arrays.toString(VALID_DIGITS));
		}
		return configuration;
	}
}
