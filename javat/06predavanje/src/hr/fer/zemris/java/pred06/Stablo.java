package hr.fer.zemris.java.pred06;

import java.io.File;

public class Stablo {

	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Dragi korisiče...");
			return;
		}

		File staza = new File(args[0]);

		ispisiStablo(staza);
	}

	public static void ispisiStablo(File staza) {
		if (!staza.isDirectory()) {
			throw new RuntimeException("Staza mora biti direktorij!");
		}

		ispisiStabloRekurzivno(staza, 0);
	}

	private static void ispisiStabloRekurzivno(File dir, int razina) {
		System.out.println(" ".repeat(razina * 2) + (razina == 0 ? dir.getAbsolutePath() : dir.getName()));

		File[] djeca = dir.listFiles();
		if (djeca == null) {
			return;
		}

		for (File f : djeca) {
			if (f.isDirectory()) {
				ispisiStabloRekurzivno(f, razina + 1);
			} else if (f.isFile()) {
				System.out.println(" ".repeat((razina + 1) * 2) + f.getName());
			}
		}

	}

}
