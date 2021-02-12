package hr.fer.zemris.java.pred06;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Ekstenzije2 {

	static class Rezultat {
		public Map<String, Integer> mapa = new HashMap<>();
		public File stazaDoNajveceDatoteke;
		private long velicinaNajveceDatoteke = -1;
	}

	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Dragi korisiče...");
			return;
		}

		File staza = new File(args[0]);

		Rezultat rezultat = brojiEkstenzije2(staza);

		Comparator<Map.Entry<String, Integer>> poBrojevima = (p1, p2) -> p2.getValue().compareTo(p1.getValue());
		Comparator<Map.Entry<String, Integer>> poEkstenzijama = (p1, p2) -> p1.getKey().compareTo(p2.getKey());

		List<Map.Entry<String, Integer>> lista = new ArrayList<>(rezultat.mapa.entrySet());

		lista.sort(poBrojevima.thenComparing(poEkstenzijama));

		for (Map.Entry<String, Integer> par : lista) {
			System.out.printf("%s => %d%n", par.getKey(), par.getValue());
		}

		System.out.println("Staza do najveće datoteke je " + rezultat.velicinaNajveceDatoteke);
	}

	public static Rezultat brojiEkstenzije2(File staza) {
		if (!staza.isDirectory()) {
			throw new RuntimeException("Staza mora biti direktorij!");
		}
		Rezultat rezultat = new Rezultat();
		brojiEkstenzijeRekurzivno(staza, rezultat);
		return rezultat;
	}

	private static void brojiEkstenzijeRekurzivno(File dir, Rezultat rezultat) {
		File[] djeca = dir.listFiles();
		if (djeca == null) {
			return;
		}

		for (File f : djeca) {
			if (f.isDirectory()) {
				brojiEkstenzijeRekurzivno(f, rezultat);
			} else if (f.isFile()) {
				String ekstenzija = odrediEkstenziju(f.getName());
				rezultat.mapa.compute(ekstenzija, (fileEkstenzija, count) -> count == null ? 1 : count + 1);
				// rezultat.mapa.merge(ekstenzija, 1, (v, i) -> v + i);

				long velicina = f.length();
				if (velicina > rezultat.velicinaNajveceDatoteke) {
					rezultat.velicinaNajveceDatoteke = velicina;
					rezultat.stazaDoNajveceDatoteke = f;
				}
			}
		}
	}

	private static String odrediEkstenziju(String name) {
		int index = name.lastIndexOf('.');
		if (index <= 0) {
			return "";
		}
		return name.substring(index + 1).toLowerCase();
	}
}
