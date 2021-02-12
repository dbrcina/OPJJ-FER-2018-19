package hr.fer.zemris.java.pred06;

import java.io.File;

public class Obilazak {

	public static void obidi(File dir, Obrada obrada) {
		if (!dir.isDirectory()) {
			throw new RuntimeException("Staza mora biti direktorij!");
		}
		obidiRekurzivno(dir, obrada);
	}

	private static void obidiRekurzivno(File dir, Obrada obrada) {
		File[] djeca = dir.listFiles();
		if (djeca == null) {
			return;
		}

		obrada.ulazimUDirektorij(dir);
		for (File f : djeca) {
			if (f.isDirectory()) {
				obidiRekurzivno(f, obrada);
			} else if (f.isFile()) {
				obrada.imamDatoteku(f);
			}
		}
		obrada.izlazimIzDirektorija(dir);
	}
}
